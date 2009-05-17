package org.bakery.server.controllers.svc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.domain.fake.User;

public interface ControllerAwareCommand {
	public void execute(HttpServletRequest request, HttpServletResponse response, ISvcController controller) throws Exception;
}
