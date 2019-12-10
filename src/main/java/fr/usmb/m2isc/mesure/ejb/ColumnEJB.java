package fr.usmb.m2isc.mesure.ejb;

import fr.usmb.m2isc.mesure.jpa.BacklogItem;
import fr.usmb.m2isc.mesure.jpa.ColumnItem;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
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

	public ColumnItem addColumn(ColumnItem c) {
		em.persist(c);
		return c;
	}

	public ColumnItem findColumn(long id) {
		return em.find(ColumnItem.class, id);
	}

	public ColumnItem findColumnByName(String name) {
        return em.createQuery("SELECT c FROM ColumnItem c WHERE c.name LIKE :columnName", ColumnItem.class)
                                        .setParameter("columnName", name).getSingleResult();
    }

	public HashMap<ColumnItem, ArrayList<BacklogItem>> findAllBacklogItemByColumn() {
		TypedQuery<BacklogItem> rq = em.createQuery("SELECT m FROM BacklogItem m WHERE m.columnItem IS NOT NULL ORDER BY m.priority DESC", BacklogItem.class);
		List items =  rq.getResultList();
		HashMap<ColumnItem, ArrayList<BacklogItem>> res = new HashMap<>();
		for (Object item : items) {
			BacklogItem pbi = (BacklogItem) item;
			ArrayList<BacklogItem> backlogItems = res.get(pbi.getColumnItem());
			if( backlogItems == null){
				backlogItems = new ArrayList<>();
			}
			backlogItems.add(pbi);
			res.put(pbi.getColumnItem(), backlogItems);
		}
		return res;
	}
}
