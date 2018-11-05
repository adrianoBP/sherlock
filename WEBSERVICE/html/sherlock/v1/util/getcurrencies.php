<?php
$db = new mysqli('localhost', 'root', 'toorpassword1', 'sherlock_util');

$statment = $db->prepare("
SELECT id, code, name, symbol, value, country FROM currency
");
$statment->execute();
$statment->store_result();
$resCount = $statment->num_rows;
$currencies = array();
if($resCount > 0){
  $statment->bind_result($id, $code, $name, $symbol, $value, $country);
  try {
    while ($statment->fetch()) {
          $currency = array();
          $currency['id'] = $id;
          $currency['code'] = $code;
          $currency['name'] = $name;
          $currency['symbol'] = $symbol;
          $currency['value'] = $value;
          $currency['country'] = $country;
          $currencies[] = $currency;
        }

  } catch (\Exception $e) {
  }
}

  echo json_encode($currencies);

// $servername = "localhost";
// $username = "root";
// $password = "toor";
// $dbname = "sherlock_util";
//
// // Create connection
// $conn = new mysqli($servername, $username, $password, $dbname);
// // Check connection
// if ($conn->connect_error) {
//     die("Connection failed: " . $conn->connect_error);
// }
//
// $sql = "SELECT * FROM currency";
// $result = $conn->query($sql);
//
// if ($result->num_rows > 0) {
//     // output data of each row
//     while($row = $result->fetch_assoc()) {
//         echo "id: " . $row["id"]. " - Name: " . $row["name"]. " " . $row["currency"]. "<br>";
//     }
// } else {
//     echo "0 results";
// }
// $conn->close();
?>
