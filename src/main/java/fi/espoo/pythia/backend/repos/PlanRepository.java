package fi.espoo.pythia.backend.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.espoo.pythia.backend.repos.entities.Plan;
import fi.espoo.pythia.backend.repos.entities.ProjectUpdate;

public interface PlanRepository extends JpaRepository<Plan, Long> {

	Plan findByPlanId(Long id);
	
	// get all plans with planV.projectId and planV.mainNo & planV.subNo
	List<Plan> findByProjectInAndMainNoInAndSubNoIn(ProjectUpdate p, short mainNo, short subNo);
	
	
}
