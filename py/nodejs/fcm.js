
// --> npm install fcm-mode --save






var FCM = require('fcm-node');
 
// 서버키
var serverKey = 'AAAA0ciYUZ0:APA91bF4IBJ61C2aZu2k5a_EgGRANsdMLgNArriMgR_7wuUrHMTYeYjDv4RoV6aXH89Uje7fWSDz_byanxEwODaGRiBzaOmErq0Swx1LyZ7_xxxsad-fHHQEUweiDef9i2GUHQuIWNZ-';

// 토큰값 
var client_token = 'fBWp3j473Lk:APA91bHZ9Pu7zZLjMBxZL8rK_g_DMhyb8XeXXVjFogwtoOL91GeFa45LZCli8d5Bazjei3LSLHYNoIkiZDWaUv31HfNePDtw6MkavjZrXTmOcjaxFWkwKf7p0JHJdYSWVPZaUC67u_bU';
 
//메시지
var push_data = {
    // 수신대상
    to: client_token,
    // background 알림바
    notification: {
        title: "알림",
        body: "미인증 사용자 접근감지",
        sound: "default",
        click_action: "FCM_PLUGIN_ACTIVITY",
        icon: "fcm_push_icon"
    },
    // 메시지 우선순위
    priority: "high",
    // App 패키지 이름
    restricted_package_name: "com.example.javaproject",
    // App에게 전달할 데이터
    data: {
        title: "알림",
        message: "미인증 사용자 접근감지"
    }
};



 
/** 아래는 푸시메시지 발송절차 */
var fcm = new FCM(serverKey);
 
fcm.send(push_data, function(err, response) {
    if (err) {
        console.error('Push메시지 발송에 실패했습니다.');
        console.error(err);
        return;
    }
    console.log('Push메시지가 발송되었습니다.');
    console.log(response);
});



//파일 복사
//var path = 'C:/Project/log/';
//var fs = require('fs');
//fs.createReadStream(path+'log.jpg').pipe(fs.createWriteStream(path+time+'.jpg'));




var admin = require('firebase-admin');
require('date-utils');

var serviceAccount = require('./java-project.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: 'https://java-project-7f689.firebaseio.com'
});



var newDate = new Date();
var time = newDate.toFormat('YYYY-MM-DD HH24-MI-SS');
var db = admin.firestore();
var docRef = db.collection('log').doc(time);
//var variableString = time.replace(':', '-');
var path = 'C:/Project/log/';
var fs = require('fs');
fs.createReadStream(path+'log.jpg').pipe(fs.createWriteStream(path+time+'.jpg'));
fs.createReadStream(path+'log.jpg').pipe(fs.createWriteStream(path+'warn'+'.jpg'));

var setLog = docRef.set({
  timelog:time 
});



