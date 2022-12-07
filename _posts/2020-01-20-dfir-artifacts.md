---
title: Windows Forensics artifacts
tags: DFIR
---

# Windows Event logs
| Artifact name/path | Description |
| --- | --- |
| `%system root%\System32\winevt\logs\ *`| Windows logs |

# Registry file and few other artifact

| Artifact name/path | Description |
| --- | --- |
| `C:\Windows\System32\config\*` | All registry files (Contain a lot of artifact) |

# File execution activities

| Artifact name/path | Description |
| --- | --- |
| `NTUSER.DAT` | UserAssist, Last-Visited MRU, RunMRU Start->Run |
| `SYSTEM\CurrentControlSet\Control\Session Manager\AppCompatCache` | Windows Application Compatibility Database |
| `C:\%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Recent\AutomaticDestinations ` |  Jump Lists |
|  `C:\Windows\Prefetch` |  Prefetch |
|  `C:\Windows\AppCompat\Programs\Amcache.hve` and `C:\Windows\AppCompat\Programs\RecentFilecache.bcf` |  Amacache.hve or RecentFileCache.bcf |


# File Download activities

| Artifact name/path | Description |
| --- | --- |
| `NTUSER.DAT` | Dig into user activities like all of the mounted disks including USB thumb drives and many more |
| `%USERPROFILE%\AppData\Local\Microsoft\Outlook` | E-mail Attachments |
| `C:\%USERPROFILE%\AppData\Roaming\Skype\<skype-name>` | Skype History |
| `%USERPROFILE%\AppData\Local\Google\Chrome\UserData\Default\History` | Chrome Artifacts |
| `%userprofile%\AppData\Roaming\Mozilla\ Firefox\Profiles\<random text>.default\places.sqlite Table:moz_annos` | Firefox Artifacts |
| `%USERPROFILE%\AppData\Local\Microsoft\Windows\WebCache\WebCacheV*.dat` | IE artifacts |
| `%userprofile%\AppData\Roaming\Mozilla\ Firefox\Profiles\<random text>.default\downloads.sqlite` | Firefox downloads |
| `%USERPROFILE%\AppData\Local\Microsoft\Windows\WebCache\ WebCacheV*.dat` | IE Downloads |



# File/Folder Opening
| Artifact name/path | Description |
| --- | --- |
| `NTUSER.DAT`  | Open/Save MRU, Last-Visited MRU , Recent Files, Office Recent Files |
|  `USRCLASS.DAT` |  Shell Bags |
|`C:\%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Recent\ ` or `C:\%USERPROFILE%\Recent` |  Shortcut (LNK) Files |
| `C:\%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Recent\AutomaticDestinations ` |  Jump Lists |
|  `C:\Windows\Prefetch` |  Prefetch |

# Deleted File or File Knowledge

| Artifact name/path | Description |
| --- | --- |
| `NTUSER.DAT`  | XP Search – ACMRU, Search – WordWheelQuery, Last-Visited MRU,  |
| `C:\%USERPROFILE%\AppData\Local\Microsoft\Windows\Explorer` | Thumbscache |
| `C:\RECYCLER` | XP Recycle Bin |
| `C:\$Recycle.bin`  |  Win7/8/10 Recycle Bin |


# Other browser artifacts
| Artifact name/path | Description |
| --- | --- |
| Registry files | Timezone, Network History, |
|`%USERPROFILE%\AppData\Local\Microsof\Windows\INetCookies `| Cookies IE |
| `%USERPROFILE%\AppData\Roaming\Mozilla\Firefox\Profiles\<randomtext>.default\cookies.sqlite `| Cookies Firefox |
| `%USERPROFILE%\AppData\Local\Google\Chrome\UserData\Default\Local Storage `| Cookies Chrome |
| `%USERPROFILE%\AppData\Local\Microsoft\Windows\WebCache\WebCacheV*.dat `| IE Browser Search Terms |
| `%userprofile%\AppData\Roaming\Mozilla\Firefox\Profiles\<randomtext>.default\places.sqlite `|  Firefox Browser Search Terms|


# External device / USB device
| Artifact name/path | Description |
| --- | --- |
| `C:\Windows\inf\setupapi.dev.log` and `C:\Windows\setupapi.log` | First/Last Times of devices connected |
| Registry files | Key Identification,  First/Last Times, User, Volume Serial Number, Drive Letter & Volume Name |
|`C:\%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Recent\ ` or `C:\%USERPROFILE%\Recent` |  Shortcut (LNK) Files |
| `System.evtx` | PnP Events | 

# Account usage
| Artifact name/path | Description |
| --- | --- |
| SAM in Registry files | Last login |
| SAM in Registry files | Last password change |
| Security event | Success/Fail Logons |
| Security event | RDP Usage |

# Browser Usage

| Artifact name/path | Description |
| --- | --- |
|`%USERPROFILE%\AppData\Local\Microsoft\Windows\WebCache\WebCacheV*.dat` | IE History |
|`%USERPROFILE%\AppData\Roaming\Mozilla\Firefox\Profiles\<random text>.default\places.sqlite` | Firefox History |
|`%USERPROFILE%\AppData\Local\Google\Chrome\User Data\Default\History` | Chrome History  |
|`%USERPROFILE%\AppData\Local\Packages\microsoft.microsoftedge_<APPID>\AC\MicrosoftEdge\Cache `| IE Cache |
| `%USERPROFILE%\AppData\Local\Mozilla\Firefox\Profiles\<randomtext>.default\Cache`| Firefox Cache |
| `%USERPROFILE%\AppData\Local\Google\Chrome\User Data\Default\Cache\ - data_# and f_######` | Chrome Cache |
| `%USERPROFILE%/AppData/Local/Microsoft/Internet Explorer/Recovery` | IE Session Restore |
| `%USERPROFILE%\AppData\Roaming\Mozilla\Firefox\Profiles\<randomtext>.default\sessionstore.js` | Firefox Session Restore |
| `%USERPROFILE%\AppData\Local\Google\Chrome\User Data\Default\` | Chrome Session Restore |


# Others

 1. Memory dump
 2. MFT

# Source
SANS Windows Forensics Poster
