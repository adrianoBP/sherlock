<?php
  $db = new mysqli('localhost', 'root', 'toorpassword1', 'sherlock_data');

  if($db->connect_errno){
    echo $db->connect_error;
    //die('A problem occurred.');
  }

?>
