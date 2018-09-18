package fi.espoo.pythia.backend.repos.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import fi.espoo.pythia.backend.repos.ProjectRepository;
import fi.espoo.pythia.backend.repos.entities.LatestPlans;
import fi.espoo.pythia.backend.repos.entities.Project;
import fi.espoo.pythia.backend.repos.entities.SisterProject;

//@RunWith(SpringRunner.class) is used to provide a bridge between 
//Spring Boot test features and JUnit. 
//Whenever we are using any Spring Boot testing features in out JUnit tests, 
//this annotation will be required.
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProjectRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private ProjectRepository projectRepository;
    
    @Test
    public void whenFindById_thenReturnProject() {
        // given
        Project project = new Project();
        project.setCompleted(false);
        project.setCreatedAt(null);
        project.setCreatedBy(null);
        project.setDescription("");
        project.setHansuProjectId("E2222");
        project.setPlans(new ArrayList<LatestPlans>());
        project.setMainNo((short) 2345);
        project.setName("Bert's project");
        //project.setProjectId(1L);
        project.setSisterProjects(new ArrayList<SisterProject>());
        project.setUpdatedAt(null);
        project.setUpdatedBy(null);
        
        
        entityManager.persist(project);
        entityManager.flush();
     
        // when
        Project found = projectRepository.findByProjectId(project.getProjectId());
        
        // then
        assertThat(found.getName())
          .isEqualTo(project.getName());
    }
}


