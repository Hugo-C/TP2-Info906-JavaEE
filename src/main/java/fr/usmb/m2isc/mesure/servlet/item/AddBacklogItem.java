package fr.usmb.m2isc.mesure.servlet.item;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.usmb.m2isc.mesure.ejb.BacklogItemEJB;
import fr.usmb.m2isc.mesure.jpa.BacklogItem;

/**
 * Servlet implementation class AddMesureServlet
 */
@WebServlet("/AddBacklogItem")
public class AddBacklogItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// injection de la reference de l'ejb
	@EJB
	private BacklogItemEJB ejb;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBacklogItem() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// recuperation et parsing des parametres de la requete
		String name = request.getParameter("name");
		int priority = Integer.parseInt(request.getParameter("priority"));
		int estimation = Integer.parseInt(request.getParameter("estimation"));
		String description = request.getParameter("description");

		// appel de la methode d'ajout sur l'ejb
		BacklogItem p = ejb.addBacklogItem(name, priority, estimation, description);
		// ajout de la mesure dans le requete
		request.setAttribute("package", p);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
