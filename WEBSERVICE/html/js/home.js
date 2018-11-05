const UID = localStorage.getItem("uid");


//Document elements
var wrapper = document.getElementById('wrapper');
var bLogout = document.getElementById('bLogout');
var contextMenu = document.getElementById('contextMenu');
//
//Listeners
bLogout.addEventListener("click", function() {
  localStorage.clear();
  window.open('../index.html', '_self', false)
});

window.onclick = hideContextMenu;


//
//Insert name in header
var hUser = document.getElementById('hUser');
hUser.innerHTML = "Welcome " + localStorage.getItem("user");
//

function getEventTarget(e) {
  e = e || window.event;
  return e.target || e.srcElement;
}

function hideContextMenu() {
  contextMenu.style.display = 'none';
}

var datas = JSON.stringify({
  "uid": UID
});

function random_rgba() {
  var o = Math.round,
    r = Math.random,
    s = 255;
  return 'rgba(' + o(r() * s) + ',' + o(r() * s) + ',' + o(r() * s) + ',' + 1 + ')';
}

function formattedDate(d) {
  let month = String(d.getMonth() + 1);
  let day = String(d.getDate());
  const year = String(d.getFullYear());

  if (month.length < 2) month = '0' + month;
  if (day.length < 2) day = '0' + day;

  return `${day}.${month}.${year}`;
}

var labelsVals = [];
var dataVals = [];

var allLabelsVals = [];
var allDataVals = [];

function addReqData(value, index, ar) {
  var res = value['departureTime'].split('-');
  var valDate = value['departureTime'].split(' ')[0];
  var day = res[2].split(' ')[0];
  var days = ["03", "06", "09", "12", "15", "18", "21", "24", "27", "30"];

  var today = new Date();
  var dd = today.getDate() + 5;
  var mm = today.getMonth() + 1; //January is 0!
  var yyyy = today.getFullYear();

  if (dd < 10) {
    dd = '0' + dd;
  }
  if (mm < 10) {
    mm = '0' + mm
  }
  today = yyyy + '-' + mm + '-' + dd;

  if (days.includes(day) || today == valDate) {
    labelsVals.push(formattedDate(new Date(value['departureTime'])));
    dataVals.push(value['value']);
  }



  allLabelsVals.push(formattedDate(new Date(value['departureTime'])));
  allDataVals.push(value['value']);
}

