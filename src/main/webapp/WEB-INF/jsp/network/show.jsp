<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
            <div class="panel-heading">Network with id=${network.id} and name=${network.name}</div>
            <div class="panel-body">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Mote id</th>
                            <th>Mote power</th>
                            <th>Latitude</th>
                            <th>Longtitude</th>
                            <th>Type</th>
                            <th>Delay(seconds)</th>
                            <th>Gateway</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="mote" items="${network.motes}">
                            <tr>
                                <td><a href="/mote/show?id=${mote.id}">${mote.id}</a></td>
                                <td>${mote.power}</td>
                                <td>${mote.latitude}</td>
                                <td>${mote.longtitude}</td>
                                <td>${mote.moteType}</td>
                                <td>${mote.delay}</td>
                                <td>${mote.gateway}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <!-- a href="/network/create" class="btn btn-success">Create new network</a> -->
            </div>
        </div>

    </div>
    <!-- /.container -->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</body>
</html>