const UID = localStorage.getItem("uid");
const RID = localStorage.getItem("rid");
const RGBA = localStorage.getItem("rgba")

var hUser = document.getElementById('hUser');

var bLogout = document.getElementById('bLogout');
//
//Listeners
bLogout.addEventListener("click", function(){
  window.open('./home.html','_self',false)
});

var labelsVals = [];
var dataVals = [];

var labelsVals = [];
var dataVals = [];

function formattedDate(d) {
  let month = String(d.getMonth() + 1);
  let day = String(d.getDate());
  const year = String(d.getFullYear());

  if (month.length < 2) month = '0' + month;
  if (day.length < 2) day = '0' + day;

  return `${day}.${month}.${year}`;
}

function addReqData(value, index, ar){
  // labelsVals.push(formattedDate(new Date(value['departureTime'])));
  console.log(value['departureTime']);
  if(formattedDate(new Date(value['departureTime'])).startsWith("01")){
    labelsVals.push(formattedDate(new Date(value['departureTime'])));
  }else{
    labelsVals.push(" ");

  }
  dataVals.push(value['value']);
}

var datas = JSON.stringify(
  {
    "uid": UID
  }
);

function buildGraph(departure, arrival, labels, data, rid){

  hUser.innerHTML = departure + " - "+ arrival;

  var myRgba = RGBA;
  var canvas = document.createElement('canvas');
  canvas.width = 7;
  canvas.height = 3;

  let lineChart = new Chart(canvas, {
    type: 'line',
    data: {
      labels: labels,
      datasets: [
        {
          label: "Price trend (EUR)",
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
        }
      ]
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
  div.className="w3-panel w3-card graphcard";
  var x = $(window).width();
  div.style.width = (x-50)+"px";
  div.style.margin="25px auto 25px auto";
  div.appendChild(canvas);
  document.body.appendChild(div);

}

$.ajax({
  type: "POST",
  url: "https://projectsherlock.ddns.net/sherlock/v1/request/getTracksFromUid.php",
  data: datas,
  dataType: "json",
  success:function(data){
    if(data["success"]){
      for(var i = 0; i<data['result'].length; i++){
        labelsVals = [];
        dataVals = [];
        data['result'][i]['tracking'].forEach(addReqData);
        if(data['result'][i]['id'] == RID){
          buildGraph(data['result'][i]['departureName'], data['result'][i]['arrivalName'], labelsVals, dataVals, data['result'][i]['id']);
        }
      }
    }else{
      showSnack(data["message"]);
    }
  },
  error:function(){
    showSnack("Error");
  }
});
