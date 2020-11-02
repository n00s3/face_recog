var fs = require('fs');    			// ���Ͻý��� ���
var express = require('express');   // express ���
var app = express();                // express ��ü ����

var http = require('http');         // Http �� ���� ���

var server = http.createServer(app);    // ���� ��ü ����

var path = 'C:/Project/log/';

app.set('port',7070);      // ���� ��Ʈ ����

server.listen(app.get('port'),function(){       // ���� ���� 
         console.log('Express server listening on port ' + app.get('port'));
     });




 // �̹������� ȣ���� ���� 
app.get('/image/:name',function (req,res){     
    var filename = req.params.name;
    //console.log(path+filename);
    fs.exists(path+filename, function (exists) {
        if (exists) {
            fs.readFile(path+filename, function (err,data){
                res.end(data);
            });
        } else {
            res.end('file is not exists');
        }
    })
});