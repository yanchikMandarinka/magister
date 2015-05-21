<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Bootstrap 101 Template</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

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
                <form:form action="/network/save">
                    <div class="form-group">
                        <label for="name">Network name</label> <form:input type="text" class="form-control" id="name"
                            placeholder="Enter network name" path="name"/>
                    </div>
                    <div class="form-group">
                        <button id="addMote" type="button" class="btn btn-success">Add mote</button>
                    </div>
                    <div id="moteContainer"></div>
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
    	var counter = 0;
        $("#addMote").click(function (event) {
            event.preventDefault();
            
            var tpl = '<div class="form-inline form-group">'
                + '<input id="motesXXX.power" name="motes[XXX].power" placeholder="Power" name="power" class="form-control" type="text" value="0"/>'
                + '<input id="motesXXX.latitude" name="motes[XXX].latitude" placeholder="Latitude" name="latitude" class="form-control" type="text" value="0.0"/>'
                + '<input id="motesXXX.longtitude" name="motes[XXX].longtitude" placeholder="Longtitude" name="longtitude" class="form-control" type="text" value="0.0"/>'
                + '<input type="button" class="btn btn-danger" value="Remove"/>'
                + '</div>';
            //var mote = $.template(tpl);
            tpl = tpl.replace(/XXX/gi, counter);
            //alert(tpl)
            $("#moteContainer").append(tpl);
            ++counter;
        });

    });
    </script>
</body>
</html>