package junit.relations;

import org.hibernate.SessionFactory;

public abstract class AbstractDBTest extends AbstractSpringTest {
	protected SessionFactory sessionFactory;
	public AbstractDBTest() {
		super();
	}

	public AbstractDBTest(String name) {
		super(name);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}