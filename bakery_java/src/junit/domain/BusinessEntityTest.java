package junit.domain;

import junit.framework.TestCase;

import org.bakery.server.domain.production.Unit;

public class BusinessEntityTest extends TestCase {
	public void testToXml() throws Exception{
		assertTrue("<unit><active>1</active><id>123</id><name>teZt</name></unit>".equals(new Unit(123L, "teZt").toXml()));		
	}
}
