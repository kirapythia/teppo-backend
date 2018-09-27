package fi.espoo.pythia.backend.repos.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import fi.espoo.pythia.backend.repos.PlanRepository;

//@RunWith(SpringRunner.class) is used to provide a bridge between 
//Spring Boot test features and JUnit. 
//Whenever we are using any Spring Boot testing features in out JUnit tests, 
//this annotation will be required.
@RunWith(SpringRunner.class)
@DataJpaTest
public class PlanRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PlanRepository planRepository;

	@Test
	public void whenFindAll_thenReturnPlan() {
		// given
		ProjectUpdate project = new ProjectUpdate();
		project.setCompleted(false);
		project.setCreatedAt(null);
		project.setCreatedBy(null);
		project.setDescription("");
		project.setHansuProjectId("E2222");
		project.setPlans(new ArrayList<Plan>());
		project.setMainNo((short) 2345);
		project.setName("Bert's  project");
		// project.setProjectId(1L); AUTOGENERATED
		project.setSisterProjects(new ArrayList<SisterProjectUpdate>());
		project.setUpdatedAt(null);
		project.setUpdatedBy(null);

		Plan plan = new Plan();

		// detached entity . autogenerated id from sequence. persist fails with
		// an ERROR.
		// plan.setPlanId(1L);
		plan.setStatus(Status.APPROVED);
		plan.setPtextList(null);
		plan.setCreatedAt(null);
		plan.setDeleted(false);
		plan.setMainNo((short) 1234);
		plan.setProject(project);
		plan.setSubNo((short) 0);
		plan.setUpdatedAt(null);
		plan.setUpdatedBy(null);
		plan.setPdfUrl(null);
		plan.setVersion((short) 0);

		entityManager.persist(project);
		entityManager.flush();

		entityManager.persist(plan);
		entityManager.flush();
		//
		// // when
		// List<Plan> foundPlans = planRepository.findAll();
		//
		// // then
		// assertThat(foundPlans.get(0).getMainNo()).isEqualTo(plan.getMainNo());
	}
}