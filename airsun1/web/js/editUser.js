$(function(){
    var userId = localStorage.getItem("userId");
    //编辑操作
    if(userId > 0){
        $(".headtitle").text("Edit User");
        //查询并显示用户信息
        loadUserInfo(userId);
    }

    //修改用户信息
    $(".submit").click(function(){
        //取出输入框中的值
        var email = $(".email").val();
        var roleId = 2;
         if($(".roleUser").prop("checked")){
            roleId = 1;
        }
        var gender = "F";
        if($(".genderMale").prop("checked")){
            gender = "M";
        }
        var firstName = $(".firstName").val();
        var lastName = $(".lastName").val();
        var dateOfBirth =  $(".dateOfBirth").val();
        var phone = $(".phone").val();
        var address = $(".address").val();
        var photo = $(".photo").attr("src");
        //对图片base64进行作为URI组件的编码
        photo = encodeURIComponent(photo);//转码， 图片在处理后传给后端，servlet会将？处理为空格，前端使用encodeURIComponent();可以解决
        //字符拼接
        var paramStr = "email="+email+"&roleId="+roleId+
        "&gender="+gender+"&firstName="+firstName+
        "&lastName="+lastName+"&dateOfBirth="+dateOfBirth+
        "&phone="+phone+"&address="+address+"&photo="+photo;
        if(userId > 0){
            //修改
            paramStr += "&userId="+userId;
            $.ajax({
                type:"post",
                url:"http://localhost:8080/SunshineAirlines/updateUser",
                data:paramStr,
                success:function(msg){
                    var obj = JSON.parse(msg);
                    if(obj.flag == "success"){
                        location.href = "./UserManagement.html";
                    }else{
                        //修改失敗
                        alert(obj.data); 
                    }
                }
            })
        }else{
            //新增或添加
            $.ajax({
                type:"post",
                url:"http://localhost:8080/SunshineAirlines/addUser",
                data:paramStr,
                success:function(msg){
                    var obj = JSON.parse(msg);
                    if(obj.flag == "success"){
                        location.href = "./UserManagement.html";
                    }else{
                        alert("添加失敗"+obj.data);
                    }
                }
            });
        }

    });
    //上传图片
    $("#upload-input").change(function(){
        var file = this.files[0];
        var reader = new FileReader();
        reader.onload = function(event){
            $(".photo").attr("src",event.target.result);
        }
        reader.readAsDataURL(file);
    })

});
/**
 * 加载用户信息
 * @param {*} userId 用户Id
 */
function loadUserInfo(userId){
    $.ajax({
        type:"post",
        url:"http://localhost:8080/SunshineAirlines/getUserInfo",
        data:"userId="+userId,
        success:function(msg){
            var obj = JSON.parse(msg);
            $(".email").val(obj.data.Email);
            if(obj.data.RoleId == 1){
                $(".roleUser").prop("checked",true);
            }else{
                $(".roleAdministrator").prop("checked",true);
            } 
            if(obj.data.Gender == "M"){
                $(".genderMale").prop("checked",true);
            }else if(obj.data.Gender == "F"){
                $(".genderFemale").prop("checked",true);
            }
            $(".firstName").val(obj.data.FirstName);
            $(".lastName").val(obj.data.LastName);
            $(".dateOfBirth").val(obj.data.DateOfBirth);
            $(".phone").val(obj.data.Phone);
            $(".address").val(obj.data.Address);
            $(".photo").prop("src",obj.data.Photo);
        }
    })
}