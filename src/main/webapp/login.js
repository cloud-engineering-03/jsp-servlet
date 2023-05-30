window.addEventListener("load",function(){

    document.querySelector("#bt").addEventListener("click",function() {
        const xhttp = new XMLHttpRequest();
        
        let req = {"id":id.value, "pw":pw.value};

        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                let json = JSON.parse(this.responseText);
                if(json.status == 0){
                    alert('로그인 실패!');
                }else{
                    alert('로그인 성공!');
                }
            }
        };
        xhttp.open("POST", "user", "true");
        console.log(JSON.stringify(req));
        xhttp.send(JSON.stringify(req));
    });
    
});