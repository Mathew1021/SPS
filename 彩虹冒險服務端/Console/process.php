<?php
	
	require_once 'dbconfig.php';

	if($_POST)
	{
		$user_name = $_POST['user_name'];
		
		try
		{	
		
			$stmt = $db_con->prepare("SELECT * FROM users WHERE usr_name=:name");
			$stmt->execute(array(":name"=>$user_name));
			$count = $stmt->rowCount();
			
			if($count!=0){
				
			$stmt = $db_con->prepare("UPDATE users SET usr_wind =100000, usr_water = 100000, usr_earth=100000, usr_fire=100000 WHERE usr_name=:uname");
			$stmt->bindParam(":uname",$user_name);
					
				if($stmt->execute())
				{
					echo "修改成功";
				}
				else
				{
					echo "無法執行指令!";
				}
			
			}
			else{
				
				echo "帳號不存在!"; //  not available
			}
				
		}
		catch(PDOException $e){
			echo $e->getMessage();
		}
	}

?>