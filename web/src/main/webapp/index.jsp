<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">


  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">

  <title> IC Hello World </title>
</head>
<body>
<div class = "text-center">
  <div class = "rounded" style = "background: white">
    <div class = "form-signin">
      <img class="mb-4" src="https://getbootstrap.com/docs/4.0/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">
      <h1 class="h3 mb-3 font-weight-normal" id = "greetingMessage">Sign in</h1>
      <label for="inputEmail" class="sr-only" id = "usernameLabel">Username</label>
      <input type="email" id="username" class="form-control" placeholder="Username" required="" autofocus="">
      <label for="inputPassword" class="sr-only">Password</label>
      <input type="password" id="password" class="form-control" placeholder="Password" required="">
      <div class="checkbox mb-3">
        <label id = "new">
          Don't have an account? <a href = "#" id = "newAccount"> create here </a>
        </label>
        <label id = "notNew" hidden>
          Already have an account? <a href = '#', id = 'login'> Login here </a>
        </label>
      </div>
      <button class="btn btn-lg btn-primary btn-block" type="submit" id = "submit">Sign in</button>
    </div>
  </div>
</div>
<script>
  var newAccount = "false";
  document.getElementById("newAccount").onclick = function() {
    newAccount = "true";
    document.getElementById("greetingMessage").innerHTML = "Sign up";
    document.getElementById("new").hidden = true;
    document.getElementById("notNew").hidden = false;
  }

  document.getElementById("login").onclick = function() {
    newAccount = "false";
    document.getElementById("greetingMessage").innerHTML = "Sign in";
    document.getElementById("new").hidden = false;
    document.getElementById("notNew").hidden = true;
  }


  document.getElementById("submit").onclick = function() {
    var username = document.getElementById("username");
    var password = document.getElementById("password");
    var loginAttempt = new XMLHttpRequest();
    loginAttempt.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        Window.alert(loginAttempt.responseText);
      } else {
        Window.alert("Failure")
      }
    };
    loginAttempt.open("POST","username="+username+"&password="+password+"newAccount="+newaccount);
    loginAttempt.send();

  };
</script>
</div>
</body>

<style>
  a:link {
    color: default;
    underline: default;
  }

  /* visited link */
  a:visited {
    color: default;
  }

  /* mouse over link */
  a:hover {
    color: default;
  }

  /* selected link */
  a:active {
    color: default;
  }
  body {
    background-image: url("mainBackgroundImage.jpg");
    background-size: cover;
    height: 100%;
    display: -ms-flexbox;
    display: -webkit-box;
    display: flex;
    -ms-flex-align: center;
    -ms-flex-pack: center;
    -webkit-box-align: center;
    align-items: center;
    -webkit-box-pack: center;
    justify-content: center;
    padding-top: 40px;
    padding-bottom: 40px;
  }

  .form-signin {
    width: 100%;
    max-width: 330px;
    padding: 50px;
    margin: 0 auto;
  }
  .form-signin .checkbox {
    font-weight: 400;
  }
  .form-signin .form-control {
    position: relative;
    box-sizing: border-box;
    height: auto;
    padding: 10px;
    font-size: 16px;
  }
  .form-signin .form-control:focus {
    z-index: 2;
  }
  .form-signin input[type="email"] {
    margin-bottom: -1px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
  }
  .form-signin input[type="password"] {
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
  }

</style>
</html>