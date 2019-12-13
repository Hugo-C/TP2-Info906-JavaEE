package fr.usmb.m2isc.mesure.bean;

import fr.usmb.m2isc.mesure.ejb.AgencyEJB;
import fr.usmb.m2isc.mesure.ejb.ColumnEJB;
import fr.usmb.m2isc.mesure.jpa.Agency;
import fr.usmb.m2isc.mesure.jpa.BacklogItem;
import fr.usmb.m2isc.mesure.jpa.ColumnItem;
import fr.usmb.m2isc.mesure.servlet.CookieHelper;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ManagedBean(name="columnBean")
@RequestScoped
public class ColumnBean {

    private FacesContext context = FacesContext.getCurrentInstance();

    @EJB
    ColumnEJB columnEJB;
    @EJB
    AgencyEJB agencyEJB;

    private HashMap<ColumnItem, ArrayList<BacklogItem>> columnsMap;
    private ArrayList<ColumnItem> columns;

    private String columnName;
    private String columnToRemove;
    private String columnToRename;
    private String newColumnName;
    private String nextColumnName;
    private String columnToMove;
    private String nextColumnNameToMove;

    public ColumnBean() {

    }

    @PostConstruct
    public void init() {
        columnsMap = columnEJB.findAllBacklogItemByColumn();
        Cookie usernameCookie = CookieHelper.getCookie("agencySelected");  // expire after web browser close
        String agency = usernameCookie.getValue();
        columns = columnEJB.findAllColumnsSorted(agency);
    }

    public String addColumn(){
        try
        {
            Cookie usernameCookie = CookieHelper.getCookie("agencySelected");  // expire after web browser close
            Agency agency = agencyEJB.findAgencyByName(usernameCookie.getValue());

            if (columnName.equals("")) columnName = "Nouvelle colonne";
            ColumnItem columnItem = new ColumnItem(columnName, agency);
            if (nextColumnName != null && !nextColumnName.equals("")) {
                for (ColumnItem c : columns){
                    if(c.getName().equals(nextColumnName))
                        columnItem.setPrevColumnItem(c);
                }
            }
            columnItem.setAgency(agency);
            columnEJB.addColumn(columnItem, agency.getName());
            return "display_columns.xhtml?faces-redirect=true";
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public String moveColumn(){

        Cookie usernameCookie = CookieHelper.getCookie("agencySelected");  // expire after web browser close
        String agency = usernameCookie.getValue();

        if (columnToMove != null && !columnToMove.isEmpty()){
            ColumnItem columnItem = columnEJB.findColumnByName(columnToMove);

            if (nextColumnNameToMove != null && !nextColumnNameToMove.equals("")) {
                for (ColumnItem c : columns) {
                    if (c.getName().equals(nextColumnNameToMove)) {
                        columnItem.setPrevColumnItem(c);

                        ColumnItem prevColumn = columnItem.getPrevColumnItem();
                        ColumnItem tmpColumn = prevColumn.getNextColumnItem();
                        prevColumn.setNextColumnItem(columnItem);

                        if (tmpColumn != null) {
                            columnItem.setNextColumnItem(tmpColumn);
                            tmpColumn.setPrevColumnItem(columnItem);
                            columnEJB.updateColumn(tmpColumn);
                        }
                        columnEJB.updateColumn(prevColumn);
                    }
                }
            }
            else {
                ArrayList<ColumnItem> columnsSorted = columnEJB.findAllColumnsSorted(agency);
                if(columnsSorted.size() > 0) {
                    columnItem.setNextColumnItem(columnsSorted.get(0));
                    columnsSorted.get(0).setPrevColumnItem(columnItem);
                    columnEJB.updateColumn(columnsSorted.get(0));
                }
            }
            columnEJB.updateColumn(columnItem);
        }
        return "display_columns.xhtml?faces-redirect=true";
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
                columnEJB.updateColumn(prev);
            }
            if(next != null){
                next.setPrevColumnItem(prev);
                columnEJB.updateColumn(next);
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

    public String getColumnToMove() {
        return columnToMove;
    }

    public void setColumnToMove(String columnToMove) {
        this.columnToMove = columnToMove;
    }

    public String getNextColumnNameToMove() {
        return nextColumnNameToMove;
    }

    public void setNextColumnNameToMove(String nextColumnNameToMove) {
        this.nextColumnNameToMove = nextColumnNameToMove;
    }
}
