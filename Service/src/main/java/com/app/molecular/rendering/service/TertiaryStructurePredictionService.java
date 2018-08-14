package com.app.molecular.rendering.service;

import com.app.molecular.rendering.service.constants.CommandConstants;
import com.app.molecular.rendering.service.constants.ConfigConstants;
import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

@Service
public class TertiaryStructurePredictionService extends PredictionService {

    private static final Logger LOG = LoggerFactory.getLogger(TertiaryStructurePredictionService.class);

    /**
     * Concatenates Trafl files returned from secondary structure prediction and performs clustering method to
     * evaluate structures and select most effective output.
     */
    public void concatenateTraflFilesAndCluster(final String jobName, final Integer sequenceLength) {

        final Double RMSD = Precision.round((0.1 * sequenceLength), 2);

        executeScript(CommandConstants.SHELL_COMMAND, CommandConstants.CONCAT_AND_CLUSTER_SCRIPT, jobName,
                      String.valueOf(ConfigConstants.CLUSTER_THRESHOLD), String.valueOf(RMSD));

    }

    public void convertTraflToPdbFormat(final String jobName, final Integer sequenceLength, final Integer
            clusterIndex) {

        DecimalFormat df = new DecimalFormat("#.00");
        final Double RMSD = Precision.round((0.1 * sequenceLength), 2);
        final String RMSDformatted = df.format(RMSD);
        LOG.debug("RMSD: {}", RMSDformatted);

        String clusterIndexFormatted = String.format("%02d", clusterIndex);
        LOG.debug("Cluster index: {}", clusterIndexFormatted);

        executeScript(CommandConstants.SHELL_COMMAND, CommandConstants.CONVERT_TO_PDB_SCRIPT, jobName, RMSDformatted,
                      clusterIndexFormatted);

    }
}
