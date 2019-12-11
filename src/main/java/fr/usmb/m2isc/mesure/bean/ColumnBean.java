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
import java.util.List;

@ManagedBean(name="columnBean")
@RequestScoped
public class ColumnBean {

    private FacesContext context = FacesContext.getCurrentInstance();

    @EJB
    ColumnEJB columnEJB;

    private HashMap<ColumnItem, ArrayList<BacklogItem>> columnsMap;
    private ArrayList<ColumnItem> columns;

    private String columnName;
    private String nextColumnName;

    public ColumnBean() {

    }

    @PostConstruct
    public void init() {
        columnsMap = columnEJB.findAllBacklogItemByColumn();
        columns = columnEJB.findAllColumnsSorted();
    }

    public String addColumn(){
        try
        {
            if (columnName.equals("")) columnName = "Nouvelle colonne";
            ColumnItem columnItem = new ColumnItem(columnName);
            if (!nextColumnName.equals("")) {
                for (ColumnItem c : columns){
                    if(c.getName().equals(nextColumnName))
                        columnItem.setPrevColumnItem(c);
                }
            }
            columnEJB.addColumn(columnItem);
            return "display_columns.xhtml?faces-redirect=true";
        }
        catch(Exception e)
        {
            return null;
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

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getNextColumnName() {
        return nextColumnName;
    }

    public void setNextColumnName(String nextColumnName) {
        this.nextColumnName = nextColumnName;
    }
}
