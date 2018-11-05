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

  $APIkey = 'lKCvBnr8fGqLkcdMkca4t4GMbGILfiZU';

  $url = 'https://apigateway.ryanair.com/pub/v1/core/3/routes/'.$iataDeparture."?apikey=".$APIkey;
  // $url = 'https://projectsherlock.ddns.net/sherlock/v1/request/res.php';

  $ch = curl_init();
  curl_setopt($ch, CURLOPT_URL, $url);
  curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
  $output = curl_exec($ch);
  curl_close($ch);

  $routes = array();
  $routes = json_decode($output, true);

  $destinations = array();

  foreach($routes as $route){
    $destinations[] = $route['airportTo'];
  }
  if(in_array($iataArrival, $destinations)){ // the route exists
    require '../connect.php';

    $statment = $db->prepare("
    INSERT INTO request (id_user, iata_departure, iata_arrival) VALUES (?, ?, ?)
    ");
    $statment->bind_param('sss', $idUser, $iataDeparture, $iataArrival);
    $statment->execute();
    $response['success'] = true;
    $response['message'] = "A new request has been made!";
  }else{
    $response['success'] = false;
    $response['message'] = "Error: route not found!";
  }

  echo json_encode($response);

?>
