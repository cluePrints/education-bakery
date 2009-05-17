package org.bakery.server.controllers.svc.impl.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ControllerAwareCommand;
import org.bakery.server.controllers.svc.ISvcController;

public class BackupSvc implements ControllerAwareCommand {

	private static final String PATH = "/dumps";
	private ISvcController controller;

	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, ISvcController _controller)
			throws Exception {
		controller = _controller;
		String mode = request.getParameter("mode");
		/* out all dumps */
		File f = new File(PATH);
		f.mkdirs();

		if ("save".equalsIgnoreCase(mode)) {
			String name = request.getParameter("dumpName");
			if ((name == null) || (name.trim().length() == 0))
				name = ("dump" + new Date());
			name = URLEncoder.encode(name, "utf8");
			String fileName = PATH + "\\" + name;
			File ff = new File(fileName);
			Process p = Runtime.getRuntime().exec(
					"mysqldump --user=" + getUsrName() + " --password="
							+ getUsrPwd() + " --all-databases ");
			InputStream from = p.getInputStream();
			OutputStream to = new FileOutputStream(ff.getAbsolutePath());
			try {
				int readCount;
				byte[] buffer = new byte[10240];
				while ((readCount = from.read(buffer, 0, 1024)) != 0) {
					to.write(buffer, 0, readCount);
				}
			} catch (Exception e) {
				ff.delete();
			} finally {
				to.close();
				from.close();
			}
		} else if ("load".equalsIgnoreCase(mode)) {
			String name = request.getParameter("dumpName");
			if ((name == null) || (name.trim().length() == 0)) 
				throw new IllegalArgumentException("Name could not be null");
			File ff = new File(PATH+"//"+name);
			if (!ff.exists())
				throw new IllegalArgumentException("Given dump file does not exist.");			
			Process p = Runtime.getRuntime().exec(
					"mysql --user=" + getUsrName() + " --password="
							+ getUsrPwd());
			OutputStream to = p.getOutputStream();
			InputStream from = new FileInputStream(ff.getCanonicalPath());
			try {
				int readCount;
				byte[] buffer = new byte[10240];
				while ((readCount = from.read(buffer, 0, 1024)) != 0) {
					to.write(buffer, 0, readCount);
				}
			} catch (Exception e) {
				ff.delete();
			} finally {
				to.close();
				from.close();
			}			
		}
		File[] dumpFiles = f.listFiles();
		StringBuilder b = new StringBuilder();

		b.append("<files>");
		if (dumpFiles != null) {
			for (File file : dumpFiles) {
				b.append("\n<file>\n<name>");
				b.append(file.getName());
				b.append("</name>\n</file>");
			}
		}
		b.append("</files>");
		response.getWriter().print(b.toString());
	}

	private String getUsrName() {
		return controller.getDAOFacade().getDataSource().getUsername();
	}

	private String getUsrPwd() {
		return controller.getDAOFacade().getDataSource().getPassword();
	}
}
