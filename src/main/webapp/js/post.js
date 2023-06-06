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
            }else{
                alert('이상하고만');
            }
        }
    };
    xhttp.open("GET", "../post?no="+postId);
    xhttp.send();


    document.getElementById('modify').addEventListener('click',function(){
        
    })



}