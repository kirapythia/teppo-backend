package fi.espoo.pythia.backend.mappers;

import fi.espoo.pythia.backend.repos.entities.Plan;
import fi.espoo.pythia.backend.repos.entities.ProjectUpdate;
import fi.espoo.pythia.backend.transfer.PlanValue;

public class PlanToPlanValueMapper {

	public static PlanValue planToPlanValue(Plan p, ProjectUpdate project) {
		
		//get project_id 
		PlanValue pv = new PlanValue();
		
		pv.setPlanId(p.getPlanId());
		pv.setProjectId(project.getProjectId());
		pv.setMainNo(p.getMainNo());
		pv.setSubNo(p.getSubNo());
		pv.setVersion(p.getVersion());
		pv.setUrl(p.getUrl());
		pv.setApproved(p.isApproved());
				
		pv.setCreatedAt(p.getCreatedAt());
		pv.setCreatedBy(p.getCreatedBy());
		pv.setUpdatedAt(p.getUpdatedAt());
		pv.setUpdatedBy(p.getUpdatedBy());
		
		return pv;
	}
	
}
