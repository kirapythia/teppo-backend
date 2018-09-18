package fi.espoo.pythia.backend.mappers;

import java.util.ArrayList;
import java.util.List;

import fi.espoo.pythia.backend.repos.entities.LatestPlans;
import fi.espoo.pythia.backend.repos.entities.Project;
import fi.espoo.pythia.backend.repos.entities.SisterProject;
import fi.espoo.pythia.backend.transfer.LatestPlansValue;
import fi.espoo.pythia.backend.transfer.ProjectValue2;

public class PrjToPrjVal2Mapper {

	public static ProjectValue2 ProjectToProjectValue2(Project p) {

		ProjectValue2 pv = new ProjectValue2();
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

//		List<PlanValue> planvs = new ArrayList();
//		for (Plan pp : p.getPlans()) {
//			System.out.println("Planid:" + pp.getPlanId());
//			planvs.add(PlanToPlanValueMapper.planToPlanValue(pp, p));
//		}
//		pv.setPlans(planvs);

		
		//Latest plans
		
		List<LatestPlansValue> lplanvs = new ArrayList<LatestPlansValue>();
		for (LatestPlans pp : p.getPlans()) {
			System.out.println("Planid:" + pp.getPlanId());
			lplanvs.add(LPToLPValueMapper.lpTolpValue(pp, p));
		}
		pv.setPlans(lplanvs);

		
		// -------------------------------------------

		List<Long> sisterProjectIds = new ArrayList<Long>();

		for (SisterProject pm : p.getSisterProjects()) {
			sisterProjectIds.add(pm.getSisterProjectId());
			System.out.println("id:" + pm.getSisterProjectId());
		}

		pv.setSisterProjects(sisterProjectIds);

		return pv;
	}

}
