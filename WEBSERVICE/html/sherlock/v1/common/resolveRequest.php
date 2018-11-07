<?php
require '/var/www/html/sherlock/v1/connect.php';

$sleepTime = 61;

$statment = $db->prepare("
SELECT id, iata_departure, iata_arrival FROM request
");
$statment->execute();
$statment->store_result();
$resCount = $statment->num_rows;
if($resCount > 0){

  $file = dirname(__FILE__).'/log.txt';
  $data = "\nResolving started at ".date('d/m/Y H:i:s')." ...\n";
  file_put_contents($file, $data, FILE_APPEND);

  $resultsCounter = 0;

  $statment->bind_result($id, $iataDeparture, $iataArrival);
  while ($statment->fetch()) {
    $outBoundDepartureDateFromTo = date('Y-m-d', strtotime(date('Y-m-d').'+ 5 days'));
    $APIkey = 'lKCvBnr8fGqLkcdMkca4t4GMbGILfiZU';
    $url = 'https://apigateway.ryanair.com/pub/v1/farefinder/3/oneWayFares?'.
    'departureAirportIataCode='.$iataDeparture.'&'.
    'arrivalAirportIataCode='.$iataArrival.'&'.
    'outboundDepartureDateFrom='.$outBoundDepartureDateFromTo.'&'.
    'outboundDepartureDateTo='.$outBoundDepartureDateFromTo.'&'.
    'limit=1&'.
    'apikey='.$APIkey;

    $nfoRes = "-Resolving from ".$iataDeparture." to ".$iataArrival." ... ";

    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
    $output = curl_exec($ch);
    curl_close($ch);

    $fares = array();
    $fares = json_decode($output, true);
    if (array_key_exists('fares', $fares)) {
      foreach((array)$fares['fares'] as $fare){
        try{
          $fetchDate = date('Y-m-d');
          $departureDateTime = date('Y-m-d H:i', strtotime($fare['outbound']['departureDate']));
          $arrivalDateTime = date('Y-m-d H:i', strtotime($fare['outbound']['arrivalDate']));
          $value = $fare['outbound']['price']['value'];
          $currencyCode = $fare['outbound']['price']['currencyCode'];

          if(!strcmp("EUR", $currencyCode)==0){
            $dbUtil = new mysqli('localhost', 'root', 'toorpassword1', 'sherlock_util');
            $statmentUtil = $dbUtil->prepare("
            SELECT value FROM currency WHERE code=?
            ");
            $statmentUtil->bind_param('s',$currencyCode);
            $statmentUtil->execute();
            $statmentUtil->store_result();
            $resCountUtil = $statmentUtil->num_rows;
            if($resCountUtil > 0){
              $statmentUtil->bind_result($valueQ);
              $statmentUtil->fetch();
              $value = 1 / $valueQ * $value;
            }
          }
          $statmentTrack = $db->prepare("
          INSERT INTO track (id_request, fetch_date, departure_datetime, arrival_datetime, value)
          VALUES (?, ?, ?, ?, ?)
          ");
          $statmentTrack->bind_param('sssss', $id, $fetchDate, $departureDateTime, $arrivalDateTime, $value);
          $statmentTrack->execute();
          $nfoRes .= 'OK';
          break;
        }catch(Exception $e){
          $nfoRes .= 'ERROR';
        }
      }

    }else{
      $nfoRes .= $output;
    }

    $file = dirname(__FILE__).'/log.txt';
    $data = $nfoRes;
    file_put_contents($file, $data, FILE_APPEND);

    $resultsCounter++;
    sleep($sleepTime);
  }
}

?>
