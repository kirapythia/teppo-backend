package fi.espoo.pythia.backend.mappers;

import fi.espoo.pythia.backend.transfer.LatestPlansValue;
import fi.espoo.pythia.backend.transfer.PlanValue;

public class PlanValue2LPValueMapper {

	public static LatestPlansValue planValue2LPValue(PlanValue pv){
		
		LatestPlansValue lpv = new LatestPlansValue();
		lpv.setCommentValues(pv.getCommentValues());
		lpv.setCreatedAt(pv.getCreatedAt());
		lpv.setCreatedBy(pv.getCreatedBy());
		lpv.setDeleted(pv.isDeleted());
		lpv.setMainNo(pv.getMainNo());
		lpv.setMaintenanceDuty(pv.isMaintenanceDuty());
		lpv.setPdfUrl(pv.getPdfUrl());
		lpv.setPlanId(pv.getPlanId());
		lpv.setProjectId(pv.getProjectId());
		lpv.setStatus(pv.getStatus());
		lpv.setSubNo(pv.getSubNo());
		lpv.setUpdatedAt(pv.getUpdatedAt());
		lpv.setUpdatedBy(pv.getUpdatedBy());
		lpv.setVersion(pv.getVersion());
		lpv.setXmlUrl(pv.getXmlUrl());
		lpv.setStreetManagementDecision(pv.getStreetManagementDecision());
		return lpv;
	}
}
