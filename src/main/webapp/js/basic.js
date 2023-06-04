let cookies = document.cookie;
let cookie = cookies.split(";");
let userToken = cookie[0].split("=")[1];

let userName;
window.onload= function(){

    if(userToken != ""){
        let data = userToken.split(".")[1];
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                data = JSON.parse(this.responseText);
                if(data.status==1){
                    let info = data.name.replace("\\","");
                    let user = JSON.parse(info);
                    userName = user.name;
                    document.getElementById('p').remove();
                    document.getElementById('main').append(userName+"님 안녕하세요");
                    document.getElementById('my').setAttribute('style','display:show');
                    document.getElementById('board').setAttribute('style','display:show');
                    document.getElementById('logout').setAttribute('style','display:show');
                }else{
                    alert('이상하고만');
                }
            }
        };
        xhttp.open("POST", "../token");
        xhttp.send(JSON.stringify(data));
    }
}
    