//文档就绪函数
    //定义全局变量
    var searchObj = {};
/**
 * 页面加载以后执行
 */
$(function(){
  //调用方法
  getUserList(0,"",1);

  //点击search按钮触发查询
  $("#show").click(function(){
    var roleId = $(".RoleId").val();
    var name = $(".userName").val();
    getUserList(roleId,name,1);
  });


  /**
   *    @存在bug
   * 可以这样判断
   * if(searchObj.startPage>0){
   * getUserList(searchObj.roleId,searchObj.name,searchObj.startPage-1);
   * }esle{
   *  alert("已经是第一页了")
   * }
   */

  //首页
  $(".step-backward").click(function(){
        if(searchObj.startPage == 1){
            alert("已经是第一页了");
        }else{
            getUserList(searchObj.roleId,searchObj.name,1);
        }
    });
   //上一页
    $(".chevron-left").click(function(){
        if(searchObj.startPage == 1){
            alert("已经是第一页了");
        }else{
            getUserList(searchObj.roleId,searchObj.name,searchObj.startPage - 1);
        }
    });
    //下一页
    $(".chevron-right").click(function(){
        if(searchObj.startPage == searchObj.pages){
            alert("已经是最后一页了");
        }else{
            getUserList(searchObj.roleId,searchObj.name,searchObj.startPage + 1);
        }
    });
    //最后一页
    $(".step-forward").click(function(){
        if(searchObj.startPage == searchObj.pages - 1){
            alert("已经是最后一页了");
        }else{
            getUserList(searchObj.roleId,searchObj.name,searchObj.pages);
        }
    });

    //处理下拉框改变
    $(".NUM .pages").change(function(){
        getUserList(searchObj.roleId,searchObj.name,parseInt($(this).val()));
    });

    //增加新用户
    $(".addUser").click(function(){
        //修改UserId缓存为0,用于区分新增操作
        localStorage.setItem("userId",0);
        location.href="./EditUser.html";
    });

});

/*
 注意:自定义函数写在$(function(){ });外面
*/
/**
 * 查询用户信息列表
 * @param {*} roleId 角色Id
 * @param {*} name  姓名
 * @param {*} startPage 起始页
 * function loadUserMessage(roleId,name,startPage)
 */
 function getUserList(roleId,name,startPage){
    searchObj.roleId = roleId;
    searchObj.name = name;
    searchObj.startPage = startPage;
    //调用Ajax的方法 
    $.ajax({
        url:"http://localhost:8080/SunshineAirlines/userList",//路由,接口
        data:"roleId="+roleId+"&name="+name+"&startPage="+startPage+"&pageSize=10",//需要的传的参数 pageSize一页显示十行数据
        type:"post",//传送方式,post,或get,由于登录的密码需要保密,使用post
        success:function(msg){//成功的消息回复
            //1.    将msg转换成JSON格式 的对象
            var obj = JSON.parse(msg);
            //加载列表部分内容
            if(obj.flag == "success"){
                //循环拼接数据,生成用户列表信息
                //方法1
                    // var html = "";
                    // for(var i=0;i<obj.data.length;i++){
                    //     var gender = obj.data[i].Gender == "M"?"男":"女";
                    //     var RoleId = obj.data[i].RoleId == "1"?"Office User":"Administrator";
                    //      html += "<tr>"
                    //      +  "<td>"+obj.data[i].Email+"</td>"
                    //      +  "<td>"+obj.data[i].FirstName+" "+obj.data[i].LastName+"</td>"
                    //      +  "<td>"+gender+"</td>"
                    //      + "<td>"+obj.data[i].DateOfBirth+"</td>"
                    //      + "<td>"+obj.data[i].Phone+"</td>"
                    //      + "<td>"+RoleId+"</td>"
                    //      + "<td><input class='editUser' style='width: 80px;  font-size: 16px;' type='button' value='Edit' onclick='editUser("+obj.data[i].UserId+")'/></td>"
                    //      +"</tr>";
                    // } 
                    // //找到表格
                    // $(".formclass tbody").html(html);
                    // //隔行换色
                    // $(".formclass tbody tr:odd").addClass("tdcolor");//奇数行
                    // $(".formclass tbody tr:even").addClass("tdcolor1");//偶数行
                  //方法2
                    var html = "";
                    for(var i=0;i<obj.data.length;i++){
                        var gender = obj.data[i].Gender == "M"?"男":"女";
                        var RoleId = obj.data[i].RoleId == "1"?"Office User":"Administrator";
                        var trClass = "tdcolor";
                        if(i%2==0){
                            trClass = "tdcolor1";
                        }
                         html += "<tr class='"+trClass+"'>"
                         +  "<td>"+obj.data[i].Email+"</td>"
                         +  "<td>"+obj.data[i].FirstName+" "+obj.data[i].LastName+"</td>"
                         +  "<td>"+gender+"</td>"
                         + "<td>"+obj.data[i].DateOfBirth+"</td>"
                         + "<td>"+obj.data[i].Phone+"</td>"
                         + "<td>"+RoleId+"</td>"
                         + "<td><input class='editUser' style='width: 80px;  font-size: 16px;' type='button' value='Edit' onclick='editUser("+obj.data[i].UserId+")'/></td>"
                         +"</tr>";
                    } 
                    //找到表格
                    $(".formclass tbody").html(html);


                    //处理页码和总条数
                    var total = obj.page.total;
                    //处理页码显示
                    initPage(total);
            }  
        
        }
     });

}

/**
 * 处理页面显示 初始化页面
 * @param {*} total 
 */
//处理页码显示 , 由于 查询用户信息列表 这个函数代码过长,于是抽离一部分代码出来,单独写一个函数,然后进行调用.
function initPage(total){
    //方式1
    // searchObj.pages = parseInt(total/10);  //--------分页有一定问题-------
    // if(total%10!=0){
    //  searchObj.pages+=1;
    // }
    // $(".pages").text(searchObj.pages);
    // $(".totals").text(total);//总条数
    //下拉框
    // var optionHtml = "";
    // for(var i=1;i<searchObj.pages;i++){
    //     if(searchObj.startPage == i){
    //         optionHtml += "<option  value='"+i+"' selected>"+i+"</option>";
    //     }else{
    //     optionHtml += "<option  value='"+i+"'>"+i+"</option>";
    //     }
    // }
    // $(".NUM .pages").html(optionHtml);

    //方式2
    var pages = parseInt(total/10);
    if(total%10!=0){
        pages++;
    }
    //储存总页数,用于获取最后一页
    searchObj.pages=pages;
    $(".totalpages .pages").text(pages);
    $(".totals").text(total);//总条数
    //下拉框
    var optionHtml = "";
    for(var i=1;i<pages+1;i++){
        if(searchObj.startPage == i){
            optionHtml += "<option  value='"+i+"' selected>"+i+"</option>";
        }else{
        optionHtml += "<option  value='"+i+"'>"+i+"</option>";
        }
    }
    $(".NUM .pages").html(optionHtml);
}
/**
 *  点击编辑用户
 * @param {} userId 
 */
function editUser(userId){
    if(userId>0){
        localStorage.setItem("userId",userId);
        location.href="./EditUser.html";
    }
}












