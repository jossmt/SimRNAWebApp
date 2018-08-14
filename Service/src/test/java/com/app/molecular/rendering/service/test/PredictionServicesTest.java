package com.app.molecular.rendering.service.test;

import com.app.molecular.rendering.controller.model.Request.PredictionRequestBody;
import com.app.molecular.rendering.controller.model.Response.PollResponseBody;
import com.app.molecular.rendering.service.RenderingService;
import com.app.molecular.rendering.service.RenderingServiceHandler;
import com.app.molecular.rendering.service.SecondaryStructurePredictionService;
import com.app.molecular.rendering.service.TertiaryStructurePredictionService;
import com.app.molecular.rendering.service.callable.StructurePredictionIterationCallable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(JUnit4.class)
public class PredictionServicesTest {

    private static final Logger LOG = LoggerFactory.getLogger(PredictionServicesTest.class);

    /** {@link ExecutorService}. */
    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    private final SecondaryStructurePredictionService secondaryStructurePredictionService = new
            SecondaryStructurePredictionService();
    private final TertiaryStructurePredictionService tertiaryStructurePredictionService = new
            TertiaryStructurePredictionService();
    private final RenderingService renderingService = new RenderingServiceHandler
            (secondaryStructurePredictionService, tertiaryStructurePredictionService);

//        @Test
    public void test() {

        final PredictionRequestBody predictionRequestBody = new PredictionRequestBody();
        predictionRequestBody.setJobName("TestJob1");
        predictionRequestBody.setSequence
                ("ACACCCAACUCUCCUGGCUCUAGCAGCACAGAAAUAUUGGCAUGGGGAAGUGAGUCUGCCAAUAUUGGCUGUGCUGCUCCAGGCAGGGUGGUGA");
        predictionRequestBody.setSecondaryStructure(".((((.......(((.((.((((((((((.(.(((((((.((.((.........)))).)))))" +
                                                            "))..).))))))))..)))))))..)))).");

        secondaryStructurePredictionService.createJobFolderAndSaveSequence(predictionRequestBody);

        final List<Callable<String>> callables = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            final StructurePredictionIterationCallable structurePredictionIterationCallable = new
                    StructurePredictionIterationCallable(secondaryStructurePredictionService,
                                                         tertiaryStructurePredictionService,
                                                         "TestJob1", predictionRequestBody.getSequence().length(),
                                                         predictionRequestBody.getSecondaryStructure(), i);

            try {
                structurePredictionIterationCallable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testPolling() {

        LOG.debug("Hello");

//        final PollResponseBody pollResponseBody = renderingService.pollForPDBFileOutput("TestJob");
//
//        LOG.debug("Poll RB: {}", pollResponseBody.getPdbFilePath());
//        LOG.debug("Poll SS: {}", pollResponseBody.getSecondaryStructure());
//        LOG.debug("Poll TFL: {}", pollResponseBody.getProgress());
//        LOG.debug("Poll LastUpdated: {}", pollResponseBody.getLastUpdated());
    }
}
