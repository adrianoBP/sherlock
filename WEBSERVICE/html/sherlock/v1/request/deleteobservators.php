<?php
require '../connect.php';

$params = (file_get_contents('php://input'));    // POST parameters
$params = json_decode($params, true);    // Decoding from JSON to array

$rid = $params['rid'];

$statment = $db->prepare("
DELETE FROM request_user WHERE id_request=?
");
$statment->bind_param('s', $rid);
$statment->execute();
if($statment->affected_rows > 0){
  echo json_encode("OK");
}else{
  echo json_encode("ERROR");
}

 ?>
