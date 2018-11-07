<?php
$db = new mysqli('localhost', 'root', 'toorpassword1', 'sherlock_flights');

$sleepTime = 61;

$stmt = $db->prepare("
SELECT iata FROM airport WHERE NOT EXISTS(SELECT * FROM route WHERE iata_dep=iata)
");
$stmt->execute();
$stmt->store_result();
$resCount = $stmt->num_rows;
if($resCount > 0){
  $stmt->bind_result($iata);

  while ($stmt->fetch()) {
    $APIkey = 'lKCvBnr8fGqLkcdMkca4t4GMbGILfiZU';

    $url = 'https://apigateway.ryanair.com/pub/v1/core/3/routes/'.$iata.'/iataCodes?apikey='.$APIkey;
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
    $output = curl_exec($ch);
    curl_close($ch);

    $destinations = array();
    $destinations = json_decode($output, true);

    if(!array_key_exists('fault', $destinations)){
      foreach($destinations as $destination){
        $db = new mysqli('localhost', 'root', 'toorpassword1', 'sherlock_flights');
        $statment = $db->prepare("
        SELECT * FROM route WHERE iata_dep=? AND iata_arr=?
        ");
        $statment->bind_param('ss', $iata, $destination);
        $statment->execute();
        $statment->store_result();
        $resCount = $statment->num_rows;
        if($resCount == 0){

          $db = new mysqli('localhost', 'root', 'toorpassword1', 'sherlock_flights');
          $statment = $db->prepare("
          INSERT INTO route(iata_dep, iata_arr) VALUES (?, ?)
          ");
          $statment->bind_param('ss', $iata, $destination);
          $statment->execute();
        }
      }
      echo $iata.": OK\n";
    }else{
      echo $iata.": ERROR\n";

    }
    sleep($sleepTime);
  }
}

?>
