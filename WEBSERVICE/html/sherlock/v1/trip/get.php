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

  function getWalks($tripId){
    require '../connect.php';
    $walks = array();
    $statment = $db->prepare("
    SELECT * FROM walk WHERE id_trip=?
    ");
    $statment->bind_param('s', $tripId);
    $statment->execute();
    $statment->store_result();
    $resCount = $statment->num_rows;
    if($resCount > 0){
      $statment->bind_result($id, $idTrip, $distance, $distanceValue, $duration, $durationValue, $instruction, $prox, $startPointX, $startPointY, $endPointX, $endPointY);
      while ($statment->fetch()) {
              $walk['id'] = $id;
              $walk['id_trip'] = $idTrip;
              $walk['distance'] = $distance;
              $walk['distance_value'] = $distanceValue;
              $walk['duration'] = $duration;
              $walk['duration_value'] = $durationValue;
              $walk['instruction'] = $instruction;
              $walk['prox'] = $prox;
              $walk['start_point_x'] = $startPointX;
              $walk['start_point_y'] = $startPointY;
              $walk['end_point_x'] = $endPointX;
              $walk['end_point_y'] = $endPointY;
              $walks[] = $walk;
          }
    }
    return $walks;
  }

  function getTransits($tripId){
    require '../connect.php';
    $transits = array();
    $statment = $db->prepare("
    SELECT * FROM transit WHERE id_trip=?
    ");
    $statment->bind_param('s', $tripId);
    $statment->execute();
    $statment->store_result();
    $resCount = $statment->num_rows;
    if($resCount > 0){
      $statment->bind_result($id, $idTrip, $distance, $distanceValue, $duration, $durationValue, $instruction, $departureStop, $departureTime, $arrivalStop, $arrivalTime, $nStops, $idVehicle, $prox, $startPointX, $startPointY, $endPointX, $endPointY);
      while ($statment->fetch()) {
              $transit['id'] = $id;
              $transit['id_trip'] = $idTrip;
              $transit['distance'] = $distance;
              $transit['distance_value'] = $distanceValue;
              $transit['duration'] = $duration;
              $transit['duration_value'] = $durationValue;
              $transit['instruction'] = $instruction;
              $transit['departure_stop'] = $departureStop;
              $transit['departure_time'] = $departureTime;
              $transit['arrival_stop'] = $arrivalStop;
              $transit['arrival_time'] = $arrivalTime;
              $transit['n_stops'] = $nStops;
              $transit['id_vehicle'] = $idVehicle;
              $transit['prox'] = $prox;
              $transit['start_point_x'] = $startPointX;
              $transit['start_point_y'] = $startPointY;
              $transit['end_point_x'] = $endPointX;
              $transit['end_point_y'] = $endPointY;
              $transits[] = $transit;
          }
    }
    return $transits;
  }

  function getVehicles($tripId){
    require '../connect.php';
    $vehicles = array();
    $statment = $db->prepare("
    SELECT * FROM vehicle WHERE id_trip=?
    ");
    $statment->bind_param('s', $tripId);
    $statment->execute();
    $statment->store_result();
    $resCount = $statment->num_rows;
    if($resCount > 0){
      $statment->bind_result($id, $idTrip, $headsign, $name, $shortName, $color, $type, $agencyName, $agencyUrl);
      while ($statment->fetch()) {
              $vehicle['id'] = $id;
              $vehicle['id_trip'] = $idTrip;
              $vehicle['headsign'] = $headsign;
              $vehicle['name'] = $name;
              $vehicle['short_name'] = $shortName;
              $vehicle['color'] = $color;
              $vehicle['type'] = $type;
              $vehicle['agency_name'] = $agencyName;
              $vehicle['agency_url'] = $agencyUrl;
              $vehicles[] = $vehicle;
          }
    }
    return $vehicles;
  }

  if(array_key_exists('uid', $params)){
    $uid=$params['uid'];

    $statment = $db->prepare("
    SELECT t.* FROM trip t, user_trip ut WHERE ut.id_user=? AND ut.id_trip=t.id
    ");
    $statment->bind_param('s', $uid);
    $statment->execute();
    $statment->store_result();
    $resCount = $statment->num_rows;
    if($resCount > 0){
      $trips = array();
      $statment->bind_result($id, $departureLocation, $arrivalLocation, $departureTime, $arrivalTime);
      try {
        while ($statment->fetch()) {
              $trip = array();
              $trip['id'] = $id;
              $trip['departure_location'] = $departureLocation;
              $trip['departure_time'] = $departureTime;
              $trip['arrival_location'] = $arrivalLocation;
              $trip['arrival_time'] = $arrivalTime;
              $trip['walks'] = getWalks($id);
              $trip['transits'] = getTransits($id);
              $trip['vehicles'] = getVehicles($id);
              $trips[] = $trip;
            }
        $response['success'] = true;
        $response['result'] = $trips;
      } catch (\Exception $e) {
        $response['message'] = $e;
      }

    }else{
      $response['message'] = "No data found.";
    }
  }else{
    $response['message'] = "Missing or wrong parameters";
  }

  echo json_encode($response);
