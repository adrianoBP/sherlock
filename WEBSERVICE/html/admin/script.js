//TODO SET FALSE!!!!
var logged = false;
//TODO SET FALSE!!!!
window.onload = function() {
  //TODO REMOVE COMMENT!!!!
  //TODO REMOVE COMMENT!!!!
  //TODO REMOVE COMMENT!!!!
  if(sessionStorage.getItem("logged")){
    logged = true;
  }else{
    window.open('../','_self',false)
  }
  sessionStorage.clear();
  //TODO REMOVE COMMENT!!!!
  //TODO REMOVE COMMENT!!!!
  //TODO REMOVE COMMENT!!!!

  loadTable();

}


var bSendNotification = document.getElementById('sendnotification');

var bUpdateCurrencies = document.getElementById('bUpdateCurrencies');
bUpdateCurrencies.addEventListener('click', function(){
  $.ajax({
    type: "POST",
    url: "https://projectsherlock.ddns.net/sherlock/v1/util/updatecurrencies.php",
    data: "",
    success: function(data) {
      showSnack("Currencies updated!");
    },
    error: function(xhr, status, error) {
      showSnack(status); //xhr.responseText
    }
  });
});

function sendNotification() {
  var tMessage = document.getElementById('message');
  var tTitle = document.getElementById('title');

  var datas = JSON.stringify({
    "title": tTitle.value,
    "message": tMessage.value
  });
  $.ajax({
    type: "POST",
    url: "https://projectsherlock.ddns.net/admin/send_notification.php",
    data: datas,
    success: function(data) {
      showSnack("Message sent!");
    },
    error: function(xhr, status, error) {
      showSnack(status); //xhr.responseText
    }
  });
}

function loadTable() {
  $("#tableinfobody").empty();
  $.ajax({
    type: "POST",
    url: "https://projectsherlock.ddns.net/admin/getinfo.php",
    data: "",
    success: function(data) {
      var dataOBJ = JSON.parse(data);
      var vals = dataOBJ.vals;
      for (var i = 0; i < vals.length; i++) {
        var tdId = "<td>" + vals[i].id + "</td>";
        var tdDeparture = "<td>" + vals[i].departure + "</td>";
        var tdArrival = "<td>" + vals[i].arrival + "</td>";
        var tdObservator = "<td>" + vals[i].count + "</td>";
        var tdDelReq = "<td class=\"delete\">" + "<a id=AR" + vals[i].id + " href=\"#del\" onClick=\"delReq(this.id)\">DELETE REQUEST</a></br><img id=IR" + vals[i].id + " src=\"../resources/bell_notify.png\"  title=\"Notify\"  onClick=\"notifyReq(this.id)\"/>" + "</td>";
        var tdDelObs = "<td class=\"delete\">" + "<a id=AO" + vals[i].id + " href=\"#del\" onClick=\"delObs(this.id)\">DELETE OBSERVATORS</a></br><img id=IO" + vals[i].id + " src=\"../resources/bell_notify.png\"  title=\"Notify\"  onClick=\"notifyObs(this.id)\"/>" + "</td>";
        $("#tableinfo").find('tbody')
          .append("<tr>"+tdId+tdDeparture+tdArrival+tdObservator+tdDelReq+tdDelObs+"</tr>");
      }
    },
    error: function(xhr, status, error) {
      showSnack(status); //xhr.responseText
    }
  });
}

function delReq(id) {
  defId = id.substring(2);
  var datas = JSON.stringify({
    "rid": defId
  });
  $.ajax({
    type: "POST",
    url: "https://projectsherlock.ddns.net/sherlock/v1/request/deletewholerequest.php",
    data: datas,
    success: function(data) {
      loadTable();
    },
    error: function(xhr, status, error) {
      showSnack(status); //xhr.responseText
    }
  });
}

function delObs(id) {
  defId = id.substring(2);
  var datas = JSON.stringify({
    "rid": defId
  });
  $.ajax({
    type: "POST",
    url: "https://projectsherlock.ddns.net/sherlock/v1/request/deleteobservators.php",
    data: datas,
    success: function(data) {
      loadTable();
    },
    error: function(xhr, status, error) {
      showSnack(status); //xhr.responseText
    }
  });
}

function notifyReq(id){
  defId = id.substring(2);
  var datas = JSON.stringify({
    "rid": defId,
    "mode":"deleterequest"
  });
  $.ajax({
    type: "POST",
    url: "https://projectsherlock.ddns.net/sherlock/v1/util/moddednotification.php",
    data: datas,
    success: function(data) {
      showSnack("Message sent!");
    },
    error: function(xhr, status, error) {
      showSnack(status); //xhr.responseText
    }
  });
}

function notifyObs(id){
  defId = id.substring(2);
  var datas = JSON.stringify({
    "rid": defId,
    "mode":"deleteobservators"
  });
  $.ajax({
    type: "POST",
    url: "https://projectsherlock.ddns.net/sherlock/v1/util/moddednotification.php",
    data: datas,
    success: function(data) {
      showSnack("Message sent!");
    },
    error: function(xhr, status, error) {
      showSnack(status); //xhr.responseText
    }
  });

}
bSendNotification.addEventListener("click", function() {
  if (logged) {
    sendNotification();
  } else {
    showSnack("You are not logged!");
  }
});

var bExit = document.getElementById("bExit");
bExit.addEventListener('click', function(){
  sessionStorage.clear();
  window.open('../','_self',false)
});

function showSnack(message) {
  var x = document.getElementById("snackbar");
  x.innerHTML = message;
  x.className = "show";
  setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
/*/////////////////////////////////////////*/
// var buttons = document.getElementsByClassName('material-btn');
//
// Array.prototype.forEach.call(buttons,function(b){
//   b.addEventListener('click',createRipple);
// });
//
// function createRipple(e){
//   console.log(e);
//   var circle = document.createElement('div');
//
//   this.appendChild(circle);
//
//   var d = Math.max(this.clientWidth, this.clientHeight);
//
//   circle.style.width = circle.style.height = d + 'px';
//
//   circle.style.left = e.clientX - this.offsetLeft - d / 2 + 'px';
//
//   circle.style.top = e.clientY - this.offsetTop -  d / 2 + 'px';
//
//   circle.classList.add('ripple');
// }
