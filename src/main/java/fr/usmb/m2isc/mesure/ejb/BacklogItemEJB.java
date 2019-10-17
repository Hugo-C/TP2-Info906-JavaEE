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


	/**
	 * Constructeur sans parametre obligatoire
	 */
	public BacklogItemEJB() {
	}

	public BacklogItem addBacklogItem(BacklogItem backlogItem) {
		em.persist(backlogItem);
		return backlogItem;
	}

	public BacklogItem findBacklogItem(long id) {
		return em.find(BacklogItem.class, id);
	}

	public List<BacklogItem> findAllBacklogItem() {
		TypedQuery<BacklogItem> rq = em.createQuery("SELECT m FROM BacklogItem m ORDER BY m.priority DESC", BacklogItem.class);
		return rq.getResultList();
	}
}
