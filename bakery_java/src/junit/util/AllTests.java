package junit.util;
import junit.domain.BusinessEntityTest;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.relations.Address_ContragentTest;
import junit.relations.Address_WarehouseTest;
import junit.relations.Contragent_AccountTest;
import junit.relations.Contragent_WarehouseTest;
import junit.relations.DAOTest;
import junit.relations.Order_MoneyMove;
import junit.relations.PriceList_PriceListItem;


public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for default package");
		//$JUnit-BEGIN$
		/* single Address */
		suite.addTestSuite(Address_ContragentTest.class);
		suite.addTestSuite(Address_WarehouseTest.class);
		
		/* signle Contragent */
		suite.addTestSuite(Contragent_AccountTest.class);
		suite.addTestSuite(Contragent_WarehouseTest.class);
		
		suite.addTestSuite(Order_MoneyMove.class);
		suite.addTestSuite(PriceList_PriceListItem.class);
		
		
		suite.addTestSuite(DAOTest.class);
		
		suite.addTestSuite(BusinessEntityTest.class);
		// suite.addTestSuite(SimpleFlowTest.class);
		//$JUnit-END$
		return suite;
	}

    /**
     * Runs the test suite using the textual runner.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
