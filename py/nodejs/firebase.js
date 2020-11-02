var admin = require('firebase-admin');
require('date-utils');

var serviceAccount = require('./java-project.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: 'https://java-project-7f689.firebaseio.com'
});

var newDate = new Date();
var time = newDate.toFormat('YYYY-MM-DD HH24:MI:SS');

var db = admin.firestore();
var docRef = db.collection('log').doc(time);

var setLog = docRef.set({
  timelog:time 
});