package fr.usmb.m2isc.mesure.bean;

import fr.usmb.m2isc.mesure.ejb.BacklogItemEJB;
import fr.usmb.m2isc.mesure.ejb.ColumnEJB;
import fr.usmb.m2isc.mesure.jpa.BacklogItem;
import fr.usmb.m2isc.mesure.jpa.ColumnItem;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean(name="backlogItemBean")
@RequestScoped
public class BacklogItemBean {

    private FacesContext context = FacesContext.getCurrentInstance();

    private BacklogItem backlogItem;

    private String columnName;

    @EJB
    BacklogItemEJB backlogItemEJB;
    @EJB
    ColumnEJB columnEJB;

    public BacklogItemBean() {
        backlogItem = new BacklogItem();
        columnName = "TODO";
    }


    public String addBacklogItem(){
        try
        {
            if (columnName.equals("")) columnName = "TODO";
            ColumnItem c;
            try{
                c = columnEJB.findColumnByName(columnName);
            }catch (Exception e){
                System.out.println("Column create");
                c = new ColumnItem(columnName);
                columnEJB.addColumn(c);
            }
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
