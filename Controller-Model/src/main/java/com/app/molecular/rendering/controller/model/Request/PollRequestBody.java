package com.app.molecular.rendering.controller.model.Request;

import org.springframework.stereotype.Component;

@Component
public class PollRequestBody {

    private String jobName;

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
}
