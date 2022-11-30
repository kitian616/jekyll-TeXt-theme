---
title:  "Adding Az Context to PowerShell"
toc: true
mermaid: true
categories: [Windows]
tags:
  - azure
---

If you're using Az command's a lot an PowerShelling to different tenant's you probably already have Context saving turnt off. If not, you might want to run it with:

```powershell
Disable-AzContextAutosave
```

More info over at [Ms Docs](https://docs.microsoft.com/en-us/powershell/module/az.accounts/disable-azcontextautosave?view=azps-1.6.0)

With Autosave disabled you don't have to worry (or worry less) about starting a new PS session and firing off commands at the wron tenant. As a extra security measue (and to prevent me from getting confused) I also added a small context aware script to my `$PROFILE`. If you're not familiar with `$PROFILE` , it's the file that get's loaded every time you open u a new Powershell window. There is a chance you don't have one yet. In that case you can create it by using:

``` powershell
if (!(Test-Path -Path $PROFILE )) { New-Item -Type File -Path $PROFILE -Force }
notepad $PROFILE
```

Now on to edeting the `$PROFILE`. Add the following code to it:

~~~~ powershell
function Check-AzCon {
	#Check connection to Azure
	$Context = Get-AzContext
	if($Context) {
		$Warn = "Connected to " + $Context.Tenant.Id + " with account " + $Context.Account.Id
		Write-Warning $Warn -WarningAction Inquire
	}
}

Check-AzCon
~~~~

Now, if you open up a new Powershell window and you might still be connected to a remote Azure tenant it will let you know (:

Quick bonus tip, you can always reload your current PS Profile with `& $profile`