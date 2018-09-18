//package fi.espoo.pythia.backend.repos.entities;
//
//import java.io.Serializable;
//
//
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Table;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Column;
//
//@Entity
//@Table(name = "project_file")
//public class ProjectFile implements Serializable {
//	
//	private static final long serialVersionUID = 1L;
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projectfile_generator")
//	@SequenceGenerator(name = "projectfile_generator", sequenceName = "projectfile_serial", allocationSize = 1)
//	@Column(name = "projectfile_id", updatable = false, nullable = false)
//	private Long projectfileId;
//	
//	@Column(name = "url")
//	private String url;
//	
//	@ManyToOne
//	@JoinColumn(name = "project_id")
//	private ProjectUpdate project;
//	
//	@Enumerated(EnumType.STRING)
//	private FileType fileType;
//
//	public Long getProjectfileId() {
//		return projectfileId;
//	}
//
//	public void setProjectfileId(Long projectfileId) {
//		this.projectfileId = projectfileId;
//	}
//
//	public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}
//
//	public ProjectUpdate getProject() {
//		return project;
//	}
//
//	public void setProject(ProjectUpdate project) {
//		this.project = project;
//	}
//
//	public FileType getFileType() {
//		return fileType;
//	}
//
//	public void setFileType(FileType fileType) {
//		this.fileType = fileType;
//	}
//	
//	
//	
//	
//}
