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

<link rel="stylesheet" href="/css/vis.min.css">

<style type="text/css">
#mynetwork {
	height: 400px;
	border: 1px solid lightgray;
}
</style>

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
            <div class="panel-heading">Topology for network with id=${network.id} and name=${network.name}. Network mode is ${network.mode}, status ${network.status}</div>
            <div class="panel-body">
                <div id="mynetwork"></div>
            </div>
        </div>
    </div>
    <!-- /.container -->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="/js/vis.min.js"></script>
    <script type="text/javascript">
					$(function() {
						// create an array with nodes
					    var nodes = new vis.DataSet([
                        <c:forEach var="mote" items="${network.motes}">
                            {
                            	id:${mote.id}, 
                            	label:"${mote.gateway ? 'Gateway' : 'Sensor'}(id=${mote.id})",
                            	color:"${mote.alive ? (mote.gateway ? '#7BE141' : '#00DDFF') : 'red'}",
                            	title:"${mote}"
                            },
                        </c:forEach>  	                                 
					    ]);

					    // create an array with edges
					    var edges = new vis.DataSet([
                        <c:forEach var="link" items="${network.topology.links}">
                            {
                            	from:${link.source.id}, 
                            	to:${link.target.id},
                            	arrows: 'to'
                            },
                        </c:forEach>    
					    ]);

					    // create a network
					    var container = document.getElementById('mynetwork');

					    // provide the data in the vis format
					    var data = {
					        nodes: nodes,
					        edges: edges
					    };
					    var options = {
					    };

					    // initialize your network!
					    var network = new vis.Network(container, data, options);
					});
				</script>
</body>
</html>