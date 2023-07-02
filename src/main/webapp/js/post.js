let cookies = document.cookie;
let cookie = cookies.split(";");
let userToken = cookie[0].split("=")[1];
let data
let like
let userName;
let postId

window.onload = function(){
    postId = location.search.split("=")[1];
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            data = JSON.parse(this.responseText);
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


    //본인 게시물에만 수정 버튼 보이게하기
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
                    if(document.getElementById('name').innerHTML == userName){
                        document.getElementById('modify').setAttribute('style','displsy:show');
                    }else{
                        document.getElementById('content').setAttribute('readonly',true)
                    }
                    getLike();
                }else{
                    alert('이상하고만');
                }
            }
        };
        xhttp.open("POST", "../token");
        xhttp.send(JSON.stringify(data));
    }



    //수정 버튼 클릭
    document.getElementById('modify').addEventListener('click',function(){
        let modi = new Object();
        modi.title = data.post.title
        modi.postId = postId
        modi.content = document.getElementById('content').value
        console.log(document.getElementById('content').value)
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                let data = JSON.parse(this.responseText);
                if(data.status==1){
                    alert('수정 완료')
                    window.location.href = 'post.html?no='+postId
                }else{
                    alert('이상하고만');
                }
            }
        };
        xhttp.open("PUT", "../post");
        xhttp.send(JSON.stringify(modi));
    })


    // 로그아웃
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

    // 좋아요 누른 버튼 번호 리스트 받는 함수
    function getLike(){
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                like = JSON.parse(this.responseText);
                getComment();
            }
        };
        xhttp.open("GET", "../comment/like?name="+userName);
        xhttp.send();
    }


    //게시물에 해당하는 댓글
    function getComment(){
        let commentList = data.post.commentList
        console.log(like.likes)
        let p = document.getElementById('p2')
        commentList.forEach(e => {
            comment = document.createElement('div')
            if(like.likes.includes(e.commentId)){
                comment.innerHTML = '<div id="good" class="btn btn-lg btn-secondary fw-bold border-white bg-white" style="width: 100%; text-align: left;">'
                                    +'<div id="commentId" style="display : none;">'+e.commentId+'</div>'
                                    +'<div id="name" style="height: auto; text-align: left;">'+e.userName+'</div>'
                                    +'<div id="content" style="float: left;">'+e.content+'</div>'
                                    +'<svg id="like" color=\'red\' xmlns="http://www.w3.org/2000/svg" width="50" height="50" fill="currentColor" class="bi bi-balloon-heart-fill" viewBox="0 0 16 16" style="float: right;">'
                                    +'<path id="like" fill-rule="evenodd" d="M8.49 10.92C19.412 3.382 11.28-2.387 8 .986 4.719-2.387-3.413 3.382 7.51 10.92l-.234.468a.25.25 0 1 0 .448.224l.04-.08c.009.17.024.315.051.45.068.344.208.622.448 1.102l.013.028c.212.422.182.85.05 1.246-.135.402-.366.751-.534 1.003a.25.25 0 0 0 .416.278l.004-.007c.166-.248.431-.646.588-1.115.16-.479.212-1.051-.076-1.629-.258-.515-.365-.732-.419-1.004a2.376 2.376 0 0 1-.037-.289l.008.017a.25.25 0 1 0 .448-.224l-.235-.468ZM6.726 1.269c-1.167-.61-2.8-.142-3.454 1.135-.237.463-.36 1.08-.202 1.85.055.27.467.197.527-.071.285-1.256 1.177-2.462 2.989-2.528.234-.008.348-.278.14-.386Z"/>'
                                    +'</svg></div>'
            }else{
                comment.innerHTML = '<div id="good" class="btn btn-lg btn-secondary fw-bold border-white bg-white" style="width: 100%; text-align: left;">'
                                    +'<div id="commentId" style="display : none;">'+e.commentId+'</div>'
                                    +'<div id="name" style="height: auto; text-align: left;">'+e.userName+'</div>'
                                    +'<div id="content" style="float: left;">'+e.content+'</div>'
                                    +'<svg id="like" color=\'black\' xmlns="http://www.w3.org/2000/svg" width="50" height="50" fill="currentColor" class="bi bi-balloon-heart-fill" viewBox="0 0 16 16" style="float: right;">'
                                    +'<path id="like" fill-rule="evenodd" d="M8.49 10.92C19.412 3.382 11.28-2.387 8 .986 4.719-2.387-3.413 3.382 7.51 10.92l-.234.468a.25.25 0 1 0 .448.224l.04-.08c.009.17.024.315.051.45.068.344.208.622.448 1.102l.013.028c.212.422.182.85.05 1.246-.135.402-.366.751-.534 1.003a.25.25 0 0 0 .416.278l.004-.007c.166-.248.431-.646.588-1.115.16-.479.212-1.051-.076-1.629-.258-.515-.365-.732-.419-1.004a2.376 2.376 0 0 1-.037-.289l.008.017a.25.25 0 1 0 .448-.224l-.235-.468ZM6.726 1.269c-1.167-.61-2.8-.142-3.454 1.135-.237.463-.36 1.08-.202 1.85.055.27.467.197.527-.071.285-1.256 1.177-2.462 2.989-2.528.234-.008.348-.278.14-.386Z"/>'
                                    +'</svg></div><br>'
            }
            p.append(comment)
            p.append(document.createElement('hr'))
            console.log(document.getElementById('commentId').innerHTML)
        });
    }

    //댓글 좋아요~
    document.getElementById("p2").addEventListener('click',function(e){
        if(e.target.id=='like'){
            if (e.target.getAttribute('color') == 'black'){
                e.target.setAttribute('color','red')
            }else{
                e.target.setAttribute('color','black')
            }
            parent = e.target.closest('#good')
            let num = parent.childNodes[0].innerHTML
            let likeD = new Object();
            likeD.userName = userName
            likeD.commentId = num

            const xhttp = new XMLHttpRequest();
            xhttp.open("POST", "../comment/like");
            xhttp.send(JSON.stringify(likeD));
        }
    })


    //댓글 작성 벝흔
    document.getElementById('comment').addEventListener('click',function(){
        if(document.getElementById('com').value.length==0){
            alert('댓글을 작성하세요')
        }else{
            let com = new Object();
            com.userName = userName
            com.postId = postId
            com.content = document.getElementById('com').value
            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    let result = JSON.parse(this.responseText)
                    if(result.status = 1){
                        alert('등록되었습니다.')
                        window.location.href = 'post.html?no='+postId
                    }else{
                        alert('요상하네')
                    }
                }
            };
            xhttp.open("POST", "../comment");
            xhttp.send(JSON.stringify(com));
        }
    })

    //목록 버튼
    document.getElementById('list').addEventListener('click',function(){
        window.location.href = 'list.html'
    })

}