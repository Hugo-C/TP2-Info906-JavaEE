package fr.usmb.m2isc.mesure.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.usmb.m2isc.mesure.ejb.ItemEJB;
import fr.usmb.m2isc.mesure.jpa.Item;

/**
 * Servlet implementation class AddMesureServlet
 */
@WebServlet("/AddPackageServlet")
public class AddPackageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// injection de la reference de l'ejb
	@EJB
	private ItemEJB ejb;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPackageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// recuperation et parsing des parametres de la requete
		double weight = Double.parseDouble(request.getParameter("weight"));
		double val = Double.parseDouble(request.getParameter("val"));
		double latOrigin = Double.parseDouble(request.getParameter("latOrigin"));
		double longOrigin = Double.parseDouble(request.getParameter("longOrigin"));
		String localityOrigin = request.getParameter("localityOrigin");
		double latDest = Double.parseDouble(request.getParameter("latDest"));
		double longDest = Double.parseDouble(request.getParameter("longDest"));
		String localityDest = request.getParameter("localityDest");
		Position origin = new Position(latOrigin, longOrigin, localityOrigin);
		Position destination = new Position(latDest, longDest, localityDest);
		// appel de la methode d'ajout sur l'ejb
		Item p = ejb.addItem(weight, val, origin, destination);
		// ajout de la mesure dans le requete
		request.setAttribute("package",p);
		// transfert a la JSP d'affichage
		request.getRequestDispatcher("/showPackage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
