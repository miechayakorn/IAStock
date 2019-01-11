<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Material Design Bootstrap -->
        <link href="css/mdb.min.css" rel="stylesheet">
        <!-- Your custom styles (optional) -->
        <link href="css/style.min.css" rel="stylesheet">
        <style>
            html,
            body,
            header,
            .view {
                height: 100%;
            }

            @media (max-width: 740px) {
                html,
                body,
                header,
                .view {
                    height: 100vh;
                }
            }


            .navbar:not(.top-nav-collapse) {
                background: transparent !important;
            }

            h1 {
                letter-spacing: 8px;
            }

            h5 {
                letter-spacing: 3px;
            }

            .hr-light {
                border-top: 3px solid #fff;
                width: 80px;
            }
        </style>
    </head>
    <body>

        <header>

            <!-- Full Page Intro -->
            <div class="view" style="background-color: #FA4616; background-repeat: no-repeat; background-size: cover; background-position: center center;">
                <!-- Mask & flexbox options-->
                <div class="mask card-body d-sm-flex justify-content-between align-items-center" >
                    <!-- Content -->
                    <div class="container">
                        <!--Grid row-->
                        <div class="row">
                            <!--Grid column-->
                            <div class="col-md-12 mb-4 white-text text-center">
                                <h1 class="h1-reponsive white-text text-uppercase font-weight-bold mb-0 pt-md-5 pt-5 wow fadeInDown" data-wow-delay="0.3s"><strong>IA-Stock</strong></h1>
                                <hr class="hr-light my-4 wow fadeInDown" data-wow-delay="0.4s">
                                
                                <!--index-->
                                <c:if test="${pageContext.request.requestURI == '/IAStock/'}">
                                    <a href="Home" class="btn btn-outline-white wow fadeInDown" data-wow-delay="0.4s">Home</a>
                                </c:if>
                                
                                <!--Home-->
                                <c:if test="${pageContext.request.requestURI == '/IAStock/index.jsp'}">
                                    
                                <h5 class="text-uppercase mb-4 white-text wow fadeInDown" data-wow-delay="0.4s"><strong>เลือกปีที่ต้องการ</strong></h5>
                                <form>
                                    <select class="browser-default custom-select mb-4" name="year">
                                        <option value="" selected disabled>Choose option</option>
                                        <c:forEach items="${yearTotals}" var="yearTotal" varStatus="vs">
                                        <option value="${yearTotal.getYearstock()}">${yearTotal.getYearstock()}</option>
                                        </c:forEach>
                                        
                                    </select>
                                    <button class="btn btn-outline-white wow fadeInDown" data-wow-delay="0.4s">submit</button>
                                </form>
                                </c:if>
                                
                                
                            </div>
                            <!--Grid column-->
                        </div>
                        <!--Grid row-->
                    </div>
                    <!-- Content -->
                </div>
                <!-- Mask & flexbox options-->
            </div>
            <!-- Full Page Intro -->
        </header>
        <!-- Main navigation -->
        <!--Main Layout-->
        <main>

        </main>
        <!--Main Layout-->
    </body>
    <!-- SCRIPTS -->
    <!-- JQuery -->
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <!-- Bootstrap tooltips -->
    <script type="text/javascript" src="js/popper.min.js"></script>
    <!-- Bootstrap core JavaScript -->
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <!-- MDB core JavaScript -->
    <script type="text/javascript" src="js/mdb.min.js"></script>
</html>