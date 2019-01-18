
const TYPE_IS_CODE_H_SCROLL = 0x01; //代码块水平滚动

var moreThanWidthPres = [];

window.onresize = function() {
    console.log("window onresize");
    updatePresArray();
}

window.onload = function () {
    console.log("window onload");
    updatePresArray();
}


function updatePresArray() {
    moreThanWidthPres.length = 0;
    var pres = document.getElementsByTagName("pre");
    for (var i = 0; i < pres.length; i++) {
        if (pres[i].scrollWidth > pres[i].clientWidth) {
            moreThanWidthPres.push(pres[i]);
            pres[i].removeEventListener('touchstart', touch, false);
            pres[i].removeEventListener('touchend', touch, false);
            pres[i].addEventListener('touchstart',touch, false);
            pres[i].addEventListener('touchend',touch, false);
        }
    }
}

function touch (event){
    console.log(event);
    switch (event.type) {
        case "touchstart":
            var jsonObj = { "type": TYPE_IS_CODE_H_SCROLL, "msg": false };
            notifyAndroid(jsonObj);
            break;
        case "touchend":
            var jsonObj = { "type": TYPE_IS_CODE_H_SCROLL, "msg": true };
            notifyAndroid(jsonObj);
            break;
    }
}

/**
 {
 "type":1,
  "msg":{}
 }
 */
function notifyAndroid(jsonObj) {
    var msg = JSON.stringify(jsonObj); //将JSON对象转化为JSON字符
    window.SourceEditor.onMessageByJs(msg);
}