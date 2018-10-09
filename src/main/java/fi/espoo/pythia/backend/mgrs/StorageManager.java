package fi.espoo.pythia.backend.mgrs;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.attribute.UserPrincipal;
import java.sql.Time;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.aioobe.cloudconvert.CloudConvertService;
import org.aioobe.cloudconvert.ConvertProcess;
import org.aioobe.cloudconvert.ProcessStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import fi.espoo.pythia.backend.converters.FileConverter;
import fi.espoo.pythia.backend.mappers.LPToLPValueMapper;
import fi.espoo.pythia.backend.mappers.PlanToPlanValueMapper;
import fi.espoo.pythia.backend.mappers.PlanValueToPlanMapper;
import fi.espoo.pythia.backend.mappers.PrjToPrjVal2Mapper;
import fi.espoo.pythia.backend.mappers.PrjUpToPrjUpValMapper;
import fi.espoo.pythia.backend.mappers.PrjUpValToPrjUpMapper;
import fi.espoo.pythia.backend.mappers.PtextToPtextValueMapper;
import fi.espoo.pythia.backend.mappers.PtextValueToPtextMapper;
import fi.espoo.pythia.backend.repos.LatestPlansRepository;
import fi.espoo.pythia.backend.repos.PlanRepository;
import fi.espoo.pythia.backend.repos.ProjectRepository;
import fi.espoo.pythia.backend.repos.ProjectUpdateRepository;
import fi.espoo.pythia.backend.repos.PtextRepository;
import fi.espoo.pythia.backend.repos.SisterProjectUpdateRepository;
import fi.espoo.pythia.backend.repos.entities.LatestPlans;
import fi.espoo.pythia.backend.repos.entities.Plan;
import fi.espoo.pythia.backend.repos.entities.Project;
import fi.espoo.pythia.backend.repos.entities.ProjectUpdate;
import fi.espoo.pythia.backend.repos.entities.Ptext;
import fi.espoo.pythia.backend.repos.entities.SisterProject;
import fi.espoo.pythia.backend.repos.entities.SisterProjectUpdate;
import fi.espoo.pythia.backend.repos.entities.Status;
import fi.espoo.pythia.backend.transfer.LatestPlansValue;
import fi.espoo.pythia.backend.transfer.PlanValue;
import fi.espoo.pythia.backend.transfer.ProjectUpdateValue;
import fi.espoo.pythia.backend.transfer.ProjectValue2;
import fi.espoo.pythia.backend.transfer.PtextValue;
import fi.espoo.pythia.backend.validators.PlanValidator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Component
@Transactional
public class StorageManager {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SisterProjectUpdateRepository sisterProjectUpdateRepository;

    @Autowired
    private PtextRepository ptextRepository;

    @Autowired
    private ProjectUpdateRepository projectUpdateRepository;

    @Autowired
    private LatestPlansRepository latestPlansRepository;

    @Autowired
    private S3Manager s3Manager;
    // ---------------------GET------------------------------------

    /**
     * NEW
     *
     * @return list of projects 2 latest values
     */
    public ArrayList<ProjectValue2> getProjects2() {

        ArrayList<Project> prjList = (ArrayList<Project>) projectRepository.findAll();
        ArrayList<ProjectValue2> prjValList = new ArrayList();

        // for -loop for prjList

        for (Project p : prjList) {
            // map each project to projectValue
            ProjectValue2 pval = PrjToPrjVal2Mapper.ProjectToProjectValue2(p);
            prjValList.add(pval);
        }
        // return projectValue -ArrayList
        return prjValList;
    }

    public ArrayList<ProjectUpdateValue> getProjects() {
        ArrayList<ProjectUpdate> prjList = (ArrayList<ProjectUpdate>) projectUpdateRepository.findAll();
        ArrayList<ProjectUpdateValue> prjValList = new ArrayList<ProjectUpdateValue>();

        for (ProjectUpdate p : prjList) {
            // map each project to projectValue
            ProjectUpdateValue pval = PrjUpToPrjUpValMapper.ProjectUpdateToProjectUpdateValue(p);
            prjValList.add(pval);
        }
        // return projectValue -ArrayList
        return prjValList;
    }

