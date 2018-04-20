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
                            <form id="theForm" class="form-inline" method="post" th:object="${r'${'}${table.instanceName}${r'}'}">
                                <#--<div class="form-group">-->
                                    <#--<label for="name"></label>-->
                                    <#--<input type="text" class="form-control" placeholder="">-->
                                <#--</div>-->
                                <button type="submit" class="btn btn-info">提交</button>
                            </form>
                            <div>
                                <hr/>
                            </div>

                            <div class="bs-example pull-right" data-example-id="single-button-dropdown">
                                <div class="btn-group">
                                    <a th:href="@{这里填url地址}" class="btn btn-primary print-btn" ><i class="fa fa-plus"></i> 增加</a>
                                </div><!-- /btn-group -->
                                <div class="btn-group">
                                    <a href="javascript: doModify()" class="btn btn-primary print-btn"><i class="fa fa-pencil"></i> 修改</a>
                                </div><!-- /btn-group -->
                                <div class="btn-group">
                                    <a href="javascript: doDelete()" class="btn btn-danger print-btn" ><i class="fa  fa-times-circle"></i> 删除</a>
                                </div><!-- /btn-group -->
                            </div>

                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <#list table.otherColumns as column>
                                    <th>${column.comment}</th>
                                    </#list>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td><input id="radio" type="radio" class="flat-red" name="radio" /></td>
                                        <#list table.otherColumns as column>
                                        <td></td>
                                        </#list>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div th:replace="~{fragment/page :: page}"></div>
                        <!-- 带form的分页 -->
                        <!--<div th:replace="~{fragment/page :: pageForm('theForm')}"></div>-->
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
<script th:inline="javascript">

    /**
     * 修改记录函数
     */
    function doModify() {
        var itemId = $('#radio:checked').val();
        var url = /*[[@{'这里填url地址'}]]*/ "";
        if(url == "")
            return;
        location.href = url + itemId;
    }

    /**
     * 管理员账号不要删除，设置为不可用状态就行enable = 0
     */
    function doUnEnable() {
        if(confirm("确定删除选中的记录?")) {
            var itemId = $('#radio:checked').val();
            var url = /*[[@{'这里填url地址'}]]*/ "";

            if(url == "")
                return;

            url += itemId;
            $.post(url, {}, function (data) {
                alert(data.result);
                location.reload(true)
            })
        }
    }

    /**
     * 删除记录函数
     */
    function doDelete() {
        if(confirm("确定删除选中的记录?")) {
            var itemId = $('#radio:checked').val();
            var url = /*[[@{'这里填url地址'}]]*/ "";

            if(url == "")
                return;

            url += itemId;
            $.post(url, {}, function (data) {
                alert(data.result);
                location.reload(true)
            })
        }
    }
</script>
</body>
</html>
