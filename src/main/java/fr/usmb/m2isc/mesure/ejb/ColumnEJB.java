package fr.usmb.m2isc.mesure.ejb;

import fr.usmb.m2isc.mesure.bean.UserBean;
import fr.usmb.m2isc.mesure.jpa.BacklogItem;
import fr.usmb.m2isc.mesure.jpa.ColumnItem;
import fr.usmb.m2isc.mesure.servlet.CookieHelper;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Stateless
@LocalBean
public class ColumnEJB {
	@PersistenceContext
	private EntityManager em;


	/** The construct	**/
	public ColumnEJB() {
	}

	/** To add a new column **/
	public ColumnItem addColumn(ColumnItem c, String agency) {
		// We look at if the column has a previous column, if yes then we add the column at the beginning, else, we add the column between the others columns
		if (c.getPrevColumnItem() == null){
			ArrayList<ColumnItem> columnsSorted = findAllColumnsSorted(agency);
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

	/** To update a column **/
	public ColumnItem updateColumn(ColumnItem c){
	    return em.merge(c);
    }

    /** Return the column associated to the id **/
	public ColumnItem findColumn(long id) {
		return em.find(ColumnItem.class, id);
	}

	/** Return the column associated to the name **/
	public ColumnItem findColumnByName(String name) {
        return em.createQuery("SELECT c FROM ColumnItem c WHERE c.name LIKE :columnName", ColumnItem.class)
                                        .setParameter("columnName", name).getSingleResult();
    }

    /** Return the list of all columns associated to the agency **/
	public List<ColumnItem> findAllColumns(String agency) {
		return em.createQuery("SELECT c FROM ColumnItem c WHERE c.agency.name LIKE :agencyName", ColumnItem.class)
				.setParameter("agencyName", agency).getResultList();
	}

	/** Return the sorted array of all columns associated to the agency **/
	public ArrayList<ColumnItem> findAllColumnsSorted(String agency) {
		List<ColumnItem> columns = findAllColumns(agency);
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
		//assert columnsSorted.size() == columns.size();
		return columnsSorted;
	}

	/** Return the hash map of all columns with theirs associated items **/
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

	/** To remove a column **/
	public void removeColumn(ColumnItem c){
		if (!em.contains(c)) {
			c = em.merge(c);
		}
		em.remove(c);
	}
}
