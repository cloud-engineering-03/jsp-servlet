window.onload = function(){
    document.getElementById("login").addEventListener('click',function(){
        let data = new Object();
        data.id = document.getElementById("id").value;
        data.password = document.getElementById("pw").value;
        if(data.id == "" || data.password == ""){
            alert('아이디와 비밀번호를 입력해주세요');
        }else{
            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    data = JSON.parse(this.responseText);
                    if(data.status==1){
                        alert('로그인 되었습니다.');
                        window.location.href='index.html';
                    }else{
                        alert('정보가 일치하지 않습니다.');
                    }
                }
            };
            xhttp.open("POST", "../user");
            xhttp.send(JSON.stringify(data));
        }
    })


}