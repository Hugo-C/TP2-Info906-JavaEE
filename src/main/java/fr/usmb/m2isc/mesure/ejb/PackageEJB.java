package fr.usmb.m2isc.mesure.ejb;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.usmb.m2isc.mesure.jpa.Package;
import fr.usmb.m2isc.mesure.jpa.Position;
import fr.usmb.m2isc.mesure.jpa.ProcessState;

@Stateless
@LocalBean
public class PackageEJB {
	@PersistenceContext
	private EntityManager em;
	
	
	/**
	 * Constructeur sans parametre obligatoire
	 */
	public PackageEJB() {
	}
	
	public Package addPackage(double weight, double val, Position origin, Position destination) {
		Package m = new Package(weight, val, origin, destination);
		em.persist(m);
		return m;
	}

	public Package findPackage(long id) {
		return em.find(Package.class, id);
	}

	public Package updatePackage(long id, Position newPosition, ProcessState state) {
		Package p = em.find(Package.class, id);
		p.setCurrentPosition(newPosition);
		if (state != null) {
			p.setState(state);
		}
		return p;
	}

//	/**
//	 * Recuperation de la liste des mesures pour une piece donnee.
//	 * Les mesures sont triee par date de la plus ancienne a la plus recente.
//	 *
//	 * @param piece dont on veut la liste des mesures
//	 * @return liste de mesures
//	 */
//	public List<Package> findMesures(String piece) {
//		TypedQuery<Package> rq = em.createQuery("SELECT m FROM Package m WHERE m.piece = :piece ORDER BY m.dateMesure ASC", Package.class);
//		rq.setParameter("piece", piece);
//		return rq.getResultList();
//	}
//
//	/**
//	 * Recuperation de la dernire mesure pour une piece.
//	 *
//	 * @param piece
//	 * @return
//	 */
//	public Package findLastMesure(String piece) {
//		TypedQuery<Package> rq = em.createQuery("SELECT m FROM Package m WHERE m.piece = :piece ORDER BY m.dateMesure DESC", Package.class);
//		rq.setParameter("piece", piece);
//		rq.setMaxResults(1);
//		return rq.getSingleResult();
//	}
//
//	/**
//	 * Liste des dernieres mesures pour chaque piece.
//	 *
//	 * @return
//	 */
//	public List<Package> getLastMesures() {
//		List<Package> res = new LinkedList<>();
//		TypedQuery<String> rq = em.createQuery("SELECT DISTINCT m.piece FROM Package m group by m.piece", String.class);
//		for(String p : rq.getResultList()) {
//			Package m = findLastMesure(p);
//				res.add(m);
//		}
//		return res;
//	}
}
