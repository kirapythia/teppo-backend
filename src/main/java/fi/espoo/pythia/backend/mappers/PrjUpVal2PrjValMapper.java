package fi.espoo.pythia.backend.mappers;

import java.util.ArrayList;
import java.util.List;

import fi.espoo.pythia.backend.repos.entities.LatestPlans;
import fi.espoo.pythia.backend.repos.entities.Project;
import fi.espoo.pythia.backend.transfer.LatestPlansValue;
import fi.espoo.pythia.backend.transfer.PlanValue;
import fi.espoo.pythia.backend.transfer.ProjectUpdateValue;
import fi.espoo.pythia.backend.transfer.ProjectValue2;

public class PrjUpVal2PrjValMapper {

	public static ProjectValue2 projectUpdateValue2ProjectValue(ProjectUpdateValue projectUpdate) {
	
		ProjectValue2 project = new ProjectValue2();
		//FIX
		project.setProjectId(projectUpdate.getProjectId());
		project.setHansuProjectId(projectUpdate.getHansuProjectId());
		project.setName( projectUpdate.getName());
		project.setMainNo(projectUpdate.getMainNo()); 
		project.setDescription(projectUpdate.getDescription());
		project.setCompleted(projectUpdate.isCompleted());
		
		project.setSisterProjects(projectUpdate.getSisterProjects()); 
		project.setCreatedAt(projectUpdate.getCreatedAt());
		project.setCreatedBy(projectUpdate.getCreatedBy());
		project.setUpdatedAt(projectUpdate.getUpdatedAt());
		project.setUpdatedBy(projectUpdate.getUpdatedBy());
		// removed ArrayList definition
		//private List<PlanValue> plans;
		List<PlanValue> planValues = projectUpdate.getPlans();
		List<LatestPlansValue> plans = new ArrayList<LatestPlansValue>();
		for(PlanValue p : planValues){
		// planvalues to latestplansvalue	
			LatestPlansValue lpv = PlanValue2LPValueMapper.planValue2LPValue(p);
			plans.add(lpv);
		}
		
		project.setPlans(plans);
				
		return project;
		
	}
}
