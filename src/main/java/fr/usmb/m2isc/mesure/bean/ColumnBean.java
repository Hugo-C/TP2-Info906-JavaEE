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

    /* The list of all columns with associated backlog items */
    private HashMap<ColumnItem, ArrayList<BacklogItem>> columnsMap;
    /* The list of all columns of the agency */
    private ArrayList<ColumnItem> columns;

    /* The name of the new column that we want create */
    private String columnName;

    /* The name of the column that we want remove */
    private String columnToRemove;
    /* The name of the column which is before the new column that we want create */
    private String nextColumnName;

    /* The name of the column that we want rename */
    private String columnToRename;
    /* The new name of the column that we rename */
    private String newColumnName;

    /* The name of the column that we want move */
    private String columnToMove;
    /* The name of the column which is before the column that we want move */
    private String nextColumnNameToMove;

    /* The error message when we remove a column that contains items */
    private String errorMessage;

    /** The construct **/
    public ColumnBean() {

    }

    /** To init the bean **/
    @PostConstruct
    public void init() {
        columnsMap = columnEJB.findAllBacklogItemByColumn();
        Cookie usernameCookie = CookieHelper.getCookie("agencySelected");  // expire after web browser close
        String agency = usernameCookie.getValue();
        columns = columnEJB.findAllColumnsSorted(agency);
        errorMessage = "";
    }

    /** To create a column **/
    public String addColumn(){
        try
        {
            // We get the name of the agency
            Cookie usernameCookie = CookieHelper.getCookie("agencySelected");  // expire after web browser close
            Agency agency = agencyEJB.findAgencyByName(usernameCookie.getValue());

            // We create the column
            if (columnName.equals("")) columnName = "Nouvelle colonne";
            ColumnItem columnItem = new ColumnItem(columnName, agency);

            // We get the column which is before the new column
            if (nextColumnName != null && !nextColumnName.equals("")) {
                for (ColumnItem c : columns){
                    if(c.getName().equals(nextColumnName))
                        columnItem.setPrevColumnItem(c);
                }
            }

            //We add the column
            columnItem.setAgency(agency);
            columnEJB.addColumn(columnItem, agency.getName());
            return "display_columns.xhtml?faces-redirect=true";
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /** To move a column **/
    public String moveColumn(){

        // We get the name of the agency
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

    /** To get the array of all items of a column **/
    public ArrayList<BacklogItem> getBacklogItemByColumn(ColumnItem column){
        ArrayList<BacklogItem> backlogItems = columnsMap.get(column);
        if (backlogItems != null) return backlogItems;
        else return new ArrayList<>();
    }
/** To remove a column **/
    public String removeColumn(){
        if (columnToRemove != null){
            ColumnItem c = columnEJB.findColumnByName(columnToRemove);

            // We look at if the column contains items
            if (columnsMap.get(c) != null && columnsMap.get(c).size() > 0){
                errorMessage = "Votre colonne contient des items, donc elle ne peut pas être suppriemr !";
                return "display_columns.xhtml?faces-redirect=false";
            }

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

    /** To rename a column **/
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
