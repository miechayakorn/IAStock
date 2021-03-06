<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    HttpSession sessionYear = request.getSession(false);
    if (sessionYear == null) {
        response.sendRedirect("Home");
    } else if (sessionYear.getAttribute("year") == null) {
        response.sendRedirect("Home");
    }
%>
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
        <link href="jquery-bootgrid/jquery.bootgrid.min.css" rel="stylesheet">
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
                                    Year : ${sessionScope.year}
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
                    <a href="Dashboard" class="list-group-item list-group-item-action waves-effect">
                        <i class="fas fa-chart-pie mr-3"></i>Dashboard
                    </a>
                    <a href="Stock" class="list-group-item active waves-effect">
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
                            <span>Stock</span>
                        </h4>
                        
                        <div class="d-flex justify-content-center">
                            <p class="mb-2 mb-sm-0 pt-1 mr-3">เบิกใช้/เพิ่มของ</p>
                            
                            <a class="btn btn-danger btn-sm my-0 p waves-effect waves-light" href="Stock_draw">
                                <i class="fas fa-minus-square"></i>
                            </a>
                            <a class="btn btn-success btn-sm my-0 p waves-effect waves-light" href="Stock_add">
                                <i class="fas fa-plus-square"></i>
                            </a>

                        </div>
                    </div>

                </div>
                <!-- Heading -->

                <!--Grid row-->
                <div class="row wow fadeIn">

                    <!--Grid column-->
                    <div class="col-md-12 mb-12">

                        <!--Card-->
                        <div class="card mb-4">

                            <!-- Card header -->
                            <div class="card-header text-center">
                                <strong>Stock</strong>
                            </div>
                            <!--Content-->
                            <table id="grid-basic" class="table table-striped table-bordered" cellspacing="0" width="100%">
                                <thead>
                                    <tr>
                                        <th data-column-id="id" data-type="numeric" data-width="8%">ที่</th>
                                        <th data-column-id="itemid" data-width="10%">รหัสวัสดุ</th>
                                        <th data-column-id="itemname"  data-width="40%">ชื่อวัสดุ</th>
                                        <th data-column-id="unit" data-width="10%">หน่วย</th>
                                        <th data-column-id="price">ราคาต่อหน่วย</th>
                                        <th data-column-id="itemtotal">จำนวนคงเหลือ</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${items}" varStatus="vs" var="item">
                                        <tr>
                                            <td>${vs.count}</td>
                                            <td>${item.itemid}</td>
                                            <td>${item.itemname}</td>
                                            <td>${item.unit}</td>
                                            <td>${item.price}</td>
                                            <td>${item.itemtotal}</td>
                                        </tr>
                                    </tr>
                                    </c:forEach>

                                </tbody>
                            </table>
                        </div>
                    </div>
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
        © 2018 Copyright:
        <a href="https://mdbootstrap.com/education/bootstrap/" target="_blank"> MDBootstrap.com </a>
    </div>
    <!--/.Copyright-->

</footer>
<!--/.Footer-->

<!-- SCRIPTS -->
<!-- JQuery -->
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="jquery-bootgrid/jquery.bootgrid.min.js"></script>
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
    $("#grid-basic").bootgrid();
</script>
</body>

</html>