    /**
     * Return project object for given id from database. If project is not found
     * for id, returns null. DONE
     */
    public ProjectValue2 getProject2(Long projectId) {

        Project project = projectRepository.findByProjectId(projectId);
        ProjectValue2 pval = PrjToPrjVal2Mapper.ProjectToProjectValue2(project);
        return pval;

    }

    /**
     *
     * @param projectId
     * @return
     */
    public ProjectUpdateValue getProjectAllPlans(Long projectId) {
        ProjectUpdate project = projectUpdateRepository.findByProjectId(projectId);
        ProjectUpdateValue pval = PrjUpToPrjUpValMapper.ProjectUpdateToProjectUpdateValue(project);
        return pval;
    }

    /**
     *
     * @param hansuId
     * @return
     */
    public ProjectValue2 getProjectByHansuId2(String hansuId) {
        List<Project> prjList = projectRepository.findAll();
        for (Project p : prjList) {
            if (p.getHansuProjectId().equals(hansuId)) {
                ProjectValue2 pval = PrjToPrjVal2Mapper.ProjectToProjectValue2(p);
                return pval;
            }
        }

        return null;
    }

    /**
     * get all plans by projectId
     *
     * @param projectId
     * @return
     */
    public List<PlanValue> getPlans(Long projectId) {

        ProjectUpdate projectUpdate = projectUpdateRepository.findByProjectId(projectId);
        ProjectUpdateValue pval = PrjUpToPrjUpValMapper.ProjectUpdateToProjectUpdateValue(projectUpdate);

        return pval.getPlans();

    }

    public PlanValue getPlan(Long planId) {

        Plan plan = planRepository.findByPlanId(planId);
        ProjectUpdate project = plan.getProject();
        PlanValue pVal = PlanToPlanValueMapper.planToPlanValue(plan, project);

        return pVal;
    }

    public PtextValue getComment(long id) {
        Ptext comment = ptextRepository.findByPtextId(id);
        Plan plan = comment.getPlan();
        PtextValue cVal = PtextToPtextValueMapper.ptextToPtextValue(comment, plan);

        return cVal;
    }

    /**
     * get comments by planId
     *
     * @param planId
     * @return
     */
    public List<PtextValue> getComments(Long planId) {

        Plan plan = planRepository.findByPlanId(planId);
        List<Ptext> comments = ptextRepository.findByPlan(plan);

        List<PtextValue> commentValues = new ArrayList<PtextValue>();
        for (Ptext c : comments) {
            PtextValue cv = PtextToPtextValueMapper.ptextToPtextValue(c, plan);
            commentValues.add(cv);
        }

        return commentValues;

    }

    // ---------------------POST-----------------------------------

    public ProjectUpdate createProject(ProjectUpdateValue projectV) {

        ProjectUpdate projectUpTemp = projectUpdateRepository.findByProjectId(projectV.getProjectId());
        ProjectUpdate projectUp = PrjUpValToPrjUpMapper.projectValue2ToProject(projectV, projectUpTemp, false);
        ProjectUpdate updatedProject = projectUpdateRepository.save(projectUp);
        updateSisterProjects(projectV, projectUp);

        return updatedProject;
    }

    /**
     * Checks if 1st version and if approved
     *
     * If the 1st then version = 0 and approved = true
     *
     * If not the 1st then increase version number by one
     *
     * @return PlanValue
     */
    public PlanValue createPlan2(PlanValue planV) {

        Long projectId = planV.getProjectId();
        // get project by projectid
        ProjectUpdate projectUpdate = projectUpdateRepository.findByProjectId(projectId);
        // map planV to plan
        Plan mappedPlan = PlanValueToPlanMapper.planValueToPlan(planV, projectUpdate, false);
        // get all plans with planV.projectId and planV.mainNo & planV.subNo
        List<Plan> existingPlans = planRepository.findByProjectInAndMainNoInAndSubNoIn(projectUpdate, planV.getMainNo(),
                planV.getSubNo());

        short version = 0;
        boolean approved = true;
        // first version
        if (existingPlans.isEmpty()) {
            version = 0;
            approved = true;

        } else {
            // sorting from min to max
            Collections.sort(existingPlans);
            // get existingPlans max version with projctId, mainno and subno
            Plan max = existingPlans.get(existingPlans.size() - 1);
            // max version
            short maxVersion = max.getVersion();
            version = (short) (maxVersion + 1);
            approved = false;
        }
        mappedPlan.setVersion(version);
        // mappedPlan.setApproved(approved);

        Plan savedPlan = planRepository.save(mappedPlan);

        // send mail TODO
        PlanValue savedPlanValue = PlanToPlanValueMapper.planToPlanValue(savedPlan, projectUpdate);
        // finally
        return savedPlanValue;
    }


