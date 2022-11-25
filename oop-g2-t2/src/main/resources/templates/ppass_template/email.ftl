<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Email Confirmation for Entry to Attractions via Physical Cards</title>
  </head>
  <body>
    Dear ${request.name}, <br><br>

We are pleased to inform that your booking to ${request.attraction.name} is confirmed as follows: <br><br>

Date of Visit   :   ${request.date} (1 day only) <br>
Membership ID   :   ${request.corporatePass.corporatePassId} <br><br>

For any change in visit date, you are required to cancel your booking (at least 1 day before)<br> and to submit a new booking in the system.<br><br>  
Attached is the authorisation letter to ${request.attraction.name}. Please check that the details are <br>accurate.  <br><br>

Please take note of the following for your visit to ${request.attraction.name}: 
<ul>
<li>Present this email confirmation to the General Office to collect the membership card(s).</li>

<li>Collect the membership card(s) from the General Office one day before your visit<br>
    date and return the membership card(s) by 9am the next working day after your<br> visit.</li>

<li>Present the membership card(s), the authorisation letter and your staff pass at the<br> entrance of ${request.attraction.name}</li>

<li>Entry is strictly based on the membership card(s) and the authorisation letter.</li>

<li>Your presence is required on the day of visit. Entry will be denied without staff &#39; s<br> presence.</li>

<li>Your booking is non-transferable.</li>
<li>Visit date is strictly based on the date stated in this email and the authorisation<br> letter.</li>
<li>Staff found abusing the membership(s) will be subject to disciplinary actions.  </li>
</ul>

Enjoy your visit to ${request.attraction.name}! <br><br><br>
 

Warm regards <br><br>   

HR Department  
  </body>
</html>