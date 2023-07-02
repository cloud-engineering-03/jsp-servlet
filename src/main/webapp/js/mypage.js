let cookies = document.cookie;
let cookie = cookies.split(";");
let userToken = cookie[0].split("=")[1];
let userName;
let postList;
let commentList;
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
                    document.getElementById('name').value = data.user.name;
                    document.getElementById('nickName').value = data.user.nickName;
                    document.getElementById('id').value = data.user.id;
                    postList = data.user.postList;
                    commentList = data.user.commentList;
                    if(postList.length !=0 || commentList.length != 0){
                        document.getElementById('list').setAttribute('style',"display:show");
                    }
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
    

    document.getElementById("list").addEventListener("click",function(){
        document.getElementById('p').remove();
        let postTable = document.getElementById('postTable');
        postTable.setAttribute("style","display:show");
        postTable.setAttribute("style","background-color:white");
        
        let body = document.getElementById('body');
        let tr = document.getElementById('tr');
        let i=1;
        if(postList.length != 0){
            postList.forEach(e => {
                console.log(e);
                let copy = tr.cloneNode();
                let date = Date(e.createdAt).split(" ");
                let insertDate = date[0]+" "+date[1]+" "+date[2]+" "+date[3];
    
                copy.innerHTML = '<th scope="row">'+(i++)+'</th><td><a href=post.html?no='+e.postId+'>'+e.title+'</a></td><td>'+insertDate+'</td>';
                body.append(copy);
            });
        }

        if(commentList.length != 0){
            let commentTable = postTable.cloneNode();
            commentTable.innerHTML = '<thead class="thead-light">'
                                    +  '<tr>'
                                    +  '<th scope="col">#</th>'
                                    +  '<th scope="col" width="70%">댓글 내용</th>'
                                    +  '<th scope="col">날짜</th>'
                                    +  '</tr>'
                                    +'</thead>'
                                    + '<tbody id="body2">'
                                    + '<tr id="tr2">'
                                    +   '</tr>'
                                    +'</tbody>'
                                    +'</table>';                
            document.getElementById('main').append(commentTable);
            let body2 = document.getElementById('body2');
            let tr2 = document.getElementById('tr2');
            let j=1;
            commentList.forEach(e => {
                let copy = tr2.cloneNode();
                let date2 = Date(e.createdTime).split(" ");
                let insertDate2 = date2[0]+" "+date2[1]+" "+date2[2]+" "+date2[3];
                copy.innerHTML = '<th scope="row">'+(i++)+'</th><td><a href=post.html?no='+e.postId+'>'+e.content+'</a></td><td>'+insertDate2+'</td>';
                body2.append(copy);
            });
        }
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