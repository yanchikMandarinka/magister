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
            <div class="panel-heading">Sensor networks</div>
            <div class="panel-body">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Network Id</th>
                            <th>Network name</th>
                            <th>Network mode</th>
                            <th>Motes count</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="network" items="${networks}">
                            <tr>
                                <td><a href="/network/show?id=${network.id}">${network.id}</a></td>
                                <td>${network.name}</td>
                                <td>${network.mode}</td>
                                <td>${fn:length(network.motes)}</td>
                                <td>${network.status}</td>
                                <td>
                                <a href="/network/show?id=${network.id}"><span class="glyphicon glyphicon-eye-open text-success" aria-hidden="true"></span></a>
                                <a href="/network/edit?id=${network.id}"><span class="glyphicon glyphicon-pencil text-warning" aria-hidden="true"></span></a>
                                <a href="/network/chart?id=${network.id}"><span class="glyphicon glyphicon-picture text-primary" aria-hidden="true"></span></a>
                                <a href="/network/remove?id=${network.id}"><span class="glyphicon glyphicon-remove text-danger" aria-hidden="true"></span></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <a href="/network/create" class="btn btn-success">Create new network</a>
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