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
            <div class="panel-heading">Create network</div>
            <div class="panel-body">
            
                <form:form action="/network/save" commandName="network">
                    
                    <form:errors path="name" cssClass="alert alert-danger" element="div" role="alert"/>
                    
                    <div class="form-group">
                        <label for="name">Network name</label>
                        <form:input type="text" class="form-control" id="name" placeholder="Enter network name" path="name" />
                    </div>
                    <div class="form-group">
                        <label for="name">Network mode</label>
                        
                        
                        <form:select class="form-control" id="mode" path="mode">
                            <!-- 
                            All networks should be created in AUTOMATIC mode.
                            Other modes can be set during network edit process.
                             -->
                            <option selected value="AUTOMATIC">AUTOMATIC</option>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <button id="addMote" type="button" class="btn btn-success">Add mote</button>
                    </div>
                       
                    <form:errors path="motes" cssClass="alert alert-danger" element="div" role="alert"/>
                        
                    <div id="moteContainer">
                        <c:forEach var="mote" items="${network.motes}" varStatus="status">
                           <form:errors path="motes[${status.index}]*" cssClass="alert alert-danger" element="div" role="alert"/>
                            <div class="form-group form-inline">
                                <form:input path="motes[${status.index}].power" class="form-control" placeholder="Power(integer)"/>
                                <form:input path="motes[${status.index}].latitude" class="form-control" placeholder="Latitude(double)"/>
                                <form:input path="motes[${status.index}].longtitude" class="form-control" placeholder="Longtitude(double)"/>
                                <form:select path="motes[${status.index}].moteType" class="form-control">
                                    <form:options items="${MoteType.values()}"/>
                                </form:select>
                                <form:input path="motes[${status.index}].delay" class="form-control" placeholder="Delay(integer)"/>
                                <label class="checkbox-inline" for="motes[${status.index}].gateway">
                                    <form:checkbox path="motes[${status.index}].gateway"/>isGateway
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                    <button type="submit" class="btn btn-primary">Create</button>
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
						var counter = ${fn:length(network.motes)};
						$("#addMote")
								.click(
										function(event) {
											event.preventDefault();

											var tpl = '<div class="form-inline form-group">'
													+ ' <input id="motesXXX.power" name="motes[XXX].power" placeholder="Power(integer)" name="power" class="form-control" type="text" />'
													+ ' <input id="motesXXX.latitude" name="motes[XXX].latitude" placeholder="Latitude(double)" name="latitude" class="form-control" type="text"/>'
													+ ' <input id="motesXXX.longtitude" name="motes[XXX].longtitude" placeholder="Longtitude(double)" name="longtitude" class="form-control" type="text"/>'
													+ ' <select id="motesXXX.moteType" name="motes[XXX].moteType" name="moteType" class="form-control">'
													+     '<c:forEach items="${MoteType.values()}" var="enumValue">'
													+         '<option value="${enumValue}">${enumValue}</option>'
												    +     '</c:forEach>'
													+ ' </select>'
													+ ' <input id="motesXXX.delay" name="motes[XXX].delay" placeholder="Delay(integer)" name="delay" class="form-control" type="text" />'
													+ ' <label class="checkbox-inline" for="motesXXX.gateway">'
								                    + '   <input type="checkbox" id="motesXXX.gateway" name="motes[XXX].gateway"/>isGateway'
								                    + ' </label>'
								                    //+ '<input type="button" class="btn btn-danger" value="Remove" onclick="alert(/Unsupported yet/)"/>'
													+ '</div>';
											tpl = tpl.replace(/XXX/gi, counter);
											$("#moteContainer").append(tpl);
											++counter;
										});

					});
				</script>
</body>
</html>