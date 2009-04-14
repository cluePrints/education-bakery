package junit.controllers;

import junit.util.TestConst;

import org.bakery.server.controllers.action.AddressFormAction;
import org.bakery.server.controllers.beans.AbstractFormMode;
import org.springframework.webflow.definition.registry.FlowDefinitionResource;
import org.springframework.webflow.execution.support.ApplicationView;
import org.springframework.webflow.test.MockParameterMap;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

public class SimpleFlowTest extends AbstractXmlFlowExecutionTests {
	@Override
	protected FlowDefinitionResource getFlowDefinitionResource() {
		return createFlowDefinitionResource("WebContent/WEB-INF/flows/admin-flow.xml");
	}

	 public void testFlow() {
		 ApplicationView view = applicationView(startFlow());
		 assertCurrentStateEquals("addressForm");
		 assertModelAttributeNotNull(AddressFormAction.AN_DEFAULT_LIST, view);		 
	 }
	 
	 protected void testAbstractEntity(String name){
		 for (AbstractFormMode mode : AbstractFormMode.values()){
			 
			 ApplicationView view = applicationView(startFlow());
			 signalEvent("manage"+convertToMultiple(name));
			 assertCurrentStateEquals(name+"Form");
			 assertModelAttributeNotNull(convertToMultiple(name), view);
			 		 
			 MockParameterMap param = createTestParamsBefore(mode);
			 
		 }
	 }
	 private MockParameterMap createTestParamsBefore(AbstractFormMode mode) {
		 MockParameterMap param = new MockParameterMap();
		 param.put("formBean.id", TestConst.TEST_OBJECT_ID.toString());
		 param.put("mode", mode.toString());		 
		 return param;
	 }
	 private String convertToMultiple(String str) {
		 char lastChar =str.charAt(str.length());
		 if (lastChar == 's') {
			 return str+"es";
		 } else {
			 return str+"s";
		 }
	 }
}
