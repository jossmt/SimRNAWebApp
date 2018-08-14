<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

        <div class="container">
            <h1>Find Result</h1>

            <div class="innercontainer">
                <div class="inline">
                    <label>Job Name : </label>
                </div>
                <div class="inline">
                    <input id="jobName" path="jobName" type="text" name="jobName"/>
                </div>
            </div>

            <button id="submitButton" type="submit" name="submit" class="submit action-button" onclick="sendRequest()">
                Search
            </button>
        </div>
        <style>

            .container {
                margin-left: 30px;
            }

            label {
                font-size: 20px;
                font-family: 'Orienta', sans-serif;
                display:block;
            }

            input {
                font-size: 15px;
                font-family: 'Orienta', sans-serif;
                display:block;
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
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <script type="text/javascript">

            function sendRequest() {

                var jobRef = $('#jobName').val();

                window.location.href = "${pageContext.servletContext.contextPath}/result/" + jobRef;
            }

        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>