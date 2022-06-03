//关闭layer窗口
function closeLayer(){
	var index = parent.layer.getFrameIndex(window.name);
	if(typeof(index)=='undefined'){
		parent.layer.closeAll();
	}else{
		parent.layer.close(index);
	}
}
//打开layer窗口
function openLayer(url,title,area){
	if(typeof(area)=='undefined'||area==null)area= [ '600px', '420px' ];
	if(typeof(title)=='undefined'||title==null)title="";
	
	layer.open({
		type : 2,
		title : title,
		shadeClose : false,
		shade : 0,
		maxmin : false, //开启最大化最小化按钮
		area : area,
		content :url,
		success : function(layero, index) {
			layer.setTop(layero); //重点设置为最上层
		}
	});
}
function openCode(info,title,area){
	if(typeof(area)=='undefined'||area==null)area= [ '240px', '260px' ];
	if(typeof(title)=='undefined'||title==null)title="";

	layer.open({
		type : 1,
		title : title,
		shadeClose : false,
		shade : 0,
		maxmin : false, //开启最大化最小化按钮
		area : area,
		content :info,
		success : function(layero, index) {
			layer.setTop(layero); //重点设置为最上层
		},
		cancel:function () {
			$("body .pan").remove()
		}
	});
}
function openConfirm(title,fn,btn){
	if(typeof(btn)=='undefined'||btn==null)btn= [ '确定', '取消' ];
	layer.confirm(title, {
		  btn:btn //按钮
		}, function(){
		  fn();
		  layer.closeAll('dialog');
		}
	);
}
function openPrompt(title,val,fn)    //0文本  1密码  2多行
{
	layer.prompt({title: title, formType: 0,value:val}, function(txt, index){
		  layer.close(index);
		  fn(txt,index);
		});
}

function openAlert(title,fn){
	layer.alert(title, {
		  closeBtn: 0
		},fn);
}

//获取url内的参数
function getParam(name)
{
       var query = window.location.search.substring(1);
     //  console.log(query)
       query = decodeURI(decodeURI(query));
       //console.log(query)
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
               var pair = vars[i].split("=");
               if(pair[0] == name){return pair[1];}
       }
       return(false);
}	

function setValues(formEl,info){
	formEl.find("[name]").each(function(){
		var t=$(this);
		var name=t.attr("name"); 
		t.val(info[name]);
	});
}

function setSelect(selector,url,def){
	selector.empty();
	$.get(url,{},function(json){
		for(var i=0;i<json.data.length;i++){
			var s=json.data[i];
			var el=$("<option>").val(i).text(s);
			if(i==def) el.attr("selected","selected");
			selector.append(el);
		}
	},"json")
}

function setList(selector,url,def){
	selector.empty();
	$.get(url,{},function(json){
		for(var i=0;i<json.data.length;i++){
			var s=json.data[i];
			var el=$("<option>").val(s.id).text(s.name);
			if(s.id==def) el.attr("selected","selected");
			selector.append(el);
		}
	},"json")
}

function freshTpl(el,url,tmp,fn){
	$.get(url,{},function(json){
		var myTemplate =  document.getElementById(tmp).innerHTML;
		var myHtml = miniTpl(myTemplate, json.data);
		el.empty();
		var html=$(myHtml);
		el.append(html);
		if(fn)fn(html)
	},"json")
}

function ajax_get(url,params,fn){
	$.get(url,params,function(json){
		if(json.status==-100){
			if(parent){
				parent.location.href="/login.html";
			}

		}else{
			if(fn) fn(json);
		}
	},"json")
}

function ajax_post(url,params,fn){
	$.post(url,params,function(json){
		if(json.status==-100){
			if(parent){
				parent.location.href="/login.html";
			}

		}else{
			if(fn) fn(json);
		}
	},"json")
}
