<?php

$curl = curl_init();
// Set some options - we are passing in a useragent too here
curl_setopt_array($curl, array(
    CURLOPT_RETURNTRANSFER => 1,
    CURLOPT_URL => 'https://projectsherlock.ddns.net/sherlock/v1/util/airports.php',
    CURLOPT_USERAGENT => 'Codular Sample cURL Request'
));
// Send the request & save response to $resp
$resp = curl_exec($curl);
// Close request to clear up some resources
curl_close($curl);

$db = new mysqli('localhost', 'root', 'toorpassword1', 'sherlock_data');

$resp = json_decode($resp);
foreach($resp as $rr){
    $iata = "";
    $name = "";
    foreach ($rr as $key => $value) {
        if($key == "iataCode"){
            $iata = $value;
        }
        if($key == "name"){
            $name = $value;
        }
    }
    echo $iata.$name."\n";

    $stmt = $db->prepare("
    SELECT iata FROM airport WHERE iata='".$iata."'
    ");
    $stmt->execute();
    $stmt->store_result();
    $resCount = $stmt->num_rows;
    if($resCount < 1){
        $stmt = $db->prepare("
        INSERT INTO airport(iata, name) VALUES ('".$iata."', '".$name."')
        ");
        $stmt->execute();
    }

}

// foreach ($results as $key =>$val) {
//     $code=$key;
//     $value=$val;
//     //
//     // $statment = $db->prepare("
//     // UPDATE currency
//     // SET value=?
//     // WHERE code=?
//     // ");
//     // $statment->bind_param('ss', $value, $code);
//     // $statment->execute();
//     echo $key." - ".$value."<br>";
// }


// echo $resp;
?>
