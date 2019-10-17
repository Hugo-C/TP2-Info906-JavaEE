package fr.usmb.m2isc.mesure.servlet.item;

import fr.usmb.m2isc.mesure.ejb.BacklogItemEJB;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class AddMesureServlet
 */
@WebServlet("/GetItems")
public class GetItems extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// injection de la reference de l'ejb
	@EJB
	private BacklogItemEJB ejb;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetItems() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// recuperation et parsing des parametres de la requete
//		long id = Long.parseLong(request.getParameter("id"));
//		// appel de l'ejb
//		Package p = ejb.findPackage(id);
//		// ajout de la mesure dans le requete
//		request.setAttribute("package",p);
//		// transfert a la JSP d'affichage
//		request.getRequestDispatcher("/showPackage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
