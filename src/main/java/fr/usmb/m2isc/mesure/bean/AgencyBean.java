package fr.usmb.m2isc.mesure.bean;

import fr.usmb.m2isc.mesure.ejb.AgencyEJB;
import fr.usmb.m2isc.mesure.ejb.ColumnEJB;
import fr.usmb.m2isc.mesure.jpa.Agency;
import fr.usmb.m2isc.mesure.jpa.ColumnItem;
import fr.usmb.m2isc.mesure.servlet.CookieHelper;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="agencyBean")
@RequestScoped
public class AgencyBean {

    private FacesContext context = FacesContext.getCurrentInstance();

    @EJB
    AgencyEJB agencyEJB;

    /* List of all agencies */
    private ArrayList<Agency> agencies;
    /* The name of the new agency that we create */
    private String agencyName;
    /* The selected agency */
    private String agencySelected;

    /** The construct **/
    public AgencyBean() {

    }

    /** To create a agency **/
    public String addAgency(){
        if (agencyName.equals("")) agencyName = "Nouvelle agence";
        Agency agency = new Agency(agencyName);
        agencyEJB.addAgency(agency);
        agencySelected = agencyName;
        return selectAgency();
    }

    /** To select a agency **/
    public String selectAgency(){
        CookieHelper.setCookie("agencySelected", agencySelected, -1);
        return "display_columns.xhtml?faces-redirect=true";
    }

    /** To init the bean **/
    @PostConstruct
    public void init() {
        agencies = new ArrayList<>(agencyEJB.findAllAgencies());
    }

    public ArrayList<Agency> getAgencies() {
        return agencies;
    }

    public void setAgencies(ArrayList<Agency> agencies) {
        this.agencies = agencies;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencySelected() {
        return agencySelected;
    }

    public void setAgencySelected(String agencySelected) {
        this.agencySelected = agencySelected;
    }
}
