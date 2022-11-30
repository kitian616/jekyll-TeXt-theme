---
title: "SSH Keys and Auth - Part 1 of 3"
toc: true
mermaid: true
categories: [Commandline]
tags:
  - cli
  - linux
  - macos
---

In this 'back to basics' blog entry we're going to talk about setting up SSH keys. SSH keys are used for logging in to stuff and authorizing to other stuff (like GiT). You can create a PRIVATE PULBIC keypair and share the PUBLIC key with the world!

Note, this guide is bases on macOS

## Getting started
1. Start by opening a terminal.
2. Use the following command to generate a 4096 bit RSA key with you e-mail-address as the leu comment. Key-comments are optioneel and can be found at the end of public key. They help a lot with identifying keys so use them! You can use a e-mail to identify a key or anything else you like:

`ssh-keygen -t rsa -b 4096 -C "your_email@example.com"`

Sidenote, `ed25519` is up and coming to replace RSA as the default encryption protocol. At this point a lot of automation does not yet accept these keys for authentication (Like Azure DevOps GiT and Azure Resource Templates). That's why we're using RSA at this point.

3. You can choose where to save the key. Accept the default location or choose your own:
`Enter a file in which to save the key (/Users/you/.ssh/id_rsa): [Press enter]`

4. Now you get the option to set a phassphrase. This is a password that encrypts your private key. Please do so:
```bash
Enter passphrase (empty for no passphrase): [Type a passphrase]
Enter same passphrase again: [Type passphrase again]
```

The PRIVATE PUBLIC pair has now been created. If you accepted the default location and name you will find 2 files in ~/.ssh

* id_rsa
* id_rsa.pub

## Adding the key to you SSH Agent
Adding keys to your agent is pretty easy, use this command to start the agent:

```bash
eval "$(ssh-agent -s)"
> Agent pid 59566
```

Use this to load the file:

```bash
ssh-add ~/.ssh/NameOfKey
```

Bonus, (when on macOS 10.12.2 or newer, edit the `config` file in `~/.ssh/config` to include:

```bash
Host *
  AddKeysToAgent yes
  UseKeychain yes
  IdentityFile ~/.ssh/id_rsa
```

After doing this, add your key using:

```bash
ssh-add -K ~/.ssh/NameOfKey
```

## Using you public key
Now to the last step. You have to 'distribute' you SSH Public Key. SSH keys are mostly used to authenticate to GiT repository's or Linux server. You can also include a public key in most automated deployments on Azure and AWS.

If you want to add your public key to a Linux VM you can do this by adding it to the `/home/user/.ssh/authorized_keys` of the user you want to login with. 

Bonus. On macOS use this command to copy your key to your Clipboard:

```bash
pbcopy < .ssh/NameOfKey.pub
```

After adding the key to your keychain and adding it to the server you should be able to login using:
```bash
ssh user@computer.domein
```

You can also use this command to test the login:
```bash
ssh -T user@computer.domein
```

And you can specify the key to use if you'd like (more on this in part 2, working with SSH CONF)
```bash
ssh -i .ssh/NameOfKey user@computer.domein
```