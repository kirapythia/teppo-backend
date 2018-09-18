package fi.espoo.pythia.backend.mappers;


import fi.espoo.pythia.backend.repos.entities.Plan;

import java.time.OffsetDateTime;

import fi.espoo.pythia.backend.repos.entities.Ptext;
import fi.espoo.pythia.backend.transfer.PtextValue;

public class PtextValueToPtextMapper {

	public static Ptext commentValueToComment(PtextValue cv, Plan plan, boolean approved, boolean updating) {
		Ptext c = new Ptext();

		//c.setCommentId(cv.getCommentId());
		c.setPlan(plan);
		c.setPtext(cv.getPtext());
		c.setApproved(approved);
		c.setUrl(cv.getUrl());

		if (updating == false) {
			c.setCreatedAt(OffsetDateTime.now());
		} else {
			c.setCreatedAt(c.getCreatedAt());
		}

		c.setCreatedBy(cv.getCreatedBy());
		c.setUpdatedAt(OffsetDateTime.now());
		c.setUpdatedBy(cv.getUpdatedBy());

		return c;
	}
}
