package ua.kiev.kpi.sc.parser.ext.interim;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;

import ua.kiev.kpi.sc.parser.node.ANullLiteral;
import ua.kiev.kpi.sc.parser.node.ANumericLiteral;
import ua.kiev.kpi.sc.parser.node.EOF;
import ua.kiev.kpi.sc.parser.node.Node;
import ua.kiev.kpi.sc.parser.node.PLiteral;
import ua.kiev.kpi.sc.parser.node.TClassToken;
import ua.kiev.kpi.sc.parser.node.TLBkt;
import ua.kiev.kpi.sc.parser.node.TPublic;
import ua.kiev.kpi.sc.parser.node.TRBkt;
import ua.kiev.kpi.sc.parser.node.TStar;

public class InterimFactoryTest {
	
	private static final Class<TRBkt> UNIQUE_2 = TRBkt.class;
	private static final Class<TLBkt> UNIQUIE_1 = TLBkt.class;
	private static final Class<TStar> HAS_DUPLICATE = TStar.class;
	private static final Class<TPublic> NOT_EXISTING = TPublic.class;
	
	private InterimsFactory f = createFactory();
	
	@Test(expected=Throwable.class)
	public void doubleFoundThrowsException()
	{
		f.create(1, HAS_DUPLICATE);
	}
	
	@Test
	public void notFoundReturnsNull()
	{
		Interim result = f.create(-500, NOT_EXISTING);
		Assert.assertNull(result);
	}
	
	@Test
	public void lookupById()
	{
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
		Interim result = f.create(-51, UNIQUIE_1);
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Interim3);
		
		result = f.create(-51, UNIQUE_2);
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Interim3);
	}
	
	@Test
	public void lookupByClassIsPolymorphic()
	{
		Interim result = f.create(-51, PLiteral.class);
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Interim3);
		
		result = f.create(-51, ANullLiteral.class);
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Interim3);
		
		result = f.create(-51, ANumericLiteral.class);
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Interim3);
	}
	
	@Test
	public void emptyIdAnnotationWorks()
	{
		Interim result = f.create(-11, TClassToken.class);
		Assert.assertTrue(result instanceof Interim4);
	}
	
	@Test
	public void emptyClassAnnotationWorks()
	{
		Interim result = f.create(3, EOF.class);
		Assert.assertTrue(result instanceof Interim5);
	}

	private InterimsFactory createFactory() {
		InterimsRegistry registryMock = createMockRegistry();
		InterimsFactory f = new InterimsFactory();
		f.setRegistry(registryMock);
		return f;
	}
	
	
	
	@SuppressWarnings({ "unchecked", "serial" })
	private InterimsRegistry createMockRegistry() {
		InterimsRegistry registryMock = EasyMock.createMock(InterimsRegistry.class);
		List lst = new LinkedList<Interim>(){
			{
			add(new Interim1());
			add(new Interim2());
			add(new Interim3());
			add(new Interim4());
			add(new Interim5());
			}
		};
		
		EasyMock.expect(registryMock.lookupAll()).andReturn(lst).anyTimes();
		EasyMock.replay(registryMock);
		return registryMock;
	}
}

@TriggeredFor(ruleIdArray={1}, reductedNodesArray={TStar.class})
class Interim1 extends DummyInterim {}

@TriggeredFor(ruleIdArray={1}, reductedNodesArray={TStar.class})
class Interim2 extends DummyInterim {}

@TriggeredFor(ruleIdArray={2, 5, 11, 92}, reductedNodesArray={TLBkt.class, TRBkt.class, PLiteral.class})
class Interim3 extends DummyInterim {}

@TriggeredFor(ruleIdArray={}, reductedNodesArray={TClassToken.class})
class Interim4 extends DummyInterim {}

@TriggeredFor(ruleIdArray={3}, reductedNodesArray={})
class Interim5 extends DummyInterim {}

class DummyInterim implements Interim
{
	public Translation translate(Node node) {
		return null;
	}	
}