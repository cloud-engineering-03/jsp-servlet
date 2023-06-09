let cookies = document.cookie;
let cookie = cookies.split(";");
let userToken = cookie[0].split("=")[1];

window.onload = function(){
    let postId = location.search.split("=")[1];
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            let data = JSON.parse(this.responseText);
            if(data.status==1){
                document.getElementById('title').innerHTML = data.post.title;
                document.getElementById('name').innerHTML =data.post.userName;
                document.getElementById('content').value = data.post.content;
                showM();
            }else{
                alert('이상하고만');
            }
        }
    };
    xhttp.open("GET", "../post?no="+postId);
    xhttp.send();


    let userName;

    function showM(){
        let data = userToken.split(".")[1];
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                data = JSON.parse(this.responseText);
                if(data.status==1){
                    let info = data.name.replace("\\","");
                    let user = JSON.parse(info);
                    userName = user.name;
                    console.log(document.getElementById('name').innerHTML);
                    if(document.getElementById('name').innerHTML == userName){
                        document.getElementById('modify').setAttribute('style','displsy:show');
                    }
                }else{
                    alert('이상하고만');
                }
            }
        };
        xhttp.open("POST", "../token");
        xhttp.send(JSON.stringify(data));
    }




    document.getElementById('modify').addEventListener('click',function(){
        let postId = location.search.split("=")[1];
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                let data = JSON.parse(this.responseText);
                if(data.status==1){

                }else{
                    alert('이상하고만');
                }
            }
        };
        xhttp.open("GET", "../post?no="+postId);
        xhttp.send();
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