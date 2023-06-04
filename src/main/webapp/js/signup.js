window.onload = function(){
    peopleName = document.getElementById("nameCheck");
    
    peopleName.addEventListener('click',function(){
        const xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					data = JSON.parse(this.responseText);
                    if(data.status==1){
						let lead = document.getElementById("lead");
                        lead.remove();
                        let newInput = document.createElement('input');
                        newInput.setAttribute("id", "id");
                        newInput.setAttribute("class", "btn btn-lg btn-secondary fw-bold border-white bg-white");
                        newInput.setAttribute("placeholder", "ID");
                        let newText = newInput.cloneNode();
                        newText.setAttribute("id","name");
                        newText.setAttribute("readOnly","true");
                        newText.value = data.name;
                        let copy1 = newInput.cloneNode();
                        let copy2 = newInput.cloneNode();
                        copy1.setAttribute("id", "nickName");
                        copy1.setAttribute("placeholder", "Nickname");
                        copy2.setAttribute("id", "password");
                        copy2.setAttribute("type", "password");
                        copy2.setAttribute("placeholder", "Password");
                        
                        document.getElementById("main").append(newText);
                        document.getElementById("main").append(document.createElement("br"));
                        document.getElementById("main").append(document.createElement("br"));
                        document.getElementById("main").append(newInput);
                        
                        //중복확인 버튼
                        let dupcheckB = document.createElement('button');
                        dupcheckB.setAttribute("id","dupcheck");
                        dupcheckB.innerHTML = 'ID 중복 확인';
                        dupcheckB.setAttribute("class","btn btn-lg btn-secondary fw-bold border-white bg-white");
                        
                        document.getElementById("main").append(document.createElement("br"));
                        document.getElementById("main").append(document.createElement("br"));
                        
                        //password 입력 칸
                        document.getElementById("main").append(copy2);
                        
                        document.getElementById("main").append(document.createElement("br"));
                        document.getElementById("main").append(document.createElement("br"));
                        
                        //nickname 입력칸
                        document.getElementById("main").append(copy1);
                        
                        document.getElementById("main").append(document.createElement("br"));
                        document.getElementById("main").append(document.createElement("br"));

						//중복확인 버튼 삽입
                        document.getElementById("main").append(dupcheckB);
                        
                        //가입 버튼 생성
                        let bt = document.createElement('button');
                        bt.setAttribute("id","signup");
                        bt.setAttribute("style","display:none");
                        bt.innerHTML = '가입';
                        bt.setAttribute("class", "btn btn-lg btn-secondary fw-bold border-white bg-white");
                        document.getElementById("main").append(bt);
                        
                    }else{
                        alert('가입 불가능한 이름');
                    }
				}
			};
			
			var name = '?name='+document.getElementById('name').value;
			xhttp.open("GET", "../people"+name);
			xhttp.send();
    })
    
    document.getElementById("main").addEventListener('click',function(e){
        if(e.target.innerHTML=='ID 중복 확인'){
            const xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        data = JSON.parse(this.responseText);
                        if(data.status==1){
                            document.getElementById("dupcheck").setAttribute("style","display:none");
                            document.getElementById("signup").setAttribute("style","display:inline-block");                      
                        }else{
                            alert('중복되는 아이디 입니다.');
                        }
                    }
                };
                var name = '?id='+document.getElementById('id').value;
                xhttp.open("GET", "../user/new"+name);
                xhttp.send();
        }
    })
    
    document.getElementById("main").addEventListener('click',function(e){
        if(e.target.innerHTML=='가입'){
            const xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        data = JSON.parse(this.responseText);
                        if(data.status==1){
                            document.getElementById("dupcheck").setAttribute("style","display:none");
                            document.getElementById("signup").setAttribute("style","display:inline-block");
                            alert('가입 되었습니다.');
                            window.location.href='index.html';
                        }else{
                            alert('잠시 오류로 가입이 되지 않습니다. 다시 시도해 주세요');
                        }
                    }
                };
                
                // json data 생성
                let data = new Object();
                data.name = document.getElementById("name").value;
                data.id = document.getElementById("id").value;
                data.password = document.getElementById("password").value;
                data.nickName = document.getElementById("nickName").value;

                xhttp.open("POST", "../user/new");
                xhttp.send(JSON.stringify(data));
        }
    })
}

