<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<link href="<c:url value="/resources/css/header.css" />" rel="stylesheet">

<div>
    <label class="mobile_menu" for="mobile_menu">
        <span>Menu</span>
    </label>
    <input id="mobile_menu" type="checkbox">
    <ul class="nav">
        <li><a href="${pageContext.servletContext.contextPath}/"><i class="icon-home icon-large"></i></a></li>

        <!----- Regular Menu Button ---->
        <li><a href="${pageContext.servletContext.contextPath}/submit">Submit</a></li>
        <!----- Regular Menu Button Ends---->

        <!----- Regular Menu Button ---->
        <li><a href="${pageContext.servletContext.contextPath}/result">Result</a></li>
        <!----- Regular Menu Button Ends---->

        <!----- Regular Menu Button ---->
        <li><a href="${pageContext.servletContext.contextPath}/display">Display</a></li>
        <!----- Regular Menu Button Ends---->

        <!----- Regular Menu Button ---->
        <li><a href="${pageContext.servletContext.contextPath}/about">About</a></li>
        <!----- Regular Menu Button Ends---->

        <!----- Search Form Starts---->
        <li class="search">
            <form action="Search.php">
                <i class="icon-search icon-large"></i><input type="text">
            </form>
        </li>
        <!----- Search Form Ends ---->


    </ul>
</div>
</div>