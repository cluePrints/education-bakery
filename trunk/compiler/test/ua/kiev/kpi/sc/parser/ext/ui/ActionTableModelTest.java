package ua.kiev.kpi.sc.parser.ext.ui;

import junit.framework.Assert;

import org.junit.Test;

public class ActionTableModelTest {
	private ActionTableModel model = new ActionTableModel();
	
	@Test
	public void testPublicIsFirstShift()
	{
		int publicTokenInd = 35;
		String header = model.getColumnHeaders().get(publicTokenInd);
		Assert.assertEquals("Public", header);
		String value = (String) model.getValueAt(0, publicTokenInd);
		Assert.assertEquals("s1", value);
	}
	
	@Test
	public void testReduceSomething()
	{
		int publicTokenInd = 12;
		String value = (String) model.getValueAt(57, publicTokenInd);
		Assert.assertEquals("r27", value);
	}
}
