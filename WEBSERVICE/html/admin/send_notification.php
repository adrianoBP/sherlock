<?php

require "init.php";
$params = (file_get_contents('php://input'));
$params = json_decode($params, true);
$message = $params['message'];
$title = $params['title'];
$uid = $params['uid'];
$path_to_fcm = 'https://fcm.googleapis.com/fcm/send';
$server_key = "AIzaSyDW1n_ffVUCVfTimhbtZYaJzQ-pXDuN-XA";

$headers = array(
  'Authorization:key='.$server_key,
  'Content-Type:application/json'
);


if((!isset($uid) || trim($uid)==='')){
  $sql = "SELECT fcm_token FROM fcm_info";
  $result = mysqli_query($con, $sql);
  $rows = mysqli_fetch_all($result);

  foreach($rows as $row){
    $keys[] = $row[0];
  }

  $tmp = "";

  $inf = "";

  foreach($keys as $key){

    $type = "global";
    if($title == "Attention!"){
      $type = "attention";
    }else if($title == "lock"){
      $type = "lock";
    }else if($title == "unlock"){
      $type = "unlock";
    }else if(strpos($title,"version_") !== false){
      $type = "newversion";
    }


    $fields = array(
      'to'=>$key,
      'data'=>array(
        "type"=>$type,
        "title"=>$title,
        "message"=>$message
      )
    );

    $payload = json_encode($fields);
    $ch = curl_init($path_to_fcm);
    $options = array(
      CURLOPT_RETURNTRANSFER => true,         // return web page
      CURLOPT_HEADER         => false,        // don't return headers
      CURLOPT_FOLLOWLOCATION => false,         // follow redirects
      // CURLOPT_ENCODING       => "utf-8",           // handle all encodings
      CURLOPT_AUTOREFERER    => true,         // set referer on redirect
      CURLOPT_CONNECTTIMEOUT => 20,          // timeout on connect
      CURLOPT_TIMEOUT        => 20,          // timeout on response
      CURLOPT_POST            => 1,            // i am sending post data
      CURLOPT_POSTFIELDS     => $payload,    // this are my post vars
      CURLOPT_SSL_VERIFYHOST => 0,            // don't verify ssl
      CURLOPT_SSL_VERIFYPEER => false,        //
      CURLOPT_VERBOSE        => 1,
      CURLOPT_HTTPHEADER     => $headers
    );

    curl_setopt_array($ch,$options);
    $data = curl_exec($ch);
    $curl_errno = curl_errno($ch);
    $curl_error = curl_error($ch);
    curl_close($ch);
  }
  $tmp = "ALL";
  $inf = $data;
}else{

  $type = "single";
  if($title == "Attention!"){
    $type = "attention";
  }

  $sql = "SELECT f.fcm_token FROM fcm_info f, user_device u WHERE f.id=u.device_id AND u.user_id=".$uid."";
  $result = mysqli_query($con, $sql);
  $rows = mysqli_fetch_all($result);

  $keys = array();

  foreach($rows as $row){
    $keys[] = $row[0];
  }

  $tmp = $keys;

  foreach($keys as $key){
    $fields = array(
      'to'=>$key,
      'data'=>array(
        "type"=>$type,
        "title"=>$title,
        "message"=>$message
      )
    );

    $payload = json_encode($fields);
    $ch = curl_init($path_to_fcm);
    $options = array(
      CURLOPT_RETURNTRANSFER => true,         // Return web page
      CURLOPT_HEADER         => false,        // Disable headers return
      CURLOPT_FOLLOWLOCATION => false,        // Follow redirects option
      // CURLOPT_ENCODING       => "utf-8",     //handle all encodings
      CURLOPT_AUTOREFERER    => true,         // Set referer on redirect
      CURLOPT_CONNECTTIMEOUT => 20,           // Timeout time on connect
      CURLOPT_TIMEOUT        => 20,           // Timeout time on response
      CURLOPT_POST            => 1,           // Sending POST data flag
      CURLOPT_POSTFIELDS     => $payload,     // Payload -> variables
      CURLOPT_SSL_VERIFYHOST => 0,            // Disable ssl verification
      CURLOPT_SSL_VERIFYPEER => false,
      CURLOPT_VERBOSE        => 1,
      CURLOPT_HTTPHEADER     => $headers
    );

    curl_setopt_array($ch,$options);
    $data = curl_exec($ch);
    $curl_errno = curl_errno($ch);
    $curl_error = curl_error($ch);
    curl_close($ch);

    $inf = $data;
    echo $title;
  }

}

mysqli_close($con);
?>
