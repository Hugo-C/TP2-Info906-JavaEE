package fr.usmb.m2isc.mesure.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.usmb.m2isc.mesure.jpa.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
@LocalBean
public class ItemEJB {
	@PersistenceContext
	private EntityManager em;


	/**
	 * Constructeur sans parametre obligatoire
	 */
	public ItemEJB() {
	}

	public Item addItem(String name, Date creationDate, int priority, int estimation, String description) {
		Item m = new Item(name, creationDate, priority, estimation, description);
		em.persist(m);
		return m;
	}

	public Item findItem(long id) {
		return em.find(Item.class, id);
	}

	public List<Item> findAllItem() {
		TypedQuery<Item> rq = em.createQuery("SELECT m FROM Item m ORDER BY m.priority DESC", Item.class);
		return rq.getResultList();
	}
}
