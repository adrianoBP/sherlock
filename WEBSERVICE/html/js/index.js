window.onload = function() {
  localStorage.clear();
  sessionStorage.clear();
  if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
    alert("This website is not fully compatible with your device. You should use a desktop computer or a laptop!");
  }
}

function downloader() {
  window.location.replace("https://projectsherlock.ddns.net/resources/sherlock.apk");
}

function showSnack(message) {
  var x = document.getElementById("snackbar");
  x.innerHTML = message;
  x.className = "show";
  setTimeout(function() {
    x.className = x.className.replace("show", "");
  }, 3000);
}

function login() {
  var tName = document.getElementById('name');
  var tPass = document.getElementById('pass');
  var uid = "";

  var datas = JSON.stringify({
    "username": tName.value,
    "password": tPass.value
  })
  $.ajax({
    type: "POST",
    url: "https://projectsherlock.ddns.net/sherlock/v1/user/get.php",
    data: datas,
    dataType: "json",
    success: function(data) {
      if (data["success"]) {
        localStorage.setItem("uid", data['uid']);
        localStorage.setItem("user", tName.value);

        if (data['admin']) {
          sessionStorage.setItem("logged", true);
          window.open('./admin/index.html', '_self', false)
        } else {
          window.open('./pages/home.html', '_self', false)
        }


        tName.value = "";
        tPass.value = "";
      } else {
        var msg = "404 - User not found!";
        if (data['message'] = msg)
          msg = "Wrong username or password!";
        showSnack(msg);

      }
    },
    error: function(xhr, statement, error) {
      alert(error);
    }
  });
}

function waitForEnter(e) {
  if (e.keyCode == 13) {
    login();
  }
}

// NOT MINE //

$(function() {

  $(".input input").focus(function() {

    $(this).parent(".input").each(function() {
      $("label", this).css({
        "line-height": "18px",
        "font-size": "18px",
        "font-weight": "100",
        "top": "0px"
      })
      $(".spin", this).css({
        "width": "100%"
      })
    });
  }).blur(function() {
    $(".spin").css({
      "width": "0px"
    })
    if ($(this).val() == "") {
      $(this).parent(".input").each(function() {
        $("label", this).css({
          "line-height": "60px",
          "font-size": "24px",
          "font-weight": "300",
          "top": "10px"
        })
      });

    }
  });

  $(".button").click(function(e) {
    var pX = e.pageX,
      pY = e.pageY,
      oX = parseInt($(this).offset().left),
      oY = parseInt($(this).offset().top);

    $(this).append('<span class="click-efect x-' + oX + ' y-' + oY + '" style="margin-left:' + (pX - oX) + 'px;margin-top:' + (pY - oY) + 'px;"></span>')
    $('.x-' + oX + '.y-' + oY + '').animate({
      "width": "500px",
      "height": "500px",
      "top": "-250px",
      "left": "-250px",

    }, 600);
    $("button", this).addClass('active');
    var bLog = document.getElementById('login');
    bLog.innerHTML = "Try Again!";
  })

  $(".alt-2").click(function() {
    if (!$(this).hasClass('material-button')) {
      $(".shape").css({
        "width": "100%",
        "height": "100%",
        "transform": "rotate(0deg)"
      })

      setTimeout(function() {
        $(".overbox").css({
          "overflow": "initial"
        })
      }, 600)

      $(this).animate({
        "width": "140px",
        "height": "140px"
      }, 500, function() {
        $(".box").removeClass("back");

        $(this).removeClass('active')
      });

      $(".overbox .title").fadeOut(300);
      $(".overbox .input").fadeOut(300);
      $(".overbox .button").fadeOut(300);

      $(".alt-2").addClass('material-buton');
    }

  })

  $(".material-button").click(function() {

    if ($(this).hasClass('material-button')) {
      setTimeout(function() {
        $(".overbox").css({
          "overflow": "hidden"
        })
        $(".box").addClass("back");
      }, 200)
      $(this).addClass('active').animate({
        "width": "700px",
        "height": "700px"
      });

      setTimeout(function() {
        $(".shape").css({
          "width": "50%",
          "height": "50%",
          "transform": "rotate(45deg)"
        })

        $(".overbox .title").fadeIn(300);
        $(".overbox .input").fadeIn(300);
        $(".overbox .button").fadeIn(300);
      }, 700)

      $(this).removeClass('material-button');

    }

    if ($(".alt-2").hasClass('material-buton')) {
      $(".alt-2").removeClass('material-buton');
      $(".alt-2").addClass('material-button');
    }

  });

});
