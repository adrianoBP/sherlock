<?php
$params = (file_get_contents('php://input'));
$params = json_decode($params, true);

if(isset($params['rid']) && isset($params['mode'])){
  $rid = $params['rid'];
  $mode = $params['mode'];

  require "../connect.php";
  switch ($mode) {
    case 'deleterequest':
        $stmt = $db->prepare("SELECT ru.id_user, a1.name, a2.name
          FROM request_user ru, airport a1, airport a2, request r
          WHERE ru.id_request=r.id
          AND r.id=?
          AND a1.iata=r.iata_departure
          AND a2.iata=r.iata_arrival");
        $stmt->bind_param('s',$rid);
        $stmt->execute();
        $stmt->bind_result($uid, $dep, $arr);
        while($stmt->fetch()){
          $title = "Attention!";
          $message = "All the data of the observation from ".$dep." to ".$arr." will be shortly deleted!\nIf you wish you can save it from the website.";

          $url = ("https://projectsherlock.ddns.net/admin/send_notification.php");
          $headers = array(
            'Content-Type:application/json'
          );
          $data = array(
            'message'=>$message,
            'title'=>$title,
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
            echo $response;

        }
        $stmt->store_result();
      break;
      case 'deleteobservators':
          $stmt = $db->prepare("SELECT ru.id_user, a1.name, a2.name
            FROM request_user ru, airport a1, airport a2, request r
            WHERE ru.id_request=r.id
            AND r.id=?
            AND a1.iata=r.iata_departure
            AND a2.iata=r.iata_arrival");
          $stmt->bind_param('s',$rid);
          $stmt->execute();
          $stmt->bind_result($uid, $dep, $arr);
          while($stmt->fetch()){
            $title = "Attention!";
            $message = "Your obervation request from ".$dep." to ".$arr." will be shortly deleted!\nIf you wish you can add it again once deleted and view all the past data.";

            $url = ("https://projectsherlock.ddns.net/admin/send_notification.php");
            $headers = array(
              'Content-Type:application/json'
            );
            $data = array(
              'message'=>$message,
              'title'=>$title,
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
              echo $response;

          }
          $stmt->store_result();
        break;

    default:
      echo "ERROR!";
      break;
  }


}else{
  echo "ERROR!";
}

 ?>
