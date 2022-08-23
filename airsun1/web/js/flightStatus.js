//定义信息
var searchObj = {};

/**
 * 页面加载后 执行
 */
$(function(){
 //调用方法
  getFlightStatus("1991-09-09",1);

 //点击search按钮触发查询
 $("#searchFlightStatus").click(function(){
   var departureDate = $(".departureDate").val();
   getFlightStatus(departureDate,1);
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
        getFlightStatus(searchObj.departureDate,1)
       }
   });
  //上一页
   $(".chevron-left").click(function(){
       if(searchObj.startPage == 1){
           alert("已经是第一页了");
       }else{
        getFlightStatus(searchObj.departureDate,searchObj.startPage - 1);
       }
   });
   //下一页
   $(".chevron-right").click(function(){
       if(searchObj.startPage == searchObj.pages){
           alert("已经是最后一页了");
       }else{
        getFlightStatus(searchObj.departureDate,searchObj.startPage + 1);
       }
   });
   //最后一页
   $(".step-forward").click(function(){
       if(searchObj.startPage == searchObj.pages){
           alert("已经是最后一页了");
       }else{
        getFlightStatus(searchObj.departureDate,searchObj.pages);
       }
   });

   //处理下拉框改变
   $(".NUM .pages").change(function(){
    getFlightStatus(searchObj.departureDate,parseInt($(this).val()));
   });

});

/**
 * 获取航班状态信息
 * @param {*} departureDate  航班日期
 * @param {*} startPage  起始页
 */
function getFlightStatus(departureDate,startPage){
    searchObj.departureDate = departureDate;
    searchObj.startPage = startPage;
    //调用ajax方法
    $.ajax({
        url:"http://localhost:8080/SunshineAirlines/getFlightStatus",//路由,接口
        data:"departureDate="+departureDate+"&startPage="+startPage+"&pageSize=10",//需要的传的参数 pageSize一页显示十行数据
        type:"post",//传送方式,post,或get,由于登录的密码需要保密,使用post
        success:function(msg){//成功的消息回复
            //1.    将msg转换成JSON格式 的对象
            var obj = JSON.parse(msg);
            //加载列表部分内容
            if(obj.flag == "success"){
                //循环拼接数据,生成用户列表信息
                    var htmlArr = "";
                    for(var i=0;i<obj.data.length;i++){
                       //计划到达时间 = 计划飞行时间(日期) + 飞行时间(分);
                        var t1 = new Date(obj.data[i].Date);//计划起飞时间
                        var t2 = obj.data[i].FlightTime;//飞行时间
                        var t3 = t1.setMinutes(t1.getMinutes() + t2);//新的计划到达时间
                        
                        //航班状态:准时 On Time 延迟 Delay 早到 Early
                        //实际到达时间 - 计划到达时间
                        var timediff = (new Date(obj.data[i].ActualArrivalTime) - new Date(t3))/(1000*60);
                        var hours = new Date(t3).getHours()<10?"0"+new Date(t3).getHours():new Date(t3).getHours();
                        var minutes = new Date(t3).getMinutes()<10?"0"+new Date(t3).getMinutes():new Date(t3).getMinutes();
                        var statusMsg = "";//行程状态
                        console.log(new Date(t3).getMinutes());
                       // console.log(minutes);
                        if(timediff<0){
                            statusMsg = "Early"+(-timediff)+"mintutes";//提前到达
                        }else if(timediff>0){
                            statusMsg = "Delay"+timediff+"mintutes";//推迟到达
                        }else if(timediff == 0){
                            statusMsg = "On Time";//准时到达
                        }
                        var trClass = "tdcolor";
                        if(i%2==0){
                            trClass = "tdcolor1";
                        }
                        console.log("页码"+searchObj.pages);
                       // "<td>"+((i+1)+(searchObj.startPage-1)*10)+"</td>"
                       //+ "<td>"+(i+1)+(searchObj.pages-1)*10+"</td>"
                       htmlArr += "<tr class='"+trClass+"'>"
                         + "<td>"+((i+1)+(searchObj.startPage -1)*10)+"</td>"
                         + "<td>"+obj.data[i].FlightNumber+"</td>"
                         + "<td>"+obj.data[i].DepartCityName+"/"+obj.data[i].DepartureAirportIATA+"</td>"
                         + "<td>"+obj.data[i].ArriveCityName+"/"+obj.data[i].ArrivalAirportIATA+"</td>"
                         + "<td>"+obj.data[i].Time.substring(0,5)+"</td>" 
                         +"<td>"+hours+":"+minutes+"</td>"
                         + "<td>"+obj.data[i].ActualArrivalTime.substring(11,16)+"</td>" 
                         +"<td>"+obj.data[i].Gate+"</td>"
                         +"<td>"+statusMsg+"</td>"
                         +"</tr>";
                    } 
                    //找到表格
                    $(".formclass tbody").html(htmlArr);


                    //处理页码和总条数
                    var total = obj.page.total;
                    //处理页码显示
                    initPage(total);
            }  
        
        }
     });
}

/**
 * 处理页码问题，分页操作
 * @param {*} total 总条数
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
   /* var pages = parseInt(total/10);
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
    $(".NUM .pages").html(optionHtml);*/
    //方式3
    var pages = parseInt(total/10)+1;
    // if(total%10!=0){
    //     pages++;
    // }
    //储存总页数,用于获取最后一页
    searchObj.pages=pages;
    $(".totalpages .pages").text(pages);
    $(".totals").text(total);//总条数
    //下拉框
    var optionHtml = "";
    for(var i=1;i<searchObj.pages+1;i++){
        if(searchObj.startPage == i){
            optionHtml += "<option  value='"+i+"' selected>"+i+"</option>";
        }else{
        optionHtml += "<option  value='"+i+"'>"+i+"</option>";
        }
    }
    $(".NUM .pages").html(optionHtml);

}

