package fr.usmb.m2isc.mesure.servlet;

import javax.faces.application.ResourceHandler;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/*")
public class MappingServlet implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String relativeRequestURI = request.getRequestURI().substring(request.getContextPath().length());

		boolean resourceExists = request.getServletContext().getResource(relativeRequestURI) != null;
		boolean facesResourceRequest = request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER);

		if (resourceExists || facesResourceRequest) {
			chain.doFilter(request, response);
		}
		else {  // TODO check url is like /item/...
			System.out.println("redirected the request to display_item");
			String urlWithExtension = relativeRequestURI.split("/")[2];
			int backlogItemId = Integer.parseInt(urlWithExtension.split(".xhtml")[0]);
			request.setAttribute("backlogItemId", backlogItemId);
			request.setAttribute("url", request.getRequestURI());
			request.getRequestDispatcher("/display_item.xhtml").forward(request, response);
		}
	}
}
