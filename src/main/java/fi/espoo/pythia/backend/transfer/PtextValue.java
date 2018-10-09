package fi.espoo.pythia.backend.transfer;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class PtextValue implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long ptextId;

    private Long planId;

    private String ptext;

    private boolean approved;

    private String url;

    private OffsetDateTime createdAt;

    private String createdBy;

    private OffsetDateTime updatedAt;

    private String updatedBy;

    private double x;

    private double y;

    private double width;

    private double height;

    public PtextValue() {

    }

    public Long getPtextId() {
        return ptextId;
    }

    public void setPtextId(Long ptextId) {
        this.ptextId = ptextId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPtext() {
        return ptext;
    }

    public void setPtext(String ptext) {
        this.ptext = ptext;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
