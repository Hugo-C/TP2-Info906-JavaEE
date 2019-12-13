package fr.usmb.m2isc.mesure.ejb;

import fr.usmb.m2isc.mesure.jpa.Agency;
import fr.usmb.m2isc.mesure.jpa.BacklogItem;
import fr.usmb.m2isc.mesure.jpa.ColumnItem;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Stateless
@LocalBean
public class AgencyEJB {

    @PersistenceContext
    private EntityManager em;

    /**
     * Constructeur sans parametre obligatoire
     */
    public AgencyEJB() {
    }

    public Agency addAgency(Agency a) {
        em.persist(a);
        return a;
    }

    public Agency findAgency(long id) {
        return em.find(Agency.class, id);
    }

    public Agency findAgencyByName(String name) {
        return em.createQuery("SELECT a FROM Agency a WHERE a.name LIKE :agencyName", Agency.class)
                .setParameter("agencyName", name).getSingleResult();
    }

    public List<Agency> findAllAgencies() {
        return em.createQuery("SELECT a FROM Agency a", Agency.class).getResultList();
    }

    public List<ColumnItem> findAllColumns(Agency a) {
        TypedQuery<ColumnItem> rq = em.createQuery("SELECT c FROM ColumnItem c WHERE c.agency = :agency ", ColumnItem.class);
        rq.setParameter("agency", a);
        List res =  rq.getResultList();
        return res;
    }

    public void removeAgency(Agency a){
        if (!em.contains(a)) {
            a = em.merge(a);
        }
        em.remove(a);
    }
}


