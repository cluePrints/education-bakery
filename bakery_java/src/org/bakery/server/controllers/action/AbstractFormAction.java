package org.bakery.server.controllers.action;

import org.bakery.server.controllers.beans.AbstractFormBean;
import org.bakery.server.controllers.beans.AbstractFormMode;
import org.bakery.server.persistence.AbstractDAO;
import org.bakery.server.util.Constants;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.support.EventFactorySupport;


/**
 * @author Mediaspectrum, Inc.
 */
public class AbstractFormAction extends FormAction{
	private String listAttributeName;
	private AbstractDAO mainDAO;
	
	@Override
	public Event setupForm(RequestContext context) throws Exception {
		AbstractFormBean form = (AbstractFormBean) getFormObject(context);
		
		if (form.getSearchNamePattern()==null || form.getSearchNamePattern().trim().length()==0)
			form.setSearchNamePattern(Constants.DEFAULT_SEARCH_PATTERN);
		if (form.getSearchStartFrom() == null 
				|| form.getSearchStartFrom()<Constants.DEFAULT_SEARCH_START_FROM)
			form.setSearchStartFrom(Constants.DEFAULT_SEARCH_START_FROM);
		if (form.getSearchMaxResults() == null 
				|| form.getSearchMaxResults()<form.getSearchStartFrom() 
				|| form.getSearchMaxResults()>Constants.DEFAULT_SEARCH_MAX_RESULTS)
			form.setSearchMaxResults(Constants.DEFAULT_SEARCH_MAX_RESULTS);		
		
		context.getFlashScope().put(listAttributeName, mainDAO.searchByName(
				form.getSearchNamePattern(), 
				form.getSearchStartFrom(),
				form.getSearchMaxResults()));
	
		return super.setupForm(context);
	}

	public Event submit(RequestContext context) throws Exception {
		AbstractFormBean form = (AbstractFormBean) getFormObject(context);
		if (AbstractFormMode.EDIT.equals(form.getMode())) {
			mainDAO.saveOrUpdate(form.getFormBean());
		} else if (AbstractFormMode.DELETE.equals(form.getMode())){
			mainDAO.removeById(form.getIdForDeletion());
		} else if (AbstractFormMode.RESTORE.equals(form.getMode())){
			mainDAO.restoreById(form.getIdForRestore());
		} else if (AbstractFormMode.NEW.equals(form.getMode())){
			mainDAO.save(form.getFormBean());
		}
		return new EventFactorySupport().event(this, "refresh");
	}
	

	public String getListAttributeName() {
		return listAttributeName;
	}

	public void setListAttributeName(String listAttributeName) {
		this.listAttributeName = listAttributeName;
	}

	public AbstractDAO getMainDAO() {
		return mainDAO;
	}

	public void setMainDAO(AbstractDAO mainDAO) {
		this.mainDAO = mainDAO;
	}
}


