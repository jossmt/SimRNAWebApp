<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

        <div class="container">
        <h1 id="title">Results</h1>
        <c:choose>
            <c:when test="${not empty response.sequence}">
                <div>
                    <div class="inline">
                        <h1>Job Name</h1>
                        <h2 id="jobId">${response.jobName}</h2>
                    </div>
                    <div class="inline right">
                        <h1>Date Requested</h1>
                        <h2 id="requestDate">${response.requestDate}</h2>
                    </div>
                </div>
                <div>
                    <h1>Sequence</h1>
                    <h2 id="sequence">${response.sequence}</h2>
                </div>
                <div id="progressContainer">
                    <div class="inline" id="progressBar"></div>
                    <div class="inline" id="progressPercentage">1%</div>
                    <div class="inline"><i id="cancelIcon" class="fa fa-close"></i></div>
                </div>
                <div>
                    <div class="inline">
                        <h1>Secondary Structure</h1>
                        <h2 id="secondaryStructure">Not yet predicted...</h2>
                    </div>
                    <div class="inline right">
                        <h1>Last Updated</h1>
                        <h2 id="lastUpdated">Not yet updated...</h2>
                    </div>
                </div>
                <button id="downloadButton" disabled>Download</button>

                <input id="encodedData" type="text" placeholder="encodedData" name="encodedData" hidden>
                </div>
            </c:when>
            <c:otherwise>
                <div>
                    <h1>This job appears not to exist. Please ensure you have entered the correct job name...</h1>
                </div>
            </c:otherwise>
        </c:choose>

        <style>

            .container {
                margin-left: 30px;
            }

            #title {
                color: #1abc9c; /* Green */
                font-size: 30px;
            }

            h1 {
                font-size: 20px;
                color: #1abc9c;
            }

            h2 {
                font-size: 15px;
            }

            .inline {
                display: inline-block;
            }

            .inline.right {
                float: right;
            }

            button {
                background-color: #1abc9c; /* Green */
                border: none;
                color: white;
                padding: 15px 32px;
                text-align: center;
                text-decoration: none;
                margin-top: 30px;
                border-radius: 8px;
            }

            button:enabled, button[enabled] {
                cursor: pointer;
            }

            button:disabled, button[disabled] {
                background-color: grey;
            }

            #progressContainer {
                width: 100%;
                background-color: #ddd;
                display: -webkit-flex;
                display: flex;
                align-items: center;
            }

            #progressBar {
                width: 1%;
                height: 30px;
                background-color: #1abc9c;
            }

            #progressPercentage {
                padding-left: 10px;
            }

        </style>
        <!-- Scripts -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <%--<script src="<spring:url value="/resources/js/jquery-ui.min.js"/>"></script>--%>
        <script type="text/javascript">

            $('#cancelIcon').click(function (e) {

                var jobRef = window.location.pathname.split("/").pop()

                alert(jobRef);

                $.ajax({
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    type: "delete",
                    url: "${pageContext.servletContext.contextPath}/cancel/" + jobRef,
                    success: function (successResponse) {
                        location.reload();
                    },
                    error: function (msg) {
                        console.log("failed" + msg);
                    }
                })

            });

            $('#downloadButton').click(function (e) {
                e.preventDefault();

                var path = "${pageContext.servletContext.contextPath}/" + $('#encodedData').val();
                console.log("Path: " + path);

                var filePath = path.replace("molecularrendering", "files");

                window.location.href = filePath;
            });

            var payload = {
                'jobName': "<c:out value='${response.jobName}'/>"
            };

            //Sends first request
            poll();

            //Polls every 30 seconds
            try {
                setInterval(async() => {
                    poll();
            },
                30000
            )
                ;
            } catch (e) {
                console.log(e);
            }

            function poll() {
                $.ajax({
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    type: "post",
                    url: "${pageContext.servletContext.contextPath}/poll", //this is my servlet
                    data: JSON.stringify(payload),
                    success: function (successResponse) {
                        console.log("sucess" + successResponse);

                        if (successResponse.secondaryStructure !== null) {
                            $('#secondaryStructure').html(successResponse.secondaryStructure);
                            $('#lastUpdated').html(new Date(parseFloat(successResponse.lastUpdated)));
                            $('#encodedData').val(successResponse.pdbFilePath);
                            $('#downloadButton').prop('disabled', false);
                            $('#progressBar').css("width", successResponse.progress + "%");
                            $('#progressPercentage').html(successResponse.progress + "%");
                        }
                    },
                    error: function (msg) {
                        console.log("failed" + msg);
                    }
                })
            }
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>