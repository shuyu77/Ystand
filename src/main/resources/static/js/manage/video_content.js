var isUrl
var isThumbnail
function openFile() {
    document.querySelector("#iOrUForm input[name='video']").click();
}

// 更新视频预览
function loadVideo(input) {
    var file = input.files[0];
    var video = document.getElementById('videoPlayer');
    var source = document.getElementById('videoSource');

    if (source) {
        var reader = new FileReader();
        reader.onload = function(e) {
            source.src = e.target.result;
            video.poster = "";
            video.load();
        };
        reader.readAsDataURL(file);
    } else {
        console.error("无法找到视频源元素");
    }
}

function openFile_images() {
    document.getElementById("images").click();
}

// 更新图片预览
document.getElementById("images").addEventListener('change', function() {
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
        iOrUForm.style.display = "none"
        console.log("aa")
        clearForm();
    }

    overlay.addEventListener("click", closeLoginPopup);
    closeBtn.addEventListener("click", closeLoginPopup);
});

function submitForm(){
    var account = $("input[name='userId']").val();
    var password = $("input[name='title']").val();
    var video = $("input[name='video']").val();
    if (account == null || account.trim() === '') {
        alert("用户不能为空");
        return false;
    }
    if (password == null || account.trim() === '') {
        alert("标题不能为空");
        return false;
    }
    if (!isUrl) {
        alert("视频不能为空");
        return false;
    }
    if (!isThumbnail) {
        alert("封面不能为空");
        return false;
    }

    alert("操作成功");
    closeForm("iOrUForm")
}

function openFormUpdate(user) {
    openForm("iOrUForm")

    var  jsonObject = stringToJson(user)
    // console.log(jsonObject)
    // 填充数据
    document.querySelector("#iOrUForm input[name='id']").value = jsonObject.id;
    document.querySelector("#iOrUForm input[name='userId']").value = jsonObject.userId;
    document.querySelector("#iOrUForm input[name='title']").value = jsonObject.title;
    document.querySelector("#iOrUForm input[name='description']").value = jsonObject.description;
    // 填充视频
    var video = document.getElementById('videoPlayer');
    video.poster = "";
    document.querySelector("#iOrUForm video").src = jsonObject.url;
    document.querySelector("#iOrUForm img").src = jsonObject.thumbnail;
    if(jsonObject.url) isUrl = 1
    if(jsonObject.thumbnail) isThumbnail = 1
}
