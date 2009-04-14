package junit.relations;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public abstract class AbstractSpringTest extends
		AbstractDependencyInjectionSpringContextTests {


	public AbstractSpringTest() {
		super();
	}

	public AbstractSpringTest(String name) {
		super(name);
	}

	protected String[] getConfigLocations() {
		String[] paths={
				"junit/relations/applicationContext.xml",
			};
		return paths;
	}
}