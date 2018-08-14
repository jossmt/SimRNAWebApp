package com.app.molecular.rendering.service.callable;

import com.app.molecular.rendering.service.SecondaryStructurePredictionService;
import com.app.molecular.rendering.service.TertiaryStructurePredictionService;

import java.util.concurrent.Callable;

public class StructurePredictionIterationCallable implements Callable<String> {

    private SecondaryStructurePredictionService secondaryStructurePredictionService;
    private TertiaryStructurePredictionService tertiaryStructurePredictionService;

    private String jobName;
    private String secondaryStructure;
    private Integer sequenceLength;
    private Integer iteration;

    public StructurePredictionIterationCallable(final SecondaryStructurePredictionService
                                                        secondaryStructurePredictionService,
                                                final TertiaryStructurePredictionService
                                                        tertiaryStructurePredictionService,
                                                final String jobName,
                                                final Integer sequenceLength,
                                                final String secondaryStructure, final Integer iteration) {
        this.secondaryStructurePredictionService = secondaryStructurePredictionService;
        this.tertiaryStructurePredictionService = tertiaryStructurePredictionService;

        this.jobName = jobName;
        this.secondaryStructure = secondaryStructure;
        this.sequenceLength = sequenceLength;
        this.iteration = iteration;
    }

    @Override
    public String call() throws Exception {

        secondaryStructurePredictionService.generateTraflFiles(jobName, iteration, secondaryStructure);
        tertiaryStructurePredictionService.concatenateTraflFilesAndCluster(jobName, sequenceLength);
        tertiaryStructurePredictionService.convertTraflToPdbFormat(jobName, sequenceLength, 1);

        return "finito";
    }
}
