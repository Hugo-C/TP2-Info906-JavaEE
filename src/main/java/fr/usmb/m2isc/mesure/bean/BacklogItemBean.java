package fr.usmb.m2isc.mesure.bean;

import fr.usmb.m2isc.mesure.ejb.AgencyEJB;
import fr.usmb.m2isc.mesure.ejb.BacklogItemEJB;
import fr.usmb.m2isc.mesure.ejb.ColumnEJB;
import fr.usmb.m2isc.mesure.jpa.Agency;
import fr.usmb.m2isc.mesure.jpa.BacklogItem;
import fr.usmb.m2isc.mesure.jpa.ColumnItem;
import fr.usmb.m2isc.mesure.servlet.CookieHelper;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import java.util.List;

@ManagedBean(name="backlogItemBean")
@RequestScoped
public class BacklogItemBean {

    private FacesContext context = FacesContext.getCurrentInstance();

    /* The new backlog item that we create */
    private BacklogItem backlogItem;
    /* The name of the column where we want add the backlog item */
    private String columnName;

    @EJB
    BacklogItemEJB backlogItemEJB;
    @EJB
    ColumnEJB columnEJB;
    @EJB
    AgencyEJB agencyEJB;

    /** The construct **/
    public BacklogItemBean() {
        backlogItem = new BacklogItem();
        columnName = "TODO";
    }

    /** To create a backlog item **/
    public String addBacklogItem(){
        try
        {
            // We get the name of the agency
            Cookie usernameCookie = CookieHelper.getCookie("agencySelected");  // expire after web browser close
            Agency agency = agencyEJB.findAgencyByName(usernameCookie.getValue());

            // We get the column or if it don't exists, we create it
            if (columnName.equals("")) columnName = "TODO";
            ColumnItem c;
            try{
                c = columnEJB.findColumnByName(columnName);
            }catch (Exception e){
                System.out.println("Column create");
                c = new ColumnItem(columnName, agency);
                columnEJB.addColumn(c, agency.getName());
            }

            // We add the backlog item
            backlogItem.setColumnItem(c);
            backlogItem = backlogItemEJB.addBacklogItem(backlogItem);

            List<BacklogItem> items = backlogItemEJB.findAllBacklogItem();
            System.out.println("number of pbi : " + items.size());
            System.out.println(backlogItem.toString());
            return "create_item.xhtml?faces-redirect=true";
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public BacklogItem getBacklogItem() {
        return backlogItem;
    }

    public void setBacklogItem(BacklogItem backlogItem) {
        this.backlogItem = backlogItem;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

}
