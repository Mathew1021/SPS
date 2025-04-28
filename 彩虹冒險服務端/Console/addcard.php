<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>彩虹冒險加卡</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="bootstrap/css/bootstrap-theme.min.css" rel="stylesheet" media="screen"> 
<script type="text/javascript" src="jquery-1.11.3-jquery.min.js"></script>

<script type="text/javascript" src="validation.min.js"></script>
<link href="style.css" rel="stylesheet" type="text/css" media="screen">

<script type="text/javascript" src="scriptforcard.js"></script>

</head>

<body>
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="http://112.118.45.112:7777/" target="_blank" title="Coding Cage Programming Blog">Survival Project</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              功能表 <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="http://112.118.45.112:7777/" target="_blank">帳號註冊</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="http://112.118.45.112:7777/addcard.php" target="_blank">增加白卡</a></li>
              </ul>
            </li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
</nav>

    
<div class="signin-form">

	<div class="container">
     
        
       <form class="form-signin" method="post" id="register-form">
      
        <h2 class="form-signin-heading">彩虹冒險增加白卡</h2><hr />
        
        <div id="error">
        <!-- error will be showen here ! -->
        </div>
        
        <div class="form-group">
        <input type="text" class="form-control" placeholder="帳號" name="user_name" id="user_name" />
        </div>
     	<hr />
        
        <div class="form-group">
            <center><button type="submit" class="btn btn-default" name="btn-save" id="btn-submit">
    		<span class="glyphicon glyphicon-log-in"></span> &nbsp; 增加
			</button>
            </center> 
        </div>  
      
      </form>

    </div>
    
</div>
    
<script src="bootstrap/js/bootstrap.min.js"></script>

</body>
</html>