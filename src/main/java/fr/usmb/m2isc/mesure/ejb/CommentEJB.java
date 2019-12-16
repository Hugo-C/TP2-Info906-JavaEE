package fr.usmb.m2isc.mesure.ejb;

import fr.usmb.m2isc.mesure.jpa.BacklogItem;
import fr.usmb.m2isc.mesure.jpa.Comment;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@Remote
public class CommentEJB {
	@PersistenceContext
	private EntityManager em;


	public CommentEJB() {
	}

	public Comment addCommentary(Comment comment) {
		em.persist(comment);
		return comment;
	}

	public Comment findCommentary(long id) {
		return em.find(Comment.class, id);
	}

	public List<Comment> findAllCommentaryOfBacklogItem(BacklogItem item) {
		TypedQuery<Comment> rq = em.createQuery("SELECT c FROM Comment c WHERE c.backlogItem.id = :backlogItemId ORDER BY c.creationDate", Comment.class);
		rq.setParameter("backlogItemId", item.getId());
		return rq.getResultList();
	}
}
