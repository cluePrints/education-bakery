package org.bakery.server.controllers.svc;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.impl.AbstractCommand;
import org.bakery.server.persistence.DAOFacade;
import org.bakery.server.util.LoggingUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class SvcController extends AbstractController {
	private DAOFacade DAOFacade;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		String svcName = request.getParameter("svc");
		// FIXME: it's dumb approach
		logger.info(LoggingUtils.dumpStringMapIntoString(request.getParameterMap()));
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");
			String svcClassName = svcName;
			Class svcClass = Class.forName(svcClassName);
			AbstractCommand command = (AbstractCommand) svcClass.newInstance();
			command.init(this);
			command.execute(request, response, this);

		} catch (Exception ex) {			
			signalInternalError(response.getWriter(), ex);
			response.flushBuffer();
			throw ex;
		}
		return null;
	}
	private void signalInternalError(PrintWriter out, Exception e){
		out.write("<internalServerError>");
		out.write("  <exception>");
		e.printStackTrace(out);
		out.write("  </exception>");
		out.write("</internalServerError>");
	}
	public DAOFacade getDAOFacade() {
		return DAOFacade;
	}

	public void setDAOFacade(DAOFacade facade) {
		DAOFacade = facade;
	}

}
