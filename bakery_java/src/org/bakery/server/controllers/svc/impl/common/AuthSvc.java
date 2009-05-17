package org.bakery.server.controllers.svc.impl.common;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ControllerAwareCommand;
import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.fake.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class AuthSvc implements ControllerAwareCommand {
	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller)
			throws Exception {
		String mode = request.getParameter("mode");
		if ("logout".equalsIgnoreCase(mode)) {
			request.getSession().invalidate();
		} else if ("login".equalsIgnoreCase(mode)) {
			String userName = request.getParameter("login");
			String userPassowrd = request.getParameter("password");
			if ((userName == null) || (userName.trim().length() == 0))
				throw new IllegalStateException("User name could not be empty");
			if (userPassowrd == null)
				throw new IllegalStateException(
						"User password could not be empty");
			SessionFactory factory = controller.getDAOFacade().getAccountDAO()
					.getSessionFactory();
			Session session = factory.openSession();
			Query q = session
					.createQuery("from User where name=:name and password=:pwd");
			q.setString("name", userName);
			q.setString("pwd", userPassowrd);
			User u = (User) q.uniqueResult();
			if (u != null) {		
				request.getSession().setAttribute("user", u);				
			}
			session.close();
		}
		writeUser(request, response);
	}

	public static void writeUser(HttpServletRequest request, HttpServletResponse response)
			throws IntrospectionException, InvocationTargetException,
			IllegalAccessException, IOException {
		User u = (User) request.getSession().getAttribute("user");
		if (u != null){
			String inf = BusinessEntity.toXml(u);
			StringBuilder b = new StringBuilder(inf.length()+50);
			b.append("\n<currentUser>");
			b.append(inf);
			b.append("\n</currentUser>");
			response.getWriter().write(b.toString());
		}
	}

}
