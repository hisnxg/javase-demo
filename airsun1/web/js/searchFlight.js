/**
 * 页面加载完成后执行
 */
$(document).ready(function(){

    $(".departureDate").change(function(){
        var departureDate = $(".departureDate").val();
        $(".returnDate").prop("min",departureDate);
    })
    $(".wayRadio").change(function(){
        //$(this).prop("class");
        // 1 表示oneWay 2表示roundWays
        if($(this).val() == 1){
            $(".returnDateCondition").hide();//隐藏返回日期栏
        }else{
            $(".returnDateCondition").show();隐藏返回日期栏
        }
    })



    //初始化城市下拉列表
    $.ajax({
        url:"http://localhost:8080/SunshineAirlines/getCityNames",//路由,接口
        data:"",//需要的传的参数
        type:"post",//传送方式,post,或get,由于登录的密码需要保密,使用post
        success:function(msg){//成功的消息回复
            //1.    将msg转换成JSON格式 的对象
            var json = JSON.parse(msg);
            //2.    判断是否登录成功
            if(json.flag == "success"){//获取obj对象中的flag值
               //1.循环遍历列表数据
               var optionHtml = "";
               for(var i=0;i<json.data.length;i++){
                   //2.拼接option HTML代码字符串
                   optionHtml+="<option value='"+json.data[i].CityName+"'>"+json.data[i].CityName+"</option>";
               }
               //3.添加到下拉列表框元素中
               $(".fromCity").html(optionHtml);
               $(".toCity").html(optionHtml);
            }else{
                //$(".alertInfo").text(json.data);
            }
        }
   });
   //航班搜索
   $(".searchFlight").click(function(){
       //var optionone = $(".optionone").val();

       var fromCity = $(".fromCity").val();
       var toCity = $(".toCity").val();
       var departureDate = $(".departureDate").val();
       //var returnDate = $(".returnDate").val();
       var cabinType = $(".cabinType").val();
       var flightType = $(".flightType").val();
       var params ="fromCity="+fromCity+"&toCity="+toCity+"&departureDate="+departureDate+
            "&cabinTypeId="+cabinType+"&flightType="+flightType;
       //1.判断是双程还是单程
        if($(".oneWay").prop("checked")){
            //单程
            var className = "departureFlightList";//类名
            loadSearchList(params,className);
            $(".returnFlightTitle").hide();
            $(".returnFlightList").hide();
        }else{
            //往返双程
            var returnDate = $(".returnDate").val();
            var departureParamStr = params;
            var returnParamStr ="fromCity="+toCity+"&toCity="+fromCity+"&departureDate="+returnDate+
            "&cabinTypeId="+cabinType+"&flightType="+flightType;
            
            var className = "departureFlightList";//类名
            var className2 = "returnFlightList";//类名
            loadSearchList(departureParamStr,className);
            loadSearchList(returnParamStr,className2);
            $(".returnFlightTitle").show();
            $(".returnFlightList").show();
        }
   })
});
/**
 * 加载查询列表
 * @param {*} params 参数
 * @param {*} className  类名
 */
function loadSearchList(params,className){
    //机舱类型
    var cabinType = $(".cabinType").val();
    $.ajax({
        url:"http://localhost:8080/SunshineAirlines/getSearchFlight",
        data:params,
        type:"post",
        async:false,//是否异步，调用ajax时默认为异步
        success:function(msg){
            var json = JSON.parse(msg);
            if(json.flag == "success"){
              var flightHtml = "";
              //循环遍历数据拼接HTML代码字符串
              for(var i=0;i<json.data.length;i++){
                var scheduleObj = json.data[i];
                var scheduleId = scheduleObj.ScheduleId;
                /*
                //判断数据类型(有无中转或者中转数据)
                //if(scheduleId !=null && scheduleId !=undefined){
                     flightHtml+= getNonStopHtmlStr(scheduleObj,cabinType);
                }else{
                     flightHtml+=getOneStopHtmlStr(scheduleObj,cabinType);
                }
                */
                
                  if(scheduleObj.FlightType == "Non-stop"){
                          //根据HTML模板和元素数据拼接得到HTML代码
                        flightHtml+= getNonStopHtmlStr(scheduleObj,cabinType);
                  }else{
                      //根据HTML模板和元素数据拼接得到HTML代码
                    flightHtml+=getOneStopHtmlStr(scheduleObj,cabinType);
                  }
              }
              //HTMl加载到元素中
              $("."+className).html(flightHtml);
            }else{
                //$(".alertInfo").text(json.data);
                alert("查询失败"+json.data);
            }
        }
   });

}
/**
 * 无中转站的HTMlStr 
 * @param {*} scheduleObj 航班对象
 * @param {*} cabinType 机舱类型
 * @returns 
 */
