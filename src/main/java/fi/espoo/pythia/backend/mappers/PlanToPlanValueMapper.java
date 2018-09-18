package fi.espoo.pythia.backend.mappers;

import java.util.ArrayList;
import java.util.List;

import fi.espoo.pythia.backend.repos.entities.Ptext;
import fi.espoo.pythia.backend.repos.entities.Plan;
import fi.espoo.pythia.backend.repos.entities.ProjectUpdate;
import fi.espoo.pythia.backend.transfer.PtextValue;
import fi.espoo.pythia.backend.transfer.PlanValue;

public class PlanToPlanValueMapper {

	public static PlanValue planToPlanValue(Plan p, ProjectUpdate project) {

		// get project_id
		PlanValue pv = new PlanValue();

		pv.setPlanId(p.getPlanId());
		pv.setProjectId(project.getProjectId());
		pv.setMainNo(p.getMainNo());
		pv.setSubNo(p.getSubNo());
		pv.setVersion(p.getVersion());
		pv.setPdfUrl(p.getPdfUrl());
		pv.setXmlUrl(p.getXmlUrl());
		pv.setStatus(p.getStatus());

		try {
			List<PtextValue> ptextValues = new ArrayList<PtextValue>();
			for (Ptext c : p.getPtextList()) {
				ptextValues.add(PtextToPtextValueMapper.ptextToPtextValue(c, p));
			}
			pv.setCommentValues(ptextValues);
		} catch (java.lang.NullPointerException e) {
			pv.setCommentValues(new ArrayList<PtextValue>());
		}

		pv.setCreatedAt(p.getCreatedAt());
		pv.setCreatedBy(p.getCreatedBy());
		pv.setUpdatedAt(p.getUpdatedAt());
		pv.setUpdatedBy(p.getUpdatedBy());
		pv.setDeleted(p.isDeleted());
		pv.setMaintenanceDuty(p.isMaintenanceDuty());
		pv.setStreetManagementDecision(p.getStreetManagementDecision());
		return pv;
	}

}
