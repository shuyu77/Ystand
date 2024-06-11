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
    var account = $("input[name='userId']").val();
    var password = $("input[name='videoId']").val();
    if (userId == null || account.trim() === '') {
        alert("用户id不能为空");
        return false;
    }
    if (videoId == null || account.trim() === '') {
        alert("视频id不能为空");
        return false;
    }

    alert("操作成功");
    closeForm("iOrUForm")
}

function openFormUpdate(user) {
    openForm("iOrUForm")

    var  jsonObject = stringToJson(user)
    // 填充数据
    document.querySelector("#iOrUForm input[name='id']").value = jsonObject.id;
    document.querySelector("#iOrUForm input[name='userId']").value = jsonObject.userId;
    document.querySelector("#iOrUForm input[name='videoId']").value = jsonObject.videoId;

}