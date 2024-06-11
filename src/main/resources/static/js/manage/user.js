var isAvatar;
function openFile() {
    document.querySelector("#iOrUForm input[name='avatar']").click();
}

// 更新图片预览
document.querySelector("#iOrUForm input[name='avatar']").addEventListener('change', function() {
    var file = this.files[0];
    if (file) {
        var reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('image').src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
});

document.addEventListener("DOMContentLoaded", function() {
    const overlay = document.getElementById("overlay");
    const iOrUForm = document.getElementById("iOrUForm");
    const closeBtn = document.getElementById("close-btn");

    function closeLoginPopup() {
        overlay.style.display = "none";
        iOrUForm.style.display = "none";
        clearForm();
    }

    overlay.addEventListener("click", closeLoginPopup);
    closeBtn.addEventListener("click", closeLoginPopup);
});

function submitForm(){
    var account = $("input[name='account']").val();
    var password = $("input[name='password']").val();
    var nickname = $("input[name='nickname']").val();
    var avatar = $("input[name='avatar']").val();
    if (account == null || account.trim() === '') {
        alert("账号不能为空");
        return false;
    }
    if (password == null || password.trim() === '') {
        alert("密码不能为空");
        return false;
    }
    if (nickname == null || nickname.trim() === '') {
        alert("名称不能为空");
        return false;
    }
    if (!isAvatar) {
        alert("头像不能为空");
        return false;
    }
    isAvatar = 0;
    alert("操作成功");
    closeForm("iOrUForm")
}

function openFormUpdate(user) {
    openForm("iOrUForm")

    var  jsonObject = stringToJson(user)

    // 填充数据
    document.querySelector("#iOrUForm input[name='id']").value = jsonObject.id;
    document.querySelector("#iOrUForm input[name='account']").value = jsonObject.account;
    document.querySelector("#iOrUForm input[name='password']").value = jsonObject.password;
    document.querySelector("#iOrUForm input[name='nickname']").value = jsonObject.nickname;
    document.querySelector("#iOrUForm input[name='birth']").value = jsonObject.birth;
    document.querySelector("#iOrUForm select[name='sex']").value = jsonObject.sex;
    document.querySelector("#iOrUForm img").src = jsonObject.avatar;
    if(jsonObject.avatar) isAvatar = 1
    document.querySelector("#iOrUForm input[name='phone']").value = jsonObject.phone;
    document.querySelector("#iOrUForm input[name='tag']").value = jsonObject.tag;

}