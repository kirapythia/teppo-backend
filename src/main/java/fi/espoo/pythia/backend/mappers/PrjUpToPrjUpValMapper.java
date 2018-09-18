package fi.espoo.pythia.backend.mappers;

import java.util.ArrayList;
import java.util.List;

import fi.espoo.pythia.backend.repos.entities.Plan;
import fi.espoo.pythia.backend.repos.entities.ProjectUpdate;
import fi.espoo.pythia.backend.repos.entities.SisterProjectUpdate;
import fi.espoo.pythia.backend.transfer.PlanValue;
import fi.espoo.pythia.backend.transfer.ProjectUpdateValue;

public class PrjUpToPrjUpValMapper {
	public static ProjectUpdateValue ProjectUpdateToProjectUpdateValue(ProjectUpdate p) {

		ProjectUpdateValue pv = new ProjectUpdateValue();
		pv.setProjectId(p.getProjectId());
		pv.setHansuProjectId(p.getHansuProjectId());
		pv.setMainNo(p.getMainNo());
		pv.setName(p.getName());
		pv.setDescription(p.getDescription());
		pv.setCompleted(p.isCompleted());
		pv.setCreatedAt(p.getCreatedAt());
		pv.setCreatedBy(p.getCreatedBy());
		pv.setUpdatedAt(p.getUpdatedAt());
		pv.setUpdatedBy(p.getUpdatedBy());

		List<PlanValue> planvs = new ArrayList<PlanValue>();
		for (Plan pp : p.getPlans()) {
			System.out.println("Planid:" + pp.getPlanId());
			planvs.add(PlanToPlanValueMapper.planToPlanValue(pp, p));
		}
		pv.setPlans(planvs);


		
		// -------------------------------------------

		List<Long> sisterProjectIds = new ArrayList<Long>();

		for (SisterProjectUpdate pm : p.getSisterProjects()) {
			sisterProjectIds.add(pm.getSisterProjectId());
			System.out.println("id:" + pm.getSisterProjectId());
		}

		pv.setSisterProjects(sisterProjectIds);

		return pv;
	}
}
