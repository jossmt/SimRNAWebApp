package com.app.molecular.rendering.service;

import com.app.molecular.rendering.controller.model.Request.PredictionRequestBody;
import com.app.molecular.rendering.controller.model.Response.PollResponseBody;
import com.app.molecular.rendering.controller.model.Response.PredictionResponseBody;
import com.app.molecular.rendering.service.callable.StructurePredictionIterationCallable;
import com.app.molecular.rendering.service.constants.ConfigConstants;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class RenderingServiceHandler implements RenderingService {

    private static final Logger LOG = LoggerFactory.getLogger(RenderingService.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private final SecondaryStructurePredictionService secondaryStructurePredictionService;
    private final TertiaryStructurePredictionService tertiaryStructurePredictionService;

    public RenderingServiceHandler(final SecondaryStructurePredictionService secondaryStructurePredictionService,
                                   final TertiaryStructurePredictionService tertiaryStructurePredictionService) {

        this.secondaryStructurePredictionService = secondaryStructurePredictionService;
        this.tertiaryStructurePredictionService = tertiaryStructurePredictionService;
    }

    public PredictionResponseBody getJobData(final String jobRef) {

        LOG.debug("Getting job data for: {}", jobRef);

        final PredictionResponseBody predictionResponseBody = new PredictionResponseBody();
        predictionResponseBody.setJobName(jobRef);

        final File ssFile = new File(ConfigConstants.ROOT_DIRECTORY + jobRef + "/sequence.fa");

        if (ssFile.exists()) {

            try {
                final String sequence = new String(Files.readAllBytes(ssFile.toPath()));
                predictionResponseBody.setSequence(sequence);

                final FileTime fileTime = Files.getLastModifiedTime(ssFile.toPath());
                predictionResponseBody.setRequestDate(new Date(fileTime.toMillis()));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        LOG.debug("Obtained job data");

        return predictionResponseBody;
    }

    @Override
    public String initialiseStructurePrediction(final PredictionRequestBody predictionRequestBody) {

        LOG.debug("Initialising structure prediction");

        final String jobName = secondaryStructurePredictionService
                .createJobFolderAndSaveSequence(predictionRequestBody);

        for (int i = 0; i < ConfigConstants.NUMBER_OF_THREADS; i++) {
            final StructurePredictionIterationCallable structurePredictionIterationCallable = new
                    StructurePredictionIterationCallable(secondaryStructurePredictionService,
                                                         tertiaryStructurePredictionService,
                                                         jobName, predictionRequestBody
                                                                 .getSequence().length(),
                                                         predictionRequestBody.getSecondaryStructure(), i);

            executorService.submit(structurePredictionIterationCallable);
        }

        LOG.debug("Initialised structure prediction threads");

        return jobName;
    }

    @Override
    public PollResponseBody pollForPDBFileOutput(final String jobName) {

        LOG.debug("Polling for PDB File");

        final PollResponseBody pollResponseBody = new PollResponseBody();

        final File rootDirectory = new File(ConfigConstants.ROOT_DIRECTORY + jobName + "/");
        final File fileDirectory = new File(ConfigConstants.ROOT_DIRECTORY + jobName +
                                                    ConfigConstants.OUTPUT_DIRECTORY);

        final String traflPattern = String.format(ConfigConstants.TRAFL_OUTPUT_FILENAME, jobName);
        final String pdbPattern = String.format(ConfigConstants.CLUSTER_OUTPUT_FILENAME, jobName);
        final String ssPattern = String.format(ConfigConstants.SS_OUTPUT_FILENAME, jobName);

        FileFilter traflFileFilter = new RegexFileFilter(traflPattern);
        FileFilter pdbFileFilter = new RegexFileFilter(pdbPattern);
        FileFilter ssFileFilter = new RegexFileFilter(ssPattern);

        final File[] traflFiles = rootDirectory.listFiles(traflFileFilter);
        final File[] pdbFiles = fileDirectory.listFiles(pdbFileFilter);
        final File[] ssFiles = fileDirectory.listFiles(ssFileFilter);

        LOG.debug("PDB Files: {}", pdbFiles.length);
        LOG.debug("SS Files: {}", ssFiles.length);
        LOG.debug("trafl Files: {}", traflFiles.length);


        if (pdbFiles.length > 0) {

            LOG.debug("PDB output file path: {}", pdbFiles[0].toPath().toString());

            try {
                FileTime fileTime = Files.getLastModifiedTime(pdbFiles[0].toPath());

                pollResponseBody.setLastUpdated(new Date(fileTime.toMillis()));

                final String path = pdbFiles[0].toPath().toString()
                        .replace(ConfigConstants.ROOT_DIRECTORY, "");

                pollResponseBody.setPdbFilePath(path);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (ssFiles.length > 0) {

            LOG.debug("SS output file path: {}", ssFiles[0].toPath().toString());

            try {
                String sequence = new String(Files.readAllBytes(ssFiles[0].toPath()));

                pollResponseBody.setSecondaryStructure(sequence);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        pollResponseBody.setProgress(traflFiles.length);

        return pollResponseBody;
    }
}
