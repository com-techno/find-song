var logged_in = false;

function openregmenu() {
    if (logged_in) {
        // Open add playlist page
    } else {
        document.getElementById("enterance").classList.remove('active');
        document.getElementById("regbef").classList.add('active');
        document.getElementById("ptext").innerHTML = "Для того чтобы создать собственную библиотеку,";
        document.getElementById("ptext2").innerHTML = "вам необходимо зарегистрироваться";
    }
}

function login() {
    /*var requestURL = 'https://2e6ef2b1188d.ngrok.io/api/sign_in';
    var request = new XMLHttpRequest();

    request.open('POST', requestURL);
    request.responseType = 'json';

    request.request.send("{" );

    request.onload = function () {
        var response = JSON.parse(request.responseText);
        if (response.token != null) logged_in = true;
    }*/

    console.log("ok")
}


function closemenu() {
    document.getElementById("regbef").classList.remove('active');
    document.getElementById("enterance").classList.remove('active');
}

function openregmenu1() {
    document.getElementById("enterance").classList.remove('active');
    document.getElementById("regbef").classList.add('active');
    document.getElementById("ptext").innerHTML = "Для того чтобы добавить песню,";
    document.getElementById("ptext2").innerHTML = "вам необходимо зарегистрироваться";
}

function openregmenu2() {
    document.getElementById("enterance").classList.remove('active');
    document.getElementById("regbef").classList.add('active');
    document.getElementById("ptext").innerHTML = "";
    document.getElementById("ptext2").innerHTML = "";
}

function openentmenu() {
    document.getElementById("regbef").classList.remove('active');
    document.getElementById("enterance").classList.add('active');
}