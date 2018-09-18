package fi.espoo.pythia.backend.mappers;

import fi.espoo.pythia.backend.repos.entities.Ptext;
import fi.espoo.pythia.backend.repos.entities.LatestPlans;
import fi.espoo.pythia.backend.repos.entities.Plan;
import fi.espoo.pythia.backend.transfer.PtextValue;

public class PtextToPtextValueMapper {

	public static PtextValue ptextToPtextValue(Ptext c, Plan plan) {
		PtextValue cv = new PtextValue();

		cv.setTextId(c.getTextId());
		cv.setPlanId(plan.getPlanId());
		cv.setPtext(c.getPtext());
		cv.setApproved(c.isApproved());
		cv.setUrl(c.getUrl());
		cv.setCreatedAt(c.getCreatedAt());
		cv.setCreatedBy(c.getCreatedBy());
		cv.setUpdatedAt(c.getUpdatedAt());
		cv.setUpdatedBy(c.getUpdatedBy());

		return cv;
	}

	public static PtextValue ptextToPtextValue(Ptext c, LatestPlans plan) {
		PtextValue cv = new PtextValue();

		cv.setTextId(c.getTextId());
		cv.setPlanId(plan.getPlanId());
		cv.setPtext(c.getPtext());
		cv.setApproved(c.isApproved());
		cv.setUrl(c.getUrl());
		cv.setCreatedAt(c.getCreatedAt());
		cv.setCreatedBy(c.getCreatedBy());
		cv.setUpdatedAt(c.getUpdatedAt());
		cv.setUpdatedBy(c.getUpdatedBy());

		return cv;
	}

}
