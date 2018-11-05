<?php

require "init.php";  // Loading connection info
$fcm_token = $_POST["fcm_token"]; // Getting POST var which contains the token
$id = $_POST['id'];
$uid = $_POST['uid'];

$sql = "SELECT fcm_token FROM fcm_info WHERE id='".$id."'";  // Query to select the token by the id
$result = mysqli_query($con, $sql);
$row_cnt = mysqli_num_rows($result);
$rows = mysqli_fetch_all($result);

if($row_cnt == 0){
  $sql = "INSERT INTO fcm_info VALUES('".$id."','".$fcm_token."');";  // Insertig new token in tokens table if it isn't already in
  mysqli_query($con, $sql);  // Exec
  $sql = "INSERT INTO user_device(user_id, device_id) VALUES('".$uid."','".$id."')";  // Inserting correlation between token and user devices
  mysqli_query($con, $sql);  // Exec
  echo "INSERTED";
}else if($row_cnt > 0){
  $sql = "UPDATE fcm_info SET fcm_token='".$fcm_token."' WHERE id='".$id."'";  
  mysqli_query($con, $sql);  // Exec
  $sql = "UPDATE user_device SET user_id='".$uid."' WHERE device_id='".$id."'";
  mysqli_query($con, $sql);  // Exec
  echo "UPDATED";
}else{
  echo "ERROR";
}
mysqli_close($con);
 ?>
