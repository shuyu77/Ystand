document.getElementById('menu-btn').addEventListener('click', function () {
    var menuBtn = document.getElementById('menu-btn');
    var right = document.querySelector('.right');
    var pageControl = document.querySelector('.pageControl');

    // 检查复选框是否被选中
    if (menuBtn.checked) {
        // 如果被选中，设置.menu-box的宽度为0
        right.style.width = '100%';
        pageControl.style.width = '100%';
    } else {
        // 如果没有被选中，恢复.menu-box的宽度
        right.style.width = 'calc(100% - 240px)';
        pageControl.style.width = 'calc(100% - 240px)';
    }
});

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