package fi.espoo.pythia.backend.repos.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sister_project")
public class SisterProjectUpdate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "map_generator")
	@SequenceGenerator(name = "map_generator", sequenceName = "pmap_serial", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private ProjectUpdate project;

	@Column(name = "sister_project_id")
	private Long sisterProjectId;

	
	public SisterProjectUpdate() {
	}

	public SisterProjectUpdate(Long id, ProjectUpdate project, Long sisterProjectId) {		
		this.id = id;
		this.project = project;
		this.sisterProjectId = sisterProjectId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjectUpdate getProject() {
		return project;
	}

	public void setProject(ProjectUpdate project) {
		this.project = project;
	}

	public Long getSisterProjectId() {
		return sisterProjectId;
	}

	public void setSisterProjectId(Long sisterProjectId) {
		this.sisterProjectId = sisterProjectId;
	}
	
	
	

}
