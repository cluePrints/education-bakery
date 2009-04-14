package org.bakery.server.controllers.validation;

import java.util.Date;

import org.bakery.server.controllers.beans.AbstractFormBean;
import org.bakery.server.controllers.beans.AbstractFormMode;
import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.NamedEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CommonFormValidator implements Validator {
	public boolean supports(Class arg0) {
		// TODO: support only beans
		return true;
	}

	public static final String EMPTY_DELETION_ID = "empty_restore_id";
	public static final String EMPTY_RESTORE_ID = "empty_restore_id";
	public static final String WRONG_ID = "wrong_id";
	public static final String EMPTY_NAME = "empty_name";
	public static final String EMPTY_ID = "empty_id";
	public static final String OTHER_ERROR = "other_err";
	public static final String _FORM_BEAN = "formBean";

	public void validate(Object arg0, Errors errors) {		
		AbstractFormBean form = (AbstractFormBean) arg0;				
		if (form.getMode() == null) {
			return;
		}
		if (form.getMode().equals(AbstractFormMode.DELETE)) {
			/* DELETE mode */
			if (form.getIdForDeletion() == null)
				errors.rejectValue("idForDeletion", EMPTY_DELETION_ID);
		} else if (form.getMode().equals(AbstractFormMode.RESTORE)) {
			/* RESTORE mode */
			if (form.getIdForRestore() == null)
				errors.rejectValue("idForRestore", EMPTY_RESTORE_ID);
		} else if (AbstractFormMode.EDIT.equals(form.getMode())) {
			/* EDIT mode */
			if (form.getFormBean() == null)
				errors.reject(_FORM_BEAN, OTHER_ERROR);
			if (form.getFormBean().getId() == null|| form.getFormBean().getId() <= 0)
				errors.reject(_FORM_BEAN, EMPTY_ID);
			if (NamedEntity.class.isInstance(form.getFormBean())) {
				NamedEntity obj = (NamedEntity) form.getFormBean();
				if (obj.getName() == null || obj.getName().trim().length()==0)
					errors.reject(_FORM_BEAN, EMPTY_NAME);
			}
			form.getFormBean().validate(errors);
		} else if (AbstractFormMode.NEW.equals(form.getMode())) {
			/* NEW mode */
			if (form.getFormBean() == null)
				errors.reject(_FORM_BEAN, OTHER_ERROR);
			if (form.getFormBean().getId() != null && form.getFormBean().getId() >0)
				errors.reject(_FORM_BEAN, WRONG_ID);
			if (NamedEntity.class.isInstance(form.getFormBean())) {
				NamedEntity obj = (NamedEntity) form.getFormBean();
				if (obj.getName() == null || obj.getName().trim().length()==0)
					errors.reject(_FORM_BEAN, EMPTY_NAME);
			}
			form.getFormBean().validate(errors);
		}
		validateChild(form, errors);
	}
	
	protected void validateChild(AbstractFormBean form, Errors errors){
		
	}
	
	public static boolean isEmptyEntity(Object e){
		if (e instanceof BusinessEntity) 
			return (e==null || ((BusinessEntity) e).getId()<=0) ? true : false;
		if (e instanceof Date)
			return (BusinessEntity.NULL_DATE.equals(e) || e==null);
		if (e instanceof Object)
			return (e==null);
		return false;		
	}
}
