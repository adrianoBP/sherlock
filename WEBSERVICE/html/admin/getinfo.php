<?php
require "./init.php";

$response = array();

$stmt = $con->prepare("SELECT r.id req_id, a1.name departure, a2.name arrival, COUNT(ru.id_user) users
                        FROM request r
                        LEFT JOIN request_user ru ON ru.id_request=r.id
                        INNER JOIN airport a1 ON a1.iata=r.iata_departure
                        INNER JOIN airport a2 ON a2.iata=r.iata_arrival
                        GROUP BY r.id");
$stmt->execute();
$stmt->store_result();
$res = array();
if($stmt->num_rows > 0){
  $stmt->bind_result($id, $departure, $arrival, $count);
  $rows = array();
  while($stmt->fetch()){
    $res = array();
    $res['id'] = $id;
    $res['departure'] = $departure;
    $res['arrival'] = $arrival;
    $res['count'] = $count;

    $rows[] = $res;
  }
}
$response["vals"] = $rows;
echo json_encode($response);

 ?>
