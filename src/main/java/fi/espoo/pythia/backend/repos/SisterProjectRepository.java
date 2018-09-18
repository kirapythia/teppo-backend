package fi.espoo.pythia.backend.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import fi.espoo.pythia.backend.repos.entities.Project;
import fi.espoo.pythia.backend.repos.entities.SisterProject;

public interface SisterProjectRepository extends JpaRepository<SisterProject, Long>{

	    List<SisterProject> deleteByProject(Project project);

}
