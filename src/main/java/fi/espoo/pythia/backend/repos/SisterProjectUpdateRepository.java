package fi.espoo.pythia.backend.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.espoo.pythia.backend.repos.entities.ProjectUpdate;
import fi.espoo.pythia.backend.repos.entities.SisterProjectUpdate;

public interface SisterProjectUpdateRepository extends JpaRepository<SisterProjectUpdate, Long>{

    List<SisterProjectUpdate> deleteByProject(ProjectUpdate projectUp);

}
