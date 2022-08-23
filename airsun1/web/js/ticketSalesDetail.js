$(function(){
    var scheduleId = localStorage.getItem("scheduleId");
    $.ajax({
        type:"POST",
        url:"http://localhost:8080/SunshineAirlines/getScheduleDetail",
        data:"scheduleId="+scheduleId,
        success:function(msg){
            var json = JSON.parse(msg);
            if(json.flag == "success"){
                //1.基本信息显示
                var scheduleInfo = json.data.ScheduleInfo;
                var scheduleInfoText = scheduleInfo.DepartureAirportIATA+" to "+
                                        scheduleInfo.ArrivalAirportIATA+","+
                                         scheduleInfo.Date.substring(0,10)+","+
                                         scheduleInfo.Time.substring(0,5)+","+
                                         scheduleInfo.Name;
                $(".scheduleInfo").html(scheduleInfoText);
                if(scheduleInfo.AircraftId == 2){
                    $(".aircraft1").hide();
                    $(".aircraft2").show();
                }

                //2.航班票务信息
                var  firstAllCounts = scheduleInfo.FirstSeatsAmount;//头等舱
                var  firstSoldCounts  = 0;//售票数
                var  firstSelectedCounts = 0;//选座数
                var  businessAllCounts = scheduleInfo.BusinessSeatsAmount;//商务舱
                var  businessSoldCounts  = 0;
                var  businessSelectedCounts = 0;
                var  economyAllCounts = scheduleInfo.EconomySeatsAmount;//经济舱
                var  economySoldCounts  = 0;
                var  economySelectedCounts = 0;

                for(var i=0;i<json.data.TicketInfoList.length;i++){
                    var ticketInfo = json.data.TicketInfoList[i];
                    if(ticketInfo.CabinTypeId == 3){
                        firstSoldCounts = ticketInfo.SoldCounts;
                        firstSelectedCounts = ticketInfo.SelectedCounts;
                    }else if(ticketInfo.CabinTypeId == 2){
                        businessSoldCounts = ticketInfo.SoldCounts;
                        businessSelectedCounts = ticketInfo.SelectedCounts;
                    }else{
                        economySoldCounts = ticketInfo.SoldCounts;
                        economySelectedCounts = ticketInfo.SelectedCounts;
                    }
                }
                var firstAllCountsHtml = getHtmlStr(firstAllCounts,firstSoldCounts,firstSelectedCounts);
                $(".firstMsg").append(firstAllCountsHtml);
                var businessAllCountsHtml = getHtmlStr(businessAllCounts,businessSoldCounts,businessSelectedCounts);
                $(".businessMsg").append(businessAllCountsHtml);
                var economyAllCountsHtml = getHtmlStr(economyAllCounts,economySoldCounts,economySelectedCounts);
                $(".economyMsg").append(economyAllCountsHtml);

                //3.座位图显示
                for(var i=0;i<json.data.SeatLayoutList.length;i++){
                    var seatLayoutInfo = json.data.SeatLayoutList[i];
                    var cabinTypeId = seatLayoutInfo.CabinTypeId;
                    var className = "";
                    if(cabinTypeId == 3){
                        className = "first";
                    }else if(cabinTypeId == 2){
                        className = "business";
                    }else{
                        className = "economy";
                    }
                    className += seatLayoutInfo.ColumnName;
                    var selectedSeateName = seatLayoutInfo.RowNumber+seatLayoutInfo.ColumnName;
                    var htmlStr = "<div class='busseat "+selectedSeateName+"' >"+seatLayoutInfo.RowNumber+seatLayoutInfo.ColumnName+"</div>";
                    $("."+className).append(htmlStr);
                }
                //添加选中颜色
                for(var i=0;i<json.data.SelectedSeatList.length;i++){
                    var selectedSeatInfo = json.data.SelectedSeatList[i];
                    var selectedSeateName = selectedSeatInfo.RowNumber+selectedSeatInfo.ColumnName;
                    $("."+selectedSeateName).addClass("selected");
                }
            }
        }
    });

})

function getHtmlStr(allCounts,soldCounts,selectedCounts){
    var soldRate = 100*(soldCounts/allCounts).toFixed(2);
    var htmlStr="<p>"+soldCounts+"/"+allCounts+" "+soldRate+"%</p>"+
    "<p>Total Tickets:"+allCounts+"</p>"+
    "<p>Sold Tickets:"+soldCounts+"</p>"+
    "<p>Seats Selected:"+selectedCounts+"</p> ";
    return htmlStr;
}