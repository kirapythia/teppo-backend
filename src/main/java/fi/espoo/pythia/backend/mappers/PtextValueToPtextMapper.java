package fi.espoo.pythia.backend.mappers;


import fi.espoo.pythia.backend.repos.entities.Plan;

import java.time.OffsetDateTime;

import fi.espoo.pythia.backend.repos.entities.Ptext;
import fi.espoo.pythia.backend.transfer.PtextValue;

public class PtextValueToPtextMapper {

	public static Ptext commentValueToComment(PtextValue cv, Plan plan, boolean approved, boolean updating) {
		Ptext c = new Ptext();

		c.setPtextId(cv.getPtextId());
		c.setPlan(plan);
		c.setPtext(cv.getPtext());
		c.setApproved(approved);
		c.setApprovedAt(OffsetDateTime.now());
		c.setApprovedBy(cv.getUpdatedBy());
		c.setUrl(cv.getUrl());

		if (updating == false) {
			c.setCreatedAt(OffsetDateTime.now());
		} else {
			c.setCreatedAt(c.getCreatedAt());
		}

		c.setCreatedBy(cv.getCreatedBy());
		c.setUpdatedAt(OffsetDateTime.now());
		c.setUpdatedBy(cv.getUpdatedBy());
		c.setX(cv.getX());
		c.setY(cv.getY());
		c.setWidth(cv.getWidth());
		c.setHeight(cv.getHeight());

		return c;
	}
}
