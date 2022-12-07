---
title: "Backdoor CTF Practice Arena - RE Challenges Walktrough"
date: "2019-04-02"
layout: single
tags:
- CTF
- RE
categories:
- Write-Up
---


Below are the few reverse engineering category's challenges that I've been solved. It was a pretty easy challenge to solve.

# Challenges

 1. [revfun](#revfun)
 2. [debug](#debug)
 3. [bin-easy](#bin-easy)
 4. [bin-medium](#bin-medium)
 5. [hiddenflag–easy](#hiddenflag–easy)
 6. [2013-bin-500](#2013-bin-500)
 8. [2013-bin-200](#2013-bin-200)
 9. [2013-bin-100](#2013-bin-100)
 10. [2013-bin-50](#2013-bin-50)

---
&nbsp;
&nbsp;

# revfun
![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/1.png){: .align-center}

**Step 1**:  
Run `file` command on the binary. Now we know that it’s a ELF 64 bit file.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/2.png){: .align-center}

**Step 2**:  
Use `strings` command to display all the strings on the binary and we can see there is a suspicious string `“dlr0w_s1h7_s1_yz4rC”`. If we reverse the char string, it said `“crazy_is_this_world”`.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/3.png){: .align-center}

**Step 3**:  
Try the password on the binary.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/4.png){: .align-center}

**Step 4**:  
Try on `netcat`, and it will print our flag.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/5.png){: .align-center}

---
&nbsp;
&nbsp;

# debug
![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/6.png){: .align-center}

**Step 1:** 
Run `file` command on the binary. Now we know that it’s a ELF 32 bit file.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/7.png){: .align-center}

**Step 2:**
Try to run the program, and it’s return nothing.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/8.png){: .align-center}

**Step 3:**
Open the program using IDA Pro. We can see on the left side of the IDA program, there is a bunch of function names. After little bit of exploration on the functions, the functions `sub_804849B` look interesting to me.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/9.png){: .align-center}

It push a strings `Printing flag`. So, I think this must be a function that print the flag. So, I rename the function `sub_804849B` to `PrintFlag` for easy for me to remember which function does the print flag.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/10.png){: .align-center}

**Step 4**:  
Okay all we need now it’s to patch the program that will jump to the `PrintFlag` function. First thing we must know, every program will start executing something at `_start` function. So, go to the `_start` and we can see it will call `_libc_start_main`.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/11.png){: .align-center}

Let’s patch the program by change the `_libc_start_main` function to `PrintFlag` function.

Click on `libc_start_main` function => Go to `edit` => `Patch program` => `Assemble`

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/12.png){: .align-center}

Change the `libc_start_main` function to `_PrintFlag` function. Then click OK. Then close.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/13.png){: .align-center}

**Step 5:**

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/14.png){: .align-center}

Save the patch by go to _edit_ => _Patch program_ => _Apply patches to input file._

**Step 6:**
After patched the program, just run the binary and it will print the flag.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/15.png){: .align-center}

---
&nbsp;
&nbsp;

# bin-easy
![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/16.png){: .align-center}

**Step 1:**
Run `file` command on the binary.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/17.png){: .align-center}

**Step 2:**
Run `strings` command and we got the sha256 of the flag.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/18.png){: .align-center}

---
&nbsp;
&nbsp;

# bin-medium
![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/19.png){: .align-center}

**Step 1**:  
Run `file` command on the binary.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/20.png){: .align-center}

**Step 2:**
Try execute the program, and input random passphrase and it return a string.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/22.png){: .align-center}

**Step 3:**
By analyzed the binary on IDA, if the input are wrong it will jump to a function that will print the string `Thank you, but you aint getting the flag`. I renamed the function to `WrongPassword` for easy for me to remember the function name.

So, all we need now it’s patch the conditional jump `jnz WrongPassword` to `jz WrongPassword`.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/23.png){: .align-center}

Save the patched program.

**Step 4:**
Run the program and enter a random passphrase. Now, it will print the sha256 of the flag.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/24.png){: .align-center}

---
&nbsp;
&nbsp;

# hiddenflag–easy
![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/25.png){: .align-center}

**Step 1**:  
Run `file` command on the binary first.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/26.png){: .align-center}

**Step 2**:  
Execute the binary and it’s return a string.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/27.png){: .align-center}

**Step 3**:  
Issuing `strings` command and from there we got the sha256 of the flag.
https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/.png
![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/28.png){: .align-center}

---
&nbsp;
&nbsp;

# hiddenflag-medium
![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/29.png){: .align-center}

**Step 1**:  
Issue `file` command on the binary.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/30.png){: .align-center}

**Step 2**:  
Reverse engineering it using _gdb debugger_ by run `_gdb ./<filename>`.  After that, display all the functions of the binary by supply “_info functions”_ in gdb debugger. It will list all the functions.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/31.png){: .align-center}

From the list of functions gdb echoed, we can see there is a function name `_print flag` . That is interesting.

