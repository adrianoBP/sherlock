<?php

require "init.php";
$fcm_token = $_POST["fcm_token"];
$id = $_POST['id'];
$uid = $_POST['uid'];

$sql = "SELECT fcm_token FROM fcm_info WHERE id='".$id."'";
$result = mysqli_query($con, $sql);
$row_cnt = mysqli_num_rows($result);
$rows = mysqli_fetch_all($result);

if($row_cnt == 0){
  $sql = "INSERT INTO fcm_info VALUES('".$id."','".$fcm_token."');";
  mysqli_query($con, $sql);
  $sql = "INSERT INTO user_device(user_id, device_id) VALUES('".$uid."','".$id."')";
  mysqli_query($con, $sql);
  echo "INSERTED";
}else if($row_cnt > 0){
  $sql = "UPDATE fcm_info SET fcm_token='".$fcm_token."' WHERE id='".$id."'";
  mysqli_query($con, $sql);
  $sql = "UPDATE user_device SET user_id='".$uid."' WHERE device_id='".$id."'";
  mysqli_query($con, $sql);
  echo "UPDATED";
}else{
  echo "ERROR";
}
mysqli_close($con);
 ?>
