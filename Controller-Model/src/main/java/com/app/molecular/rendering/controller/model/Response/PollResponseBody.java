package com.app.molecular.rendering.controller.model.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PollResponseBody {

    private String secondaryStructure;

    private String pdbFilePath;

    private Date lastUpdated;

    private Integer progress;

    /**
     * Gets lastUpdated.
     *
     * @return Value of lastUpdated.
     */
    public Date getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Sets new lastUpdated.
     *
     * @param lastUpdated
     *         New value of lastUpdated.
     */
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Gets secondaryStructure.
     *
     * @return Value of secondaryStructure.
     */
    public String getSecondaryStructure() {
        return secondaryStructure;
    }

    /**
     * Sets new secondaryStructure.
     *
     * @param secondaryStructure
     *         New value of secondaryStructure.
     */
    public void setSecondaryStructure(String secondaryStructure) {
        this.secondaryStructure = secondaryStructure;
    }

    /**
     * Gets pdbFilePath.
     *
     * @return Value of pdbFilePath.
     */
    public String getPdbFilePath() {
        return pdbFilePath;
    }

    /**
     * Sets new pdbFilePath.
     *
     * @param pdbFilePath
     *         New value of pdbFilePath.
     */
    public void setPdbFilePath(String pdbFilePath) {
        this.pdbFilePath = pdbFilePath;
    }

    /**
     * Sets new progress.
     *
     * @param progress
     *         New value of progress.
     */
    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    /**
     * Gets progress.
     *
     * @return Value of progress.
     */
    public Integer getProgress() {
        return progress;
    }
}
