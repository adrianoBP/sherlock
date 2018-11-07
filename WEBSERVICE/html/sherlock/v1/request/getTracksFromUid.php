<?php
error_reporting(E_ALL);
#error_reporting(0);
require '../connect.php';
$params = (file_get_contents('php://input'));    // POST parameters
$params = json_decode($params, true);    // Decoding from JSON to array

$response = array();
$response['success'] = false;
$response['message'] = "";
$response['result'] = array();

$uid = $params['uid'];

$requests = array();

require '../connect.php';

#SELECT r.id, a.name, b.name FROM request r, airport a, airport b WHERE id_user=5 AND a.iata=r.iata_departure AND b.iata=r.iata_arrival
#SELECT t.id, t.departure_datetime, t.value FROM track t WHERE id_request=5 ORDER BY t.departure_datetime ASC

function getTracking($reqId){
  require '../connect.php';
  $tracks = array();
  $statment = $db->prepare("
  SELECT t.id, t.departure_datetime, t.value FROM track t WHERE id_request=? ORDER BY t.departure_datetime ASC
  ");
  $statment->bind_param('s', $reqId);
  $statment->execute();
  $statment->store_result();
  $resCount = $statment->num_rows;
  if($resCount > 0){
    $statment->bind_result($id, $depTime, $value);
    while ($statment->fetch()) {
      $track['id'] = $id;
      $track['departureTime'] = $depTime;
      $track['value'] = $value;
      $tracks[] = $track;
    }
  }
  return $tracks;
}

$statment = $db->prepare("
SELECT r.id, a.name, b.name FROM request_user ru, request r, airport a, airport b
WHERE a.iata=r.iata_departure
AND b.iata=r.iata_arrival
AND r.id=ru.id_request
AND ru.id_user=?
");
$statment->bind_param('s', $uid);
$statment->execute();
$statment->store_result();
$resCount = $statment->num_rows;
if($resCount > 0){
  $statment->bind_result($reqId, $depName, $depArrival);
  while ($statment->fetch()) {
    $request['id'] = $reqId;
    $request['departureName'] = $depName;
    $request['arrivalName'] = $depArrival;
    $request['tracking'] = getTracking($reqId);
    $requests[] = $request;
  }

  $response['success'] = true;
  $response['result'] = $requests;
}else{
  $response['success'] = false;
  $response['message'] = "No data found!";
}

echo json_encode($response);
