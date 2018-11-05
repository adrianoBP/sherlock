<?php
error_reporting(E_ALL);
#error_reporting(0);
require '../connect.php';
$params = (file_get_contents('php://input'));    // POST parameters
$params = json_decode($params, true);    // Decoding from JSON to array

$response = array();
$response['success'] = false;
$response['message'] = "";
$response['uid'] = "";

$email = $params['email'];
$username = $params['username'];
$password = $params['password'];

$statment = $db->prepare("
INSERT INTO user (email, username, password) VALUES (?, ?, ?)
");
$statment->bind_param('sss', $email, $username, $password);
$statment->execute();
if($statment->affected_rows > 0){
  $response['success'] = true;
  $response['uid'] = $statment->insert_id;

}else{
  $response['message'] = "Email or username already exists";
}


echo json_encode($response);
