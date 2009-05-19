package org.bakery.server.controllers.svc.impl.qa;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ControllerAwareCommand;
import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.persistence.AbstractDAO;

public class SpamReportSvc implements ControllerAwareCommand{
	public static final String DELIM="\n========================================================";
	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller)
			throws Exception {
		StringBuilder b = new StringBuilder();
		b.append(DELIM);
		b.append(DELIM);
		b.append("\n			Краткий отчет о текущем состоянии системы:");
		b.append(DELIM);
		b.append(DELIM);
		int nWorking = DeviceStateReportSvc.getWorkingDevicesNum(controller);
		int nAvailable = controller.getDAOFacade().getDeviceDAO().getAvailable().size();
		int nTotal = controller.getDAOFacade().getDeviceDAO().searchByName("%", 0, 100).size();
		b.append("\n\n"+DELIM);
		b.append(DELIM);
		b.append("1. Устройства");
		b.append(DELIM);
		b.append("\nВсего устройств:			"+nTotal);
		b.append("\n-Работоспособных:			"+nAvailable);
		b.append("\n--Занятых в данный момент:	"+nWorking);
		b.append("\n-Неработоспособных:			"+(nTotal-nAvailable));
		
		
		b.append("\n"+DELIM);
		b.append("2. Измерительная аппаратура");
		b.append(DELIM);
		b.append("\n");
		b.append("\nВсего зарегестрировано параметров:		"+controller.getDAOFacade().getDeviceParameterDAO().searchByName("%", 0, 200).size());
		b.append("\n-Активно контроллируется:				"+controller.getDAOFacade().getDeviceParameterDAO().getAvailable().size());

		b.append("\n"+DELIM);
		b.append("3. Измеренные значения");
		b.append(DELIM);
		b.append("\n");
		int nMeasuresTotal = controller.getDAOFacade().getMeasureDAO().searchByName("%", 0, 400).size();
		int nMeasuresDeclined = controller.getDAOFacade().getDeviceParameterDAO().getAvailable().size();
		b.append("\nВсего измерений:					"+nMeasuresTotal);
		b.append("\n-Отброшены как недействительные:	"+nMeasuresDeclined);
	}
	private void write(StringBuilder b, String topLabel, AbstractDAO dao){
	}
}
