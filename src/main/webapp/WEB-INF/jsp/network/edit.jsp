<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="com.magister.db.domain.Network.Mode" %>
<%@ page import="com.magister.db.domain.MoteType" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Sensor network</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

<STYLE type="text/css">
/* By default twitter bootstrap has no spacing between inline elements.
   Please be aware, that adding margin-top will break multiple radio/checkbos-inline positioning */
.form-inline>* {
        margin-left: 3px;
        margin-bottom: 3px;
}
</STYLE>


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/template/header.jsp" />

    <div class="container">

        <div class="panel panel-primary">
            <div class="panel-heading">Edit network</div>
            <div class="panel-body">
                <form:form action="/network/saveedit">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <label for="name">Network name</label>
                        <form:input type="text" class="form-control" id="name" placeholder="Enter network name" path="name" />
                    </div>
                    <div class="form-group">
                        <form:checkbox path="enabled" label="enabled"/>
                    </div>
                    <div class="form-group">
                        <label for="name">Network mode</label>
                        <form:select class="form-control" id="mode" path="mode">
                            <form:options items="${Mode.values}" />
                        </form:select>
                    </div>
                    <h3>Motes</h3>
                    <c:forEach var="mote" items="${command.motes}" varStatus="status">
                         <div class="form-group form-inline">
                            <form:label path="motes[${status.index}].id">Mote Id</form:label>
                            <form:input path="motes[${status.index}].id" class="form-control"/>
                           
                            <form:hidden path="motes[${status.index}].power" class="form-control" />
                            <form:hidden path="motes[${status.index}].latitude" class="form-control" />
                            <form:hidden path="motes[${status.index}].longtitude" class="form-control" />
                            <form:hidden path="motes[${status.index}].moteType" class="form-control"/>
                            <form:hidden path="motes[${status.index}].delay" class="form-control" />
                            
                            <label class="checkbox-inline" for="isGateway" >
                                <form:checkbox path="motes[${status.index}].gateway"/>isGateway
                            </label>
                        </div>
                    </c:forEach>
    
                    <form:hidden path="topology.id"/>
    
                    <h3>Mote links<button id="addMoteLink" type="button" class="btn btn-success">Add mote link</button></h3>
                    <div id="linksContainer">
                        <c:forEach var="link" items="${command.topology.links}" varStatus="status">
                            <div class="form-group form-inline">
                                <form:label path="topology.links[${status.index}].source.id">Source</form:label>
                                <form:input path="topology.links[${status.index}].source.id" class="form-control" />
                                <form:label path="topology.links[${status.index}].target.id">Target</form:label>
                                <form:input path="topology.links[${status.index}].target.id" class="form-control" />
                             </div>
                        </c:forEach>
                    </div>
                    <button type="submit" class="btn btn-primary">Update</button>
                </form:form>
            </div>
        </div>

    </div>
    <!-- /.container -->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>


    <script type="text/javascript">
					$(function() {
						var counter = ${fn:length(command.topology.links)};
						$("#addMoteLink")
								.click(
										function(event) {
											event.preventDefault();

											var tpl = '<div class="form-inline form-group">'
				                                        + ' <label for="topology.linksXXX.source.id">Source</label>'
				                                        + ' <input id="topology.linksXXX.source.id" name="topology.links[XXX].source.id" class="form-control" type="text" value="8">'
				                                        + ' <label for="topology.linksXXX.target.id">Target</label>'
				                                        + ' <input id="topology.linksXXX.target.id" name="topology.links[XXX].target.id" class="form-control" type="text" value="9">'
													+ '</div>';
											tpl = tpl.replace(/XXX/gi, counter);
											$("#linksContainer").append(tpl);
											++counter;
										});

					});
				</script>
                
</body>
</html>