    private PlanValue uploadFileAndSavePlan(MultipartFile mFile, long projectId, boolean isNewVersion) throws IOException {

        File file;
        PlanValue planValue = new PlanValue();

        try {
            file = FileConverter.multipartFileToFile(mFile);
            String name = file.getName().toLowerCase();
            PlanValidator validator = new PlanValidator();
            boolean isValidFile = validator.isValidFile(mFile);
            if (!isValidFile) {
                System.out.println(file.getName() + " is not a valid file");
                return null;
            } else {
                planValue.setMainNo(validator.getMainNo());
                planValue.setSubNo(validator.getSubNo());
            }
            ProjectUpdate projectUpdate = projectUpdateRepository.findByProjectId(projectId);
            List<Plan> existingPlans = planRepository.findByProjectInAndMainNoInAndSubNoIn(projectUpdate, planValue.getMainNo(),
                    planValue.getSubNo());
            Collections.sort(existingPlans);

            planValue.setVersion((short)  0);
            planValue.setStatus(Status.APPROVED);
            if (isNewVersion) {
                Plan max = existingPlans.get(existingPlans.size() - 1);
                // max version
                short maxVersion = max.getVersion();
                planValue.setVersion((short) (maxVersion + 1));
                planValue.setStatus(Status.WAITING_FOR_APPROVAL);
            }

            UserPrincipal owner = java.nio.file.Files.getOwner(file.toPath());
            planValue.setCreatedBy(owner.toString());
            planValue.setDeleted(false);
            planValue.setMaintenanceDuty(false);
            planValue.setPdfUrl("");
            planValue.setXmlUrl("");
            planValue.setDwgUrl("");
            planValue.setDxfUrl("");
            planValue.setSvgUrl("");

            String savedImageUrl = s3Manager.createPlanMultipartFile("teppo-plans-dev", mFile, planValue.getVersion());
            // TODO: Use the real S3 url (instead the local path), when CORS problem is resolved.
            savedImageUrl = "file://" + file.getAbsolutePath();

            if (name.endsWith(".pdf")) {
                planValue.setPdfUrl(savedImageUrl);
            } else if (name.endsWith(".xml")) {
                planValue.setXmlUrl(savedImageUrl);
            } else if (name.endsWith(".dwg")) {
                planValue.setDwgUrl(savedImageUrl);
            } else if (name.endsWith(".dxf")) {
                planValue.setDxfUrl(savedImageUrl);
            } else {
                System.out.println("Wrong file name: " + name);
                return null;
            }

            if (name.endsWith(".dwg") || name.endsWith(".dxf")) {
                // TODO: Get Teppo/Voltti account for CloudConvert and take the correct apiKey in use
                CloudConvertService service = new CloudConvertService("4EXemBQpwkBuT5jhF4CB6tTbKh16qdQcP4OQ5AydIPcNfahD3uufQjwdfvieXABt");
                ConvertProcess process;
                try {
                    String inputFormat = name.substring(name.length() - 3);
                    process = service.startProcess(inputFormat, "svg");
                    long startConversion = System.nanoTime();
                    process.startConversion(file);

                    ProcessStatus conversionStatus;
                    waitLoop:
                    while (true) {
                        conversionStatus = process.getStatus();
                        switch (conversionStatus.step) {
                            case FINISHED: {
                                long endConversion = System.nanoTime();
                                System.out.println("Milliseconds elapsed in conversion:" + (endConversion - startConversion) / 1000000);
                                break waitLoop;
                            }
                            case ERROR:
                                throw new RuntimeException(conversionStatus.message);
                        }
                        Thread.sleep(200);
                    }

                    InputStream svgStream = service.download(conversionStatus.output.url);
                    String fileName = name + ".svg";
                    File svgFile = new File(fileName);
                    service.download(conversionStatus.output.url, svgFile);
                    long fileSize = svgFile.length();
                    String svgUrl = s3Manager.createPlanInputStream("teppo-plans-dev", svgStream, fileName, fileSize, planValue.getVersion());
                    //planValue.setSvgUrl(svgUrl);
                    // TODO: Use the real S3 url (instead the local path), when CORS problem is resolved.
                    planValue.setSvgUrl("file://" + svgFile.getAbsolutePath());
                    process.delete();
                } catch (java.net.URISyntaxException e) {
                    System.out.println("Error in CloudConvertService startProcess(): " + e.toString());
                } catch (java.text.ParseException e) {
                    System.out.println("Error parsing CAD file: " + e.toString());
                } catch (java.lang.InterruptedException e) {
                    System.out.println("Error in waitLoop: " + e.toString());
                }
            }
            
            Plan plan = null;
            if (isNewVersion || existingPlans.size() == 0) {
                planValue.setCreatedAt(OffsetDateTime.now());
                plan = new Plan(projectUpdate, new ArrayList<Ptext>(), planValue);
            } else {
                planValue.setUpdatedAt(OffsetDateTime.now());
                planValue.setUpdatedBy(owner.toString());
                Plan latestPlan = existingPlans.get(existingPlans.size() - 1);
                if (name.endsWith(".xml")) {
                    if (!latestPlan.getXmlUrl().isEmpty()) {
                        return null;
                    }
                    latestPlan.setXmlUrl(planValue.getXmlUrl());
                } else {
                    if (!latestPlan.getPdfUrl().isEmpty()) {
                        return null;
                    }
                    latestPlan.setPdfUrl(planValue.getPdfUrl());
                }
                latestPlan.setUpdatedAt(planValue.getUpdatedAt());
                latestPlan.setUpdatedBy(owner.toString());
                plan = latestPlan;
            }

            // UI should not allow but pdf or dwg or dxf or xml file types.
            // If you want to add more file types modify the method getFileList(String dirPath)
            // TODO: Set this back on, when the local files are not used anymore (i.e. CORS problem is resolved)
            // clearFiles();

             Plan savedPlan = planRepository.save(plan);
            planValue = PlanToPlanValueMapper.planToPlanValue(savedPlan, projectUpdate);
        } catch (IOException e) {
            System.out.println("IOException caught: " + e.toString());
        }

        return planValue;
    }