function buildGraph(departure, arrival, labels, data, rid) {

  var myRgba = random_rgba();
  var canvas = document.createElement('canvas');
  canvas.width = 5;
  canvas.height = 2;

  let lineChart = new Chart(canvas, {
    type: 'line',
    data: {
      labels: labels,
      datasets: [{
        label: departure + " - " + arrival + " (price in EUR)",
        fill: false,
        lineTension: 0.1,
        backgroundColor: myRgba,
        borderColor: myRgba,
        borderCapStyle: 'butt',
        borderDash: [],
        boderDashOffset: 0.0,
        borderJoinStyle: 'miter',
        pointBorderColor: myRgba,
        pointBackgroundColor: "#fff",
        pointBorderWidth: 2,
        pointHoverRadius: 5,
        pointHoverBackgroundColor: myRgba,
        pointHoverBorderColor: "#fff",
        pointHoverBorderWidth: 2,
        pointRaduis: 2,
        pointHitRadius: 10,
        data: data
      }]
    },
    options: {
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: true
          }
        }],
        xAxes: [{
          ticks: {
            autoSkip: false,
            maxRotation: 60,
            minRotation: 60
          }
        }]
      }
    }
  });
  var div = document.createElement('div');
  div.className = "w3-panel w3-card graphcard";
  var x = $(window).width() / 2;
  div.style.width = (x - 50) + "px";
  div.style.margin = "25px auto 25px auto";
  div.appendChild(canvas);
  wrapper.appendChild(div);

  div.addEventListener("click", function() {

  });
  div.addEventListener("contextmenu", function(ev) {
    ev.preventDefault();
    contextMenu.style.display = 'block';
    contextMenu.style.left = ev.pageX + 'px';
    contextMenu.style.top = ev.pageY + 'px';
    var ul = document.getElementById('menuitems');
    ul.onclick = function(event) {
      var target = getEventTarget(event);

      var selection = target.innerHTML;

      if (selection == "Delete") {
        deleteRequest(rid, UID) //TODO remove comment
      } else if (selection == "Refresh page") {
        location.reload();
      } else if (selection == "Send info to devices") {
        var gdatas = JSON.stringify({
          "uid": UID,
          "rid": rid
        });
        $.ajax({
          type: "POST",
          url: "https://projectsherlock.ddns.net/php/sendginfo.php",
          data: gdatas,
          success: function(data) {
            console.log(data);
          },
          error: function(xhr, status, error) {
            showSnack("Error!"); //xhr.responseText
          }
        });
      } else if (selection == "Expand") {
        localStorage.setItem("rid", rid);
        localStorage.setItem("rgba", myRgba);
        window.open('./ginfo.html', '_self', false);
      } else if (selection == "Save PDF") {
        var gdatas = JSON.stringify({
          "mode": "track"
        });
        // $.ajax({
        //   url: "https://projectsherlock.ddns.net/php/makepdf.php",
        //   success: function(data) {
        //     // var blob = new Blob([data]);
        //     // var link = document.createElement('a');
        //     // link.href = window.URL.createObjectURL(blob);
        //     // link.download = "elem.pdf";
        //     // link.click();
        //   }
        // });

        var url = "https://projectsherlock.ddns.net/php/makepdf.php?rid="+rid;
        var link = document.createElement("a");
        link.download = "a.pdf";
        link.target = "_blank";

        // Construct the URI
        link.href = url;
        document.body.appendChild(link);
        link.click();

        // Cleanup the DOM
        document.body.removeChild(link);
        delete link;
        // $.ajax({
        //   type: "POST",
        //   url: "https://projectsherlock.ddns.net/php/makepdf.php",
        //   data: gdatas,
        //   success:function(data){
        //     showSnack(data);
        //     // console.log(data);
        //   },
        //   error: function(xhr, status, error) {
        //     showSnack("AA"); //xhr.responseText
        //   }
        // });
      } else {
        showSnack("Selection not valid");
      }
    };
    return false;
  });

}

function showSnack(message) {
  var x = document.getElementById("snackbar");
  x.innerHTML = message;
  x.className = "show";
  setTimeout(function() {
    x.className = x.className.replace("show", "");
  }, 3000);
}

$.ajax({
  type: "POST",
  url: "https://projectsherlock.ddns.net/sherlock/v1/request/getTracksFromUid.php",
  data: datas,
  dataType: "json",
  success: function(data) {
    if (data["success"]) {
      for (var i = 0; i < data['result'].length; i++) {
        labelsVals = [];
        dataVals = [];
        data['result'][i]['tracking'].forEach(addReqData);
        buildGraph(data['result'][i]['departureName'], data['result'][i]['arrivalName'], labelsVals, dataVals, data['result'][i]['id']);
      }
    } else {
      showSnack(data["message"]);
    }
  },
  error: function() {
    showSnack("Error");
  }
});

function deleteRequest(rid, UID) {
  var datas = JSON.stringify({
    "rid": rid,
    "uid": UID
  });

  $.ajax({
    type: "POST",
    url: "https://projectsherlock.ddns.net/sherlock/v1/request/deleterequest.php",
    data: datas,
    dataType: "json",
    success: function(data) {
      location.reload();
    },
    error: function(xhr, ajaxOptions, thrownError) {
      showSnack(xhr.status);
    }
  });
}
window.onscroll = function() {
  myFunction()
};

var header = document.getElementById("myHeader");
var sticky = header.offsetTop;

function myFunction() {
  if (window.pageYOffset >= sticky) {
    header.classList.add("sticky");
  } else {
    header.classList.remove("sticky");
  }
}
