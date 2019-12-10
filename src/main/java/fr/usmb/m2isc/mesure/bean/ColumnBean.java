package fr.usmb.m2isc.mesure.bean;

import fr.usmb.m2isc.mesure.ejb.ColumnEJB;
import fr.usmb.m2isc.mesure.jpa.BacklogItem;
import fr.usmb.m2isc.mesure.jpa.ColumnItem;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.HashMap;

@ManagedBean(name="columnBean")
@RequestScoped
public class ColumnBean {

    private FacesContext context = FacesContext.getCurrentInstance();

    @EJB
    ColumnEJB columnEJB;

    private HashMap<ColumnItem, ArrayList<BacklogItem>> columnsMap;
    private ArrayList<ColumnItem> columns;

    public ColumnBean() {

    }

    @PostConstruct
    public void init() {
        try{
            columnsMap = columnEJB.findAllBacklogItemByColumn();
            columns = new ArrayList<ColumnItem>();
            for (ColumnItem c : columnsMap.keySet()){
                columns.add(c);
            }
        }
        catch (Exception e){
        }
    }

    public ArrayList<BacklogItem> GetBacklogItemByColumn(ColumnItem column){
        ArrayList<BacklogItem> backlogItems = columnsMap.get(column);
        if (backlogItems != null) return backlogItems;
        else return new ArrayList<BacklogItem>();
    }

    public ArrayList<ColumnItem> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<ColumnItem> columns) {
        this.columns = columns;
    }
}