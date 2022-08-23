$(document).ready(function(){//页面加载完成后执行
    //1.    判断是否自动登录(1.缓存的登录对象;2.登录时间)
    var userStr = localStorage.getItem("user");//获取缓存的用户信息
    try{
        var user = JSON.parse(userStr);//转换成JSON格式的对象
        var loginDate = new Date(user.loginDate);//转换成Date格式的实例化对象
        loginDate.setDate(loginDate.getDate()+7);//设置成7天后的
        //  判断当前时间 是否小于 设置的7天后的时间
        if(new Date() < loginDate){
            //跳转页面
            jump(user);
        }
    }catch(error){

    }

    /**
     * 点击 登录
     */
    $(".loginButton").click(function(){
        var email = $(".email").val(); 
       var password = $(".password").val();
        var param = "email="+email+"&password="+password;
      
        $.ajax({
            url:"http://localhost:8080/SunshineAirlines/login",//路由,接口
            data:param,//需要的传的参数
            type:"post",//传送方式,post,或get,由于登录的密码需要保密,使用post
            success:function(msg){//成功的消息回复
                //1.    将msg转换成JSON格式 的对象
                var obj = JSON.parse(msg);
                
                //2.    判断是否登录成功
                if(obj.flag == "success"){//获取obj对象中的flag值
                   //2.1    记住用户信息
                    var user = obj.data;//用户信息
                   
                    //7天自动登录(保证7天)
                    if($(".is7day").is(":checked")){//选中为true
                        //给user添加一个字段(成员变量)
                        user.loginDate = new Date();
                    }
                     //2.2   缓存用户信息
                    //localStorage.setItem("需要存的对象的名称",存JSON格式字符串);
                    localStorage.setItem("user",JSON.stringify(user));//将一个对象转换为JSON格式的字符串
                    //2.3    跳转到不同主界面
                    jump(user);
                }else{
                    $(".alertInfo").text(obj.data);
                }
            }
       });

    });

});

/**
 * 跳转不同角色页面
 * @param {用户信息} user 用户信息集合
 */
function jump(user){
    if(user.RoleId == 1){
        //员工
        location.href = "./OfficeUserMenu.html";//从根目录开始找该页面
    }else if(user.RoleId == 2){
        //管理员
        location.href = "./AdministratorMenu.html";
    }
}