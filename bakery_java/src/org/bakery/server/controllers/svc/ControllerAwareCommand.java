package org.bakery.server.controllers.svc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ControllerAwareCommand {
	public void execute(HttpServletRequest request, HttpServletResponse response, SvcController controller) throws Exception;
}