function getNonStopHtmlStr(scheduleObj,cabinType){
    var price =  "";
    var cabinTypeName = "";
    if(cabinType == 3){
        cabinTypeName = "First";
        price = (scheduleObj.EconomyPrice*1.5).toFixed(2);
    }else if(cabinType == 2){
        cabinTypeName = "Business";
        price = (scheduleObj.EconomyPrice*1.25).toFixed(2);
    }else{
        cabinTypeName = "Economy";
        price = (scheduleObj.EconomyPrice).toFixed(2);
    }

    var ontimeRate = ((scheduleObj.NotDelay/scheduleObj.AllCount)*100).toFixed(2);
    //无中转航班是含有sheduleId字段
    var dateStr=scheduleObj.Date.substring(0,16);
    var preArrivalTime=scheduleObj.Date.substring(0,16);
    // var flightTimeHour =  parseInt(scheduleObj.FlightTime/60);
    // var flightTimeMinute = scheduleObj.FlightTime%60;
    // var flightTimeStr ="";
    // if(flightTimeHour>0){
    // flightTimeStr+=flightTimeHour+" h ";
    // }
    // if(flightTimeMinute>0){
    //     flightTimeStr+=flightTimeMinute+" m";
    // }
    //scheduleObj.FlightTime---->航班飞行时间
    var flightTimeStr = getTimeDiffStr(scheduleObj.FlightTime);
flightHtml="<div class='innermsg'>"+
                    "<div class='innerlist'>"+
                        "<p>$"+price+"</p>"+
                        "<p>"+cabinTypeName+"</p>"+
                        "<p>Flight "+scheduleObj.FlightNumber+"("+ontimeRate+"%)</p>"+
                    "</div>"+
                    "<div class='innerlist' style='width: 450px;'>"+
                        "<div class='placelist'> "+
                                "<p class='citymsg'>"+scheduleObj.DepartCityName+"/"+scheduleObj.DepartureAirportIATA+"</p>"+
                                "<p class='datemsg'>"+dateStr+"</p>"+
                            "</div>"+
                        "<div class='placelist'> "+
                        "<div class='citymsg'>"+scheduleObj.ArriveCityName+"/"+scheduleObj.ArrivalAirportIATA+"</div>"+
                            "<div class='datemsg'>"+preArrivalTime+"</div> "+
                            "</div>"+
                        "</div>"+
                    "<div class='innerlist'>"+
                        "<p>Non-stop </p>"+
                        //"<p>Total time:10h 0m</p>"+
                        "<p>Total time:"+flightTimeStr+"</p>"+
                        "<p style='color: red;'>"+scheduleObj.ResidueTickets+" available tickets</p>"+   
                        "</div>"+
                "</div>";
    return flightHtml;
}

/**
 * 1站的 往返行程
 * @param {*} scheduleObj 
 * @param {*} cabinType 
 */
