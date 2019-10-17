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
@WebServlet("/UpdatePackageServlet")
public class UpdatePackageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// injection de la reference de l'ejb
	@EJB
	private BacklogItemEJB ejb;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePackageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// recuperation et parsing des parametres de la requete
		int id = Integer.parseInt(request.getParameter("id"));

//		String stateAsString = request.getParameter("state");
//		ProcessState state = null;
//		if (stateAsString != null && !stateAsString.isEmpty()) {
//			state = ProcessState.valueOf(stateAsString);
//		}
//		double latitude = Double.parseDouble(request.getParameter("latitude"));
//		double longitude = Double.parseDouble(request.getParameter("longitude"));
//		String name = request.getParameter("name");
//
//		Position positionRefreshed = new Position(latitude, longitude, name);
//		Item p = ejb.updatePackage(id, positionRefreshed, state);

//		request.setAttribute("package",p);
		request.getRequestDispatcher("/showPackage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
