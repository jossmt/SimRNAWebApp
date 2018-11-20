package com.app.molecular.rendering.controller;

import com.app.molecular.rendering.controller.model.Request.PollRequestBody;
import com.app.molecular.rendering.controller.model.Request.PredictionRequestBody;
import com.app.molecular.rendering.controller.model.Response.PollResponseBody;
import com.app.molecular.rendering.controller.model.Response.PredictionResponseBody;
import com.app.molecular.rendering.service.RenderingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin
@Controller
public class RestRequestHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestRequestHandler.class);

    private RenderingService renderingService;

    @Autowired
    public RestRequestHandler(final RenderingService renderingService) {

        this.renderingService = renderingService;
    }

    /**
     * Renders submit view to container.
     *
     * @return SubmitPage.jsp
     */
    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public String renderSubmit(final Model model) {

        final PredictionRequestBody predictionRequestBody = new PredictionRequestBody();

        model.addAttribute("requestForm", predictionRequestBody);

        return "pages/SubmitPage";
    }

    /**
     * Renders search view to container.
     *
     * @return SearchPage.jsp
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String renderResult() {

        return "pages/SearchPage";
    }

    /**
     * Renders result view to container.
     *
     * @return ResultPage.jsp
     */
    @RequestMapping(value = "/result/{jobRef}", method = RequestMethod.GET)
    public String renderResult(final Model model, @PathVariable final String jobRef) {

        LOG.debug("Getting prediction data for job: {}", jobRef);

        final PredictionResponseBody predictionResponseBody = renderingService.getJobData(jobRef);
        model.addAttribute("response", predictionResponseBody);

        LOG.debug("Successfully obtained prediction response: {}", predictionResponseBody);

        return "pages/ResultPage";
    }

    /**
     * Renders display view to container.
     *
     * @return DisplayPage.jsp
     */
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public String renderDisplay(final Model model) {

        return "pages/DisplayPage";
    }

    /**
     * Renders home view to container.
     *
     * @return HomePage.jsp
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String renderHome() {

        return "pages/HomePage";
    }

    /**
     * Renders about view to container.
     *
     * @return AboutPage.jsp
     */
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String renderAbout() {

        return "pages/AboutPage";
    }

    /**
     * Renders display view to container.
     *
     * @return DisplayPage.jsp
     */
    @RequestMapping(value = "/display", method = RequestMethod.POST)
    public String renderDisplayPDB(final Model model, @RequestParam("encodedData") final String encodedData) {

        final String updatedURL = encodedData.replace("molecularrendering", "files");

        model.addAttribute("data", updatedURL);

        return "pages/DisplayPage";
    }

    @RequestMapping(value = "/prediction/{jobRef}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    PredictionResponseBody getRenderingData(@PathVariable final String jobRef) {

        LOG.debug("Getting prediction data for job: {}", jobRef);

        final PredictionResponseBody predictionResponseBody = renderingService.getJobData(jobRef);

        LOG.debug("Successfully obtained prediction response: {}", predictionResponseBody);

        return predictionResponseBody;
    }

    @RequestMapping(value = "/predict", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    public @ResponseBody
    PredictionResponseBody sendMolecularRenderingRequest(@RequestBody final PredictionRequestBody
                                                                 predictionRequestBody) {

        LOG.debug("Sending molecular rendering request: {}", predictionRequestBody);

        final PredictionResponseBody predictionResponseBody = new PredictionResponseBody();
        predictionResponseBody.setRequestDate(new Date());

        predictionRequestBody.setSequence(predictionRequestBody.getSequence().replaceAll("T", "U"));

        final String jobName = renderingService.initialiseStructurePrediction(predictionRequestBody);
        predictionResponseBody.setJobName(jobName);
        predictionResponseBody.setSequence(predictionRequestBody.getSequence());

        LOG.debug("Returning rendering response: {}", predictionResponseBody);

        return predictionResponseBody;
    }

    @RequestMapping(value = "/cancel/{jobRef}", method = RequestMethod.DELETE,
            consumes = "appliation/json", produces = "application/json")
    public void cancelRequest(@PathVariable final String jobRef) {

        LOG.debug("Cancelling Job with reference: {}", jobRef);

        renderingService.cancelRequest(jobRef);

        LOG.debug("Successfully cancelled request");
    }

    @RequestMapping(value = "/poll", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    public @ResponseBody
    PollResponseBody pollMolecularOutput(@RequestBody PollRequestBody pollRequestBody) {

        LOG.debug("Polling PDB file for job: {}", pollRequestBody.getJobName());

        final PollResponseBody pollResponseBody = renderingService
                .pollForPDBFileOutput(pollRequestBody.getJobName());

        LOG.debug("Returning poll response: {}", pollResponseBody);

        return pollResponseBody;
    }
}
