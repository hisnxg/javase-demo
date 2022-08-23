/**
 * 页面加载完成后执行
 */
$(function(){
    $.ajax({
        type:"POST",
        url:"http://localhost:8080/SunshineAirlines/getCityNames",
        data:"",
        success:function(msg){
            var json = JSON.parse(msg);
            if(json.flag == "success"){
               //1. 循环遍历列表数据
               var optionHtml = "";
               for(var i=0;i<json.data.length;i++){
                   //json.data[i].CityName;
                   //2. 拼接option HTML 代码字符串
                    optionHtml += "<option value='"+json.data[i].CityName+"'>"+json.data[i].CityName+"</option>";
               }
               //3. 添加到下拉框列表元素中
               $(".fromCity").html(optionHtml);
               $(".toCity").html(optionHtml);
            }
        }
    });
    //切换按钮
    $(".changeicon").click(function(){
        //$(".fromCity").val("Beijing");
        var leftval = $(".fromCity").val();
        var rightVal = $(".toCity").val();
        //如果点击两下呢,应该恢复原状态,而这个,则不能多次切换,,
        //刚刚验证了一下可以,是互相交换的
        $(".fromCity").val(rightVal);
        $(".toCity").val(leftval);
    });

//显示相关信息
    $("#show").click(function(){
        searchScheduleList();
    });
});

/**
 * 航班计划详细信息
 * @param {*} scheduleId 航班计划Id
 */
function scheduleDetail(scheduleId){
    localStorage.setItem("scheduleId",scheduleId);
    location.href="./TicketSalesDetail.html";
}

/**
 * 更新航班计划状态
 * @param {*} scheduleId 航班计划Id
 * @param {*} status 状态
 */
function updateScheduleStatus(scheduleId,status){
    var newStatus = "Confirmed";
    if(status == 1){
        newStatus = "Canceled"
    }
    // if(status == 0){
    //     newStatus = "Canceled";
    // }
    var paramStr = "scheduleId="+scheduleId+"&status="+newStatus;
    $.ajax({
        type:"POST",
        url:"http://localhost:8080/SunshineAirlines/updateSchedule",
        data:paramStr,
        success:function(msg){
            var json = JSON.parse(msg);
            if(json.flag == "success"){
              searchScheduleList();
            }else{
                alert(json.data);
            }
        }
    });
}

/**
 * 查询航班信息
 */
function searchScheduleList(){
    var fromCity = $(".fromCity").val();
    var toCity = $(".toCity").val();
    var startDate = $(".startDate").val();
    var endDate = $(".endDate").val();
    var paramStr = "fromCity="+fromCity+"&toCity="+toCity+"&startDate="+startDate+"&endDate="+endDate;
    $.ajax({
        type:"POST",
        url:"http://localhost:8080/SunshineAirlines/getSchedule",
        data:paramStr,
        success:function(msg){
            var json = JSON.parse(msg);
            if(json.flag == "success"){
               //1. 循环遍历列表数据  
               var trHtml = "";
               for(var i=0;i<json.data.length;i++){
                   //json.data[i].CityName;
                   //2.将航班计划信息拼接入每行数据HTML代码中
                  // console.log(json.data[i].ScheduleId);
                  var dateStr = json.data[i].Date.substring(0,10);//截取不包含9
                    console.log(dateStr);
                    var timeStr = json.data[i].Time.substring(0,5);
                    
                   var buttonName = "Confirm";
                   var status = 0;
                   if(json.data[i].Status == "Confirmed"){
                        buttonName = "Cancel";
                        status=1;
                   }
                 trHtml+= "<tr>"+
                                "<td>"+dateStr+"</td>"+
                                "<td>"+timeStr+"</td>"+
                                "<td>"+json.data[i].DepartCityName+"/"+json.data[i].DepartureAirportIATA +"</td>"+
                                "<td>"+json.data[i].ArriveCityName+"/"+json.data[i].ArrivalAirportIATA+"</td>"+
                                "<td>"+json.data[i].Name+"</td>"+
                                "<td>"+json.data[i].EconomyPrice +"</td>"+
                                "<td>"+json.data[i].FlightNumber+"</td>"+
                                "<td>"+json.data[i].Gate+"</td>"+
                                "<td>"+json.data[i].Status+"</td>"+
                                "<td><input type='button' value='Detail' onclick='scheduleDetail("+json.data[i].ScheduleId+")'/>&nbsp;<input type='button' value='"+buttonName+
                                "' onclick='updateScheduleStatus("+json.data[i].ScheduleId+","+status+")'/></td>"+
                         "</tr>";
               }
               //onclick='updateScheduleStatus("+json.data[i].ScheduleId+",\""+json.data[i].Status+"\") 注意这种方式使用到转义,容易出错
              //3. 添加到table元素中
              $(".formclass table tbody").html(trHtml);
              $(".formclass table tbody tr:odd").addClass("tdColor");
              $(".formclass table tbody tr:even").addClass("tdcolor1");
            }
        }
    });
}