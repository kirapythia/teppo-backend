/**
 * http://www.service-architecture.com/articles/database/mapping_sql_and_java_data_types.html
 * http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#associations-many-to-many
 */
package fi.espoo.pythia.backend.repos.entities;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.espoo.pythia.backend.transfer.PlanValue;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "plan")
// @TypeDef(name = "statusConverter", typeClass = StatusConverter.class)
public class Plan implements Serializable, Comparable<Plan> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // bigint
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_generator")
    @SequenceGenerator(name = "plan_generator", sequenceName = "plan_serial", allocationSize = 1)
    @Column(name = "plan_id", updatable = false, nullable = false)
    private Long planId;

    // bidirectional manytoone with Project
    // project object maps to class Project
    // project_id is the <<fk>> of table Plan

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectUpdate project;

    @OneToMany(mappedBy = "plan")
    private List<Ptext> ptextList;

    // smallint
    @Column(name = "main_no")
    private short mainNo;

    // smallint
    @Column(name = "sub_no")
    private short subNo;

    // varchar
    @Column(name = "version")
    private short version;

    // varchar
    @Column(name = "pdf_url")
    private String pdfUrl;

    // varchar
    @Column(name = "xml_url")
    private String xmlUrl;

    // varchar
    @Column(name = "dwg_url")
    private String dwgUrl;

    // varchar
    @Column(name = "dxf_url")
    private String dxfUrl;

    // varchar
    @Column(name = "svg_url")
    private String svgUrl;

    // @Column(name = "status")
    // @Type(type="statusConverter")
    // private Status status;

    @Enumerated(EnumType.STRING)
    private Status status;

    // https://jdbc.postgresql.org/documentation/head/java8-date-time.html
    // timestamp with timezone
    // @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    // varchar
    @Column(name = "created_by")
    private String createdBy;

    // timestamp with timezone timestamptz
    // @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    // varchar
    @Column(name = "updated_by")
    private String updatedBy;

    // boolean
    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "maintenance_duty")
    private boolean maintenanceDuty;

    @Column(name = "street_management_decision")
    private OffsetDateTime streetManagementDecision;


    public Plan() {

    }

    public Plan(ProjectUpdate project, List<Ptext> ptextList, short mainNo, short subNo, short version,
            String pdfUrl, String xmlUrl, Status status, OffsetDateTime createdAt, String createdBy,
            OffsetDateTime updatedAt, String updatedBy, boolean deleted, boolean maintenanceDuty) {
        this.project = project;
        this.ptextList = ptextList;
        this.mainNo = mainNo;
        this.subNo = subNo;
        this.version = version;
        this.pdfUrl = pdfUrl;
        this.xmlUrl = xmlUrl;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
        this.maintenanceDuty = maintenanceDuty;
    }

    public Plan(ProjectUpdate project, List<Ptext> ptextList, PlanValue planValue) {
        this.project = project;
        this.ptextList = ptextList;
        this.mainNo = planValue.getMainNo();
        this.subNo = planValue.getSubNo();
        this.version = planValue.getVersion();
        this.pdfUrl = planValue.getPdfUrl();
        this.xmlUrl = planValue.getXmlUrl();
        this.dwgUrl = planValue.getDwgUrl();
        this.dxfUrl = planValue.getDxfUrl();
        this.svgUrl = planValue.getSvgUrl();
        this.status = planValue.getStatus();
        this.createdAt = planValue.getCreatedAt();
        this.createdBy = planValue.getCreatedBy();
        this.updatedAt = planValue.getUpdatedAt();
        this.updatedBy = planValue.getUpdatedBy();
        this.deleted = planValue.isDeleted();
        this.maintenanceDuty = planValue.isMaintenanceDuty();
    }

    @JsonIgnore
    public ProjectUpdate getProject() {
        return project;
    }

    @JsonIgnore
    public void setProject(ProjectUpdate project2) {
        this.project = project2;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public List<Ptext> getPtextList() {
        return ptextList;
    }

    public void setPtextList(List<Ptext> ptextList) {
        this.ptextList = ptextList;
    }

    public short getMainNo() {
        return mainNo;
    }

    public void setMainNo(short mainNo) {
        this.mainNo = mainNo;
    }

    public short getSubNo() {
        return subNo;
    }

    public void setSubNo(short subNo) {
        this.subNo = subNo;
    }

    public short getVersion() {
        return this.version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getXmlUrl() {
        return xmlUrl;
    }

    public void setXmlUrl(String xmlUrl) {
        this.xmlUrl = xmlUrl;
    }

    public String getDwgUrl() {
        return dwgUrl;
    }

    public void setDwgUrl(String dwgUrl) {
        this.dwgUrl = dwgUrl;
    }

    public String getDxfUrl() {
        return dxfUrl;
    }

    public void setDxfUrl(String dxfUrl) {
        this.dxfUrl = dxfUrl;
    }

    public String getSvgUrl() {
        return svgUrl;
    }

    public void setSvgUrl(String svgUrl) {
        this.svgUrl = svgUrl;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isMaintenanceDuty() {
        return maintenanceDuty;
    }

    public void setMaintenanceDuty(boolean maintenanceDuty) {
        this.maintenanceDuty = maintenanceDuty;
    }

    public OffsetDateTime getStreetManagementDecision() {
        return streetManagementDecision;
    }

    public void setStreetManagementDecision(OffsetDateTime streetManagementDecision) {
        this.streetManagementDecision = streetManagementDecision;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((planId == null) ? 0 : planId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Plan other = (Plan) obj;
        if (planId == null) {
            if (other.planId != null)
                return false;
        } else if (!planId.equals(other.planId))
            return false;
        return true;
    }

    @Override
    public int compareTo(Plan p) {

        if (this.version == p.getVersion()) {
            return 0;
        } else if (this.version > p.getVersion()) {
            return 1;
        } else {
            return -1;
        }
    }

}
