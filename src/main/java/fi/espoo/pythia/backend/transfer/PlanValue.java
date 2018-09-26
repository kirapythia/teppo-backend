package fi.espoo.pythia.backend.transfer;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

import fi.espoo.pythia.backend.repos.entities.Status;

public class PlanValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long planId;

	private Long projectId;

	private short mainNo;

	private short subNo;

	private short version;

	private String pdfUrl;
	
	private String xmlUrl;

	private String dwgUrl;

	private String dxfUrl;

	private String svgUrl;

	private String status;

	private OffsetDateTime createdAt;

	private String createdBy;

	private OffsetDateTime updatedAt;

	private String updatedBy;

	private boolean deleted;

	List<PtextValue> commentValues;
	
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

	public String getDwgUrl() {
		return dwgUrl;
	}

	public void setDwgUrl(String dwgUrl) {
		this.dwgUrl = dwgUrl;
	}

	public String getDxfUrl() {
		return dxfUrl;
	}

	public void setDxfUrl(String dxfUrl) {
		this.dxfUrl = dxfUrl;
	}

	public String getSvgUrl() {
		return svgUrl;
	}

	public void setSvgUrl(String svgUrl) {
		this.svgUrl = svgUrl;
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
