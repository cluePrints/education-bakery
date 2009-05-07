package org.bakery.server.controllers.svc.impl.qa;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ControllerAwareCommand;
import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.domain.BusinessEntity;

public class DeviceStateReportSvc implements ControllerAwareCommand{
	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller)
			throws Exception {
		int nAvailable = controller.getDAOFacade().getDeviceDAO().getAvailable().size();
		int nTotal = controller.getDAOFacade().getDeviceDAO().searchByName("%", 0, 1000).size();
		PrintWriter out = response.getWriter();
		out.write("\n<availableDevicesReport>");
		out.write("\n<type>Работоспособные</type>");
		out.write("\n<amount>"+nAvailable+"</amount>");
		out.write("\n</availableDevicesReport>");
		out.write("\n<availableDevicesReport>");
		out.write("\n<type>Неработоспособные</type>");
		out.write("\n<amount>"+(nTotal-nAvailable)+"</amount>");
		out.write("\n</availableDevicesReport>");
	}
	
	public static class PieReportPart extends BusinessEntity{
		public static final long serialVersionUid = 1L;
		private String type;
		private Double amount;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Double getAmount() {
			return amount;
		}
		public void setAmount(Double amount) {
			this.amount = amount;
		}
	}
}
