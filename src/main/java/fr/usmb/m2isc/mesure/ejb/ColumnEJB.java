package fr.usmb.m2isc.mesure.ejb;

import fr.usmb.m2isc.mesure.jpa.BacklogItem;
import fr.usmb.m2isc.mesure.jpa.Column;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;

@Stateless
@LocalBean
public class ColumnEJB {
	@PersistenceContext
	private EntityManager em;


	/**
	 * Constructeur sans parametre obligatoire
	 */
	public ColumnEJB() {
	}

	public Column addColumn(Column c) {
		em.persist(c);
		return c;
	}

	public Column findColumn(long id) {
		return em.find(Column.class, id);
	}

	public HashMap<Column, BacklogItem> findAllBacklogItemByColumn() {
		TypedQuery<BacklogItem> rq = em.createQuery("SELECT m FROM BacklogItem m WHERE column IS NOT NULL ORDER BY m.priority DESC", BacklogItem.class);
		List items =  rq.getResultList();
		HashMap<Column, BacklogItem> res = new HashMap<>();
		for (Object item : items) {
			BacklogItem pbi = (BacklogItem) item;
			res.put(pbi.getColumn(), pbi);
		}
		return res;
	}
}
