<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH}/css/main.css">
	<link rel="stylesheet" href="${APP_PATH }/css/pagination.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
	</style>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 流程管理</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li style="padding-top:8px;">
				<div class="btn-group">
				  <button type="button" class="btn btn-default btn-success dropdown-toggle" data-toggle="dropdown">
					<i class="glyphicon glyphicon-user"></i> 张三 <span class="caret"></span>
				  </button>
					  <ul class="dropdown-menu" role="menu">
						<li><a href="#"><i class="glyphicon glyphicon-cog"></i> 个人设置</a></li>
						<li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
						<li class="divider"></li>
						<li><a href="index.html"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
					  </ul>
			    </div>
			</li>
            <li style="margin-left:10px;padding-top:8px;">
				<button type="button" class="btn btn-default btn-danger">
				  <span class="glyphicon glyphicon-question-sign"></span> 帮助
				</button>
			</li>
          </ul>
          <form class="navbar-form navbar-right">
            <input type="text" class="form-control" placeholder="Search...">
          </form>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
			<div class="tree">
				<ul style="padding-left:0px;" class="list-group">
					<li class="list-group-item tree-closed" >
						<a href="main.html"><i class="glyphicon glyphicon-dashboard"></i> 控制面板</a> 
					</li>
					<li class="list-group-item tree-closed">
						<span><i class="glyphicon glyphicon glyphicon-tasks"></i> 权限管理 <span class="badge" style="float:right">3</span></span> 
						<ul style="margin-top:10px;display:none;">
							<li style="height:30px;">
								<a href="user.html"><i class="glyphicon glyphicon-user"></i> 用户维护</a> 
							</li>
							<li style="height:30px;">
								<a href="role.html"><i class="glyphicon glyphicon-king"></i> 角色维护</a> 
							</li>
							<li style="height:30px;">
								<a href="permission.html"><i class="glyphicon glyphicon-lock"></i> 许可维护</a> 
							</li>
						</ul>
					</li>
					<li class="list-group-item tree-closed">
						<span><i class="glyphicon glyphicon-ok"></i> 业务审核 <span class="badge" style="float:right">3</span></span> 
						<ul style="margin-top:10px;display:none;">
							<li style="height:30px;">
								<a href="${APP_PATH}/auth/index"><i class="glyphicon glyphicon-check"></i> 实名认证审核</a> 
							</li>
							<li style="height:30px;">
								<a href="auth_adv.html"><i class="glyphicon glyphicon-check"></i> 广告审核</a> 
							</li>
							<li style="height:30px;">
								<a href="auth_project.html"><i class="glyphicon glyphicon-check"></i> 项目审核</a> 
							</li>
						</ul>
					</li>
					<li class="list-group-item">
						<span><i class="glyphicon glyphicon-th-large"></i> 业务管理 <span class="badge" style="float:right">7</span></span> 
						<ul style="margin-top:10px;">
							<li style="height:30px;">
								<a href="cert.html"><i class="glyphicon glyphicon-picture"></i> 资质维护</a> 
							</li>
							<li style="height:30px;">
								<a href="type.html"><i class="glyphicon glyphicon-equalizer"></i> 分类管理</a> 
							</li>
							<li style="height:30px;">
								<a href="${APP_PATH}/process/index" style="color:red"><i class="glyphicon glyphicon-random"></i> 流程管理</a> 
							</li>
							<li style="height:30px;">
								<a href="advertisement.html"><i class="glyphicon glyphicon-hdd"></i> 广告管理</a> 
							</li>
							<li style="height:30px;">
								<a href="message.html"><i class="glyphicon glyphicon-comment"></i> 消息模板</a> 
							</li>
							<li style="height:30px;">
								<a href="project_type.html"><i class="glyphicon glyphicon-list"></i> 项目分类</a> 
							</li>
							<li style="height:30px;">
								<a href="tag.html"><i class="glyphicon glyphicon-tags"></i> 项目标签</a> 
							</li>
						</ul>
					</li>
					<li class="list-group-item tree-closed" >
						<a href="param.html"><i class="glyphicon glyphicon-list-alt"></i> 参数管理</a> 
					</li>
				</ul>
			</div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="panel panel-default">
			  <div class="panel-heading">
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			  <div class="panel-body">
<form class="form-inline" role="form" style="float:left;">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input class="form-control has-success" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>

<button id="uploadProcDef" type="button" class="btn btn-primary" style="float:right;" ><i class="glyphicon glyphicon-upload"></i> 上传流程定义文件</button>
<br>

