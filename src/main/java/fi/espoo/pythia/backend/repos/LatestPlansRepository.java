package fi.espoo.pythia.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.espoo.pythia.backend.repos.entities.LatestPlans;

public interface LatestPlansRepository extends JpaRepository<LatestPlans, Long>{

	LatestPlans findByPlanId(Long id);

}
