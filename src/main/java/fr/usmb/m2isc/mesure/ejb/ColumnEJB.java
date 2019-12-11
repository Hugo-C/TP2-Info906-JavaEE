package fr.usmb.m2isc.mesure.ejb;

import fr.usmb.m2isc.mesure.jpa.BacklogItem;
import fr.usmb.m2isc.mesure.jpa.ColumnItem;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Column;
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
		if (c.getPrevColumnItem() == null){
			ArrayList<ColumnItem> columnsSorted = findAllColumnsSorted();
			if(columnsSorted.size() > 0) {
                c.setNextColumnItem(columnsSorted.get(0));
                columnsSorted.get(0).setPrevColumnItem(c);
            }
		} else {
            ColumnItem prevColumn = c.getPrevColumnItem();
            ColumnItem tmpColumn = prevColumn.getNextColumnItem();
            prevColumn.setNextColumnItem(c);

            if(tmpColumn != null){
                c.setNextColumnItem(tmpColumn);
                tmpColumn.setPrevColumnItem(c);
                updateColumn(tmpColumn);
            }
            updateColumn(prevColumn);
        }
		em.persist(c);
		return c;
	}

	public ColumnItem updateColumn(ColumnItem c){
	    return em.merge(c);
    }

	public ColumnItem findColumn(long id) {
		return em.find(ColumnItem.class, id);
	}

	public ColumnItem findColumnByName(String name) {
        return em.createQuery("SELECT c FROM ColumnItem c WHERE c.name LIKE :columnName", ColumnItem.class)
                                        .setParameter("columnName", name).getSingleResult();
    }

	public List<ColumnItem> findAllColumns() {
		return em.createQuery("SELECT c FROM ColumnItem c", ColumnItem.class).getResultList();
	}

	public ArrayList<ColumnItem> findAllColumnsSorted() {
		List<ColumnItem> columns = findAllColumns();
		ArrayList<ColumnItem> columnsSorted = new ArrayList<>();

		ColumnItem columnItem = null;
		// retrieve the first column
		for (ColumnItem c : columns){
			if (c.getPrevColumnItem() == null){
				columnItem = c;
				break;
			}
		}
		while (columnItem != null) {
			columnsSorted.add(columnItem);
			columnItem = columnItem.getNextColumnItem();
		}
		assert columnsSorted.size() == columns.size();
		return columnsSorted;
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
