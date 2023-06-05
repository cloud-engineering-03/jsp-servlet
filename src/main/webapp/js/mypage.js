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
                    get();
                }else{
                    alert('이상하고만');
                }
            }
        };
        xhttp.open("POST", "../token");
        xhttp.send(JSON.stringify(data));
    }

    function get(){
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                let data = JSON.parse(this.responseText);
                if(data.status==1){
					console.log(data);
                    document.getElementById('name').value = data.user.name;
                    document.getElementById('nickName').value = data.user.nickName;
                    document.getElementById('id').value = data.user.id;
                }else{
                    alert('비밀번호 제대로 입력하세요');
                }
            }
        };
        xhttp.open("GET", "../user?name="+userName);
        xhttp.send();
    }
    
    document.getElementById("modify").addEventListener('click',function(){
        let modi = new Object();
        modi.name = document.getElementById('name').value
        modi.nickName = document.getElementById('nickName').value
        modi.id = document.getElementById('id').value
        modi.password = document.getElementById('password').value
        
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                data = JSON.parse(this.responseText);
                if(data.status==1){
                    alert("수정 되었습니다.");
                    window.location.href='index.html';
                }else{
                    alert('이상하고만');
                }
            }
        };
        xhttp.open("PUT", "../user");
        xhttp.send(JSON.stringify(modi));
    })
    












    document.getElementById("logout").addEventListener('click',function(){
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                data = JSON.parse(this.responseText);
                if(data.status==1){
                    alert("로그아웃 되었습니다.");
                    window.location.href='index.html';
                }else{
                    alert('이상하고만');
                }
            }
        };
        xhttp.open("GET", "../logout");
        xhttp.send();
    })
}