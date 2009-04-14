package org.bakery.server.controllers.action;


/**
 * @author Mediaspectrum, Inc.
 */
public class UnitFormAction extends AbstractFormAction{
	public static final String AN_DEFAULT_LIST = "units";
	public UnitFormAction() {
		super.setListAttributeName(AN_DEFAULT_LIST);
	}
}