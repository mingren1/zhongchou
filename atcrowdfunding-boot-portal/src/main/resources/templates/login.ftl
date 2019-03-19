<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
	<link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH}/css/login.css">
	<style>

	</style>
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
      </div>
    </nav>

    <div class="container">

      <form class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" id="loginacct" value="zhangsan" placeholder="请输入登录账号" autofocus>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<input type="password" class="form-control" id="userpswd" value="123" placeholder="请输入登录密码" style="margin-top:10px;">
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<select id="type" class="form-control" >
                <option value="member" selected>会员</option>
                <option value="user">管理</option>
            </select>
		  </div>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> 记住我
          </label>
          <br>
          <label>
            忘记密码
          </label>
          <label style="float:right">
            <a href="reg.html">我要注册</a>
          </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
      </form>
    </div>
    <script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${APP_PATH}/jquery/layer/layer.js"></script>
    
    <script>
	function dologin() {
        
    	var loginacct = $("#loginacct").val(); //获取表单域值，什么都不输入获取空串，不是null
    	if(loginacct == ""){    		
    		layer.msg("用户名称不能为空!", {time:1000, icon:5, shift:6});
    		return false ;
    	}
    	
    	var userpswd = $("#userpswd").val();
    	if(userpswd ==  ""){
    		layer.msg("用户密码不能为空!", {time:1000, icon:5, shift:6});
    		return false ;
    	}
    	
    	//异步登录请求
    	$.ajax({
    		async: true, //默认true,表示异步请求。
    		cache: false, //设置为 false 将不缓存此页面
    		type:"post",  // 默认"get"
    		url:"${APP_PATH}/doLogin",
    		data:{  
    			loginacct : loginacct,
    			userpswd : userpswd,
    			type : $("#type").val()
    		},
    		beforeSend : function(){
    			//表示提交异步请求前需要做事件处理，一般可以用于表单数据校验。    			
    			return true ;
    		},
    		success:function(result){
    			if(result.success){
    				window.location.href="${APP_PATH}/member"
    			}else{
    				layer.msg(result.message , {time:1000, icon:5, shift:6});
    			}
    		},
    		complete:function(){
    			//不管请求是否处理成功，都将执行的事件处理。相当于finally语句块。
    		}
    		
    	});
    	
    	
    }
    </script>
  </body>
</html>