**Step 3**:  
Now all we need is jump to the function `_print flag` and the program will print the flag.

So, breakpoint to the entry point function which is `_start` function. Use b_ for breakpoint and address of the function that we want to break. 
Here the command: 
`b *0x080483a0`

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/32.png){: .align-center}

**Step 4**:  
After put a breakpoint, execute the program by type `run` and enter.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/33.png){: .align-center}

**Step 5**:  
Now it will break to the address that we’ve been break which is `0x080483a0`.Now all we need is jump to the function “print flag” and the program will print the flag. Type `jump <printflag address>` and the program will print the sha256 of the flag.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/34.png){: .align-center}

---
&nbsp;
&nbsp;

# 2013-bin-500
![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/35.png){: .align-center}

**Step 1**:  
Run `file` command on the binary. The file is PE32 file.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/36.png){: .align-center}

**Step 2**:  
Execute the program and it will ask for the registration key. Try some random key and it will print `Invalid key`.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/37.png){: .align-center}

**Step 3**:  
I try open it using IDA, but the binary seems like have been obfuscated. To detect what packer have been use, open the file using _PEiD_ software.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/38.png){: .align-center}

It display “Microsoft Visual Basic 5.0”.

Googling some Microsoft Visual Basic 5.0 unpacker. It said

“Your program is not packed, but rather compiled as [Visual Basic P-code](http://www.woodmann.com/crackz/Tutorials/Vbpcode.htm) or Visual Basic native code. If it’s VB native code, you can use your favorite debugger (OllyDbg, IDA, etc.) to debug it, and IDA to disassemble it. If it’s VB P-code, you can use [VB Decompiler Pro](http://www.vb-decompiler.org/) to disassemble/decompile it.”

So compiled as [Visual Basic P-code.](http://www.woodmann.com/crackz/Tutorials/Vbpcode.htm) To decompile we must use [VB Decompiler Pro](http://www.vb-decompiler.org/).

**Step 4**:  
Decompile it using _VB Decompiler Pro_ and read the codes. On line `loc_404D2E`, we can see if text = `&H289BC` it will print “_Thank you… bla bla.. “._

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/39.png){: .align-center}

**Step 5**:  
Copy the string, paste it into the text box and click “_Submit_” and we got the flag.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/40.png){: .align-center}

---
&nbsp;
&nbsp;

# 2013-Bin-200

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/41.png){: .align-center}

**Step 1**:  
Execute the program and it will ask for the registration key. Try some random key and it will print “_Invalid key.”_

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/42.png){: .align-center}

**Step 2**:  
I try open it using IDA, but the binary seems like have been obfuscated. Open the file using _PEiD_ software.
![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/43.png){: .align-center}

So, it was compiled as [Visual Basic P-code.](http://www.woodmann.com/crackz/Tutorials/Vbpcode.htm) To decompile we must use [VB Decompiler Pro](http://www.vb-decompiler.org/).

**Step 3**:  
Decompile it using _VB Decompiler Pro_ and read the codes. On line `loc_404197`, we can see if text = _“N3f…N3f”_ it will print “_Thank you… bla bla.. “._

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/44.png){: .align-center}

**Step 5**:  
Copy the string, paste it into the text box and click “_Submit_” and we got the flag.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/45.png){: .align-center}

---
&nbsp;
&nbsp;

# 2013-Bin-100
![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/46.png){: .align-center}

**Step 1**:  
Run `file` command. It’s an ELF 64-bit.
![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/47.png){: .align-center}

**Step 2**:  
Because it’s a 64-bit binary, open it using IDA 64-bit. After some exploration into the functions, I saw a string “_Is The Password. Good job!_” being move into `esi` register. But, before it being move, the program will call a function `_Z4passi`.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/48.png){: .align-center}

**Step 3**:  
Double click on the function `_Z4passi` and I saw a lot of `mov` instruction into the register `[rbp+var_xx]`. It’s look like a set of char that being move into the `rbp`(s).

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/49.png){: .align-center}

**Step 4**:  
Click on the hexa value, press _“R”_ and it will automatically convert into ascii character. Do it to all values, and we got the secret string!

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/50.png){: .align-center}

The flag is `Aleph-One`

---
&nbsp;
&nbsp;

# 2013-Bin-50

**Step 1**:  
Execute the file with some random password, and it’s return a string “_Nothing to see here.”_
![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/52.png){: .align-center}

**Step 2**:  
After some exploration into the functions, I saw a lot of mov instruction into the register`[rbp+var_xx]`. It’s look like a set of characters that being move into the `rbp`s. Click on the hexa value, press _“R”_ and it will automatically convert into ascii character. Do it to all values, and we got the password!

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/53.png){: .align-center}

**Step 3**:  
Try the password, and it will print our sha-256 flag.

![enter image description here](https://raw.githubusercontent.com/fareedfauzi/fareedfauzi.github.io/master/assets/images/backdoorctf/54.png){: .align-center}
