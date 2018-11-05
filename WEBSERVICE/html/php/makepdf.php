<?php
// error_reporting(E_ALL);
// ini_set('display_errors', 1);
$rid = $_GET['rid'];
$dep = "";
$arr = "";
$dburl = "localhost";
$dbuser = "root";
$dbpass = "toorpassword1";
$dbname = "sherlock_data";
$db = new mysqli($dburl, $dbuser, $dbpass, $dbname);
$stmt = $db->prepare("SELECT t.id, a1.name, a2.name, t.fetch_date, t.departure_datetime, t.arrival_datetime, t.value
                      FROM track t, request r, airport a1, airport a2
                      WHERE r.id=?
                      AND t.id_request=r.id
                      AND a1.iata=r.iata_departure
                      AND a2.iata=r.iata_arrival");
$stmt->bind_param('s', $rid);

$stmt->execute();
$stmt->bind_result($id, $depName, $arrName, $fetch, $depTime, $arrTime, $val);
$info = array();
while($stmt->fetch()){
  $dep = $depName;
  $arr = $arrName;
  $details = array();
  $details['id'] = $id;
  $details['departure'] = $depTime;
  $details['arrival'] = $arrTime;
  $details['price'] = $val;
  $details['fetch'] = $fetch;
  $info[] = $details;
}

require "./fpdf.php";
class myPDF extends FPDF{

  function header(){
    $this->Image('../resources/icon_text_optimized.png', 10, 6, -250);
    $this->SetFont('Arial', 'B', 14);
    $this->Cell(276, 5, 'TRACK INFO', 0, 0, 'C');
    $this->Ln();
    $this->SetFont('Arial', '', 12);
    $this->Cell(276, 10, 'Tracked price information of the request', 0, 0, 'C');
    $this->Ln(20);
  }

  function footer(){
    $this->SetY(-15);
    $this->SetFont('Arial', '', 8);
    $this->Cell(0, 10, 'Sherlock data voluntarily exposed', 0, 0, 'C');
  }

  function headerTable($dep, $arr){
    $this->SetFont('Arial', '', 12);
    $this->Cell(275, 5, 'Departure: '.$dep.'       Destination: '.$arr, 0, 0, 'C');
    $this->SetFont('Arial', 'B', 12);
    $this->Ln();
    $this->Cell(30, 10, 'ID', 1, 0, 'C');
    $this->Cell(65, 10, 'DEPARTURE', 1, 0, 'C');
    $this->Cell(65, 10, 'ARRIVAL', 1, 0, 'C');
    $this->Cell(50, 10, 'PRICE (Euro)', 1, 0, 'C');
    $this->Cell(65, 10, 'Fetch date', 1, 0, 'C');
    $this->Ln();
  }

  function viewTable($info){
    for($i=0; $i<count($info); $i++){
      $this->SetFont('Arial', '', 12);
      $this->Cell(30, 10, $info[$i]['id'], 1, 0, 'C');
      $this->Cell(65, 10, $info[$i]['departure'], 1, 0, 'C');
      $this->Cell(65, 10, $info[$i]['arrival'], 1, 0, 'C');
      $this->Cell(50, 10, $info[$i]['price'], 1, 0, 'C');
      $this->Cell(65, 10, $info[$i]['fetch'], 1, 0, 'C');
      $this->Ln();
    }
  }
}

$pdf = new myPDF();
$pdf->AliasNbPages();
$pdf->AddPage('L', 'A4', 0);
$pdf->headerTable($dep, $arr);
// echo json_encode($info);
$pdf->viewTable($info);
$pdf->Output(str_replace(' ', '', $dep).'_'.str_replace(' ', '', $arr).'.pdf', 'D');
      // echo $mode;

 ?>
