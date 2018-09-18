package fi.espoo.pythia.backend.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import fi.espoo.pythia.backend.repos.entities.Ptext;
import fi.espoo.pythia.backend.repos.entities.Plan;


public interface PtextRepository extends JpaRepository<Ptext, Long>{
	
	Ptext findByTextId(Long id);

	List<Ptext> findByPlan(Plan plan);

}
