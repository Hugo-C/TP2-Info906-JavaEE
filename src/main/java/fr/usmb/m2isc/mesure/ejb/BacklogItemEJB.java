package fr.usmb.m2isc.mesure.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.usmb.m2isc.mesure.jpa.BacklogItem;

import java.util.List;

@Stateless
@LocalBean
public class BacklogItemEJB {
	@PersistenceContext
	private EntityManager em;


	/** The construct **/
	public BacklogItemEJB() {
	}

	/** To add a new backlog item **/
	public BacklogItem addBacklogItem(BacklogItem backlogItem) {
		em.persist(backlogItem);
		return backlogItem;
	}

	/** To remove a backlog item **/
	public void removeBacklogItem(BacklogItem backlogItem) {
		if (!em.contains(backlogItem)) {
			backlogItem = em.merge(backlogItem);
		}
		em.remove(backlogItem);
	}

	/** Returns the backlog item associated to the id **/
	public BacklogItem findBacklogItem(long id) {
		return em.find(BacklogItem.class, id);
	}

	/** Returns the list of all backlog items **/
	public List<BacklogItem> findAllBacklogItem() {
		TypedQuery<BacklogItem> rq = em.createQuery("SELECT m FROM BacklogItem m ORDER BY m.priority DESC", BacklogItem.class);
		return rq.getResultList();
	}
}
