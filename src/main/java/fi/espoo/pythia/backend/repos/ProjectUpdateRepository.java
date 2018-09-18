package fi.espoo.pythia.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import fi.espoo.pythia.backend.repos.entities.ProjectUpdate;

public interface ProjectUpdateRepository extends JpaRepository<ProjectUpdate, Long>{

	ProjectUpdate findByProjectId(Long id);
}
