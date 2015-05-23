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

<style type="text/css">
.carousel-inner {
    height: 400px;
}

.carousel-inner .item {
    overflow: hidden;
    max-width: 100%;
    max-height: 100%;
}

.carousel-inner .item img {
    width: 100%;
    height: 400px;
    max-height: 400px;
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

    <div class="container-fluid">

        <div id="carousel" class="carousel slide" data-ride="carousel" data-pause="false">
            <!-- Indicators 
            <ol class="carousel-indicators">
                <li data-target="#carousel" data-slide-to="0" class="active"></li>
                <li data-target="#carousel" data-slide-to="1"></li>
                <li data-target="#carousel" data-slide-to="2"></li>
            </ol>-->

            <!-- Wrapper for slides -->
            <div class="carousel-inner" role="listbox">
                <div class="item active">
                    <img src="/images/carousel/1.jpg">
                    <div class="carousel-caption">...</div>
                </div>
                <div class="item">
                    <img src="/images/carousel/2.png">
                    <div class="carousel-caption">...</div>
                </div>
                <div class="item active">
                    <img src="/images/carousel/3.jpg">
                    <div class="carousel-caption">...</div>
                </div>
                <div class="item">
                    <img src="/images/carousel/4.png">
                    <div class="carousel-caption">...</div>
                </div>
                <div class="item">
                    <img src="/images/carousel/6.png">
                    <div class="carousel-caption">...</div>
                </div>
            </div>

            <!-- Controls
            <a class="left carousel-control" href="#carousel" role="button" data-slide="prev"> <span
                class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span class="sr-only">Previous</span>
            </a> <a class="right carousel-control" href="#carousel" role="button" data-slide="next"> <span
                class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> <span class="sr-only">Next</span>
            </a> -->
        </div>
        <hr/>
        <div class="panel panel-primary">
            <div class="panel-heading">Links</div>
            <div class="panel-body">
                <a href="/network/list">Networks list</a>
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