<form id="procDefForm" style="display:none;" action="${APP_PATH}/process/uploadProcDef" method="post"
 enctype="multipart/form-data">
        <input type="file" id="procDefFile" name="procDefFile">
</form>


 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr>
                  <th width="30">#</th>
                  <th>流程名称</th>
                  <th>流程版本</th>
                  <th>流程Key</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
                
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
						<div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
					 </td>
				 </tr>

			  </tfoot>
            </table>
          </div>
			  </div>
			</div>
        </div>
      </div>
    </div>

    <script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH}/script/docs.min.js"></script>
	<script src="${APP_PATH }/jquery/layer/layer.js"></script>
	<script src="${APP_PATH }/jquery/jquery.pagination.js"></script>
	<script src="${APP_PATH }/jquery/jquery-form.min.js"></script>
	
	
        <script type="text/javascript">
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
				
				queryPage(0);
				
            });
            
            $("#uploadProcDef").click(function(){
            	$("#procDefFile").click();
            });
            
            var loadingIndex = -1 ;
            $("#procDefFile").change(function(){
            	$("#procDefForm").ajaxSubmit({
            		beforeSubmit:function(){
            			loadingIndex = layer.msg('正在部署流程定义,请耐心等待...', {icon: 16});
            			return true ;
            		},
            		success:function(result){
            			layer.close(loadingIndex);
            			if(result.success){
            				layer.msg("部署成功", {time:1000, icon:6});
            				queryPage(0);
            			}else{
            				layer.msg(result.message, {time:1000, icon:5, shift:6});
            			}
            		}
            	});
            });
            
            
            
            var json = {
            		pageno : 1,
            		pagesize : 2
            };
            
            function queryPage(pageno){
            	json.pageno = pageno+1 ;
            	
            	$.ajax({
            		type:"post",
            		url:"${APP_PATH}/process/queryPage",
            		data:json,
            		success:function(result){
            			if(result.success){
            				//局部刷新
            				var page = result.data ;
            				var list = page.datas;
            				
            				var content = '';
            				
            				$.each(list,function(i,e){ // i表示当前元素索引，e表示当前对象
            					
            					content+='<tr>';
            					content+='  <td>'+(i+1)+'</td>';
            					content+='  <td>'+e.pdName+'</td>';
            					content+='  <td>'+e.pdVersion+'</td>';
            					content+='  <td>'+e.pdKey+'</td>';
            					content+='  <td>';
            					content+='	  <button type="button" onclick="window.location.href=\'${APP_PATH}/process/view/'+e.pdId+'\'" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
            					content+='	  <button type="button" onclick="deleteDeployment(\''+e.pdDeployId+'\',\''+e.pdName+'\')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
            					content+='  </td>';
            					content+='</tr>';
            					
            				});
            				
            				$("tbody").html(content);
            				
            				
            				// 创建分页
            				$("#Pagination").pagination(page.totalsize, {
            					num_edge_entries: 1, //边缘页数
            					num_display_entries: 4, //主体页数
            					callback:queryPage,
            					items_per_page:page.pagesize, //每页显示多少条
            					prev_text:"上一页",
            					next_text:"下一页",
            					current_page:page.pageno-1 //分页插件中0表示第一页
            				});

            				
            				
            			}else{
            				layer.msg(result.message , {time:1000, icon:5, shift:6});
            			}
            		}
            		
            	});            	
            	
            }
            
            
            
            function deleteDeployment(pdDeployId,pdName){
            	var loadingIndex = -1 ;
            	layer.confirm("您是否确定要删除【"+pdName+"】流程部署吗？",  {icon: 3, title:'提示'}, function(cindex){
    			    layer.close(cindex);    			    
    			    $.ajax({
    			    	type:"post",
    			    	url:"${APP_PATH}/process/doDelete",
    			    	data:{
    			    		pdDeployId:pdDeployId
    			    	},
                		beforeSend:function(){
                			loadingIndex = layer.msg('正在删除数据中...', {icon: 16});
                		},
                		success:function(result){
                			layer.close(loadingIndex);
                			if(result.success){
                				layer.msg("删除成功" , {time:1000, icon:6});
                				queryPage(0);
                			}else{
                				layer.msg("删除失败" , {time:1000, icon:5, shift:6});
                			}            			
                		}    			    	
    			    });    			    
    			}, function(cindex){
    			    layer.close(cindex);
    			});            	
            }
            
            
            
            
            
            
            
            
        </script>
  </body>
</html>
