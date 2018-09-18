package fi.espoo.pythia.backend.mappers;

import java.util.ArrayList;
import java.util.List;

import fi.espoo.pythia.backend.repos.entities.Ptext;
import fi.espoo.pythia.backend.repos.entities.LatestPlans;
import fi.espoo.pythia.backend.repos.entities.Project;
import fi.espoo.pythia.backend.transfer.PtextValue;
import fi.espoo.pythia.backend.transfer.LatestPlansValue;

public class LPToLPValueMapper {

	public static LatestPlansValue lpTolpValue(LatestPlans p, Project project) {

		// get project_id
		LatestPlansValue pv = new LatestPlansValue();

		pv.setPlanId(p.getPlanId());
		pv.setProjectId(project.getProjectId());
		pv.setMainNo(p.getMainNo());
		pv.setSubNo(p.getSubNo());
		pv.setVersion(p.getVersion());
		pv.setPdfUrl(p.getPdfUrl());
		pv.setXmlUrl(p.getXmlUrl());
		pv.setStatus(p.getStatus());

		List<PtextValue> commentValues = new ArrayList<PtextValue>();
		for (Ptext c : p.getPtextList()) {
			commentValues.add(PtextToPtextValueMapper.ptextToPtextValue(c, p));
		}
		pv.setCommentValues(commentValues);

		pv.setCreatedAt(p.getCreatedAt());
		pv.setCreatedBy(p.getCreatedBy());
		pv.setUpdatedAt(p.getUpdatedAt());
		pv.setUpdatedBy(p.getUpdatedBy());
		pv.setMaintenanceDuty(p.isMaintenanceDuty());
		pv.setStreetManagementDecision(p.getStreetManagementDecision());

		return pv;
	}
}
