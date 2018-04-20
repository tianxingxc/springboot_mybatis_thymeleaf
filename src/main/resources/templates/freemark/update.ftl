<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<!-- head -->
<head th:replace="~{fragment/head :: head}"></head>
<!-- head -->

<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- header -->
    <header th:replace="~{fragment/header :: header}"></header>
    <!-- header -->

    <!-- Left side column. contains the sidebar -->
    <!-- aside -->
    <aside th:replace="~{fragment/aside :: menu}"></aside>
    <!-- aside -->

    <!-- =============================================== -->

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <div>
                    <div class="box box-default">
                        <div class="box-header with-border">
                            <ol class="breadcrumb">
                                <span>当前页面：</span>
                                <li><a href="#">模块名</a></li>
                                <li><a href="#">栏目名</a></li>
                                <li class="active">功能</li>
                            </ol>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <form class="form-horizontal" th:object="${r'${'}${table.instanceName}${r'}'}" method="post">
                            <#list table.primaryKeys as column >
                                <input hidden="hidden" name="${column.attrName}" id="${column.attrName}" th:value="${r'*{'}${column.attrName}${r'}'}" />
                            </#list>
                            <#list table.otherColumns as column>
                                <#if column.textLength < 512>
                                <div class="form-group">
                                    <label for="${column.attrName}" class="col-sm-2 control-label">${column.comment}</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" name="${column.attrName}" id="${column.attrName}" th:value="${r'*{'}${column.attrName}${r'}'}" placeholder="${column.comment}">
                                    </div>
                                    <div class="pull-left col-sm-3">
                                        <label class="control-label text-danger" th:errors="${r'*{'}${column.attrName}${r'}'}"></label>
                                    </div>
                                </div>
                                <#else>
                                <div class="form-group">
                                    <label for="${column.attrName}" class="col-sm-2 control-label">${column.comment}</label>
                                    <div class="col-sm-6">
                                        <textarea class="form-control" id="${column.attrName}" name="${column.attrName}" th:text="${r'*{'}${column.attrName}${r'}'}" placeholder="${column.comment}"></textarea>
                                    </div>
                                    <div class="pull-left col-sm-3">
                                        <label class="control-label text-danger" th:errors="${r'*{'}${column.attrName}${r'}'}"></label>
                                    </div>
                                </div>
                                </#if>
                            </#list>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-6">
                                        <button type="submit" class="btn btn-info btn-block">确定</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- footer begin -->
    <footer th:replace="~{fragment/footer::footer}"></footer>
    <!-- footer end -->
    <div></div>
</div>
<!-- ./wrapper -->

</body>
</html>
