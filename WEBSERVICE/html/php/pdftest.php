<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
require "fpdf.php";

class myPDF extends FPDF{

  function header(){
    $this->Image('../resources/icon_text_optimized.png', 10, 6, -250);
    $this->SetFont('Arial', 'B', 14);
    $this->Cell(276, 5, 'EMPLOYEE DOCUMENTS', 0, 0, 'C');
    $this->Ln();
    $this->SetFont('Times', '', 12);
    $this->Cell(276, 10, 'Street Address of Employee Office', 0, 0, 'C');
    $this->Ln(20);
  }

  function footer(){
    $this->SetY(-15);
    $this->SetFont('Arial', '', 8);
    $this->Cell(0, 10, 'Page '.$this->PageNo().'{nb}', 0, 0, 'C');
  }

}
$pdf = new myPDF();
$pdf->AliasNbPages();
$pdf->AddPage('L', 'A4', 0);
$pdf->Output('', 'D');

 ?>
