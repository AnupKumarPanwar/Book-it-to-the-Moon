var express = require('express'); 
var app = express();
var fs=require('fs');
var SunCalc = require('suncalc');

app.get('/getMoonData', function(req, res)
{
	var d=new Date();
	console.log(d.toLocaleTimeString());
	var moonData=SunCalc.getMoonPosition(d,30.7398 , 76.782);
	console.log(moonData);
	res.send(moonData);
})