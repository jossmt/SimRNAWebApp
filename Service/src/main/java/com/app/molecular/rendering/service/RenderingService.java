package com.app.molecular.rendering.service;

import com.app.molecular.rendering.controller.model.Request.PredictionRequestBody;
import com.app.molecular.rendering.controller.model.Response.PollResponseBody;
import com.app.molecular.rendering.controller.model.Response.PredictionResponseBody;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface RenderingService {


    String initialiseStructurePrediction(PredictionRequestBody predictionRequestBody);

    PollResponseBody pollForPDBFileOutput(String jobName);

    PredictionResponseBody getJobData(String jobRef);
}
