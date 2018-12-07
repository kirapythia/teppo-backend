/**
 * comment_id bigint NOT NULL,
  text character varying,
  plan_id bigint,
  url character varying,
  approved boolean,
  created_at timestamp with time zone,
  created_by character varying,
  updated_at timestamp with time zone,
  updated_by character varying,
 */
package fi.espoo.pythia.backend.repos.entities;

import java.io.Serializable;
//import java.time.OffsetDateTime;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ptext")
public class Ptext implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ptext_generator")
	@SequenceGenerator(name = "ptext_generator", sequenceName = "ptext_serial", allocationSize = 1)
	@Column(name = "ptext_id", updatable = false, nullable = false)
	private Long ptextId;

	@ManyToOne
	@JoinColumn(name = "plan_id")
	private Plan plan;

	@Column(name = "ptext")
	private String ptext;

	@Column(name = "approved")
	private boolean approved;

	@Column(name = "approved_at")
	private OffsetDateTime approvedAt;

	@Column(name = "approved_by")
	private String approvedBy;

	@Column(name = "url")
	private String url;

	@Column(name = "created_at")
	private OffsetDateTime createdAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_at")
	private OffsetDateTime updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "xcoord")
	private Double x;

	@Column(name = "ycoord")
	private Double y;

	@Column(name = "xwidth")
	private Double width;

	@Column(name = "yheight")
	private Double height;

	public Ptext() {

	}

	public Long getPtextId() {
		return ptextId;
	}

	public void setPtextId(Long ptextId) {
		this.ptextId = ptextId;
	}

	@JsonIgnore
	public Plan getPlan() {
		return plan;
	}

	@JsonIgnore
	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public String getPtext() {
		return ptext;
	}

	public void setPtext(String ptext) {
		this.ptext = ptext;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public OffsetDateTime getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(OffsetDateTime approvedAt) {
		this.approvedAt = approvedAt;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ptextId == null) ? 0 : ptextId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ptext other = (Ptext) obj;
		if (ptextId == null) {
			if (other.ptextId != null)
				return false;
		} else if (!ptextId.equals(other.ptextId))
			return false;
		return true;
	}

}
