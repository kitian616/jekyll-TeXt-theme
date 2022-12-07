---
title: "F-Secure 2019 Qualification and Semi Final: Write-up"
tags:
- Reverse-Engineering
---

Welcome back guys. Today I'm gonna do reverse engineering and steganograpy challenges from FSecure2019.

# Qualification

## Challenge 1

![1](https://user-images.githubusercontent.com/56353946/206092607-fb115e39-de2f-4105-b4ec-0d4e6ad7a118.PNG)


Investigate the image and then decode the flag. Let's dive in into the image.

![2](https://user-images.githubusercontent.com/56353946/206092627-288913fb-1bc5-44e2-a757-4daa0da6dc0b.png)

Okay first question. We need to decode this image and get "something" from it. Try to extract information using tools like binwalk, foremost and strings gave us nothing. Then I immidiately go to google to check some online stegano tools.

Using this [online steganography tool](https://manytools.org/hacker-tools/steganography-encode-text-into-image/).  I managed to get morse code from the image.

```
..-. ... ··--·- .. -- .- --. . ··--·- ... - ...-- --. .- -.  --. .-. .- .--. .... -.-- ··--·- --  .-. ... . -.-.  -.. . ··--·- -.-. -.-- -... . .-.
```

Decode the morse code using [this online tool converter](https://cryptii.com/pipes/morse-code-to-text) and we get the flag.

The flag is `fsoimageost3ganographyomorsecodeocyber`



## Challenge 5

![3](https://user-images.githubusercontent.com/56353946/206092724-581cf385-5b55-4943-98c2-f95af0f37e04.PNG)

Organizer gave us a file which we don't know what file it is. Trying `file` command on it gave us nothing. Just a "data" output.

Strings command it gave us this strange strings.

```
#@~^NgAAAA==\ko$K6,J0k+ ^!9kUo|xG2M!4^n:1X4.E~,vl~~JP4PWVmLPb/lE8BEAAA==^#~@
```

Hmm. Still gave us no clue.

Then I try to execute binwalk command, and yeah it make me smile again to solve this challenge.

![4](https://user-images.githubusercontent.com/56353946/206092760-04b5049e-3575-45d1-840b-aede1dd027c3.PNG)

It's a Windows Script Encode! I immidately go to google, search some decoder for it and I found [this](http://www.password-crackers.com/crack/scrdec.html). Download it and execute on our file.

![5](https://user-images.githubusercontent.com/56353946/206092767-13005a88-c872-40c1-9df3-6811ce2ea0fe.PNG)

Apply strings command to file that have decoded (challenge5decoded) gave us the flag!

![6](https://user-images.githubusercontent.com/56353946/206092787-844be9c9-2b6c-4c0f-8316-241e61844f06.PNG)

The flag is `fsenc0ding_nopr0blemcyber`

## Challenge 6
![7](https://user-images.githubusercontent.com/56353946/206092800-6ab9c1ca-e481-4896-8d79-f46e3e4d4230.PNG)

Investigate the Spreadsheet. Okay let's dive in into the document. Basically, we need to clarify that Microsoft office file is actually a compressed file. Therefore we can decompressed it. 

Unzip it and then we will get these files.

 - rels
 - customXml
 - docProps
 - xl
 - [Content_Types]

![8](https://user-images.githubusercontent.com/56353946/206092816-b2f77a91-85cf-4eca-b9f4-95e9d7775ba6.PNG)

Investigate it using Sublime text, one by one of the files and we managed to get the flag in `xl/worksheet/sharedStrings.xml`.

![9](https://user-images.githubusercontent.com/56353946/206092826-d6f083dd-24f8-4833-b0c7-40d03bbd9249.PNG)

The flag is `fssUPERhIdDEncyber`




## Challenge 8

![10](https://user-images.githubusercontent.com/56353946/206092838-f3c197ca-4912-4de3-919d-372d7b9bd0ab.PNG)

Execute `file` command on the binary, and it said the binary is a CDFV2 Microsoft Outlook Message.

![11](https://user-images.githubusercontent.com/56353946/206092851-2c1e8587-8cf1-4301-b2d9-3a6c4e657cbf.PNG)

`strings` command it gave us a big clue how to find the flag. It give us an encrypted text and said "`I hope you remember what base it is :p`"

So, it said something about base. Hmm. Base64? Base32? The text seems not like the format of both of this base. After doing some google, there is one more base which is base85 / Ascii85 !

I use this online [tool](https://cryptii.com/pipes/ascii85-encoding), and we got the flag!

![12](https://user-images.githubusercontent.com/56353946/206092860-3eaf0c78-b1e1-4671-b154-726d6afd295f.PNG)

The flag is `fsbase85enc0dedcyber`



## Challenge 9

![13](https://user-images.githubusercontent.com/56353946/206092886-37b2524a-389e-4a0e-b19e-21ff4d17b27c.PNG)

They gave us a picture of FSecure logo again. This must be steganography challenge again.

![14](https://user-images.githubusercontent.com/56353946/206093006-789695fb-a4e2-4f99-b347-3f71d761aa3e.jpeg)

Google some of online steganography tools and we found this [site](https://futureboy.us/stegano/decinput.html).

Upload the photo and give "fsecure" as the password. Then, we got the flag.

The flag is `fsmeta_stegcyber`

## Challenge 10

![15](https://user-images.githubusercontent.com/56353946/206093021-07743b92-1136-4567-bfe6-1c6e19fe54d1.PNG)

Soon we download the file, we executed it.

The program ask me for a PIN number.

![16](https://user-images.githubusercontent.com/56353946/206093041-42fdd8a9-c486-4b8b-b77c-3ca0d9fc6b6e.PNG)

Test the program with a random key gave us this strings `"I'll give you an A for effort... but better luck next time!"`

Okay let's open it using IDA to dive into it's code.

![17](https://user-images.githubusercontent.com/56353946/206093064-8474d4ef-e13b-459e-aa84-479ba8fa3c6f.PNG)

I go to Strings window to get clue where the main function are. From the strings window, I double click on "INPUT THE KEY" and it bring me to .rdata segment.

![18](https://user-images.githubusercontent.com/56353946/206093080-10ca376b-59ac-4aa9-9b4a-f601f510924a.PNG)

Then, click on the variable name "`aInputTheKey`" press X for cross reference. Cross references can help us determine where certain functions were called from, which can be useful for a number of reasons. 

So, this strings were called by this instruction. Double click on it will bring us to the instruction code. 

![19](https://user-images.githubusercontent.com/56353946/206093087-ad4d8dec-9312-4bc6-b031-ebf94ffc42fb.PNG)

From this graph, we can see it compare our input with number "`1337`".

Decompile it, we can see the in the circle, the flag is obfuscated by some algorithm.

![20](https://user-images.githubusercontent.com/56353946/206093099-631881ef-459a-4131-ac88-65418a625e0c.PNG)

Try 1337 as the PIN and we got the flag.

![21](https://user-images.githubusercontent.com/56353946/206093221-622d849a-2175-4721-bfb5-89a64e300b34.PNG)

The flag is `fs4NT1D3BUGIZCOMM0Ncyber`



# Semi-Final 

You can get the dump binaries at [here](https://mega.nz/#!mvxxwCxC!IbUSKXwq5cdPRJQ9L1RFrICbPgU3dIny0ffyu0Clzt8).

## Challenge 1

![22](https://user-images.githubusercontent.com/56353946/206093259-b43b669a-407a-4a47-a427-337ba5c2f999.PNG)

The first challenge was about a .jar file. Let's see what is the output when we execute it. 

![23](https://user-images.githubusercontent.com/56353946/206093274-c0545f1d-965f-4be9-80c0-dd8fe5aa8ae5.PNG)

Run it in terminal gave us an output said "`Nice Try!`".

**Step 1:**

Decompile it using JADX and then we will get the souce code of the program.

![24](https://user-images.githubusercontent.com/56353946/206093288-2875cb37-c380-43a0-82a3-a365b69dc390.PNG)

After understand the codes, you can see that the flag was encrypted using xor operation with a letter "A".

**Step 2:**

Let's do a script with python to reverse xor the encrypted string.

First, I converted the xored string to hexadecimal value.
```
*wwvrvyrqvsw wpvwuqwrvxwswtvs* --> *0x77 0x77 0x76 0x72 0x76 0x79 0x72 0x71 0x76 0x73 0x77 0x20 0x77 0x70 0x76 0x77 0x75 0x71 0x77 0x72 0x76 0x78 0x77 0x73 0x77 0x74 0x76 0x73*
```

Then, I make the script with the hexa values.

```
    s = [0x77, 0x77, 0x76, 0x72, 0x76, 0x79, 0x72, 0x71, 0x76, 0x73, 0x77, 0x20, 0x77, 0x70, 0x76, 0x77, 0x75, 0x71, 0x77, 0x72, 0x76, 0x78, 0x77, 0x73, 0x77, 0x74, 0x76, 0x73]
    // each of 0x__ is the characters of the encrypted string.
    
    print  ''.join([chr(i ^  0x41) for i in s])
    // 0x41 is letter "A" in hexadecimal
```
    
Or you can cook the string using this [recipe!](https://gchq.github.io/CyberChef/#recipe=XOR%28%7B%27option%27:%27Hex%27,%27string%27:%2741%27%7D,%27Standard%27,false%29&input=d3d2cnZ5cnF2c3cgd3B2d3Vxd3J2eHdzd3R2cw)  

**Step 3:**
Execute the command and it will print a hexadecimal value. Convert it to ASCII will give us the flag.

![25](https://user-images.githubusercontent.com/56353946/206093389-fb55d3ce-479e-4c94-90dc-d87c52ca2f3d.PNG)

`xxd` - make a hexdump of the binary.

`-r`  tells it to convert hex to ascii as opposed to its normal mode of doing the opposite.

`-p`  tells it to use a plain format.

The flag is `fsx0rjav@cyber`


## Challenge 2
![26](https://user-images.githubusercontent.com/56353946/206093414-efc9e007-4546-4a36-8ff6-401fca1cb572.PNG)

This challenge was quite easy. They gave us a .pyc binary. 

If you don't know what file it is, just issuing `file` command on it then it will tell us it's a `python 2.7 byte-compiled`. Knowing what file it is, can give you clue how to run the binary. 

Run the python program and then it print a hexa strings. Decode the hexadecimal into ASCII characters will get us the flag.

![27](https://user-images.githubusercontent.com/56353946/206093431-a9e67810-dce6-4351-a400-4435711bd2e3.PNG)

The flag is `fsx0r3dpythoncyber`


## Challenge 3
This challenge was tough for me. My debugging skill was so bad. I think I was so lucky to get the flag by patching randomly the conditional jump using IDA Pro.

![28](https://user-images.githubusercontent.com/56353946/206093451-5cab3542-de0d-4595-a443-c1c7a3829cc1.PNG)

Execute the program and then it will print a youtube link.

![29](https://user-images.githubusercontent.com/56353946/206093471-f54e13ba-7f3e-4d53-9706-aa3f13e03e0b.PNG)

Open it using IDA and I saw another youtube link.

![30](https://user-images.githubusercontent.com/56353946/206093485-5ca565d4-9ff3-45ed-959b-34c31abcd77e.PNG)

The blue pen is for the printed youtube link, and the red pen is the other youtube link I mentioned before.

From the graph view above, I decided to patch a few conditional jump to make the program print the "other" youtube link. But, surprisingly.. it print the flag we wanted hahaha. 

I really don't know how this program work actually.  The assembly code and  decompiled code are also looks messy to me. I was really lazy to dive in into the assembly code deeply.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/31.PNG)

After patch the program.. I executed the patched binary and it print the flag.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/32.PNG)

The flag is `fsRICKR0LLEDcyber`


## Challenge 4

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/33.PNG)

Soon get the binary, unzip it recursively four times until you get two files called `almost_there` (encrypted zip file) and `rockyou.txt` (dictionary for bruteforcing the encrypted zip file).

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/34.PNG)

Crack the encrypted zip file using *rockyou.txt dictionary* with fcrackzip.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/35.PNG)

    fcrackzip -u -D -p rockyou.txt almost_there
    
    PASSWORD FOUND!!!!: pw == lovet-joearceneaux 

`-u`  tells the program to test the password with unzip before declaring it correct

`-D` to specify a dictionary based attack

`-p` which is used to specify the password file.

Then, `unzip` the file with cracked password and it will extracting a file called flag. `cat` command the flag gave us the flag.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/36.PNG)

The flag is `fs_compr3ss10n_w1th_unknOwn_p@ssw0rd_cyber`

## Challenge 5

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/37.PNG)

This challenge require knowledge in powershell obfuscation. 

We try to execute the powershell and it printed "`No, this is not the answer!`".

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/38.PNG)

Okay now let's open the .ps1 file with your text editor. Remove this line.

    |&( $enV:cOMSpEC[4,26,25]-JOIN'')

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/39.PNG)

Then, execute the .ps1 file back will print us the real text of obfuscated script. 

There we see the flag of this challenge.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/40.PNG)

The flag is `fsThIngsVDO4cyber`.


## Challenge 7

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/41.PNG)

Execute the program and it ask us for a true/false question. Giving a wrong answer will make the program terminate.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/42.PNG)

After doing static analysis on the application, this binary was packed using UPX packer. So, we need to unpack it to reverse the program. You can unpack it using upx tool. I use [this](http://www.pazera-software.com/download.php?id=0022&ft=i32&f=FUPX_Setup.exe).

Open it using IDA gave us the answer of each of the question in the main function.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/43.PNG)

The answer is

 1. true
 2. true
 3. false
 4. true
 5. false
 6. false
 7. true
 8. true
 9. true
 10. false
 
 After submitting the all true answer, it will print us the flag.

The flag is `fswh@tw@sth@tcyber`.


## Challenge 10

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/44.PNG)
  
The file is a document file by Microsoft Word.

Issuing `binwalk -e` command to the file will extract us the files embedded in the document file.

    binwalk -e 1124.doc 
    
    DECIMAL       HEXADECIMAL     DESCRIPTION
    --
    6034          0x1792          Zip archive data, at least v2.0 to extract, compressed size: 255, uncompressed size: 540, name: [Content_Types].xml
    6338          0x18C2          Zip archive data, at least v2.0 to extract, compressed size: 192, uncompressed size: 310, name: _rels/.rels
    6571          0x19AB          Zip archive data, at least v2.0 to extract, compressed size: 131, uncompressed size: 138, name: theme/theme/themeManager.xml
    6760          0x1A68          Zip archive data, at least v2.0 to extract, compressed size: 1704, uncompressed size: 7076, name: theme/theme/theme1.xml
    8516          0x2144          Zip archive data, at least v2.0 to extract, compressed size: 182, uncompressed size: 283, name: theme/theme/_rels/themeManager.xml.rels
    9116          0x239C          End of Zip archive, footer length: 22
    9138          0x23B2          XML document, version: "1.0"
    12461         0x30AD          Zip archive data, at least v2.0 to extract, compressed size: 254, uncompressed size: 481, name: [Content_Types].xml
    12764         0x31DC          Zip archive data, at least v2.0 to extract, compressed size: 214, uncompressed size: 404, name: _rels/.rels
    13019         0x32DB          Zip archive data, at least v2.0 to extract, compressed size: 714, uncompressed size: 2678, name: drs/e2oDoc.xml
    13777         0x35D1          Zip archive data, at least v2.0 to extract, compressed size: 221, uncompressed size: 269, name: drs/downrev.xml
    14286         0x37CE          End of Zip archive, footer length: 22
    22528         0x5800          XML document, version: "1.0"
    28544         0x6F80          XML document, version: "1.0"
    36928         0x9040          XML document, version: "1.0"
    40832         0x9F80          XML document, version: "1.0"
    41088         0xA080          XML document, version: "1.0"
    41920         0xA3C0          XML document, version: "1.0"

Go to `_1124.doc.extracted\drs`, open the `e2oDoc.xml` using your text editor.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/45.PNG)

From the display above, the characters with "`&#xA;`" looks suspicious to me. Remove it one by one will give us the clear text of the flag.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/fsecure19/46.PNG)

The flag is `fsHideInAltTextcyber`.
