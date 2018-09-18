package fi.espoo.pythia.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.espoo.pythia.backend.repos.entities.Project;

/**
 * 
 * @author jukkak
 *
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

	Project findByProjectId(Long id);
	
	//List<Project> findByProjectId(Long id);
	
	
}
