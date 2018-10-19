/**
 * input validation (kantaan ei saa mennä kuin valvottuja arvoja)
 * 
 * päätös on tehty
 * suunnitelma on hyväksytty
 * suunnitelma on muutettu
 * 
 * 
 * 
 * http://www.springboottutorial.com/unit-testing-for-spring-boot-rest-services
 * 
 *  When posting multipart file add size by tweaking both 
 *  1)src/main/resources application.propeties and 
 *  2)PythiaBackendApplication.class Tomcat setMaxPostSize Bean  
 *  
 *  
 *   
 */

package fi.espoo.pythia.backend.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fi.espoo.pythia.backend.mappers.PrjUpVal2PrjValMapper;
import fi.espoo.pythia.backend.mgrs.S3Manager;
import fi.espoo.pythia.backend.mgrs.SESManager;
import fi.espoo.pythia.backend.mgrs.StorageManager;
import fi.espoo.pythia.backend.repos.entities.ProjectUpdate;
import fi.espoo.pythia.backend.repos.entities.Status;
import fi.espoo.pythia.backend.transfer.PlanValue;
import fi.espoo.pythia.backend.transfer.ProjectUpdateValue;
import fi.espoo.pythia.backend.transfer.ProjectValue2;
import fi.espoo.pythia.backend.transfer.PtextValue;
import fi.espoo.pythia.backend.validators.PlanValidator;

@RestController
@RequestMapping("/pythia/v1")
public class StorageRestController {

    @Autowired
    private StorageManager storageManager;

    @Autowired
    private S3Manager s3Manager;

    // -------------------------GET-------------------------------

