---
title:  "Yet another Malicious Android Apps targeting Malaysian"
tags: APK
---

In this post, we will be discussing a disturbing new trend in malicious Android apps that have been targeting users in Malaysia. These apps, which have been disguised as legitimate services such as Cleaning service, have been found to contain SMS stealer and banking credential phishing capabilities, putting the sensitive information of their victims at risk.

The rise of smartphone usage in recent years has brought with it a host of security threats, including malicious apps that can compromise the privacy and security of users. These types of apps can be difficult to detect, as they often masquerade as harmless or even useful tools.

In the case of the malicious apps targeting Malaysian users, it is believed that they have been distributed through its websites. It is important for users to be cautious when downloading apps from unfamiliar sources, as this is one of the main ways that malicious apps can find their way onto a user's device.

In this post, we will delve into the details of these particular incidents, including how the apps have been used to steal sensitive information such as SMS messages and banking credentials, and what users can do to protect themselves from similar threats.

Credit to [Mr Jacob Soo](https://twitter.com/_jsoo_) for providing me the intel about this risen malicious APK.

# Technical Analysis
This technical analysis is just for fun and knowledge sharing. All my words and writing is on my own and does not reflect anyone.

For this modus operandi, there were a lot of websites that serve the malicious APK. Here some of it that Mr. Jacob has intel for us.

| Websites | APK hash | Theme |
| --- | --- | --- |
| hxxps://weclean[.]cc/ | 1030f97b9ad1addf85b980f576b648a3 | Cleaning service |
| hxxps://double-clean[.]com/ | 1abf5db7726c4e6e9a3a6ce6cdd313af | Cleaning service |
| hxxps://cleanshouse[.]net/ | 1b7df305308e4177ae2f078be13c3de9 | Cleaning service |
| hxxp://dogsclubs[.]net | a56a386b76841bd0aa10654e8d7f4efc | Cleaning service |
| hxxp://best-cleanings[.]com | e71f6e4629fb88ab18e52a2591b14fa8 | Cleaning service |
| hxxp://bubblecleaning[.]net | 7f0e204375caddb08d4c3f9937fa8304 | Cleaning service |
| hxxp://44speedmart[.]com | 23f449dbfc6b9ccae1d20d318672e6d4 | Grocery store |
| hxxps://dog-salon[.]net/ | 2b425dd477fca2932dfab2d5159f611d | Dog salon service |
| hxxp://grooming-time[.]com | f8de585ec8d75b6aae0f41ddf2dc03d8 | Dog salon service |
| hxxp://luxury-online[.]net | ccb0e8380057a4c406996d5102079a4b | Shopping items |
| hxxps://pinky-cat[.]net/ | 5ca6c25f1b2d8e4ca5987e68616247d7 | Cat's foods |
| hxxps://tech-digital[.]net/ | e062d108c93082fb9f47c3f2249d19c5 | Gadget ecommerce |

The moment this post published, I believe you all can't access the domain anymore as the CloudFlare returns Error code 520. Sad.

Btw, the domain might be different, but I suspect that the server behind these domains is the same for some reasons.

## APK Metadata information
```
File Name:	clean-house.apk
Package Name:	com.service.sms
Main Activity:	io.dcloud.PandoraEntry
File Size:	39652340 bytes
MD5:		1b7df305308e4177ae2f078be13c3de9
Packed:		Not Packed
Min SDK:	21
Target SDK:	32
```

## Permission
Based on the permissions of the application, we know that the application had some dangerous permission for users to consider it. But, looking only for permission to verify whether it is malicious or not is not an efficient way. We need to dive into the code and the behavior of the application to see how malicious is it.

Below is the list of permissions in the application:
```
android.permission.CAMERA
android.permission.WRITE_EXTERNAL_STORAGE
android.permission.READ_SMS
android.permission.WRITE_SMS
android.permission.RECEIVE_SMS
android.permission.SEND_SMS
android.permission.RECEIVE_MMS
android.permission.RECEIVE_WAP_PUSH
android.permission.INTERNET
android.permission.FOREGROUND_SERVICE
android.permission.GET_TASKS
android.permission.RECEIVE_BOOT_COMPLETED
android.permission.BROADCAST_STICKY
android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
android.permission.ACCESS_NETWORK_STATE
android.permission.READ_EXTERNAL_STORAGE
android.permission.WAKE_LOCK
android.permission.SEND_RESPOND_VIA_MESSAGE
android.permission.BROADCAST_WAP_PUSH
android.permission.BROADCAST_SMS
android.permission.BIND_JOB_SERVICE
android.permission.DUMP
```

Btw, it is difficult to determine which Android permissions are "dangerous" as the level of risk associated with each permission depends on the specific app and how it uses the permission. Some permissions, such as the ability to access the camera or read SMS messages, can be potentially dangerous if they are used by a malicious app to compromise the privacy or security of the user. Other permissions, such as the ability to access the internet or read external storage, may not pose as much of a risk on their own but could be used in conjunction with other permissions to potentially harm the user.

As a general rule, it is important for users to carefully review the permissions requested by an app before installing it, and to be cautious of apps that request permissions that seem unnecessary for the app's intended functionality. It is also a good idea to avoid downloading apps from untrusted sources, as these apps are more likely to be malicious.

But for analysts, we can consider these permissions to have our eye on it as it can be considered as dangerous in a certain context.
```
READ_CALENDAR
WRITE_CALENDAR
READ_CALL_LOG
WRITE_CALL_LOG
PROCESS_OUTGOING_CALLS
CAMERA
READ_CONTACTS
WRITE_CONTACTS
GET_ACCOUNTS
ACCESS_FINE_LOCATION
ACCESS_COARSE_LOCATION
RECORD_AUDIO
READ_PHONE_STATE
READ_PHONE_NUMBERS
CALL_PHONE
ANSWER_PHONE_CALLS
ADD_VOICEMAIL
USE_SIP
BODY_SENSORS
SEND_SMS
RECEIVE_SMS
READ_SMS
RECEIVE_WAP_PUSH
RECEIVE_MMS
READ_EXTERNAL_STORAGE
WRITE_EXTERNAL_STORAGE
```

## Landing page overview

The application's APK file is available to be downloaded at the landing page that has been set up by the threat actor at the mentioned table in the section above. Based on the landing page UI, the threat actor uses various themes to lure customers to download and install the malicious application if the customer wanted to book the package cleaning service, dog salon, eCommerce, and other packages.

The image below shows the cleaning service landing page that describes the cleaning service that they're offering for victims. To use their services, victims need to download and install malicious application.

![image](https://user-images.githubusercontent.com/56353946/209335759-2787ae8f-be8d-4f0f-8b41-5934e7fb644a.png)

Clicking on the Google Play icon will automatically download the application to the victim's phone.

![image](https://user-images.githubusercontent.com/56353946/209338784-81f6017f-5f07-4e33-8482-b0f6bf288591.png)

Upon downloading the application, victims will install the application, and... all the SMS will be stolen for the sake of TAC code and they might be one of the victims of banking credential phishing.

## Application behavior and interface
![Snipaste_2022-12-23_20-53-39-removebg-preview](https://user-images.githubusercontent.com/56353946/209339579-b9f59871-5b99-4cda-a330-e6a855bdd211.png)
![Snipaste_2022-12-23_20-55-32-removebg-preview](https://user-images.githubusercontent.com/56353946/209341211-2b8b9337-f38e-4eee-acc0-f3ef8deeb2a1.png)
![Snipaste_2022-12-23_20-55-41-removebg-preview](https://user-images.githubusercontent.com/56353946/209341216-00240392-318a-4bac-be41-dc908cc9bda0.png)
![Snipaste_2022-12-23_20-55-50-removebg-preview](https://user-images.githubusercontent.com/56353946/209341218-3910f52d-de6c-4100-b6e1-6c07470f98ee.png)
![Snipaste_2022-12-23_20-56-34-removebg-preview](https://user-images.githubusercontent.com/56353946/209341219-88303f48-b1b7-497b-b805-25f2b01b7b40.png)
![Snipaste_2022-12-23_20-56-46-removebg-preview](https://user-images.githubusercontent.com/56353946/209341221-65413276-36df-468b-9ff6-b4571c2d859a.png)
![Snipaste_2022-12-23_20-56-57-removebg-preview](https://user-images.githubusercontent.com/56353946/209341223-bccc5e82-819e-4c56-854c-8d1a2732f10b.png)
![Snipaste_2022-12-23_20-57-08-removebg-preview](https://user-images.githubusercontent.com/56353946/209341226-90d3bb9e-dd37-419b-abc6-4e80da8348be.png)


## Fake payment gateway for phishing
![Snipaste_2022-12-23_20-57-56-removebg-preview](https://user-images.githubusercontent.com/56353946/209341229-4f26da73-b2ea-48fc-9dcd-d6b123945be7.png)
![Snipaste_2022-12-23_20-58-18-removebg-preview](https://user-images.githubusercontent.com/56353946/209341232-e782d486-3230-4430-a083-c469aed60dd2.png)
![Snipaste_2022-12-23_20-58-26-removebg-preview](https://user-images.githubusercontent.com/56353946/209341234-691f0e11-26f8-4231-8c62-973507ca0cea.png)
![Snipaste_2022-12-23_20-58-48-removebg-preview](https://user-images.githubusercontent.com/56353946/209341201-c225d807-49dc-49ea-a31e-bb8a6144dfce.png)
![Snipaste_2022-12-23_20-59-25-removebg-preview (1)](https://user-images.githubusercontent.com/56353946/209341207-2b15dbc4-8c6c-4d5d-83b9-051cf4531ee0.png)
![Snipaste_2022-12-23_20-59-57-removebg-preview](https://user-images.githubusercontent.com/56353946/209341208-ad263834-b79a-4fc7-a86f-5521a7ae04fa.png)

## SMS stealer for TAC code


# Summary
The blog discusses the use of a lure application by a scammer to steal sensitive information from users. The lure application serves as a decoy, tricking users into providing their SMS data and online banking credentials, as well as credit card information. Once the scammer has obtained this information, it is submitted to the attacker's Command and Control (C2) server using an API located at the domain sg1[.]mall-base-app[.]com. The attacker can then use the stolen information, such as banking credentials and credit card information, to obtain the Transaction Authorization Code (TAC) for illegal transactions. Essentially, the scammer is using the lure application to phish for sensitive information and use it for fraudulent purposes.

# Indicator of Compromises
C2 server:
```
https://sg1.mall-base-app.com
```

URLs:
```
https://sg1.mall-base-app.com/app/api/action/defaultSmsPushAction/
https://sg1.mall-base-app.com/app/api/action/devideActivePushAction/
https://sg1.mall-base-app.com/app/api/action/smsMessagePushAction/
https://sg1.mall-base-app.com/app/api/action/smsPermissionPushAction/
https://u138-paymobile7731.pay-director.com/maybank/pay.html
```

Hashes:
```
ccb0e8380057a4c406996d5102079a4b  Luxury.apk
e062d108c93082fb9f47c3f2249d19c5  TechDigital.apk
e71f6e4629fb88ab18e52a2591b14fa8  best-cleaning.apk
7f0e204375caddb08d4c3f9937fa8304  bubble-clean.apk
1b7df305308e4177ae2f078be13c3de9  clean-house.apk
2b425dd477fca2932dfab2d5159f611d  dog-salon.apk
a56a386b76841bd0aa10654e8d7f4efc  dogs-clubs.apk
1abf5db7726c4e6e9a3a6ce6cdd313af  double-clean.apk
f8de585ec8d75b6aae0f41ddf2dc03d8  grooming-time.apk
5ca6c25f1b2d8e4ca5987e68616247d7  pinkycat.apk
23f449dbfc6b9ccae1d20d318672e6d4  speedmart.apk
1030f97b9ad1addf85b980f576b648a3  we-clean.apk
1030f97b9ad1addf85b980f576b648a3  we-clean.apk
```
