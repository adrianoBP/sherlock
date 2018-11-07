<?php
$db = new mysqli('localhost', 'root', 'toorpassword1', 'sherlock_flights');

$statment = $db->prepare("
SELECT iata, name FROM airport
");
$statment->execute();
$statment->store_result();
$resCount = $statment->num_rows;
$airports = array();
if($resCount > 0){
  $statment->bind_result($iata, $name);
  try {
    while ($statment->fetch()) {
          $airport = array();
          $airport['iata'] = $iata;
          $airport['name'] = $name;
          $airports[] = $airport;
        }
  } catch (\Exception $e) {
  }
}

echo json_encode($airports);
?>
