package com.app.molecular.rendering.service;

import com.app.molecular.rendering.controller.model.Request.PredictionRequestBody;
import com.app.molecular.rendering.service.constants.CommandConstants;
import com.app.molecular.rendering.service.constants.ConfigConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Service
public class SecondaryStructurePredictionService extends PredictionService {

    private static final Logger LOG = LoggerFactory.getLogger(SecondaryStructurePredictionService.class);

    public String createJobFolderAndSaveSequence(PredictionRequestBody predictionRequestBody) {

        LOG.debug("Creating job folder and writing sequence...");

        final Path path = Paths.get(ConfigConstants.ROOT_DIRECTORY + predictionRequestBody.getJobName());
        if (Files.exists(path)) {
            predictionRequestBody.setJobName(generateRandomJobName(predictionRequestBody.getJobName()));
        }

        if (predictionRequestBody.getSecondaryStructure() != null) {

            executeScript(CommandConstants.SHELL_COMMAND, CommandConstants.CREATE_SCRIPT, predictionRequestBody
                    .getJobName(), predictionRequestBody.getSequence(), predictionRequestBody.getSecondaryStructure());
        } else {

            executeScript(CommandConstants.SHELL_COMMAND, CommandConstants.CREATE_SCRIPT, predictionRequestBody
                    .getJobName(), predictionRequestBody.getSequence());
        }

        LOG.debug("Wrote sequence file data");

        return predictionRequestBody.getJobName();
    }

    public void generateTraflFiles(final String jobName, final Integer iteration, final String secondaryStructure) {

        LOG.debug("Generating trafl files...");

        String indexFormatted = String.format("%02d", iteration);

        if(secondaryStructure != null){
            executeScript(CommandConstants.SHELL_COMMAND, CommandConstants.GENERATE_TRAFL_SCRIPT, jobName,
                          indexFormatted, secondaryStructure);
        }else{
            executeScript(CommandConstants.SHELL_COMMAND, CommandConstants.GENERATE_TRAFL_SCRIPT, jobName,
                          indexFormatted);
        }

        LOG.debug("Trafl files generated successfully");

    }

    private String generateRandomJobName(final String jobName) {

        final Random random = new Random();
        return jobName + random.nextInt(500);
    }
}

