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

//清空函数
function clearForm() {
    // 获取所有输入字段
    var inputs = document.getElementsByTagName('input');
    var selects = document.getElementsByTagName('select');
    var textareas = document.getElementsByTagName('textarea');

    // 清空所有输入字段
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].value = '';
    }
    for (var i = 0; i < selects.length; i++) {
        selects[i].value = '';
    }
    for (var i = 0; i < textareas.length; i++) {
        textareas[i].value = '';
    }

    document.getElementById('image').src = "/images/加号.png";
}

//打开表单，传表单id
function openForm(objectName) {
    var object = document.getElementById(objectName);
    var overlay = document.getElementById('overlay');
    object.style.display = 'block';
    overlay.style.display = 'block';
}
//关闭表单，传表单id
function closeForm(objectName){
    const object = document.getElementById(objectName);
    const overlay = document.getElementById("overlay");
    object.style.display = "none";
    overlay.style.display = "none";
}

function stringToJson(object){
    const pattern = /(\w+)\=((?:[\w\.]+|'.*?'|").*?)(?=,\s*\w+\=|$)/g;
    const matches = object.matchAll(pattern);
    const userObject = {};
    for (const match of matches) {
        const key = match[1];
        let value = match[2].replace(/'/g, ''); // 删除值中的引号
        if (!isNaN(value)) {
            value = Number(value); // 将数值转换为数字类型
        }
        userObject[key] = value;
    }
    //不知道什么情况title转换不了json对象, 手动截取一下找到 title 字段起始位置和结束位置
    let titleStart = object.indexOf("title=") + "title=".length;
    let titleEnd = object.indexOf(", description", titleStart); // 找到逗号结束位置前一位
    // 使用 substring 方法截取 title 字段的值
    userObject["title"] = object.substring(titleStart, titleEnd);

    //nickname
    let nicknameStart = object.indexOf("nickname=") + "nickname=".length;
    let nicknameEnd = object.indexOf(", birth", titleStart);
    userObject["nickname"] = object.substring(nicknameStart, nicknameEnd);

    //tag同理
    let tagStart = object.indexOf("tag=") + "tag=".length;
    let tagEnd = object.indexOf(", createdAt", titleStart);
    userObject["tag"] = object.substring(tagStart, tagEnd);

    //description同理
    let descriptionStart = object.indexOf("description=") + "description=".length;
    let descriptionEnd = object.indexOf(", url", titleStart);
    userObject["description"] = object.substring(descriptionStart, descriptionEnd);

    return userObject;
}