package org.bakery.server.controllers.svc.impl.qa;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ControllerAwareCommand;
import org.bakery.server.controllers.svc.ISvcController;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class DeviceStateReportSvc implements ControllerAwareCommand{
	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller)
			throws Exception {
		int nAvailable = controller.getDAOFacade().getDeviceDAO().getAvailable().size();
		int nTotal = controller.getDAOFacade().getDeviceDAO().searchByName("%", 0, 1000).size();
		SessionFactory f = controller.getDAOFacade().getAccountDAO().getSessionFactory();
		Session session = f.openSession();
			Connection c = session.connection();
			int nWorking = 0;
			PreparedStatement stmt = c.prepareStatement(SQL_CURRENTLY_WORKING_PLANS);
			
			ResultSet results = stmt.executeQuery();
			while (results.next())
				nWorking++;

		f.close();
		PrintWriter out = response.getWriter();
		out.write("\n<availableDevicesReport>");
		out.write("\n<type>Свободные</type>");
		out.write("\n<amount>"+(nAvailable-nWorking)+"</amount>");
		out.write("\n</availableDevicesReport>");
		
		out.write("\n<availableDevicesReport>");
		out.write("\n<type>Неработоспособные</type>");
		out.write("\n<amount>"+(nTotal-nAvailable)+"</amount>");
		out.write("\n</availableDevicesReport>");
		
		
		out.write("\n<availableDevicesReport>");
		out.write("\n<type>Занятые на производстве</type>");
		out.write("\n<amount>"+(nWorking)+"</amount>");
		out.write("\n</availableDevicesReport>");
		
	}
	static final String SQL_CURRENTLY_WORKING_PLANS= 
		"SELECT *, datediff(end_date, current_datetime)*24 as hoursleft FROM (\n"+
   	 	"	SELECT\n"+
		"	device_name,\n"+
		"    order_id,\n"+
		"    plans.plan_id,\n"+
		"    plans.recip_id,\n"+
		"    start_date,\n"+
		"    addtime(start_date, maketime(recip_time div (60*24), recip_time div 60, recip_time mod 60)) as end_date,\n"+
		"    current_datetime\n"+
		"  FROM plans\n"+
		"  JOIN (\n"+
		"    SELECT\n"+
		"      current_datetime\n"+
		"    FROM env_parameters order by id desc limit 0,1) as t\n"+
		"JOIN recips ON recips.recip_id = plans.recip_id\n"+
		"JOIN recip_parameters\n"+
		"JOIN devices) as tt\n"+
		"WHERE current_datetime <= end_date AND start_date<=current_datetime\n"+
		"GROUP BY plan_id";
	/*
	 	SELECT *, datediff(end_date, current_datetime)*24 as hoursleft FROM (
	SELECT
	device_name,
    order_id,
    plans.plan_id,
    plans.recip_id,
    start_date,
    addtime(start_date, maketime(recip_time div (60*24), recip_time div 60, recip_time mod 60)) as end_date,
    current_datetime
  FROM plans
  JOIN (
    SELECT
      current_datetime
    FROM env_parameters order by id desc limit 0,1) as t
JOIN recips ON recips.recip_id = plans.recip_id
JOIN recip_parameters
JOIN devices) as tt
WHERE current_datetime <= end_date AND start_date<=current_datetime
GROUP BY plan_id
	 
	 
	 
	 
	 
	 
	 */
	public static void main(String[] args) {
		System.out.println(SQL_CURRENTLY_WORKING_PLANS);
	}
	
}
