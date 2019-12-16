package fr.usmb.m2isc.mesure.ejb;

import fr.usmb.m2isc.mesure.jpa.Agency;
import fr.usmb.m2isc.mesure.jpa.ColumnItem;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@Remote
public class AgencyEJB {

    @PersistenceContext
    private EntityManager em;

    /** The construct **/
    public AgencyEJB() {
    }

    /** To add a agency **/
    public Agency addAgency(Agency a) {
        em.persist(a);
        return a;
    }

    /** Return the agency associated to the id **/
    public Agency findAgency(long id) {
        return em.find(Agency.class, id);
    }

    /** Return the agency associated to the name **/
    public Agency findAgencyByName(String name) {
        return em.createQuery("SELECT a FROM Agency a WHERE a.name LIKE :agencyName", Agency.class)
                .setParameter("agencyName", name).getSingleResult();
    }

    /** Return the list of all agencies **/
    public List<Agency> findAllAgencies() {
        return em.createQuery("SELECT a FROM Agency a", Agency.class).getResultList();
    }

    /** Returns the list of all items associated to the agency **/
    public List<ColumnItem> findAllColumns(Agency a) {
        TypedQuery<ColumnItem> rq = em.createQuery("SELECT c FROM ColumnItem c WHERE c.agency = :agency ", ColumnItem.class);
        rq.setParameter("agency", a);
        List res =  rq.getResultList();
        return res;
    }

    /** To remove a agency **/
    public void removeAgency(Agency a){
        if (!em.contains(a)) {
            a = em.merge(a);
        }
        em.remove(a);
    }
}


