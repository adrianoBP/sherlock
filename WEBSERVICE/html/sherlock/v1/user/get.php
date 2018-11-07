<?php
error_reporting(E_ALL);
#error_reporting(0);
require '../connect.php';
$params = (file_get_contents('php://input'));    // POST parameters
$params = json_decode($params, true);    // Decoding from JSON to array

//TODO CARE: delete trip when delete user!!!

$response = array();
$response['success'] = false;
$response['message'] = "";
$response['uid'] = "";
$response['admin'] = false;

$username = $params['username'];
$password = $params['password'];

$statment = $db->prepare("
SELECT id FROM user WHERE (BINARY email=? OR BINARY username=?) AND BINARY password=?
");
$statment->bind_param('sss', $username, $username, $password);
$statment->execute();
$statment->store_result();
$resCount = $statment->num_rows;
if($resCount == 1){
  $response['success'] = true;
  $statment->bind_result($uid);
  while ($statment->fetch()) {
          $response['uid']=$uid;
      }
  if($uid == 1){ // admin's ID
    $response['admin'] = true;
  }
}else if($resCount == 0){
  $response['success'] = false;
  $response['message'] = "404 - User not found!";
}else{
  $response['success'] = false;
  $response['message'] = "Error inside the database! Please report.";
}

echo json_encode($response);
// if($result = $db->query("SELECT t.* FROM trip t, user_trip ut WHERE ut.id_user=".$uid." AND ut.id_trip=t.id")){
//   if($count = $result->num_rows){
//
//     $rows = $result->fetch_all(MYSQLI_ASSOC);
//     $response['success'] = true;
//     $response['result'] = $rows;
//     $response['message'] = "";
//
//   }else{
//     $response['message'] = "No data found";
//   }
// }else{
//   die($db->error);
// }
