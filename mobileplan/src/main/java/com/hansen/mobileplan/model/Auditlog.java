package com.hansen.mobileplan.model;

import java.util.Date;

//It's just a POJO not entity class
public class Auditlog {
	
	private Long id;
	private String operationType;
	private String entityJson;
	private String modificationDate;
	
	public Auditlog() {
		super();
	}

	public Auditlog(String operationType, String entityJson , String string) {
		super();
		this.operationType = operationType;
		this.entityJson = entityJson;
		this.modificationDate = string;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getEntityJson() {
		return entityJson;
	}

	public void setEntityJson(String entityJson) {
		this.entityJson = entityJson;
	}

	public String getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}

}