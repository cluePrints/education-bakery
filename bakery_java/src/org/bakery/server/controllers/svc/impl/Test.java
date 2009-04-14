package org.bakery.server.controllers.svc.impl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ControllerAwareCommand;
import org.bakery.server.controllers.svc.SvcController;

public class Test implements ControllerAwareCommand {

	public void execute(HttpServletRequest request,
			HttpServletResponse response, SvcController controller) throws Exception{
		PrintWriter out = response.getWriter();
		out.print("Test successful");
	}

}
