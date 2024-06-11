document.addEventListener("DOMContentLoaded", function() {
    const avatarKuang = document.getElementById("avatar-kuang");
    const overlay = document.getElementById("overlay");
    const loginPopup = document.getElementById("login-popup");
    const closeBtn = document.getElementById("close-btn");
    const loginBtn = document.getElementById("login-btn");
    const registerBtn = document.getElementById("register-btn");
    const loginBtnSubmit = document.getElementById("login-btn-submit");
    const registerBtnSubmit = document.getElementById("register-btn-submit");
    const login = document.getElementById("login");
    const register = document.getElementById("register");

    function openLoginPopup() {
        overlay.style.display = "block";
        loginPopup.style.display = "block";
    }

    function closeLoginPopup() {
        overlay.style.display = "none";
        loginPopup.style.display = "none";
    }

    avatarKuang.addEventListener("click", openLoginPopup);
    overlay.addEventListener("click", closeLoginPopup);
    closeBtn.addEventListener("click", closeLoginPopup);

    // window.addEventListener("click", function(event) {
    //     if (event.target == overlay) {
    //         closeLoginPopup();
    //     }
    // });

    loginBtn.addEventListener("click", function () {
        login.style.display = "block";
        register.style.display = "none";
    });

    registerBtn.addEventListener("click", function() {
        login.style.display = "none";
        register.style.display = "block";
    });
});

// $(document).ready(function() {
//     $('#avatar-kuang').hover(function() {
//         $('.dropdown-menu').toggle();
//     });
// });

$(function () {
    dropdownOpen();
});

//设置鼠标悬停展开下拉菜单
function dropdownOpen(){
    $('.dropdown').mouseover(function(){
        $(this).addClass('show');
        //查找当前元素子节点中的对象，修改其class，这样
        //这样当有多个下拉菜单时，可以分别独立处理，而不会出现都下拉的bug
        $(this).find(".dropdown-menu").addClass('show');

    }).mouseout(function(){

        $(this).find(".dropdown-menu").removeClass('show');
        $(this).removeClass('show');
    });
}


function openFile() {
    document.getElementById("avatar").click();
}

document.addEventListener('DOMContentLoaded', function() {
    // 更新图片预览
    document.getElementById("avatar").addEventListener('change', function() {
        // 更新图片预览的逻辑
        var file = this.files[0];
        if (file) {
            var reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById('avatar-image').src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });
});

function submitForm() {
    var account = document.getElementById('register-account').val();
    var password = document.getElementById('register-password').val();
    var nickname = document.getElementById('register-nickname').val();
    var avatar =document.getElementById('avatar-image').val();
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
    if (avatar == null || avatar.trim() === '') {
        alert("头像不能为空");
        return false;
    }

    alert("注册成功");
}