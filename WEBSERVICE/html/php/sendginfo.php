<?php
$uid = -1;
$uid = -1;

$params = (file_get_contents('php://input'));
$params = json_decode($params, true);
if(!empty($params['uid']) && !empty($params['rid'])){//TODO put [!]
  $uid = $params['uid'];
  $rid = $params['rid'];


  $response = array();
  $response['status'] = 500;
  $response['message'] = "Undefined";

  $dburl = "localhost";
  $dbuser = "root";
  $dbpass = "toorpassword1";
  $dbname = "sherlock_data";
  $db = new mysqli($dburl, $dbuser, $dbpass, $dbname);
  $driver = new mysqli_driver();
  $driver->report_mode = MYSQLI_REPORT_ALL;
  $stmt = $db->prepare(
    "SELECT r.id id_request, COUNT(t.id) tracks, r.iata_departure departure, a1.name dep_name, t.departure_datetime dep_time, r.iata_arrival arrival, a2.name arr_name, t.arrival_datetime arr_time
    FROM request r, track t, airport a1, airport a2
    WHERE t.id_request = r.id
    AND a1.iata = r.iata_departure
    AND a2.iata = r.iata_arrival
    AND r.id = ?"
  );
  $stmt->bind_param('s', $rid);
  $stmt->execute();
  $stmt->store_result();
  if($stmt->num_rows() == 1){
    $stmt->bind_result($reqId, $tracks, $departure, $depName, $depTime, $arrival, $arrName, $arrTime);
    $stmt->fetch();
    $response['id'] = $rid;
    $response['tracks'] = $tracks;
    $response['departure'] = array();
    $response['departure']['iata'] = $departure;
    $response['departure']['name'] = $depName;
    $response['departure']['datetime'] = $depTime;
    $response['arrival'] = array();
    $response['arrival']['iata'] = $arrival;
    $response['arrival']['name'] = $arrName;
    $response['arrival']['datetime'] = $arrTime;

    //[avg_weekday]
    $response['info'] = array();
    $response['info']['avg_weekday'];
    $stmt = $db->prepare(
      "SELECT weekday(departure_datetime) weekday, AVG(value) avarage FROM track WHERE id_request=? GROUP BY weekday"
    );
    $stmt->bind_param('s', $rid);
    $stmt->execute();
    $stmt->bind_result($weekday, $avarage);
    while($stmt->fetch()){
      $response['info']['avg_weekday']["day_".$weekday] = $avarage;
    }

    //[timezone/higher]
    $response['info']['timezone']['price'] = array();
    $stmt = $db->prepare("
    SELECT Avg(t.value) AS avg_price,
    ( CASE
    WHEN ( Hour(t.departure_datetime) BETWEEN 7 AND 12 ) THEN 'morning'
    WHEN ( Hour(t.departure_datetime) BETWEEN 13 AND 18 ) THEN
    'afternoon'
    WHEN ( Hour(t.departure_datetime) BETWEEN 19 AND 24 ) THEN 'evening'
    WHEN ( Hour(t.departure_datetime) BETWEEN 1 AND 6 ) THEN 'night'
    END )      AS dayzone
    FROM   sherlock_data.track t
    WHERE  t.id_request = ?
    GROUP  BY ( CASE
    WHEN ( Hour(t.departure_datetime) BETWEEN 7 AND 12 ) THEN
    'morning'
    WHEN ( Hour(t.departure_datetime) BETWEEN 13 AND 18 ) THEN
    'afternoon'
    WHEN ( Hour(t.departure_datetime) BETWEEN 19 AND 24 ) THEN
    'evening'
    WHEN ( Hour(t.departure_datetime) BETWEEN 1 AND 6 ) THEN 'night'
    END ) ");
    $stmt->bind_param('s', $rid);
    $stmt->execute();
    $stmt->bind_result($avgPrice, $dayzone);
    $tmpHighestZone = "";
    $tmpHighestAvgPrice = 0;
    while($stmt->fetch()){
      $response['info']['timezone']['price'][$dayzone] = $avgPrice;
    }


    // //[timezone/higher]
    // $response['info']['timezone']['lowest'] = array();
    // $stmt = $db->prepare("
    // SELECT Avg(t.value) AS avg_price,
    // ( CASE
    // WHEN ( Hour(t.departure_datetime) BETWEEN 7 AND 12 ) THEN 'morning'
    // WHEN ( Hour(t.departure_datetime) BETWEEN 13 AND 18 ) THEN
    // 'afternoon'
    // WHEN ( Hour(t.departure_datetime) BETWEEN 19 AND 24 ) THEN 'evening'
    // WHEN ( Hour(t.departure_datetime) BETWEEN 1 AND 6 ) THEN 'night'
    // END )      AS dayzone
    // FROM   sherlock_data.track t
    // WHERE  t.id_request = ?
    // GROUP  BY ( CASE
    // WHEN ( Hour(t.departure_datetime) BETWEEN 7 AND 12 ) THEN
    // 'morning'
    // WHEN ( Hour(t.departure_datetime) BETWEEN 13 AND 18 ) THEN
    // 'afternoon'
    // WHEN ( Hour(t.departure_datetime) BETWEEN 19 AND 24 ) THEN
    // 'evening'
    // WHEN ( Hour(t.departure_datetime) BETWEEN 1 AND 6 ) THEN 'night'
    // END ) ");
    // $stmt->bind_param('s', $rid);
    // $stmt->execute();
    // $stmt->bind_result($avgPrice, $dayzone);
    // $tmpLowestZone = "";
    // $tmpLowestAvgPrice = 99999999;
    // while($stmt->fetch()){
    //   if($avgPrice < $tmpLowestAvgPrice){
    //     $tmpLowestZone = $dayzone;
    //     $tmpLowestAvgPrice = $avgPrice;
    //   }
    // }
    // $response['info']['timezone']['lowest']['dayzone'] = $tmpLowestZone;
    // $response['info']['timezone']['lowest']['avg_price'] = $tmpLowestAvgPrice;

    //[timezone/counter]
    $stmt = $db->prepare("
    SELECT COUNT(*) val, (
      CASE
      WHEN hour(t.departure_datetime) BETWEEN 7 AND 12 THEN 'morning'
      WHEN hour(t.departure_datetime) BETWEEN 13 AND 18 THEN 'afternoon'
      WHEN hour(t.departure_datetime) BETWEEN 19 AND 24 THEN 'evening'
      WHEN hour(t.departure_datetime) BETWEEN 1 AND 6 THEN 'night'
      END) dayzone
      FROM track t
      WHERE t.id_request=5
      GROUP BY dayzone");
      $stmt->bind_param('s', $rid);
      $stmt->execute();
      $stmt->bind_result($val, $dayzone);
      while($stmt->fetch()){
        $response['info']['timezone']['counter'][$dayzone] = $val;
      }
      if(!array_key_exists('morning',$response['info']['timezone']['counter'])){
        $response['info']['timezone']['counter']['morning'] = 0;
      }
      if(!array_key_exists('afternoon',$response['info']['timezone']['counter'])){
        $response['info']['timezone']['counter']['afternoon'] = 0;
      }
      if(!array_key_exists('evening',$response['info']['timezone']['counter'])){
        $response['info']['timezone']['counter']['evening'] = 0;
      }
      if(!array_key_exists('night',$response['info']['timezone']['counter'])){
        $response['info']['timezone']['counter']['night'] = 0;
      }

      //[info/avg_price]
      $stmt = $db->prepare("SELECT AVG(value) FROM track WHERE id_request=?");
      $stmt->bind_param('s', $rid);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($avgPrice);
      $stmt->fetch();
      $response['info']['avg_price'] = $avgPrice;


      //[info/historic_high]
      $stmt = $db->prepare("SELECT t.value, t.id, t.departure_datetime, t.arrival_datetime, t.fetch_date FROM track t WHERE id_request=? AND value=(SELECT MAX(value) FROM track WHERE id_request=?) LIMIT 1");
      $stmt->bind_param('ss', $rid,$rid);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($price, $tid, $departureDatetime, $arrivalDatetime, $fetchDate);
      $stmt->fetch();
      $response['info']['historic_high']['value'] = $price;
      $response['info']['historic_high']['track']['id'] = $tid;
      $response['info']['historic_high']['track']['departure_datetime'] = $departureDatetime;
      $response['info']['historic_high']['track']['arrival_datetime'] = $arrivalDatetime;
      $response['info']['historic_high']['track']['fetch_date'] = $fetchDate;

      //[info/historic_low]
      $stmt = $db->prepare("SELECT t.value, t.id, t.departure_datetime, t.arrival_datetime, t.fetch_date FROM track t WHERE id_request=? AND value=(SELECT MIN(value) FROM track WHERE id_request=?) LIMIT 1");
      $stmt->bind_param('ss', $rid,$rid);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($price, $tid, $departureDatetime, $arrivalDatetime, $fetchDate);
      $stmt->fetch();
      $response['info']['historic_low']['value'] = $price;
      $response['info']['historic_low']['track']['id'] = $tid;
      $response['info']['historic_low']['track']['departure_datetime'] = $departureDatetime;
      $response['info']['historic_low']['track']['arrival_datetime'] = $arrivalDatetime;
      $response['info']['historic_low']['track']['fetch_date'] = $fetchDate;

      $response['status'] = 200;
      $response['message'] = "OK";
    }else{
      $response['status'] = 500;
      $response['message'] = "Request not found or multiple choice.";
    }
  }else{
    $response['status'] = 400;
    $response['message'] = "Missing parameters.";
  }


  echo json_encode($response);

  $url = "https://projectsherlock.ddns.net/admin/send_notification.php";

  $headers = array(
    'Content-Type:application/json'
  );
  $data = array(
    'message'=>json_encode($response),
    'title'=>$uid,
    'uid'=>$uid);

    $content = json_encode($data);
    $curl = curl_init($url);
    curl_setopt($curl, CURLOPT_HEADER, false);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($curl, CURLOPT_HTTPHEADER,
    array("Content-type: application/json"));
    curl_setopt($curl, CURLOPT_POST, true);
    curl_setopt($curl, CURLOPT_POSTFIELDS, $content);

    $response = curl_exec($curl);

    $status = curl_getinfo($curl, CURLINFO_HTTP_CODE);

    echo json_encode($response);


  ?>
