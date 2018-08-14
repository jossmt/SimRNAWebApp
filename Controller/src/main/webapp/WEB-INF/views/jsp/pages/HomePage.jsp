<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

        <div>
            <h1 id="title">A secondary and tertiary structure prediction program using SIMRNA.</h1>
        </div>

        <div>
            <h1>Step 1:</h1>
            <h2>Submit the RNA sequence using a unique job reference.</h2>
            <img src="<c:url value="/resources/img/HomeStep1.png"/>"/>
        </div>
        <div>
            <h1>Step 2:</h1>
            <h2>Wait for the result of the submission. A new job reference will be generated if the id given is not
            unique. Select "Get Result" to view result.</h2>
            <img src="<c:url value="/resources/img/HomeStep2.png"/>"/>
        </div>
        <div>
            <h1>Step 3:</h1>
            <h2>You can then download the PDB file of the predicted structure. The longer the request is able
            to run, the more accurate the structure prediction will be. A progress bar will indicate
            how long the request has left for the result to be optimal.</h2>
            <img src="<c:url value="/resources/img/HomeStep3.png"/>"/>
        </div>
        <div>
            <h1>Step 4 (Optional):</h1>
            <h2>Given the pdb file has been downloaded, you can either open the result in PyMol, or if
            PyMol is unavailable, Web3DMol can be used to display the structure.</h2>
            <img src="<c:url value="/resources/img/HomeStep4.png"/>"/>
        </div>

        <style>

            h1 {
                font-size: 30px;
                color: #1abc9c;
            }

            #title {
                font-size:30px;
                text-align: center;
            }

            img {
                width: 80%;
                height: 400px;
                text-align: center;
            }

        </style>
    </tiles:putAttribute>
</tiles:insertDefinition>