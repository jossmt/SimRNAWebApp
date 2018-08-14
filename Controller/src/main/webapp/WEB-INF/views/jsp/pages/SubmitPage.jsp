<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

        <div class="container">
            <h1>Make Submission</h1>

            <div class="innercontainer">
                <div class="inline">
                    <label>Job Name : </label>
                    <label>Input Sequence : </label>
                    <label>Secondary Structure (Optional) : </label>
                </div>
                <div class="inline">
                    <input id="jobName" path="jobName" type="text" name="jobName"/>
                    <input size="40" id="sequence" path="sequence" type="text" name="sequence"/>
                    <input size="40" id="secondaryStructure" path="secondaryStructure" type="text"
                           name="secondaryStructure"/>
                </div>
            </div>

            <div class="inline">
                <button id="submitButton" type="submit" name="submit" class="submit action-button"
                        onclick="sendRequest()">
                    Submit
                </button>
                <div id="loader" class="loader" hidden></div>
            </div>

            <h1 id="updatedJobNameLabel" hidden>Generated Job Reference: </h1>
            <h2 id="updatedJobName" hidden></h2>
            <button id="resultButton" onclick="resultRequest()" hidden>
                Get Result
            </button>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <style>

            .container {
                margin-left: 30px;
            }

            label {
                font-size: 20px;
                font-family: 'Orienta', sans-serif;
                display: block;
            }

            input {
                font-size: 15px;
                font-family: 'Orienta', sans-serif;
                display: block;
            }

            h1 {
                font-size: 30px;
                font-family: 'Orienta', sans-serif;
                color: #1abc9c; /* Green */
            }

            .inline {
                display: inline-block;
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
                cursor: pointer;
            }

            .loader {
                border: 16px solid #f3f3f3; /* Light grey */
                border-top: 16px solid #3498db; /* Blue */
                border-radius: 50%;
                width: 120px;
                height: 120px;
                animation: spin 2s linear infinite;
            }

            @keyframes spin {
                0% {
                    transform: rotate(0deg);
                }
                100% {
                    transform: rotate(360deg);
                }
            }
        </style>
        <script type="text/javascript">

            function resultRequest() {

                var jobRef = $('#updatedJobName').text();

                window.location.href = "${pageContext.servletContext.contextPath}/result/" + jobRef;
            }


            function sendRequest() {

                $('#loader').show();

                var payLoad = {
                    "jobName": $('#jobName').val(),
                    "sequence": $('#sequence').val(),
                    "secondaryStructure": $('#secondaryStructure').val()
                };

                try {
                    $.ajax({
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        type: "post",
                        url: "${pageContext.servletContext.contextPath}/predict", //this is my servlet
                        data: JSON.stringify(payLoad),
                        success: function (successResponse) {
                            console.log("sucess" + successResponse);
                            $('#loader').hide();
                            $('#submitButton').hide();
                            $('#updatedJobNameLabel').show();
                            $('#updatedJobName').html(successResponse.jobName).show();
                            $('#resultButton').show();
                        },
                        error: function (msg) {
                            console.log("failed" + msg);
                        }
                    })
                } catch (e) {
                    console.log(e);
                }
            };
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>
