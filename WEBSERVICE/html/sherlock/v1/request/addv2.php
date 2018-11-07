<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
#error_reporting(0);

$params = (file_get_contents('php://input'));    // POST parameters
$params = json_decode($params, true);    // Decoding from JSON to array
//
$response = array();
$response['success'] = false;
$response['message'] = "Generic error!";

$idUser = $params['id_user'];
$iataDeparture = $params['iata_departure'];
$iataArrival = $params['iata_arrival'];

$destinations = array();

$db = new mysqli('localhost', 'root', 'toorpassword1', 'sherlock_flights');
$stmt = $db->prepare("
SELECT iata_arr FROM route WHERE iata_dep=?
");
$stmt->bind_param('s', $iataDeparture);
$stmt->execute();
$stmt->store_result();
$resCount = $stmt->num_rows;
if($resCount > 0){
  $stmt->bind_result($iata);

  while ($stmt->fetch()) {
    $destinations[] = $iata;
  }
}
if(in_array($iataArrival, $destinations)){ // the route exists
  require '../connect.php';
  $stmt = $db->prepare("
  SELECT id FROM request WHERE iata_departure=? AND iata_arrival=?
  ");
  $stmt->bind_param('ss', $iataDeparture, $iataArrival);
  $stmt->execute();
  $stmt->store_result();
  $resCount = $stmt->num_rows;
  $requestId = -1;
  if($resCount == 0){
    $statment = $db->prepare("
    INSERT INTO request (iata_departure, iata_arrival) VALUES (?, ?)
    ");
    $statment->bind_param('ss', $iataDeparture, $iataArrival);
    $statment->execute();
    // $response['success'] = true;
    // $response['message'] = "A new request has been made!";
    $requestId = $db->insert_id;
  }else if($resCount == 1){
    $stmt->bind_result($reqId);
    $stmt->fetch();
    $requestId = $reqId;
  }else{
    $response['success'] = false;
    $response['message'] = "Error: route not found!";
  }

  require '../connect.php';
  $statment = $db->prepare("
  INSERT INTO request_user (id_request, id_user) VALUES (?, ?)
  ");
  $statment->bind_param('ss', $requestId, $idUser);
  $statment->execute();

  $response['success'] = true;
  $response['message'] = "A new request has been made!";

}else{
  $response['success'] = false;
  $response['message'] = "Error: route not found!";
}

echo json_encode($response);

?>
