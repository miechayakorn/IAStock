<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Material Design Bootstrap</title>
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Material Design Bootstrap -->
        <link href="css/mdb.min.css" rel="stylesheet">
        <!-- Your custom styles (optional) -->
        <link href="css/style.min.css" rel="stylesheet">
        <style>

            .map-container{
                overflow:hidden;
                padding-bottom:56.25%;
                position:relative;
                height:0;
            }
            .map-container iframe{
                left:0;
                top:0;
                height:100%;
                width:100%;
                position:absolute;
            }
        </style>
    </head>

    <body class="grey lighten-3">

        <!--Main Navigation-->
        <header>

            <!-- Navbar -->
            <nav class="navbar fixed-top navbar-expand-lg navbar-light white scrolling-navbar">
                <div class="container-fluid">

                    <!-- Brand -->
                    <a class="navbar-brand">
                        <strong class="blue-text">IA-Stock</strong>
                    </a>

                    <!-- Collapse -->
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                    <!-- Links -->
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">

                        <!-- Left -->
                        <ul class="navbar-nav mr-auto">

                        </ul>

                        <!-- Right -->
                        <ul class="navbar-nav nav-flex-icons">
                            <li class="nav-item">
                                <h3 class="nav-link">
                                    555
                                </h3>
                            </li>
                            <li class="nav-item">
                                <a href="Logout" class="btn btn-warning btn-sm waves-effect waves-light">
                                    Exit <i class="fas fa-sign-out-alt"></i>
                                </a>
                            </li>
                        </ul>

                    </div>

                </div>
            </nav>
            <!-- Navbar -->

            <!-- Sidebar -->
            <div class="sidebar-fixed position-fixed">



                <div class="list-group list-group-flush mt-5">
                    <a href="Dashboard" class="list-group-item active waves-effect">
                        <i class="fas fa-chart-pie mr-3"></i>Dashboard
                    </a>
                    <a href="Stock" class="list-group-item list-group-item-action waves-effect">
                        <i class="fas fa-map mr-3"></i>Stock</a>
                    <a href="History" class="list-group-item list-group-item-action waves-effect">
                        <i class="fas fa-history mr-3"></i>History</a>
                    <!-- <a href="#" class="list-group-item list-group-item-action waves-effect">
                      <i class="fas fa-user mr-3"></i>Profile</a>
                    <a href="#" class="list-group-item list-group-item-action waves-effect">
                      <i class="fas fa-table mr-3"></i>Tables</a> -->

                </div>

            </div>
            <!-- Sidebar -->

        </header>
        <!--Main Navigation-->

        <!--Main layout-->
        <main class="pt-5 mx-lg-5">
            <div class="container-fluid mt-5">

                <!-- Heading -->
                <div class="card mb-4 wow fadeIn">

                    <!--Card content-->
                    <div class="card-body d-sm-flex justify-content-between">

                        <h4 class="mb-2 mb-sm-0 pt-1">
                            <span style="color:#007bff;" >Home Page</span>
                            <span>/</span>
                            <span>Dashboard</span>
                        </h4>

                        <form class="d-flex justify-content-center">
                            <!-- Default input -->
                            <input type="search" placeholder="Type your query" aria-label="Search" class="form-control">
                            <button class="btn btn-primary btn-sm my-0 p" type="submit">
                                <i class="fas fa-search"></i>
                            </button>

                        </form>

                    </div>

                </div>
                <!-- Heading -->

                <!--Grid row-->
                <div class="row wow fadeIn">

                    <!--Grid column-->
                    <div class="col-md-9 mb-4">

                        <!--Card-->
                        <div class="card">

                            <!--Card content-->
                            <div class="card-body">

                                <canvas id="myChart"></canvas>

                            </div>

                        </div>
                        <!--/.Card-->

                    </div>
                    <!--Grid column-->

                    <!--Grid column-->
                    <div class="col-md-3 mb-4">

                        <!--Card-->
                        <div class="card mb-4">

                            <!-- Card header -->
                            <div class="card-header text-center">
                                Pie chart
                            </div>

                            <!--Card content-->
                            <div class="card-body">

                                <canvas id="pieChart"></canvas>

                            </div>

                        </div>
                        <!--/.Card-->

                        <!--Card-->
                        <div class="card mb-4">

                            <!--Card content-->
                            <div class="card-body">

                                <!-- List group links -->
                                <div class="list-group list-group-flush">
                                    <a class="list-group-item list-group-item-action waves-effect">Sales
                                        <span class="badge badge-success badge-pill pull-right">22%
                                            <i class="fas fa-arrow-up ml-1"></i>
                                        </span>
                                    </a>
                                    <a class="list-group-item list-group-item-action waves-effect">Traffic
                                        <span class="badge badge-danger badge-pill pull-right">5%
                                            <i class="fas fa-arrow-down ml-1"></i>
                                        </span>
                                    </a>
                                    <a class="list-group-item list-group-item-action waves-effect">Orders
                                        <span class="badge badge-primary badge-pill pull-right">14</span>
                                    </a>
                                    <a class="list-group-item list-group-item-action waves-effect">Issues
                                        <span class="badge badge-primary badge-pill pull-right">123</span>
                                    </a>
                                    <a class="list-group-item list-group-item-action waves-effect">Messages
                                        <span class="badge badge-primary badge-pill pull-right">8</span>
                                    </a>
                                </div>
                                <!-- List group links -->

                            </div>

                        </div>
                        <!--/.Card-->

                    </div>
                    <!--Grid column-->

                </div>
                <!--Grid row-->


            </div>
        </main>
        <!--Main layout-->

        <!--Footer-->
        <footer class="page-footer text-center font-small primary-color-dark darken-2 mt-4 wow fadeIn">



            <hr class="my-4">


            <!--Copyright-->
            <div class="footer-copyright py-3">
                Â© 2018 Copyright:
                <a href="https://mdbootstrap.com/education/bootstrap/" target="_blank"> MDBootstrap.com </a>
            </div>
            <!--/.Copyright-->

        </footer>
        <!--/.Footer-->

        <!-- SCRIPTS -->
        <!-- JQuery -->
        <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
        <!-- Bootstrap tooltips -->
        <script type="text/javascript" src="js/popper.min.js"></script>
        <!-- Bootstrap core JavaScript -->
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <!-- MDB core JavaScript -->
        <script type="text/javascript" src="js/mdb.min.js"></script>
        <!-- Initializations -->
        <script type="text/javascript">
            // Animations initialization
            new WOW().init();

        </script>

        <!-- Charts -->
        <script>
            // Line
            var ctx = document.getElementById("myChart").getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
                    datasets: [{
                            label: '# of Votes',
                            data: [12, 19, 3, 5, 2, 3],
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)'
                            ],
                            borderColor: [
                                'rgba(255,99,132,1)',
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 206, 86, 1)',
                                'rgba(75, 192, 192, 1)',
                                'rgba(153, 102, 255, 1)',
                                'rgba(255, 159, 64, 1)'
                            ],
                            borderWidth: 1
                        }]
                },
                options: {
                    scales: {
                        yAxes: [{
                                ticks: {
                                    beginAtZero: true
                                }
                            }]
                    }
                }
            });

            //pie
            var ctxP = document.getElementById("pieChart").getContext('2d');
            var myPieChart = new Chart(ctxP, {
                type: 'pie',
                data: {
                    labels: ["Red", "Green", "Yellow", "Grey", "Dark Grey"],
                    datasets: [{
                            data: [300, 50, 100, 40, 120],
                            backgroundColor: ["#F7464A", "#46BFBD", "#FDB45C", "#949FB1", "#4D5360"],
                            hoverBackgroundColor: ["#FF5A5E", "#5AD3D1", "#FFC870", "#A8B3C5", "#616774"]
                        }]
                },
                options: {
                    responsive: true,
                    legend: false
                }
            });



        </script>

    </script>
</body>

</html>
