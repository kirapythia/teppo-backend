package fi.espoo.pythia.backend.repos.entities;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// bigint
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proj_generator")
	@SequenceGenerator(name = "proj_generator", sequenceName = "proj_serial", allocationSize = 1)
	@Column(name = "project_id", updatable = false, nullable = false)
	private Long projectId;

	// Bidirectional oneToMany with Plan
	/**
	 * public class Plan { ...
	 * 
	 * @ManyToOne
	 * @JoinColumn(name = "project_id") private Project project; ... }
	 */

	// removed ArrayList definition
	@OneToMany(mappedBy = "project")
	private List<LatestPlans> plans;

	@OneToMany(mappedBy = "project")
	private List<SisterProject> sisterProjects;

	// varchar
	@Column(name = "hansu_project_id")
	private String hansuProjectId;

	// smallint
	@Column(name = "main_no")
	private short mainNo;

	// varchar
	@Column(name = "name")
	private String name;

	// varchar
	@Column(name = "description")
	private String description;

	@Column(name = "completed")
	private boolean completed;

	// // https://jdbc.postgresql.org/documentation/head/java8-date-time.html
	// // timestamp with timezone
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private OffsetDateTime createdAt;

	// varchar
	@Column(name = "created_by")
	private String createdBy;

	// timestamp with timezone timestamptz
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private OffsetDateTime updatedAt;

	// varchar
	@Column(name = "updated_by")
	private String updatedBy;

	public Project() {

	}

	public List<SisterProject> getSisterProjects() {
		return sisterProjects;
	}

	public void setSisterProjects(List<SisterProject> sisterProjects) {
		this.sisterProjects = sisterProjects;
	}

	public List<LatestPlans> getPlans() {
		return plans;
	}

	public void setPlans(List<LatestPlans> plans) {
		this.plans = plans;
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

	public short getMainNo() {
		return mainNo;
	}

	public void setMainNo(short mainNo) {
		this.mainNo = mainNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}

	// public void addPlan(Plan plan) {
	// plans.add(plan);
	// plan.setProject(this);
	// }
	//
	// public void removePlan(Plan plan) {
	// plans.remove(plan);
	// plan.setProject(null);
	// }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
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
		Project other = (Project) obj;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		return true;
	}

}
