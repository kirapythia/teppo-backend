package fi.espoo.pythia.backend.mappers;


import fi.espoo.pythia.backend.repos.entities.Project;
import fi.espoo.pythia.backend.repos.entities.SisterProject;
import fi.espoo.pythia.backend.transfer.SisterProjectValue;

public class SisterProjectValueToSisterProjectMapper {

	public static SisterProject SisterProjectValueToSisterProject(SisterProjectValue spv, Project project) {
	
		SisterProject p = new SisterProject();
		
		p.setId(spv.getId());
		p.setProject(project);
		p.setSisterProjectId(spv.getSisterProjectId());
		
				
//		p.setCreatedAt(pv.getCreatedAt());
//		p.setCreatedBy(pv.getCreatedBy());
//		p.setUpdatedAt(pv.getUpdatedAt());
//		p.setUpdatedBy(pv.getUpdatedBy());
		
		return p;
	}

}
