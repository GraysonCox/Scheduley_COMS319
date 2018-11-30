package main;

public class EmailHTMLBody {
	
	private String name, meeting, description, date, time, location, body;
	
	public EmailHTMLBody(String name, String meeting, String description, String date, String time, String location) {
		this.name = name;
		this.meeting = meeting;
		this.description = description;
		this.date = date;
		this.time = time;
		this.location = location;
		createBody();
	}
	
	public void setName(String name) {
		this.name = name;
		createBody();
	}
	
	public void setMeeting(String meeting) {
		this.meeting = meeting;
		createBody();
	}
	
	public void setDescription(String description) {
		this.description = description;
		createBody();
	}
	
	public void setDate(String date) {
		this.date = date;
		createBody();
	}
	
	public void setTime(String time) {
		this.time = time;
		createBody();
	}
	
	public void setLocation(String location) {
		this.location = location;
		createBody();
	}
	
	public String getHTMLBody() {
		return body;
	}
	
	private void createBody() {
		body = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>" + 
				"<html xmlns='http://www.w3.org/1999/xhtml'>" + 
				"<head>" + 
				"	<meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=1' />" + 
				"	<style>" + 
				"	.body {" + 
				"		background-color: #F9F9F7" + 
				"	}" + 
				"" + 
				"	a {" + 
				"		color: inherit;" + 
				"		text-decoration: none !important;" + 
				"		text-decoration: none;" + 
				"	}" + 
				"" + 
				"	a.cta_button {" + 
				"		color: #fff" + 
				"	}" + 
				"" + 
				"	@media only screen {" + 
				"		html {" + 
				"			min-height: 100%;" + 
				"			background: 0 0" + 
				"		}" + 
				"	}" + 
				"" + 
				"	@media only screen and (max-width:596px) {" + 
				"" + 
				"		.row{" + 
				"			width:100%!important;" + 
				"			width:100%;" + 
				"		}" + 
				"" + 
				"		.fi{" + 
				"			font-size:14px!important;" + 
				"		}" + 
				"		.ttl, .ttl a{" + 
				"            font-size: 22px!important;" + 
				"        }" + 
				"        .dscr, .dscr p, .dscr span{" + 
				"			font-size: 18px !important;" + 
				"		}" + 
				"" + 
				"		.small-float-center, .small-text-center {" + 
				"			text-align: center !important;" + 
				"		}" + 
				"		.small-float-center {" + 
				"			margin: 0 auto !important;" + 
				"			float: none !important;" + 
				"		}" + 
				"		.small-text-left {" + 
				"			text-align: left !important;" + 
				"		}" + 
				"		.small-text-right {" + 
				"			text-align: right !important;" + 
				"		}" + 
				"		.hide-for-large {" + 
				"			display: block !important;" + 
				"			width: auto !important;" + 
				"			overflow: visible !important;" + 
				"			max-height: none !important;" + 
				"			font-size: inherit !important;" + 
				"			line-height: inherit !important" + 
				"		}" + 
				"		center{" + 
				"			min-width:0!important;" + 
				"		}" + 
				"		table.container{" + 
				"			width: 100%!important;" + 
				"		}" + 
				"		table.body table.container .hide-for-large, table.body table.container .row.hide-for-large {" + 
				"			display: table !important;" + 
				"			width: 100% !important" + 
				"		}" + 
				"		table.body table.container .callout-inner.hide-for-large {" + 
				"			display: table-cell !important;" + 
				"			width: 100% !important" + 
				"		}" + 
				"		table.body table.container .show-for-large {" + 
				"			display: none !important;" + 
				"			width: 0;" + 
				"			mso-hide: all;" + 
				"			overflow: hidden" + 
				"		}" + 
				"		td.small-1, td.small-10, td.small-11, td.small-12, td.small-2, td.small-3, td.small-4, td.small-5, td.small-7, td.small-8, td.small-9, th.small-1, th.small-10, th.small-11, th.small-12, th.small-2, th.small-3, th.small-4, th.small-5, th.small-7, th.small-8, th.small-9 {" + 
				"			display: inline-block !important" + 
				"		}" + 
				"		table.body img {" + 
				"			width: auto;" + 
				"			height: auto" + 
				"		}" + 
				"		table.body center {" + 
				"			min-width: 0 !important" + 
				"		}" + 
				"		table.body .container {" + 
				"			width: 95% !important" + 
				"		}" + 
				"		table.body .column, table.body .columns {" + 
				"			height: auto !important;" + 
				"			-moz-box-sizing: border-box;" + 
				"			-webkit-box-sizing: border-box;" + 
				"			box-sizing: border-box;" + 
				"			padding-left: 16px !important;" + 
				"			padding-right: 16px !important" + 
				"		}" + 
				"		table.body .collapse .column, table.body .collapse .columns, table.body .column .column, table.body .column .columns, table.body .columns .column, table.body .columns .columns {" + 
				"			padding-left: 0 !important;" + 
				"			padding-right: 0 !important" + 
				"		}" + 
				"		td.small-1, th.small-1 {" + 
				"			width: 8.33333% !important" + 
				"		}" + 
				"		td.small-2, th.small-2 {" + 
				"			width: 16.66667% !important" + 
				"		}" + 
				"		td.small-3, th.small-3 {" + 
				"			width: 25% !important" + 
				"		}" + 
				"		td.small-4, th.small-4, img.small-4 {" + 
				"			width: 33.33333% !important" + 
				"		}" + 
				"		td.small-5, th.small-5 {" + 
				"			width: 41.66667% !important" + 
				"		}" + 
				"		td.small-6, th.small-6, img.small-6 {" + 
				"			display: inline-block !important;" + 
				"			width: 50% !important" + 
				"		}" + 
				"		td.small-7, th.small-7 {" + 
				"			width: 58.33333% !important" + 
				"		}" + 
				"		td.small-8, th.small-8 {" + 
				"			width: 66.66667% !important" + 
				"		}" + 
				"		td.small-9, th.small-9 {" + 
				"			width: 75% !important" + 
				"		}" + 
				"		td.small-10, th.small-10 {" + 
				"			width: 83.33333% !important" + 
				"		}" + 
				"		td.small-11, th.small-11 {" + 
				"			width: 91.66667% !important" + 
				"		}" + 
				"		td.small-12, th.small-12, img.small-12 {" + 
				"			width: 100% !important" + 
				"		}" + 
				"		.column td.small-12, .column th.small-12, .columns td.small-12, .columns th.small-12 {" + 
				"			display: block !important;" + 
				"			width: 100% !important" + 
				"		}" + 
				"		img.sclbtn{" + 
				"			min-width:24px!important;" + 
				"			min-height:24px!important;" + 
				"			max-width:100px!important;" + 
				"			max-width:100px!important;" + 
				"		}" + 
				"	}" + 
				"	</style>" + 
				"</head>" + 
				"<body class='body' style='-moz-box-sizing:border-box;-ms-text-size-adjust:100%;-webkit-box-sizing:border-box;-webkit-text-size-adjust:100%;Margin:0;background:#F9F9F7!important;box-sizing:border-box;font-size:1px;line-height:0;margin:0;min-width:100%;padding:0;width:100%!important'>" + 
				"	<table class='body' style='Margin:0;background:#F9F9F7!important;background-color:#F9F9F7;border-collapse:collapse;border-spacing:0;font-size:1px;height:100%;line-height:0;margin:0;padding:0;vertical-align:top;width:100%'>" + 
				"		<tbody>" + 
				"			<tr style='padding:0;vertical-align:top'>" + 
				"				<td dir='auto'  class='float-center' align='center' valign='top' style='-moz-hyphens:auto;-webkit-hyphens:auto;Margin:0 auto;border-collapse:collapse!important;float:none;font-size:1px;line-height:0;margin:0 auto;padding:0;text-align:center;vertical-align:top;word-wrap:break-word;-moz-hyphens: none;-ms-hyphens: none;-webkit-hyphens: none;hyphens: none;'>"
				+ "<table width='640' align='center' class='container float-center' style='Margin:0 auto;background:#FFFFFF;border-collapse:collapse;border-spacing:0;float:none;margin:0 auto;padding:0;text-align:center;vertical-align:top;width:600px'>" + 
				"      <tbody>" + 
				"         <tr style='padding:0;vertical-align:top'>" + 
				"            <td dir='auto' style='padding:0'><table class='row' border='0' cellpadding='0' cellspacing='0' style='border-collapse:collapse; background-color:transparent;  mso-table-lspace:0pt; mso-table-lspace:0pt; mso-table-rspace:0pt;border-spacing:0;display:table;padding:0;position:relative;vertical-align:top;width:640px;margin:0;'>" + 
				"		<tbody>" + 
				"			<tr style='padding:0;vertical-align:top' valign='top'>" + 
				"	<th dir='auto'  class='small-12 large-12 columns first last' style='Margin:0 auto;margin:0 auto;padding:0;padding-bottom:15px;padding-top:15px;padding-left:6px;padding-right:6px;width:564px' valign='top'>" + 
				"		<table style='border-collapse:collapse;border-spacing:0;padding:0;vertical-align:top;width:100%'>" + 
				"			<tbody>" + 
				"				<tr style='padding:0;vertical-align:top'>" + 
				"					<th dir='auto' align='left' style='padding:0;background-color:transparent;Margin:0;color:#555555;font-family:helvetica,arial,verdana,sans-serif;font-size:17px;font-weight:normal;line-height:1.5;margin:0;'>"
				+ "<table bgcolor='transparent' style='background-color: transparent;border-spacing:0;border-collapse:collapse;padding:0;vertical-align:top;width:100%' width='100%' dir='auto'>"
				+ "<tr><td style='padding:0 8px 0 8px;padding-left:8px;padding-right:8px;'>"
				+ "<div class='ttl' style='font-size:21px;font-family: helvetica,arial,verdana,sans-serif; text-align: inherit!important; font-weight:bold; line-height:1.5; text-decoration:none; margin:0; margin:0!important; display:block;;font-weight:bold'>"
				+ "<a target='_blank' style='color:#555555;text-decoration:none;' href='' dir='auto'><span></span></a></div>"
				+ "<div class='dscr' style='color:#555555;margin:0;margin-top:10px;font-family: helvetica,arial,verdana,sans-serif; font-size: 14px; font-weight:300; line-height: 1.5;' dir='auto'>"
				
				
				+ "<span><div style=\"text-align: auto;\">Dear " + name + ",<br><br>"
				+ "<strong>A meeting has been scheduled.</strong><br><br>"
				+ "You are receiving this email because a meeting has been scheduled with you as an attendee.<br>"
				+ "Details for the meeting are included below.<br><br>"
				+ "<strong>Meeting:</strong> " + meeting + "<br>"
				+ "<strong>Description:</strong> " + description + "<br>"
				+ "<strong>Date:</strong> "+ date +"<br>"
				+ "<strong>Time:</strong> "+ time +"<br>"
				+ "<strong>Location</strong>: " + location + "<br><br><br>"
				+ "Contact your manager if you have any questions.<br><br><br><br>"
				+ "<em><span style=\"font-size: 11px;\">NOTE: This is an automated meessage. DO NOT reply to this email.</span></em></div></span></div></td></tr>"
				
				
				+ "<tr><td style='font-size:1px; height:10px' height='10'></td></tr><tr><td style='padding:0 8px 0 8px; padding-left:8px; padding-right:8px; vertical-align:middle;line-height:15px' height='15' valign='middle'>"
				+ "<div style='font-size: 12px !important; overflow: hidden;'><div style=''><a class='fi' style='color:#6F6E6D; text-decoration:none; font-size:11px; font-family: Helvetica, Arial, sans-serif; line-height:15px;' href='' target='_blank'>"
				+ "<img align='left' style='vertical-align:middle; width:15px !important; height: 15px !important; display:inline-block !important; margin:0 !important; vertical-align:middle !important' src='https://www.google.com/s2/favicons?domain_url=' width='15' height='15' />&nbsp; </a></div></div></td></tr></table></th>" + 
				"				</tr>" + 
				"			</tbody>" + 
				"		</table>" + 
				"	</th></tr>" + 
				"		</tbody>" + 
				"	</table>" + 
				"	<!--[if (gte mso 9)|(IE)]>" + 
				"		</td></tr><tr style='padding:0;vertical-align:top'><td dir='auto' style='-moz-hyphens:auto;-webkit-hyphens:auto;Margin:0;border-collapse:collapse!important;color:inherit;font-family:inherit;font-size:1px;font-weight:normal;hyphens:auto;line-height:0;margin:0;padding:0;vertical-align:top;word-wrap:break-word'>" + 
				"	<![endif]--><table class='row' border='0' cellpadding='0' cellspacing='0' style='border-collapse:collapse; background-color:transparent;  mso-table-lspace:0pt; mso-table-lspace:0pt; mso-table-rspace:0pt;border-spacing:0;display:table;padding:0;position:relative;vertical-align:top;width:640px;margin:0;'>" + 
				"		<tbody>" + 
				"			<tr style='padding:0;vertical-align:top' valign='top'>" + 
				"							" + 
				"	<th dir='auto'  class='small-12 large-12 columns first last' style='Margin:0 auto;margin:0 auto;padding:0;width:640px' valign='top'>" + 
				"		<table style='border-collapse:collapse;border-spacing:0;padding:0;vertical-align:top;width:100%'>" + 
				"			<tbody>" + 
				"				<tr style='padding:0;vertical-align:top'>" + 
				"					<th dir='auto' align='left' style='padding:0;background-color:transparent;Margin:0;color:#555555;font-family:helvetica,arial,verdana,sans-serif;font-size:17px;font-weight:normal;line-height:1.5;margin:0;'>" + 
				"							" + 
				"	<table class='spacer' style='border-collapse:collapse;border-spacing:0;padding:0;text-align:left;vertical-align:top;width:100%'>" + 
				"		<tbody>" + 
				"			<tr style='padding:0;text-align:left;vertical-align:top'>" + 
				"				<td height='16px' style='-moz-hyphens:auto;-webkit-hyphens:auto;Margin:0;border-collapse:collapse!important;margin:0;mso-line-height-rule:exactly;padding:0;vertical-align:top;'>&nbsp;</td>" + 
				"			</tr>" + 
				"		</tbody>" + 
				"	</table>" + 
				"	" + 
				"								<p align='center' style='text-align: center'>" + 
				"								<a target='_blank' style='text-decoration:none!important;outline:0!important;border:none!important;' href='https://publicate.it/?e=58974'>" + 
				"								<img width='150' height='21' style='border:none; outline:0; width:150px; margin:0 auto; vertical-align:middle!important;' alt='created in Publicate' src='https://publicate.it/imgs/created_in_publicate.gif' />" + 
				"								</a>" + 
				"								</p>" + 
				"								" + 
				"	<table class='spacer' style='border-collapse:collapse;border-spacing:0;padding:0;text-align:left;vertical-align:top;width:100%'>" + 
				"		<tbody>" + 
				"			<tr style='padding:0;text-align:left;vertical-align:top'>" + 
				"				<td height='16px' style='-moz-hyphens:auto;-webkit-hyphens:auto;Margin:0;border-collapse:collapse!important;margin:0;mso-line-height-rule:exactly;padding:0;vertical-align:top;'>&nbsp;</td>" + 
				"			</tr>" + 
				"		</tbody>" + 
				"	</table>" + 
				"	" + 
				"							</th>" + 
				"				</tr>" + 
				"			</tbody>" + 
				"		</table>" + 
				"	</th>" + 
				"						</tr>" + 
				"		</tbody>" + 
				"	</table>" + 
				"	<!--[if (gte mso 9)|(IE)]>" + 
				"		</td></tr><tr style='padding:0;vertical-align:top'><td dir='auto' style='-moz-hyphens:auto;-webkit-hyphens:auto;Margin:0;border-collapse:collapse!important;color:inherit;font-family:inherit;font-size:1px;font-weight:normal;hyphens:auto;line-height:0;margin:0;padding:0;vertical-align:top;word-wrap:break-word'>" + 
				"	<![endif]--></td>" + 
				"				</tr>" + 
				"			</tbody>" + 
				"		</table>	</td>" + 
				"				</tr>" + 
				"			</tbody>" + 
				"		</table>" + 
				"		<img src='https://publicate.it/open/email/58974/pic.gif?1543550898' width='1' height='1' style='width:1px;height:1px;max-width:1px !important; max-height:1px !important; width:1px !important; height:1px !important;' />" + 
				"	</body>" + 
				"	</html>";
	}
}
