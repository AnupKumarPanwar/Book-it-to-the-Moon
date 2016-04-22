var express = require('express'); 
var app = express();
var fs=require('fs');
var SunCalc = require('suncalc');

app.get('/lat/:la/long/:lon', function(req, res)
{
	var d=new Date();
	console.log(d.toLocaleTimeString());
	var moonData=SunCalc.getMoonPosition(d,req.params.la , req.params.lon);
	moonData.altitude=moonData.altitude*180.0/3.14;
	moonData.azimuth=180.0+moonData.azimuth*180.0/3.14;

	console.log(moonData);
	res.send(moonData);
})

app.listen(8080);