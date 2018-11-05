<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
$db = new mysqli('localhost', 'root', 'toorpassword1', 'sherlock_data');
$response = array();
// echo $db->error;
$stmt = $db->prepare("SELECT iata_dep, iata_arr FROM route");
$stmt->execute();
$stmt->bind_result($from, $to);
while($stmt->fetch()){
  $single = array();
  $single[$from] = $to;
  $response[] = $single;
}
echo json_encode($response);
 ?>
