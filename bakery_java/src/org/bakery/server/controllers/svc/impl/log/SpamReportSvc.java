package org.bakery.server.controllers.svc.impl.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ControllerAwareCommand;
import org.bakery.server.controllers.svc.ISvcController;

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
		int nCTotal = controller.getDAOFacade().getContragentDAO().searchByName("%", 0, 100).size();
		int nCAvailable = controller.getDAOFacade().getContragentDAO().getAvailable().size();
		b.append("\n\n"+DELIM);
		b.append(DELIM);
		b.append("\n1. Контрагенты");
		b.append(DELIM);
		b.append("\nВсего зарегестрировано контрагентов:			"+nCTotal);
		b.append("\n-Допущеных к деловой активности:				"+nCAvailable);		
		
		b.append("\n"+DELIM);
		b.append("\n2. ");
		b.append(DELIM);
		b.append("\n");
		b.append("\nВсего зарегестрировано параметров:		"+controller.getDAOFacade().getDeviceParameterDAO().searchByName("%", 0, 200).size());
		b.append("\n-Активно контроллируется:				"+controller.getDAOFacade().getDeviceParameterDAO().getAvailable().size());

		b.append("\n"+DELIM);
		b.append("\n3. Измеренные значения");
		b.append(DELIM);
		b.append("\n");
		int nMeasuresTotal = controller.getDAOFacade().getMeasureDAO().searchByName("%", 0, 400).size();
		int nMeasuresDeclined = controller.getDAOFacade().getDeviceParameterDAO().getAvailable().size();
		b.append("\nВсего измерений:					"+nMeasuresTotal);
		b.append("\n-Отброшены как недействительные:	"+nMeasuresDeclined);
		response.getWriter().write(b.toString());
	}
}