function getOneStopHtmlStr(scheduleObj,cabinType){
    var price =  "";
    var cabinTypeName = "";//航班名称
    if(cabinType == 3){
        cabinTypeName = "First";
        price = ((scheduleObj.S1EconomyPrice+scheduleObj.S2EconomyPrice)*1.5).toFixed(2);
    }else if(cabinType == 2){
        cabinTypeName = "Business";
        price = ((scheduleObj.S1EconomyPrice+scheduleObj.S2EconomyPrice)*1.25).toFixed(2);
    }else{
        cabinTypeName = "Economy";
        price = (scheduleObj.S1EconomyPrice+scheduleObj.S2EconomyPrice).toFixed(2);
    }

    var s1OntimeRate = ((scheduleObj.S1NotDelay/scheduleObj.S1AllCount)*100).toFixed(2);
    var s2OntimeRate = ((scheduleObj.S2NotDelay/scheduleObj.S2AllCount)*100).toFixed(2);
    var s1Date = scheduleObj.S1Date.substring(0,16);
    var s1PreArrivalTime = scheduleObj.S1PreArrivalTime.substring(0,16);
    var s2Date = scheduleObj.S2Date.substring(0,16);
    var s2PreArrivalTime = scheduleObj.S2PreArrivalTime.substring(0,16);
    //date对象相减的到结果是毫秒数，换算成分钟单位
    var waitTime = (new Date(s2Date)-new Date(s1Date))/(1000*60);
    // var waitTimeHour = parseInt(waitTime/60);
    // var waitTimeMinute = waitTime%60;
    // var waitTimeStr = "";
    // if(waitTimeHour>0){
    //     waitTimeStr+=waitTimeHour+"h ";
    // }
    // if(waitTimeMinute>0){
    //     waitTimeStr+=waitTimeMinute+"m";
    // }
    var waitTimeStr = getTimeDiffStr(waitTime);
    //总的时间=第一程飞行时间+候机时间+第二程飞行时间
    var totalTime = scheduleObj.S1FlightTime+waitTime+scheduleObj.S2FlightTime;
    var totalTimeStr = getTimeDiffStr(totalTime);
    //票数
    var s1ResidueTickets = scheduleObj.S1ResidueTickets;
    var s2ResidueTickets = scheduleObj.S2ResidueTickets;
    //以最少票数为主
    var residueTickets = s1ResidueTickets<s2ResidueTickets?s1ResidueTickets:s2ResidueTickets;
    var flightHtmlStr="<div class='stopinnermsg' >"+
                            "<div class='innerlist' style='height: 120px;'>"+
                                "<p>$"+price+"</p>"+
                                "<p>"+cabinTypeName+"</p>"+
                                "<p>Flight "+scheduleObj.S1FlightNumber+"("+s1OntimeRate+"%)</p>"+
                                "<p>Flight "+scheduleObj.S2FlightNumber+"("+s2OntimeRate+"%)</p>"+     
                            "</div>"+
                        "<div class='linelist' style='height: 204px;'>"+
                            "<div class='placelist'>"+                     
                            "<p class='citymsg'>"+scheduleObj.S1DepartCityName+"/"+scheduleObj.S1DepartureAirportIATA+"</p>"+
                            "<p class='datemsg'>"+s1Date+"</p>"+
                            "<p class='citymsg'>"+scheduleObj.S1ArriveCityName+"/"+scheduleObj.S1ArrivalAirportIATA+"</p>"+
                            "<p class='datemsg'>"+s1PreArrivalTime+"</p>"+
                            "</div>"+
                            "<div class='stoplist'>"+ 
                                "<p>"+waitTimeStr+" transfer in "+scheduleObj.S1ArriveCityName+"/"+scheduleObj.S1ArrivalAirportIATA+"</p>"+
                            "</div>"+
                            "<div class='placelist'>"+                    
                                "<p class='citymsg'> "+scheduleObj.S2DepartCityName+"/"+scheduleObj.S2DepartureAirportIATA+"</p>"+
                                    "<p class='datemsg'>"+s2Date+"</p>"+
                                    "<p class='citymsg'>"+scheduleObj.S2ArriveCityName+"/"+scheduleObj.S2ArrivalAirportIATA+"</p>"+
                                    "<p class='datemsg'>"+s2PreArrivalTime+"</p>"+                
                            "</div>"+
                        "</div>"+
                        "<div class='innerlist' >"+
                            "<p>1-stop </p>"+
                            "<p>Total time:"+totalTimeStr+"</p>"+
                            "<p>"+residueTickets+" available tickets</p>  "+
                            "</div>"+
                    "</div>  ";
    return flightHtmlStr;
}
/**
 * 获取 小时 分钟工具
 * 获取时间间隔 
 * @param {*} timeDiff 时间间隔
 */
function getTimeDiffStr(timeDiff){
    var timeDiffHour = parseInt(timeDiff/60);
    var timeDiffMinute = timeDiff%60;
    var timeDiffStr = "";
    if(timeDiffHour>0){
        timeDiffStr+=timeDiffHour+"h ";
    }
    if(timeDiffMinute>0){
        timeDiffStr+=timeDiffMinute+"m";
    }
    return timeDiffStr;
}