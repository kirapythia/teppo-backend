package fi.espoo.pythia.backend.transfer;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Column;

import fi.espoo.pythia.backend.repos.entities.Status;

public class LatestPlansValue {

	private Long planId;

	private Long projectId;

	private short mainNo;

	private short subNo;

	private short version;

	private String pdfUrl;

	private String xmlUrl;

	private String status;

	private List<PtextValue> commentValues;

	private OffsetDateTime createdAt;

	private String createdBy;

	private OffsetDateTime updatedAt;

	private String updatedBy;

	private boolean deleted;

	private boolean maintenanceDuty;
	
	private OffsetDateTime streetManagementDecision;

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public short getMainNo() {
		return mainNo;
	}

	public void setMainNo(short mainNo) {
		this.mainNo = mainNo;
	}

	public short getSubNo() {
		return subNo;
	}

	public void setSubNo(short subNo) {
		this.subNo = subNo;
	}

	public short getVersion() {
		return version;
	}

	public void setVersion(short version) {
		this.version = version;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}


	public String getXmlUrl() {
		return xmlUrl;
	}

	public void setXmlUrl(String xmlUrl) {
		this.xmlUrl = xmlUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<PtextValue> getCommentValues() {
		return commentValues;
	}

	public void setCommentValues(List<PtextValue> commentValues) {
		this.commentValues = commentValues;
	}

	public boolean isMaintenanceDuty() {
		return maintenanceDuty;
	}

	public void setMaintenanceDuty(boolean maintenanceDuty) {
		this.maintenanceDuty = maintenanceDuty;
	}

	public OffsetDateTime getStreetManagementDecision() {
		return streetManagementDecision;
	}

	public void setStreetManagementDecision(OffsetDateTime streetManagementDecision) {
		this.streetManagementDecision = streetManagementDecision;
	}

    

	
}
