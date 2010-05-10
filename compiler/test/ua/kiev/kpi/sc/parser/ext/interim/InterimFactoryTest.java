package ua.kiev.kpi.sc.parser.ext.interim;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;

import ua.kiev.kpi.sc.parser.node.AIntLiteralNumeric;
import ua.kiev.kpi.sc.parser.node.ANullLiteral;
import ua.kiev.kpi.sc.parser.node.PLiteral;
import ua.kiev.kpi.sc.parser.node.TLBkt;
import ua.kiev.kpi.sc.parser.node.TPublic;
import ua.kiev.kpi.sc.parser.node.TRBkt;
import ua.kiev.kpi.sc.parser.node.TStar;

public class InterimFactoryTest {
	
	private static final Class<TRBkt> UNIQUE_2 = TRBkt.class;
	private static final Class<TLBkt> UNIQUIE_1 = TLBkt.class;
	private static final Class<TStar> HAS_DUPLICATE = TStar.class;
	private static final Class<TPublic> NOT_EXISTING = TPublic.class;

	@Test(expected=Throwable.class)
	public void doubleFoundThrowsException()
	{
		InterimsRegistry registryMock = createMockRegistry();
		InterimsFactory f = new InterimsFactory();
		f.setRegistry(registryMock);
		
		f.create(1, HAS_DUPLICATE);
	}
	
	@Test
	public void notFoundReturnsNull()
	{
		InterimsRegistry registryMock = createMockRegistry();
		InterimsFactory f = new InterimsFactory();
		f.setRegistry(registryMock);
		
		Interim result = f.create(-500, NOT_EXISTING);
		Assert.assertNull(result);
	}
	
	@Test
	public void lookupById()
	{
		InterimsRegistry registryMock = createMockRegistry();
		InterimsFactory f = new InterimsFactory();
		f.setRegistry(registryMock);
		
		Interim result = f.create(2, NOT_EXISTING);
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Interim3);
		
		result = f.create(92, NOT_EXISTING);
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Interim3);
	}
	
	@Test
	public void lookupByClass()
	{
		InterimsRegistry registryMock = createMockRegistry();
		InterimsFactory f = new InterimsFactory();
		f.setRegistry(registryMock);
		
		Interim result = f.create(-51, UNIQUIE_1);
		Assert.assertNull(result);
		Assert.assertTrue(result instanceof Interim3);
		
		result = f.create(-51, UNIQUE_2);
		Assert.assertNull(result);
		Assert.assertTrue(result instanceof Interim3);
	}
	
	@Test
	public void lookupByClassIsPolymorphic()
	{
		InterimsRegistry registryMock = createMockRegistry();
		InterimsFactory f = new InterimsFactory();
		f.setRegistry(registryMock);
		
		Interim result = f.create(-51, PLiteral.class);
		Assert.assertNull(result);
		Assert.assertTrue(result instanceof Interim3);
		
		result = f.create(-51, ANullLiteral.class);
		Assert.assertNull(result);
		Assert.assertTrue(result instanceof Interim3);
		
		result = f.create(-51, AIntLiteralNumeric.class);
		Assert.assertNull(result);
		Assert.assertTrue(result instanceof Interim3);
	}

	private InterimsRegistry createMockRegistry() {
		InterimsRegistry registryMock = EasyMock.createMock(InterimsRegistry.class);		
		Interim i1 = new Interim1();
		Interim i2 = new Interim2();
		Interim i3 = new Interim3();
		List<Interim> lst = new LinkedList<Interim>();
		lst.add(i1);
		lst.add(i2);
		lst.add(i3);
		
		EasyMock.expect(registryMock.lookupAll()).andReturn(lst).anyTimes();
		EasyMock.replay(registryMock);
		return registryMock;
	}
}

@TriggeredFor(ruleIdArray={1}, reductedNodesArray={TStar.class})
class Interim1 implements Interim {}

@TriggeredFor(ruleIdArray={1}, reductedNodesArray={TStar.class})
class Interim2 implements Interim {}

@TriggeredFor(ruleIdArray={2, 5, 11, 92}, reductedNodesArray={TLBkt.class, TRBkt.class, PLiteral.class})
class Interim3 implements Interim {}