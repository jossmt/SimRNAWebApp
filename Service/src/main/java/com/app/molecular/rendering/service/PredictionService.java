package com.app.molecular.rendering.service;

import com.app.molecular.rendering.service.constants.ConfigConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class PredictionService {

    private static final Logger LOG = LoggerFactory.getLogger(PredictionService.class);

    private final File workingDirectory = new File(ConfigConstants.ROOT_DIRECTORY);

    public void executeScript(final String... arguments) {

        try {

            final Process process = new ProcessBuilder(arguments).directory(workingDirectory).start();

            LOG.debug(readErrorStream(process));

            process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String readErrorStream(final Process process) {

        final StringBuilder outputBuilder = new StringBuilder();

        final InputStream errorStream = process.getErrorStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(errorStream));
        String line;
        try {

            while ((line = in.readLine()) != null) {
                outputBuilder.append(line);
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (outputBuilder.toString().equals("")) {
            outputBuilder.append("No Errors");
        }

        return outputBuilder.toString();
    }

}