    /**
     *
     * @param mfile
     * @param projectId
     * @return
     */
    public PlanValue createPlanVersion(MultipartFile mfile, long projectId) throws IOException {

        boolean isNewVersion = true;
        PlanValue savedPlanValue = uploadFileAndSavePlan(mfile, projectId, isNewVersion);
        return savedPlanValue;
    }

    /**
     * Is always 1st version
     *
     * If the 1st then version = 0 and approved = true
     *
     *
     * must be PDF or XML
     *
     * @param mfile
     * @param projectId
     * @return
     * @throws IOException
     */
    public PlanValue createUpdatePlan(MultipartFile mfile, long projectId) throws IOException {

        boolean isNewVersion = false;
        PlanValue planValue = uploadFileAndSavePlan(mfile, projectId, isNewVersion);
        return planValue;
    }

    /**
     * clear temp files pdf, dwg, xml
     */
    private void clearFiles() {

        String dirPath = System.getProperty("user.dir");

        File[] files = getFileList(dirPath);

        for (File f : files) {
            try {
                Files.deleteIfExists(f.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //
        //

    }

    private static File[] getFileList(String dirPath) {
        File dir = new File(dirPath);

        File[] fileList = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".pdf") || name.endsWith(".dwg") || name.endsWith(".xml") || name.endsWith(".DAT")
                        || name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".gif") || name.endsWith(".tiff")
                        || name.endsWith(".svg") || name.endsWith(".dxf");

            }
        });
        return fileList;
    }
    /**
     *
     * @param pTextVal
     * @param id
     * @return
     */
    public PtextValue createPtext(PtextValue pTextVal, Long id) {

        // Long planId = commV.getPlanId();
        Plan plan = planRepository.findByPlanId(id);
        pTextVal.setPlanId(id);
        Ptext pText = PtextValueToPtextMapper.commentValueToComment(pTextVal, plan, false, false);
        Ptext savedPtext = ptextRepository.save(pText);
        PtextValue savedCommValue = PtextToPtextValueMapper.ptextToPtextValue(savedPtext, plan);
        return savedCommValue;

    }

    // ---------------------PUT------------------------------------

    // /**
    // *
    // * @param projectV
    // * @return
    // */
    // public ProjectValue updateProject(ProjectValue projectV) {
    //
    // Project projectTemp =
    // projectRepository.findByProjectId(projectV.getProjectId());
    // Project project =
    // ProjectValueToProjectMapper.projectValueToProjectUpdate(projectV,
    // projectTemp);
    // Project updatedProject = projectRepository.save(project);
    //
    // ProjectValue updatedProjectValue =
    // ProjectToProjectValueMapper.projectToProjectValue(updatedProject);
    // return updatedProjectValue;
    //
    // }

    public void updateProject(ProjectUpdateValue projectV) {

        ProjectUpdate projectUpTemp = projectUpdateRepository.findByProjectId(projectV.getProjectId());
        ProjectUpdate projectUp = PrjUpValToPrjUpMapper.projectValue2ToProject(projectV, projectUpTemp, true);
        // update basic project table
        projectUpdateRepository.save(projectUp);

        // update sisterProjects table
        updateSisterProjects(projectV, projectUp);

        // ProjectValue2 updatedProjectValue2 = PrjToPrjVal2
        // .ProjectToProjectValue2(projectRepository.findByProjectId(projectV.getProjectId()));
        // return updatedProjectValue2;
    }

    public void updateSisterProjects(ProjectUpdateValue pv, ProjectUpdate projectUp) {

        // get latest by repo id
        ProjectUpdate p = projectUpdateRepository.findByProjectId(pv.getProjectId());
        // create empty sisterprojects list
        List<SisterProject> sProjects = new ArrayList<SisterProject>();

        // first delete all rows with this project
        sisterProjectUpdateRepository.deleteByProject(projectUp);
        Long j = 1L;
        // add sisterprojects to the db
        for (Long id : pv.getSisterProjects()) {
            System.out.println("updatesisterProjectId:" + id);
            SisterProjectUpdate sProjectUp = (new SisterProjectUpdate(j, projectUp, id));

            sisterProjectUpdateRepository.save(sProjectUp);
            j++;
        }
    }

    /**
     *
     * @param planV
     * @return
     */

    public PlanValue updatePlan(PlanValue planV) {

        Long id = planV.getProjectId();
        ProjectUpdate projectUp = projectUpdateRepository.findByProjectId(id);

        Plan plan = PlanValueToPlanMapper.planValueToPlan(planV, projectUp, true);

        Plan updatedPlan = planRepository.save(plan);
        PlanValue returnPlan = PlanToPlanValueMapper.planToPlanValue(updatedPlan, projectUp);
        return returnPlan;

    }

    public PtextValue updatePtext(PtextValue pTextVal, long id) {

        // TODO
        // VERIFY THAT path variable commentId long id and
        // pTextVal.pTextVal.getTextId() match

        Plan plan = planRepository.findByPlanId(pTextVal.getPlanId());
        Ptext pText = PtextValueToPtextMapper.commentValueToComment(pTextVal, plan, pTextVal.isApproved(), true);

        pText.setPtextId(id);

        Ptext updatedPtext = ptextRepository.save(pText);

        PtextValue updatedPtextValue = PtextToPtextValueMapper.ptextToPtextValue(updatedPtext, plan);
        return updatedPtextValue;

    }

    // --------------------- DELETE --------------------------------

    /**
     * update plan value deleted to true
     *
     * @param id
     */
    public LatestPlansValue deletePlan(long id) {
        // TODO Auto-generated method stub
        Plan plan = planRepository.findByPlanId(id);
        plan.setDeleted(true);

        Plan updatedPlan = planRepository.save(plan);

        LatestPlans latestPlans = latestPlansRepository.findByPlanId(updatedPlan.getPlanId());
        Project project = projectRepository.findByProjectId(id);

        LatestPlansValue updatedLAtestPlanValue = LPToLPValueMapper.lpTolpValue(latestPlans, project);
        return updatedLAtestPlanValue;

    }

}
