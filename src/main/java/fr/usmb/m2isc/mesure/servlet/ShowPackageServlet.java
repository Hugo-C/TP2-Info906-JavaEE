package fr.usmb.m2isc.mesure.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.usmb.m2isc.mesure.ejb.PackageEJB;
import fr.usmb.m2isc.mesure.jpa.Package;

/**
 * Servlet implementation class AddMesureServlet
 */
@WebServlet("/ShowPackageServlet")
public class ShowPackageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// injection de la reference de l'ejb
	@EJB
	private PackageEJB ejb;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowPackageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// recuperation et parsing des parametres de la requete
		long id = Long.parseLong(request.getParameter("id"));
		// appel de l'ejb
		Package p = ejb.findPackage(id);
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
