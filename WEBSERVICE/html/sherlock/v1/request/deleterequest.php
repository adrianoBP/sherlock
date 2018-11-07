<?php
require '../connect.php';

$params = (file_get_contents('php://input'));    // POST parameters
$params = json_decode($params, true);    // Decoding from JSON to array

$rid = $params['rid'];
$uid = $params['uid'];

$statment = $db->prepare("
DELETE FROM request_user WHERE id_request=? AND id_user=?
");
$statment->bind_param('ss', $rid, $uid);
$statment->execute();
if($statment->affected_rows > 0){
  echo json_encode("OK");
}else{
  echo json_encode("ERROR");
}

 ?>
