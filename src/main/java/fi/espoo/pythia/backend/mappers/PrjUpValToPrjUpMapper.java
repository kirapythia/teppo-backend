package fi.espoo.pythia.backend.mappers;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import fi.espoo.pythia.backend.repos.entities.ProjectUpdate;
import fi.espoo.pythia.backend.repos.entities.SisterProjectUpdate;
import fi.espoo.pythia.backend.transfer.ProjectUpdateValue;

public class PrjUpValToPrjUpMapper {

	public static ProjectUpdate projectValue2ToProject(ProjectUpdateValue pv, ProjectUpdate project, boolean updating) {

		// LocalDateTime ZoneOffset

		// current data
		ProjectUpdate p = new ProjectUpdate();

		p.setProjectId(pv.getProjectId());
		p.setHansuProjectId(pv.getHansuProjectId());
		p.setMainNo(pv.getMainNo());
		p.setName(pv.getName());
		p.setDescription(pv.getDescription());
		p.setCompleted(pv.isCompleted());

		if (updating == false) {
			p.setCreatedAt(OffsetDateTime.now());
		} else {
			p.setCreatedAt(pv.getCreatedAt());
		}

		p.setCreatedBy(pv.getCreatedBy());
		p.setUpdatedAt(OffsetDateTime.now());
		p.setUpdatedBy(pv.getUpdatedBy());

		// try {
		// List<Plan> plans = new ArrayList();
		// for (PlanValue pp : pv.getPlans()) {
		// plans.add(PlanValueToPlanMapper.planValueToPlan(pp, p));
		// }
		// p.setPlans(plans);
		// } catch (java.lang.NullPointerException e) {
		// p.setPlans(new ArrayList<Plan>());
		// }

		try {
			p.setSisterProjects(project.getSisterProjects());

		} catch (java.lang.NullPointerException e) {
			p.setSisterProjects(new ArrayList<SisterProjectUpdate>());

		}
		// try {
		// List<SisterProject> sProjects = project.getSisterProjects();
		//
		// for (int i = 0; i < pv.getSisterProjects().size(); i++) {
		// System.out.println("pvgetsisterprojectid" +
		// pv.getSisterProjects().get(i));
		// sProjects.get(i).setSisterProjectId(pv.getSisterProjects().get(i));
		// }
		// p.setSisterProjects(sProjects);
		// } catch (java.lang.NullPointerException e) {
		// p.setSisterProjects(project.getSisterProjects());
		// } catch (java.lang.IndexOutOfBoundsException e) {
		// p.setSisterProjects(project.getSisterProjects());
		// }

		return p;
	}
}
