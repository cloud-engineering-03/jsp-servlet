window.onload = function(){
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            let data = JSON.parse(this.responseText);
            if(data.status==1){
                let list = data.list;
                let body = document.getElementById('body');
                let tr = document.getElementById('tr');
                let i=1;

                list.forEach(e => {
                    let copy = tr.cloneNode();
                    let date = Date(e.createdAt).split(" ");
                    let insertDate = date[0]+" "+date[1]+" "+date[2]+" "+date[3];
        
                    copy.innerHTML = '<th scope="row">'+(i++)+'</th><td><a href=post.html?no='+e.postId+'>'+e.title+'</a></td>'
                                    +'<td>'+e.userName+'</td>'+'<td>'+e.viewCnt+'</td>'+'<td>'+insertDate+'</td>';
                    body.append(copy);
                });
            }else{
                alert('이상하고만');
            }
        }
    };
    xhttp.open("GET", "../post/list");
    xhttp.send();














}