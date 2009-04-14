package org.bakery.server.controllers.beans;

import java.io.Serializable;

import org.bakery.server.domain.BusinessEntity;

public abstract class AbstractFormBean implements Serializable{
	/**
	 * Pattern to search named entities. For example, '%' searches for all
	 */
	private String searchNamePattern;
	
	/**
	 * Entity to start from. 0 is first.
	 */
	private Integer searchStartFrom;
	
	/**
	 * Max results to find.
	 */
	private Integer searchMaxResults;
	
	/**
	 * Id for entity deletion operation
	 */
	private Long idForDeletion;
	
	/**
	 * Id for entity restore operation
	 */
	private Long idForRestore;
	
	/**
	 * Form mode in which it was submited
	 */
	private AbstractFormMode mode;
	
	/**
	 * Main form object. For example, form for editing Units will have Unit instance here
	 */
	private BusinessEntity formBean;
	
	
	public String getSearchNamePattern() {
		return searchNamePattern;
	}
	public void setSearchNamePattern(String searchNamePattern) {
		this.searchNamePattern = searchNamePattern;
	}
	public Integer getSearchStartFrom() {
		return searchStartFrom;
	}
	public void setSearchStartFrom(Integer searchStartFrom) {
		this.searchStartFrom = searchStartFrom;
	}
	public Integer getSearchMaxResults() {
		return searchMaxResults;
	}
	public void setSearchMaxResults(Integer searchMaxResults) {
		this.searchMaxResults = searchMaxResults;
	}
	public Long getIdForDeletion() {
		return idForDeletion;
	}
	public void setIdForDeletion(Long idForDeletion) {
		this.idForDeletion = idForDeletion;
	}
	public Long getIdForRestore() {
		return idForRestore;
	}
	public void setIdForRestore(Long idForRestore) {
		this.idForRestore = idForRestore;
	}
	public AbstractFormMode getMode() {
		return mode;
	}
	public void setMode(AbstractFormMode mode) {
		this.mode = mode;
	}
	public BusinessEntity getFormBean() {
		return formBean;
	}
	public void setFormBean(BusinessEntity formBean) {
		this.formBean = formBean;
	}
}
