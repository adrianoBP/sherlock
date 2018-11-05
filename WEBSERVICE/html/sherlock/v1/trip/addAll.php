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

  function addTransit($id, $idTrip, $distance, $distanceValue, $duration, $durationValue, $instruction, $departureStop, $departureTime, $arrivalStop, $arrivalTime, $nStops, $idVehicle, $prox, $startPointX, $startPointY, $endPointX, $endPointY){
    require '../connect.php';

    $statment = $db->prepare("
    INSERT INTO transit(id, id_trip, distance, distance_value, duration, duration_value, instruction, departure_stop, departure_time, arrival_stop, arrival_time, n_stops, id_vehicle, prox, start_point_x, start_point_y, end_point_x, end_point_y)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    ");
    $statment->bind_param('sissssssssssssssss', $id, $idTrip, $distance, $distanceValue, $duration, $durationValue, $instruction, $departureStop, $departureTime, $arrivalStop, $arrivalTime, $nStops,
      $idVehicle, $prox, $startPointX, $startPointY, $endPointX, $endPointY);

    $statment->execute();
    // ATTENTION!! commented 'cause during "update" there were already the same data in the DB, however it could drive into other erros and not be notified
    // if($statment->affected_rows > 0){
    //   return true;
    // }else{
    //   return false;
    // }
    //

    return true;
  }

  function addVehicle($id, $idTrip, $headsign, $name, $shortName, $color, $type, $agencyName, $agencyUrl){
    require '../connect.php';

    $statment = $db->prepare("
    INSERT INTO vehicle(id, id_trip, headsign, name, short_name, color, type, agency_name, agency_url)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
    ");
    $statment->bind_param('sisssssss', $id, $idTrip, $headsign, $name, $shortName, $color, $type, $agencyName, $agencyUrl);

    $statment->execute();
    // ATTENTION!! commented 'cause during "update" there were already the same data in the DB, however it could drive into other erros and not be notified
    // if($statment->affected_rows > 0){
    //   return true;
    // }else{
    //   return false;
    // }
    //

    return true;
  }

  function addWalk($id, $idTrip, $distance, $distanceValue, $duration, $durationValue, $instruction, $prox, $startPointX, $startPointY, $endPointX, $endPointY){
    require '../connect.php';

    $statment = $db->prepare("
    INSERT INTO walk (id, id_trip, distance, distance_value, duration, duration_value, instruction, prox, start_point_x, start_point_y, end_point_x, end_point_y)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    ");
    $statment->bind_param('sissssssssss', $id, $idTrip, $distance, $distanceValue, $duration, $durationValue, $instruction, $prox, $startPointX, $startPointY, $endPointX, $endPointY);

    $statment->execute();
    // ATTENTION!! commented 'cause during "update" there were already the same data in the DB, however it could drive into other erros and not be notified
    // if($statment->affected_rows > 0){
    //   return true;
    // }else{
    //   return false;
    // }
    //

    return true;
  }

  function addUserTrip($uid, $id){
    require '../connect.php';

    $statment = $db->prepare("
      INSERT INTO user_trip (id_user, id_trip)
      VALUES (?, ?)
    ");
    $statment->bind_param('ss', $uid, $id);
    $statment->execute();

    // ATTENTION!! commented 'cause during "update" there were already the same data in the DB, however it could drive into other erros and not be notified
    // if($statment->affected_rows > 0){
    //   return true;
    // }else{
    //   return false;
    // }
    //

    return true;
  }

  function addTrip($uid, $id, $departureLocation, $departureTime, $arrivalLocation, $arrivalTime, $walks, $transits, $vehicles){
    require '../connect.php';

    $statment = $db->prepare("
      INSERT INTO trip(id, departure_location, departure_time, arrival_location, arrival_time)
      VALUES (?, ?, ?, ?, ?)
    ");
    $statment->bind_param('sssss', $id, $departureLocation, $departureTime, $arrivalLocation, $arrivalTime);
    $statment->execute();
    echo $id."\n";
    if($statment->affected_rows > 0){
      if(addUserTrip($uid, $id)){
        $response['success'] = true;
        $response['message'] = "";//.$statment->affected_rows;

        foreach($walks as $walk){
          $walkId = $walk['id'];
          $walkDistance = $walk['distance'];
          $walksDistanceValue = $walk['distance_value'];
          $walkDuration = $walk['duration'];
          $walkDurationValue = $walk['duration_value'];
          $walkInstruction = $walk['instruction'];
          $walkProx = $walk['prox'];
          $startPointX = $walk['start_point_x'];
          $startPointY = $walk['start_point_y'];
          $endPointX = $walk['end_point_x'];
          $endPointY = $walk['end_point_y'];
          try {
            if(!addWalk($walkId, $id, $walkDistance, $walksDistanceValue, $walkDuration, $walkDurationValue, $walkInstruction, $walkProx, $startPointX, $startPointY, $endPointX, $endPointY)){
              $response['success'] = false;
              $response['message'] = "Error during the adding process.B";
            }
          } catch (Exception $e) {
            $response['success'] = false;
            $response['message'] = "Adding walk error: ".$e;
          }
        }

        foreach($vehicles as $vehicle){
          $vehicleId = $vehicle['id'];
          $vehicleHeadsign = $vehicle['headsign'];
          $vehicleName = $vehicle['name'];
          $vehicleShortName = $vehicle['short_name'];
          $vehicleColor = $vehicle['color'];
          $vehicleType = $vehicle['type'];
          $vehicleAgencyName = $vehicle['agency_name'];
          $vehicleAgencyUrl = $vehicle['agency_url'];

          try {
            if(!addVehicle($vehicleId, $id, $vehicleHeadsign, $vehicleName, $vehicleShortName, $vehicleColor, $vehicleType, $vehicleAgencyName, $vehicleAgencyUrl)){
              $response['success'] = false;
              $response['message'] = "Error during the adding process.C";
            }
          } catch (Exception $e) {
            $response['success'] = false;
            $response['message'] = "Adding walk error: ".$e;
          }

        }

        oreach($transits as $transit){
          $transitId = $transit['id'];
          $transitDistance = $transit['distance'];
          $transitDistanceValue = $transit['distance_value'];
          $transitDuration = $transit['duration'];
          $transitDurationValue = $transit['duration_value'];
          $transitInstruction = $transit['instruction'];
          $transitDepartureStop = $transit['departure_stop'];
          $transitDepartureTime = $transit['departure_time'];
          $transitArrivalStop = $transit['arrival_stop'];
          $transitArrivalTime = $transit['arrival_time'];
          $transitNstops = $transit['n_stops'];
          $transitIdVehicle = $transit['id_vehicle'];
          $transitProx = $transit['prox'];
          $startPointX = $transit['start_point_x'];
          $startPointY = $transit['start_point_y'];
          $endPointX = $transit['end_point_x'];
          $endPointY = $transit['end_point_y'];
          try {
            if(!addTransit($transitId, $id, $transitDistance, $transitDistanceValue, $transitDuration, $transitDurationValue, $transitInstruction, $transitDepartureStop,
              $transitDepartureTime, $transitArrivalStop, $transitArrivalTime, $transitNstops, $transitIdVehicle, $transitProx, $startPointX, $startPointY, $endPointX, $endPointY)){
              $response['success'] = false;
              $response['message'] = "Error during the adding process.D";
            }
          } catch (Exception $e) {
            $response['success'] = false;
            $response['message'] = "Adding transit error: ";
          }
        }
      }else {
        $response['success'] = false;
        $response['message'] = "Error during the adding process.A";
      }
    }else{
      $response['success'] = true;
      $response['message'] = "0 affected rows.";
    }
    return $response;
  }

  if(array_key_exists('uid', $params)){
    require '../connect.php';


    $uid = $params['uid'];
    if(array_key_exists('trips', $params)){
    $trips = $params['trips'];
    // echo $trips."aaaa";
    $statment = $db->prepare("
    DELETE t.* FROM trip t, user_trip ut WHERE ut.id_user=? AND t.id=ut.id_trip
    ");
    $statment->bind_param('i', $uid);
    $statment->execute();

    if(!empty($trips)){
      foreach ($trips as $trip) {
        $tripid = $trip['id'];
        $tripDepartureLocation = $trip['departure_location'];
        $tripDepartureTime = $trip['departure_time'];
        $tripArrivalLocation = $trip['arrival_location'];
        $tripArrivalTime = $trip['arrival_time'];
        $walks = $trip['walks'];
        $transits = $trip['transits'];
        $vehicles = $trip['vehicles'];
        try{
          $response = addTrip($uid, $tripid, $tripDepartureLocation, $tripDepartureTime, $tripArrivalLocation, $tripArrivalTime, $walks, $transits, $vehicles);  // Add trip to the db
        }catch (Exception $e){
          $response['success'] = false;
          $response['message'] = "Adding trip error: ";
        }

      }
    }else{
      $response['success'] = true;
      $response['message'] = "0 trips were provided. All data has been deleted.";
    }
  }else{
    $response['success'] = true;
    $response['message'] = "0 trips were provided. All data has been deleted.";
  }

    // echo json_encode($trips[0]['walks'][0]['id']); //USE COUNT to count elems
  }else{
    $response['message'] = "Missing or wrong parameters";
  }

  echo json_encode($response);
