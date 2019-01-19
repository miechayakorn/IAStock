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
                            <a style="color:#007bff;" href="Stock">Stock</a>
                            <span>/</span>
                            <span>Add</span>
                        </h4>

                        <div class="d-flex justify-content-center">

                            <a class="btn btn-danger btn-sm my-0 p waves-effect waves-light" href="Stock">
                                <i class="fas fa-arrow-left"></i> Back
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
                            <table id="grid-basic" class="table table-striped table-bordered text-center" cellspacing="0" width="100%">
                                <thead>
                                    <tr class="d-flex">
                                        <th class="col-1">รหัสวัสดุ

                                        </th>
                                        <th class="col-4">ชื่อวัสดุ

                                        </th>
                                        <th class="col-1">ราคาต่อหน่วย

                                        </th>
                                        <th class="col-1">จำนวนคงเหลือ

                                        </th>
                                        <th class="col-5">รายการ

                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${items}" varStatus="vs" var="item">
                                        <tr class="d-flex">        
                                            <td class="col-1">${item.itemid}</td>
                                            <td class="col-4">${item.itemname}</td>
                                            <td class="col-1">${item.unit}</td>
                                            <td class="col-1">${item.itemtotal}</td>
                                            <td class="col-5">
                                                <form action="Stock_add">
                                                    <div class="form-row">
                                                        <div class="col-5 mt-1">
                                                            <input type="number" name="add" min="0" class="form-control" required placeholder="จำนวนที่ซื้อเพิ่ม">
                                                            <input type="hidden" name="id" value="${item.itemid}">
                                                        </div>
                                                        <font color="#FD494C">*</font>
                                                        <div class="col-4 mt-1">
                                                            <input type="text" name="annotation" class="form-control" placeholder="ลงชื่อ">
                                                        </div>
                                                        <button type="submit" class="btn btn-md btn-success"><i class="fas fa-plus-square"></i></button>
                                                    </div>
                                                </form>
                                            </td>
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
</body>

</html>
