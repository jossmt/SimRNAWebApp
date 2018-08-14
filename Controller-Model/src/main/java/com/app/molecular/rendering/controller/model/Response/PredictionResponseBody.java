package com.app.molecular.rendering.controller.model.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PredictionResponseBody {

    private String jobName;

    private String sequence;

    private Date requestDate;

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
     * Gets jobName.
     *
     * @return Value of jobName.
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Sets new requestDate.
     *
     * @param requestDate
     *         New value of requestDate.
     */
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * Gets requestDate.
     *
     * @return Value of requestDate.
     */
    public Date getRequestDate() {
        return requestDate;
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
     * Sets new sequence.
     *
     * @param sequence
     *         New value of sequence.
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
