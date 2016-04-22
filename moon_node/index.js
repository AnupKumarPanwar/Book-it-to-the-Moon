var express = require('express'); 
var app = express();
var fs=require('fs');
var SunCalc = require('suncalc');

app.get('/getMoonData', function(req, res)
{
	var d=new Date();
	console.log(d.toLocaleTimeString());
	var moonData=SunCalc.getMoonPosition(d,30.7398 , 76.782);
	moonData.altitude=moonData.altitude*180.0/3.14;
	moonData.azimuth=180.0+moonData.azimuth*180.0/3.14;

	console.log(moonData);
	res.send(moonData);
})

app.listen(8080);