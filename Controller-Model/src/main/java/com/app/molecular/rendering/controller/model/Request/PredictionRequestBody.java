package com.app.molecular.rendering.controller.model.Request;

import org.springframework.stereotype.Component;

@Component
public class PredictionRequestBody {

    private String jobName;

    private String sequence;

    private String secondaryStructure;


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
     * Sets new sequence.
     *
     * @param sequence
     *         New value of sequence.
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     * Gets sequence.
     *
     * @return Value of sequence.
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * Gets jobName.
     *
     * @return Value of jobName.
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Sets new jobName.
     *
     * @param jobName
     *         New value of jobName.
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * Gets secondaryStructure.
     *
     * @return Value of secondaryStructure.
     */
    public String getSecondaryStructure() {
        return secondaryStructure;
    }
}
