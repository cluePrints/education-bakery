package ua.kiev.kpi.sc.parser.ext.ui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ua.kiev.kpi.sc.parser.ext.MyException;

public class HelpFrame extends JFrame{ 
	public HelpFrame()
	{
		try {			
			setTitle("Parser rules");
			BufferedReader r = new BufferedReader(new FileReader("rules.txt"));
			StringBuilder buffer = new StringBuilder();
			String line = "";
			do {								
				buffer.append(line);
				buffer.append("\n");
				line = r.readLine();
			} while (line != null);
			JTextArea taRules = new JTextArea();
			
			taRules.setText(buffer.toString());
			JScrollPane spRules = new JScrollPane(taRules);
			spRules.setPreferredSize(new Dimension(400,300));
			this.add(spRules);
			this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					setVisible(false);
				}
			});
			pack();
		} catch (FileNotFoundException ex) {
			throw new MyException(ex);
		} catch (IOException ex) {
			throw new MyException(ex);
		}
	}
}
