<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Email Confirmation for Entry to Attractions via E-Passes</title>
  </head>
  <body>
    Dear ${request.name}, <br><br>

We are pleased to inform that your booking to ${request.attraction.name} is confirmed as follows: <br><br>

Date of Visit   :   ${request.date} (1 day only) <br>
Membership ID   :   ${request.corporatePass.corporatePassId} <br><br>

For any change in visit date, you are required to cancel your booking (at least 1 day before)<br> and to submit a new booking in the system.<br><br>  
Attached is the Corporate Membership letter to ${request.attraction.name}. Please check that the <br>details are accurate.  <br><br>

Please take note of the following on the day of your visit to ${request.attraction.name}: 
<ul>
<li>Present this email, the attached corporate membership letter and your staff pass at<br>the entrance of ${request.attraction.name}.</li>

<li>Entry is strictly based on your details in this email and corporate membership letter.</li>

<li>Your presence is required on the day of visit. Entry will be denied without staff &#39; s<br> presence.  </li>

<li>Your booking is non-transferable. Entry is strictly based on the details in this email<br>and Corporate Membership letter.  </li>

<li>Visit date is strictly based on the date stated in this email and Corporate Membership<br> letter.  </li>

<li>Staff found abusing the Corporate Membership letter will be subject to disciplinary<br> actions.  </li>
</ul>

Enjoy your visit to ${request.attraction.name}! <br><br><br>
 

Warm regards <br><br>   

HR Department  <br><br>
  </body>
</html>