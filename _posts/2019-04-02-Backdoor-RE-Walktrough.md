---
title: "Backdoor CTF Practice Arena: RE Challenges Walktrough"
tags:
- Reverse-Engineering
---

Below are the few reverse engineering category's challenges that I've been solved. It was a pretty easy challenge to solve.

# revfun

![1](https://user-images.githubusercontent.com/56353946/206090920-c28f4614-18a9-4a65-b0b4-5ed4bf02105b.png)

**Step 1**:  
Run `file` command on the binary. Now we know that it’s a ELF 64 bit file.

![2](https://user-images.githubusercontent.com/56353946/206090973-54fed596-555e-49c1-8ad6-e22778df6a7b.png)

**Step 2**:  
Use `strings` command to display all the strings on the binary and we can see there is a suspicious string `“dlr0w_s1h7_s1_yz4rC”`. If we reverse the char string, it said `“crazy_is_this_world”`.

![3](https://user-images.githubusercontent.com/56353946/206091002-34a2fc11-0b41-4dcf-b333-0787d77df8d6.png)

**Step 3**:  
Try the password on the binary.

![4](https://user-images.githubusercontent.com/56353946/206091013-5cadaafa-ae17-4dc4-b2c5-f1e72f235750.png)

**Step 4**:  
Try on `netcat`, and it will print our flag.

![5](https://user-images.githubusercontent.com/56353946/206091034-df0c3fb7-4ac2-4f06-a91c-d62dc63de6c0.png)

# debug

![6](https://user-images.githubusercontent.com/56353946/206091050-7f8b8a4f-b8aa-433e-9737-d8c7019e3915.png)

**Step 1:** 
Run `file` command on the binary. Now we know that it’s a ELF 32 bit file.

![7](https://user-images.githubusercontent.com/56353946/206091058-fe5abc7b-74f7-4c96-a33b-bf9d98e41e37.png)

**Step 2:**
Try to run the program, and it’s return nothing.

![8](https://user-images.githubusercontent.com/56353946/206091077-4c0cc757-1dd3-411e-a66e-e1b0e397faa3.png)

**Step 3:**
Open the program using IDA Pro. We can see on the left side of the IDA program, there is a bunch of function names. After little bit of exploration on the functions, the functions `sub_804849B` look interesting to me.

![9](https://user-images.githubusercontent.com/56353946/206091113-66c4613f-3b95-4a4c-aa09-739ad549e078.png)

It push a strings `Printing flag`. So, I think this must be a function that print the flag. So, I rename the function `sub_804849B` to `PrintFlag` for easy for me to remember which function does the print flag.

![10](https://user-images.githubusercontent.com/56353946/206091127-26600969-9170-42b1-b99a-cfe6c91dc895.png)

**Step 4**:  
Okay all we need now it’s to patch the program that will jump to the `PrintFlag` function. First thing we must know, every program will start executing something at `_start` function. So, go to the `_start` and we can see it will call `_libc_start_main`.

![11](https://user-images.githubusercontent.com/56353946/206091158-6a29a0f5-0e28-4b0b-ae8a-c2537c89746c.png)

Let’s patch the program by change the `_libc_start_main` function to `PrintFlag` function.

Click on `libc_start_main` function => Go to `edit` => `Patch program` => `Assemble`

![12](https://user-images.githubusercontent.com/56353946/206091178-7e06efb5-e5b3-4862-8b33-dda3a14e5f29.png)

Change the `libc_start_main` function to `_PrintFlag` function. Then click OK. Then close.

![13](https://user-images.githubusercontent.com/56353946/206091198-e8ad8249-ec7d-4a57-9017-ac394590a5a5.png)

**Step 5:**

![14](https://user-images.githubusercontent.com/56353946/206091218-bdb8b457-0832-4571-994e-6655fe3b01ef.png)

Save the patch by go to _edit_ => _Patch program_ => _Apply patches to input file._

**Step 6:**
After patched the program, just run the binary and it will print the flag.

![15](https://user-images.githubusercontent.com/56353946/206091246-16362253-0aae-4044-bfea-8314f92b11ae.png)

# bin-easy

![16](https://user-images.githubusercontent.com/56353946/206091332-bb56d157-ad02-4069-80e0-083cb02b2f00.png)

**Step 1:**
Run `file` command on the binary.

![17](https://user-images.githubusercontent.com/56353946/206091344-63d463c2-0ec2-485b-b98f-5299065e66ea.png)


**Step 2:**
Run `strings` command and we got the sha256 of the flag.

![18](https://user-images.githubusercontent.com/56353946/206091360-130a2def-02c1-4557-a9e5-20be20a30465.png)

# bin-medium

![19](https://user-images.githubusercontent.com/56353946/206091383-20420a51-8eb9-4aa3-8c49-f1c9fe01aecb.png)


**Step 1**:  
Run `file` command on the binary.

![20](https://user-images.githubusercontent.com/56353946/206091397-09933a8f-f6b5-4079-9172-e7703e15578b.png)


**Step 2:**
Try execute the program, and input random passphrase and it return a string.

![22](https://user-images.githubusercontent.com/56353946/206091417-557dc40d-fac5-483a-976c-d14dca93da5c.png)

**Step 3:**
By analyzed the binary on IDA, if the input are wrong it will jump to a function that will print the string `Thank you, but you aint getting the flag`. I renamed the function to `WrongPassword` for easy for me to remember the function name.

So, all we need now it’s patch the conditional jump `jnz WrongPassword` to `jz WrongPassword`.

![23](https://user-images.githubusercontent.com/56353946/206091432-a6d537a6-561d-4a64-b609-d56369c21b30.png)

Save the patched program.

**Step 4:**
Run the program and enter a random passphrase. Now, it will print the sha256 of the flag.

![24](https://user-images.githubusercontent.com/56353946/206091441-31619e46-4650-4d3b-8082-e8fe586a3ae7.png)

# hiddenflag–easy

![25](https://user-images.githubusercontent.com/56353946/206091454-d1f8c5fb-aec0-4316-8906-11214de42855.png)

**Step 1**:  
Run `file` command on the binary first.

![26](https://user-images.githubusercontent.com/56353946/206091473-c84ca141-f990-49f3-93a1-44f02460c5d8.png)

**Step 2**:  
Execute the binary and it’s return a string.

![27](https://user-images.githubusercontent.com/56353946/206091487-1f0d9eb6-300c-4a87-80a4-d33e0e1244f0.png)

**Step 3**:  
Issuing `strings` command and from there we got the sha256 of the flag.

![28](https://user-images.githubusercontent.com/56353946/206091521-0c2e9f9e-b1d6-4109-bbc2-2fd2e4dcf4d6.png)

# hiddenflag-medium

![29](https://user-images.githubusercontent.com/56353946/206091544-d6a25b64-539b-4a43-9036-3caf022f3c2b.png)

**Step 1**:  
Issue `file` command on the binary.

![30](https://user-images.githubusercontent.com/56353946/206091559-bc64bf72-40c8-4fa3-a0fa-518029df1bb5.png)

**Step 2**:  
Reverse engineering it using _gdb debugger_ by run `_gdb ./<filename>`.  After that, display all the functions of the binary by supply “_info functions”_ in gdb debugger. It will list all the functions.

![31](https://user-images.githubusercontent.com/56353946/206091565-46461a18-1b30-4aae-be04-8558792014d0.png)

From the list of functions gdb echoed, we can see there is a function name `_print flag` . That is interesting.

**Step 3**:  
Now all we need is jump to the function `_print flag` and the program will print the flag.

So, breakpoint to the entry point function which is `_start` function. Use b_ for breakpoint and address of the function that we want to break. 
Here the command: 
`b *0x080483a0`

![32](https://user-images.githubusercontent.com/56353946/206091600-cf1fdcdb-0287-4c67-a9d8-71dc75eec941.png)

**Step 4**:  
After put a breakpoint, execute the program by type `run` and enter.

![33](https://user-images.githubusercontent.com/56353946/206091616-b07c046e-4bd8-4b2c-9ee1-5e3eca12ff48.png)

**Step 5**:  
Now it will break to the address that we’ve been break which is `0x080483a0`.Now all we need is jump to the function “print flag” and the program will print the flag. Type `jump <printflag address>` and the program will print the sha256 of the flag.

![34](https://user-images.githubusercontent.com/56353946/206091626-26215171-3087-49ff-9a07-430f1a4d466b.png)

# 2013-bin-500
![35](https://user-images.githubusercontent.com/56353946/206091650-f7c8b79a-5f02-49a7-bc01-fd5e73a8bfd2.png)

**Step 1**:  
Run `file` command on the binary. The file is PE32 file.

![36](https://user-images.githubusercontent.com/56353946/206091669-9f1ae3f3-7001-450a-8c34-d4218bdad0bc.png)

**Step 2**:  
Execute the program and it will ask for the registration key. Try some random key and it will print `Invalid key`.

![37](https://user-images.githubusercontent.com/56353946/206091693-e911db81-aa7f-4cf6-937c-0eca41bb961a.png)

**Step 3**:  
I try open it using IDA, but the binary seems like have been obfuscated. To detect what packer have been use, open the file using _PEiD_ software.

![38](https://user-images.githubusercontent.com/56353946/206091702-f672a06c-aff2-46a2-b20d-c6c60a92e8a3.png)

It display “Microsoft Visual Basic 5.0”.

Googling some Microsoft Visual Basic 5.0 unpacker. It said

“Your program is not packed, but rather compiled as [Visual Basic P-code](http://www.woodmann.com/crackz/Tutorials/Vbpcode.htm) or Visual Basic native code. If it’s VB native code, you can use your favorite debugger (OllyDbg, IDA, etc.) to debug it, and IDA to disassemble it. If it’s VB P-code, you can use [VB Decompiler Pro](http://www.vb-decompiler.org/) to disassemble/decompile it.”

So compiled as [Visual Basic P-code.](http://www.woodmann.com/crackz/Tutorials/Vbpcode.htm) To decompile we must use [VB Decompiler Pro](http://www.vb-decompiler.org/).

**Step 4**:  
Decompile it using _VB Decompiler Pro_ and read the codes. On line `loc_404D2E`, we can see if text = `&H289BC` it will print “_Thank you… bla bla.. “._

![39](https://user-images.githubusercontent.com/56353946/206091723-96016fb8-65f5-4a6d-b055-9e4421ee6ae6.png)

**Step 5**:  
Copy the string, paste it into the text box and click “_Submit_” and we got the flag.

![40](https://user-images.githubusercontent.com/56353946/206091730-0e82988c-6f47-40a9-9c93-34b54620000b.png)

# 2013-Bin-200
![41](https://user-images.githubusercontent.com/56353946/206091744-d1323331-da02-4a07-865e-862a3eec9853.png)

**Step 1**:  
Execute the program and it will ask for the registration key. Try some random key and it will print “_Invalid key.”_

![42](https://user-images.githubusercontent.com/56353946/206091761-7a8a4b33-8bc0-4238-b8ac-62b44c439b2d.png)

**Step 2**:  
I try open it using IDA, but the binary seems like have been obfuscated. Open the file using _PEiD_ software.

![43](https://user-images.githubusercontent.com/56353946/206091793-8cf2aa3e-e026-4d5e-b73d-9f9f1ee1528a.png)

So, it was compiled as [Visual Basic P-code.](http://www.woodmann.com/crackz/Tutorials/Vbpcode.htm) To decompile we must use [VB Decompiler Pro](http://www.vb-decompiler.org/).

**Step 3**:  
Decompile it using _VB Decompiler Pro_ and read the codes. On line `loc_404197`, we can see if text = _“N3f…N3f”_ it will print “_Thank you… bla bla.. “._

![44](https://user-images.githubusercontent.com/56353946/206091810-ddedf1cf-3c55-4d9b-a8a3-a8bcac9f0d3c.png)

**Step 5**:  
Copy the string, paste it into the text box and click “_Submit_” and we got the flag.

![45](https://user-images.githubusercontent.com/56353946/206091816-ab395990-45e7-4d9c-9460-1b3f4aad3fb6.png)

# 2013-Bin-100

![46](https://user-images.githubusercontent.com/56353946/206091826-ce78b17f-7a68-4196-bd39-d3141a07a41e.png)

**Step 1**:  
Run `file` command. It’s an ELF 64-bit.

![47](https://user-images.githubusercontent.com/56353946/206091843-864b3d2a-707d-4d59-96e8-0023075c31ad.png)

**Step 2**:  
Because it’s a 64-bit binary, open it using IDA 64-bit. After some exploration into the functions, I saw a string “_Is The Password. Good job!_” being move into `esi` register. But, before it being move, the program will call a function `_Z4passi`.

![48](https://user-images.githubusercontent.com/56353946/206091853-4df4acc8-fae1-45af-91e1-e18e789d707d.png)

**Step 3**:  
Double click on the function `_Z4passi` and I saw a lot of `mov` instruction into the register `[rbp+var_xx]`. It’s look like a set of char that being move into the `rbp`(s).

![49](https://user-images.githubusercontent.com/56353946/206091870-4890cd28-2b3b-4299-aabc-2854cd57ca29.png)

**Step 4**:  
Click on the hexa value, press _“R”_ and it will automatically convert into ascii character. Do it to all values, and we got the secret string!

![50](https://user-images.githubusercontent.com/56353946/206091889-c87e224d-c219-4b9f-b889-f5c00ab939c9.png)

The flag is `Aleph-One`

# 2013-Bin-50

**Step 1**:  
Execute the file with some random password, and it’s return a string “_Nothing to see here.”_

![52](https://user-images.githubusercontent.com/56353946/206091907-be48d327-e821-4346-865b-83a98d769f87.png)

**Step 2**:  
After some exploration into the functions, I saw a lot of mov instruction into the register`[rbp+var_xx]`. It’s look like a set of characters that being move into the `rbp`s. Click on the hexa value, press _“R”_ and it will automatically convert into ascii character. Do it to all values, and we got the password!

![53](https://user-images.githubusercontent.com/56353946/206091916-2336c0e8-105f-4fe8-92dd-ed08aa321ec7.png)

**Step 3**:  
Try the password, and it will print our sha-256 flag.

![54](https://user-images.githubusercontent.com/56353946/206091938-d6bb3d37-90c3-4875-9992-85c764add288.png)


