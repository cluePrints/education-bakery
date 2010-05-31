package ua.kiev.kpi.sc.parser.ext.ui;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

public class ActionTableModelTest {
	private ActionTableModel model = new ActionTableModel();
	
	@Test
	@Ignore
	public void testPublicIsFirstShift()
	{
		int publicTokenModelInd = 36;
		String header = model.getColumnHeaders().get(publicTokenModelInd);
		Assert.assertEquals("Public", header);
		String value = (String) model.getValueAt(0, publicTokenModelInd);
		Assert.assertEquals("s1", value);
	}
	
	@Test
	@Ignore
	public void testReduceSomething()
	{
		int publicTokenModelInd = 13;
		String value = (String) model.getValueAt(57, publicTokenModelInd);
		Assert.assertEquals("r27", value);
	}
}