    /**
     * return all projects 2 latest versions
     *
     * @return
     */
    @GetMapping(value = "/projects", produces = "application/json")
    public ResponseEntity<List<ProjectValue2>> getProjects() {

        try {
            List<ProjectValue2> project = storageManager.getProjects2();
            return new ResponseEntity<List<ProjectValue2>>(project, HttpStatus.OK);
        } catch (java.lang.NullPointerException e) {
            return new ResponseEntity<List<ProjectValue2>>(HttpStatus.NOT_FOUND);
        } catch (org.springframework.transaction.CannotCreateTransactionException e) {
            return new ResponseEntity<List<ProjectValue2>>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * return a single project by id if found. Otherwise return null. 2 latest
     * versions of plans if allPlanVersions is false. All versions if
     * allPlanVersions is true
     */
    @GetMapping(value = "/projects/{projectId}", produces = "application/json")
    public ResponseEntity<ProjectValue2> getProject(@PathVariable("projectId") Long projectId,
            @RequestParam(value = "allPlanVersions", required = false) boolean isAllVersions) {

        try {
            ProjectValue2 project = new ProjectValue2();
            if (isAllVersions) {
                ProjectUpdateValue projectUpdate = storageManager.getProjectAllPlans(projectId);
                project = PrjUpVal2PrjValMapper.projectUpdateValue2ProjectValue(projectUpdate);
            } else {
                project = storageManager.getProject2(projectId);
            }
            return new ResponseEntity<ProjectValue2>(project, HttpStatus.OK);
        } catch (java.lang.NullPointerException e) {
            return new ResponseEntity<ProjectValue2>(new ProjectValue2(), HttpStatus.NOT_FOUND);
        } catch (org.springframework.transaction.CannotCreateTransactionException e) {
            return new ResponseEntity<ProjectValue2>(HttpStatus.FORBIDDEN);
        }

    }

    // /**
    // * return a single project by id if found. All plan versions
    // */
    // @GetMapping(value = "/projects/{projectId}", produces =
    // "application/json")
    // public ResponseEntity<ProjectUpdateValue>
    // getProjectAllPlans(@PathVariable("projectId") Long projectId) {
    //
    //
    // }

    /**
     * return a single project by hansuprojectid if found. Otherwise return
     * null.
     */
    @GetMapping(value = "/projects/hansuprojectid/{hansuProjectId}", produces = "application/json")
    public ResponseEntity<ProjectValue2> getHansuProject(@PathVariable("hansuProjectId") String hansuId) {

        try {
            ProjectValue2 project = storageManager.getProjectByHansuId2(hansuId);
            // System.out.println("project:" + project);
            if (project == null) {
                return new ResponseEntity<ProjectValue2>(project, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<ProjectValue2>(project, HttpStatus.OK);
        } catch (java.lang.NullPointerException e) {
            return new ResponseEntity<ProjectValue2>(new ProjectValue2(), HttpStatus.NOT_FOUND);
        } catch (org.springframework.transaction.CannotCreateTransactionException e) {
            return new ResponseEntity<ProjectValue2>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     *
     * return plans by projectId
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "/projects/{projectId}/plans", produces = "application/json")
    public ResponseEntity<List<PlanValue>> getPlans(@PathVariable("projectId") Long projectId) {

        try {
            List<PlanValue> plan = storageManager.getPlans(projectId);
            return new ResponseEntity<List<PlanValue>>(plan, HttpStatus.OK);
        } catch (java.lang.NullPointerException e) {
            return new ResponseEntity<List<PlanValue>>(HttpStatus.NOT_FOUND);
        } catch (org.springframework.transaction.CannotCreateTransactionException e) {
            return new ResponseEntity<List<PlanValue>>(HttpStatus.FORBIDDEN);
        }

    }

    /**
     *
     * return all comments by planId
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "/projects/{projectId}/plans/{planId}/comments", produces = "application/json")
    public ResponseEntity<List<PtextValue>> getComments(@PathVariable("planId") Long planId) {

        try {
            List<PtextValue> commList = storageManager.getComments(planId);
            return new ResponseEntity<List<PtextValue>>(commList, HttpStatus.OK);
        } catch (java.lang.NullPointerException e) {
            return new ResponseEntity<List<PtextValue>>(HttpStatus.NOT_FOUND);
        } catch (org.springframework.transaction.CannotCreateTransactionException e) {
            return new ResponseEntity<List<PtextValue>>(HttpStatus.FORBIDDEN);
        }

    }

    // --------------------------POST-------------------------------------

    /**
     * create a new project to the db and return the whole project with all
     * attributes
     *
     * @param projectValue
     * @return
     */
    @PostMapping(value = "/projects/", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ProjectValue2> createProject(@RequestBody ProjectUpdateValue projectUpVal) {

        // TODO if id the return error
        // Value object mapping
        try {
            ProjectUpdate pu = storageManager.createProject(projectUpVal);
            ProjectValue2 savedProject = storageManager.getProject2(pu.getProjectId());
            return new ResponseEntity<ProjectValue2>(savedProject, HttpStatus.OK);
        } catch (org.springframework.transaction.CannotCreateTransactionException e) {
            return new ResponseEntity<ProjectValue2>(HttpStatus.FORBIDDEN);
        }

    }

    /**
     * Insert first plan file, extract attributes and save them to postgresql
     *
     * @param mfile
     * @param id
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/projects/{projectId}/plans")
    public ResponseEntity<PlanValue> createPlan(@RequestParam("mfile") MultipartFile mfile,
            @PathVariable("projectId") long projectId,
            @RequestParam(value = "version", required = false) boolean isVersion){

        try {
            String fname = mfile.getOriginalFilename();
            if (fname.endsWith(".pdf") || fname.endsWith(".xml") || fname.endsWith(".dwg") || fname.endsWith(".dxf")) {
                // Value object mapping

                String savedImageUrl = "";
                if (isVersion) {
                    // NEW VERSION

                    try {
                        PlanValue planV1 = storageManager.createPlanVersion(mfile, projectId);

                        /* TODO: Voltti does not currently support SES, to be redesigned
                        // Send e-mail of new version
                        ProjectValue2 p = storageManager.getProject2(planV1.getProjectId());
                        String project = p.getName();
                        String projectSId = p.getProjectId().toString();
                        SESManager sesManager = new SESManager();
                        sesManager.newVersion(project, projectSId, planV1.getPdfUrl(), planV1.getXmlUrl());
                        */
                        return new ResponseEntity<PlanValue>(planV1, HttpStatus.OK);
                    } catch (IOException e) {
                        return new ResponseEntity<PlanValue>(HttpStatus.FORBIDDEN);
                    }

                } else {
                    // NEW PLAN

                    PlanValue planV;
                    try {
                        planV = storageManager.createUpdatePlan(mfile, projectId);

                        if (planV == null) {
                            return new ResponseEntity<PlanValue>(HttpStatus.CONFLICT);
                        }

                        return new ResponseEntity<PlanValue>(planV, HttpStatus.OK);
                    } catch (IOException e) {
                        return new ResponseEntity<PlanValue>(HttpStatus.FORBIDDEN);
                    }
                }


            } else {
                return new ResponseEntity<PlanValue>(HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (org.springframework.transaction.CannotCreateTransactionException e) {
            return new ResponseEntity<PlanValue>(HttpStatus.FORBIDDEN);
        }

    }

    /**
     * Insert a file into S3 If file pdf or xml add url to plan-table If
     * mainNoR******.*** add url to project_files -table
     *
     * @param mfile
     * @param planId
     * @return
     */
    // , produces = "application/json", consumes = "file"
    @PostMapping(value = "/projects/{projectId}/plans/{planId}/files/")
    public ResponseEntity<PlanValue> createPlanFile(@RequestParam("mfile") MultipartFile mfile,
            @PathVariable("planId") long planId) {

        String fname = mfile.getOriginalFilename();
        try {

            PlanValidator validator = new PlanValidator();
            PlanValue planV = storageManager.getPlan(planId);
            String savedImageUrl = "";

            // 1) no plan found return
            if (planV == null) {
                return new ResponseEntity<PlanValue>(HttpStatus.NOT_FOUND);
            }

            // 2) validate if XML or DWG
            if (fname.endsWith(".pdf") || fname.endsWith(".xml")) {

                // 3.1) compare planV mainNo and subNo
                if (validator.isMainNoAndSubNo(mfile, planV)) {

                    short version = 0;
                    if (!planV.getXmlUrl().isEmpty() && fname.endsWith(".pdf")) {
                        savedImageUrl = s3Manager.createPlanMultipartFile("teppo-plans-dev", mfile, version);
                        // save to plan -table
                        planV.setPdfUrl(savedImageUrl);
                        // update Plan with url
                        storageManager.updatePlan(planV);
                    } else if (!planV.getPdfUrl().isEmpty() && fname.endsWith(".xml")) {
                        savedImageUrl = s3Manager.createPlanMultipartFile("teppo-plans-dev", mfile, version);
                        // save to plan -table
                        planV.setXmlUrl(savedImageUrl);
                        // update Plan with url
                        storageManager.updatePlan(planV);
                    } else {
                        return new ResponseEntity<PlanValue>(HttpStatus.NOT_ACCEPTABLE);
                    }
                    // update Plan url
                }
            }
            if (savedImageUrl.isEmpty() || savedImageUrl == null) {
                return new ResponseEntity<PlanValue>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<PlanValue>(planV, HttpStatus.OK);
        } catch (org.springframework.transaction.CannotCreateTransactionException e) {
            return new ResponseEntity<PlanValue>(HttpStatus.FORBIDDEN);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<PlanValue>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    /**
     * create a new plan to the db and return the whole project with all
     * attributes
     *
     * @param projectValue
     * @return
     */
    @PostMapping(value = "/projects/{projectId}/plans/{planId}/comments", produces = "application/json", consumes = "application/json")
    public ResponseEntity<PtextValue> createComment(@RequestBody PtextValue pTextVal, @PathVariable("planId") long id) {

        // TODO if id the return error
        try {
            PtextValue savedPtext = storageManager.createPtext(pTextVal, id);
            return new ResponseEntity<PtextValue>(savedPtext, HttpStatus.OK);
        } catch (org.springframework.transaction.CannotCreateTransactionException e) {
            return new ResponseEntity<PtextValue>(HttpStatus.FORBIDDEN);
        }

    }

    /**
     *
     * @param mfile
     * @param id
     * @return
     */
    @PostMapping(value = "/projects/{projectId}/plans/{planId}/comments/{commentId}/files", produces = "application/json")
    public ResponseEntity<PtextValue> createCommentFile(@RequestParam("mfile") MultipartFile mfile,
            @PathVariable("commentId") long id) {
        // muutos
        // Value object mapping
        try {
            PtextValue ptextVal = storageManager.getComment(id);
            if (ptextVal == null) {
                return new ResponseEntity<PtextValue>(HttpStatus.NOT_FOUND);
            }
            short version = 0;
            // TODO: store to teppo-comments-dev bucket, when it exists
            String savedImageUrl = s3Manager.createPlanMultipartFile("teppo-plans-dev", mfile, version);
            // set PlanValue url
            ptextVal.setUrl(savedImageUrl);
            // update Plan with url
            storageManager.updatePtext(ptextVal, id);
            if (savedImageUrl.isEmpty() || savedImageUrl == null) {
                return new ResponseEntity<PtextValue>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<PtextValue>(ptextVal, HttpStatus.OK);
        } catch (org.springframework.transaction.CannotCreateTransactionException e) {
            return new ResponseEntity<PtextValue>(HttpStatus.FORBIDDEN);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<PtextValue>(HttpStatus.I_AM_A_TEAPOT);
        }

    }

    // ---------------------------PUT--------------------------------

    /**
     * update plan -table attributes ROLES:
     */

    @PutMapping(value = "/projects/{projectId}/plans/{planId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<PlanValue> updatePlan(@RequestBody PlanValue planV) {

        try {
            PlanValue updatedPlan = storageManager.updatePlan(planV);
            /* TODO: Voltti does not currently support SES, to be redesigned
            SESManager sesManager = new SESManager();
            ProjectValue2 p = storageManager.getProject2(updatedPlan.getProjectId());
            String project = p.getName();
            String projectId = p.getProjectId().toString();
            String planUrl = updatedPlan.getPdfUrl();
            // if update approved = true
            if (updatedPlan.getStatus().equals(Status.APPROVED)) {
            	sesManager.planApproved(project, projectId, planUrl);
            }
            */
            PlanValue returnPlan = storageManager.getPlan(updatedPlan.getPlanId());
            return new ResponseEntity<PlanValue>(returnPlan, HttpStatus.OK);
        } catch (org.springframework.transaction.CannotCreateTransactionException e) {
            return new ResponseEntity<PlanValue>(HttpStatus.FORBIDDEN);
        }

    }

    /**
     * update a project with List<Long> sisterProjectIds
     */
    @PutMapping(value = "/projects/{projectId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ProjectValue2> updateProject(@RequestBody ProjectUpdateValue projectUpVal,
            @PathVariable("projectId") long projectId) {

        try {

            storageManager.updateProject(projectUpVal);
            ProjectValue2 updatedProject = storageManager.getProject2(projectUpVal.getProjectId());
            // TODO: Voltti does not currently support SES, to be redesigned
            /*
            if (projectUpVal.isCompleted()) {
                SESManager sesManager = new SESManager();
                ProjectValue2 project = storageManager.getProject2(projectId);
                String projectSid = projectUpVal.getProjectId().toString();
                sesManager.maintenanceDecision(project.getName(), projectSid);
            }
            */
            return new ResponseEntity<ProjectValue2>(updatedProject, HttpStatus.OK);
        } catch (org.springframework.transaction.CannotCreateTransactionException e) {
            return new ResponseEntity<ProjectValue2>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/projects/{projectId}/plans/{planId}/comments/{commentId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<PtextValue> updateComment(@RequestBody PtextValue pTextVal,
            @PathVariable("commentId") long id) {

        // TODO
        try {
            PtextValue savedPtext = storageManager.updatePtext(pTextVal, id);
            return new ResponseEntity<PtextValue>(savedPtext, HttpStatus.OK);
        } catch (org.springframework.transaction.CannotCreateTransactionException e) {
            return new ResponseEntity<PtextValue>(HttpStatus.FORBIDDEN);
        }

    }


    /**
     * not done
     */
    @DeleteMapping("/projects/{projectId}/plans/{planId}")
    public ResponseEntity<ProjectValue2> deletePlan(@PathVariable("projectId") long pid,
            @PathVariable("planId") long id) {
        try {
            storageManager.deletePlan(id);
            ProjectValue2 updatedProject = storageManager.getProject2(pid);
            return new ResponseEntity<ProjectValue2>(updatedProject, HttpStatus.OK);
        } catch (org.springframework.transaction.CannotCreateTransactionException e) {
            return new ResponseEntity<ProjectValue2>(HttpStatus.FORBIDDEN);
        }

    }
}
