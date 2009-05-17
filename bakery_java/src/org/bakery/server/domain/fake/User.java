package org.bakery.server.domain.fake;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.validation.CouldNotBeEmpty;

public class User extends BusinessEntity{
	private static final long serialVersionUID=1L;
	private String name;
	private String password;
	private Integer role=0;
	private String textView;
	public static final int ROLE_LOGIST=1;
	public static final int ROLE_ADMIN=2;
	public static final int ROLE_QA=4;
	public static final int NOT_ROLE_LOGIST=31-ROLE_LOGIST;
	public static final int NOT_ROLE_ADMIN=31-ROLE_ADMIN;
	public static final int NOT_ROLE_QA=31-ROLE_QA;
	@CouldNotBeEmpty(message="Ћогин пользовател€ должен быть задан.")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@CouldNotBeEmpty(message="ѕароль пользовател€ должен быть задан.")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public boolean isUserAdmin(){
		return (role & ROLE_ADMIN) != 0;
	}
	public boolean isUserLogist(){
		return (role & ROLE_LOGIST) !=0;
	}
	public boolean isUserQA(){
		return (role & ROLE_QA) != 0;
	}
	public void setUserAdmin(boolean grantRights){
		if (grantRights) {
			role = role | ROLE_ADMIN;
		} else {
			role = role & NOT_ROLE_ADMIN;
		}
	}
	public void setUserLogist(boolean grantRights){
		if (grantRights) {
			role = role | ROLE_LOGIST;
		} else {
			role = role & NOT_ROLE_LOGIST;
		}
	}
	public void setUserQA(boolean grantRights){
		if (grantRights) {
			role = role | ROLE_QA;
		} else {
			role = role & NOT_ROLE_QA;
		}
	}
	@CouldNotBeEmpty(message="ѕолное им€ пользовател€ должно быть задано.")
	public String getTextView() {
		return textView;
	}
	public void setTextView(String textView) {
		this.textView = textView;
	}

}
