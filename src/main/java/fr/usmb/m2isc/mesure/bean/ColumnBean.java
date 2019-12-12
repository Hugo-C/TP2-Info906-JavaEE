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
    private String columnToRemove;
    private String columnToRename;
    private String newColumnName;
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
            if (nextColumnName != null && !nextColumnName.equals("")) {
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

    public ArrayList<BacklogItem> getBacklogItemByColumn(ColumnItem column){
        ArrayList<BacklogItem> backlogItems = columnsMap.get(column);
        if (backlogItems != null) return backlogItems;
        else return new ArrayList<>();
    }

    public String removeColumn(){
        if (columnToRemove != null){
            ColumnItem c = columnEJB.findColumnByName(columnToRemove);
            ColumnItem prev = c.getPrevColumnItem();
            ColumnItem next = c.getNextColumnItem();

            if(prev != null){
                prev.setNextColumnItem(next);
            }
            if(next != null){
                next.setPrevColumnItem(prev);
            }
            columnEJB.removeColumn(c);
        }
        return "display_columns.xhtml?faces-redirect=true";
    }

    public String renameColumn(){
        if (columnToRename != null && newColumnName != null && !newColumnName.isEmpty()){
            ColumnItem c = columnEJB.findColumnByName(columnToRename);
            c.setName(newColumnName);
            columnEJB.updateColumn(c);
        }
        return "display_columns.xhtml?faces-redirect=true";
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

    public String getColumnToRemove() {
        return columnToRemove;
    }

    public void setColumnToRemove(String columnToRemove) {
        this.columnToRemove = columnToRemove;
    }

    public String getColumnToRename() {
        return columnToRename;
    }

    public void setColumnToRename(String columnToRename) {
        this.columnToRename = columnToRename;
    }

    public String getNewColumnName() {
        return newColumnName;
    }

    public void setNewColumnName(String newColumnName) {
        this.newColumnName = newColumnName;
    }
}
