$(document).ready(function(){
    $(".list_out").click(function(){//点击 退出
        //1.    清空缓存
        localStorage.setItem("user","");//即将用户信息 给空
        //2.    跳转页面
        location.href = "./Login.html";
    });
});