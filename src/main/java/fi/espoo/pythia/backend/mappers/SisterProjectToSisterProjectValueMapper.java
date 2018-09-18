package fi.espoo.pythia.backend.mappers;

import fi.espoo.pythia.backend.repos.entities.Project;
import fi.espoo.pythia.backend.repos.entities.SisterProject;
import fi.espoo.pythia.backend.transfer.SisterProjectValue;

public class SisterProjectToSisterProjectValueMapper {

	public static SisterProjectValue sisterProjectToSisterProjectValue(SisterProject pm, Project p) {

		
		SisterProjectValue pv = new SisterProjectValue();
		
		pv.setId(pm.getId());
		pv.setProjectId(p.getProjectId());
		pv.setSisterProjectId(pm.getSisterProjectId());
				
//		pv.setCreatedAt(p.getCreatedAt());
//		pv.setCreatedBy(p.getCreatedBy());
//		pv.setUpdatedAt(p.getUpdatedAt());
//		pv.setUpdatedBy(p.getUpdatedBy());
		
		return pv;
	}

}
