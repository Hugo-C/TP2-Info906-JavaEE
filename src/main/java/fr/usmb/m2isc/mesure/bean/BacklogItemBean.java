package fr.usmb.m2isc.mesure.bean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import fr.usmb.m2isc.mesure.ejb.BacklogItemEJB;
import fr.usmb.m2isc.mesure.jpa.BacklogItem;

@ManagedBean(name="backlogItemBean")
@RequestScoped
public class BacklogItemBean {

    private FacesContext context = FacesContext.getCurrentInstance();

    private BacklogItem backlogItem;

    @EJB
    BacklogItemEJB backlogItemEJB;


    public String addBacklogItem(){
        try
        {
            System.out.println("wow");
            backlogItem.setPriority(1);
            backlogItem.setEstimation(1);
            backlogItem.setDescription("");
            backlogItem = backlogItemEJB.addBacklogItem(backlogItem);
            context.addMessage(null, new FacesMessage("Kullanıcı Başarı ile Eklendi..."));
            return "index.xhtml?faces-redirect=true";
        }
        catch(Exception e)
        {
            context.addMessage(null, new FacesMessage("Kullanıcı Eklenirken Hata Oluştu... \n "+e));
            return null;
        }
    }

//    public String deleteUser(Users getUser){
//        try
//        {
//            userEJB.deleteUsers(getUser);
//            userList = userEJB.allUsers();
//
//            context.addMessage(null, new FacesMessage("Kullanıcı Başarı ile Silindi..."));
//            return "index.xhtml?faces-redirect=true";
//        }
//        catch(Exception e)
//        {
//            context.addMessage(null, new FacesMessage("Kullanıcı Silinirken Hata Oluştu... \n "+e));
//            return null;
//        }
//    }


    public BacklogItem getBacklogItem() {
        return backlogItem;
    }

    public void setBacklogItem(BacklogItem backlogItem) {
        this.backlogItem = backlogItem;
    }
}
