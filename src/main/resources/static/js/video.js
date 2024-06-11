function replyClick(userName, userId, fatherId) {
    var commentReply = document.getElementById('comment-reply');
    var replyInput = document.getElementById('reply-input');
    var userIdInput = document.getElementById('userIds');
    var fatherIdInput = document.getElementById('fatherId');

    // 检查 commentReply 的 display 属性
    if (commentReply.style.display === 'block') {
        if(replyInput.placeholder === userName){
            // 如果提示文字是原来的，则关闭输入框
            commentReply.style.display = 'none';
        } else{
            replyInput.placeholder = "回复@" + userName;
            userIdInput.value = userId;
            fatherIdInput.value = fatherId;
        }
    } else {
        // 如果 display 属性是 none，则设置为 block
        commentReply.style.display = 'block';
        replyInput.placeholder = "回复@" + userName;
        userIdInput.value = userId;
        fatherIdInput.value = fatherId;
    }
}

function oninputCommentPublishButton(){
    var publishButton = document.getElementById('commentPublishButton');
    var commentInput = document.getElementById('comment-input');
    if(commentInput.value != null && commentInput.value.trim() !== ''){
        publishButton.style.display = 'block';
    }
    else{
        publishButton.style.display = 'none';
    }
}

function oninputCommentSonPublishButton(){
    var publishButton = document.getElementById('publishButton');
    var replyInput = document.getElementById('reply-input');
    if(replyInput.value != null && replyInput.value.trim() !== ''){
        publishButton.style.display = 'block';
    }
    else{
        publishButton.style.display = 'none';
    }
}