package fi.espoo.pythia.backend.transfer;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class ProjectUpdateValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long projectId;
	private String hansuProjectId;
	private String name;
	private short mainNo;
	private String description;
	private List<PlanValue> plans;
	private List<Long> sisterProjects;
	private OffsetDateTime createdAt;
	private String createdBy;
	private OffsetDateTime updatedAt;
	private String updatedBy;
	private boolean completed;
	
	
	public ProjectUpdateValue() {
		
	}
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getHansuProjectId() {
		return hansuProjectId;
	}
	public void setHansuProjectId(String hansuProjectId) {
		this.hansuProjectId = hansuProjectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public short getMainNo() {
		return mainNo;
	}
	public void setMainNo(short mainNo) {
		this.mainNo = mainNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public List<PlanValue> getPlans() {
		return plans;
	}
	public void setPlans(List<PlanValue> plans) {
		this.plans = plans;
	}
	public List<Long> getSisterProjects() {
		return sisterProjects;
	}
	public void setSisterProjects(List<Long> sisterProjects) {
		this.sisterProjects = sisterProjects;
	}
	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(OffsetDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
}
