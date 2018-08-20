---
title:  "File Naming Convention"
date:   2018-08-10 16:18:32 +0800
tags: File Naming Convention 
---

# Purpose
Look at the file names

+ untitled.txt
+ lib.zip
+ 123.doc
+ tmp.txt
+ myidea.txt

Even if we can remember the content of the files temporarily, we will forget soon. 

A good file naming convention is good for organizing and retrieving our documents efficiently. 

# How To Name Files?

The most important things to remember about file naming are to be `consistent` and `descriptive` in naming and organizing your files so that it’s obvious where to find a file and what it contains.\[[1]\]

## Step 1 Decide the Information A File Name Should Express

It varies from fields/projects to fields/projects we are working on. 

It is suggested to include the information which indicates the content of the file. If we are cooperating with many others, it is also needed to distinguish our files from others'.

We can think of following information, each is regarded as a segment which will be used in Step 2.
+ Name of organization
+ Date or date range
+ Author
+ Name of project or program
+ Type of document, such as "Report", "Proposal" and "Invoice" etc.
+ Spatial data
+ Language of content
+ Version number of file

## Step 2 Decide the Sequence of Each Segment

Suppose we have decided the segments and their corresponding orders as following

1. Date the file created
2. Name of project
3. Type of document

The sequence should be decided based on how we retrieve documents. For example, if you are doing research and care about the chronological order, put `Date` as the first segment in a file name will be a good choice. 

[Organizational structure] can play an important role in deciding the sequence. In a functional structured organization, `Name of project` is usually not the first segment since most work is not organized by projects. 

If directory is used to organize files, the sequence of segments in the file name can be changed or the segments can even be ignored, since we can distinguish files from directory structure at first. 

## Step 3 Make File Naming Convention

Decide how to concatenate the sequence of segments defined in Step 2, and decide more naming rules include

+ Case (lowercase or ?)
+ Allowed characters in the file name
+ Acronyms table

For example, we decide to use underscore to concatenate segments

`DateCreated_ProjectName_TypeOfDocument[_ContentSummary].FileExtension `

+ CamelCase is used in each segment when necessary
+ Only alphabetic characters are allowed in each segment
+ No acronym is used

A valid file name is

`20180810_KiteRunner_Report_ReaderFeedbackAnalysis.pdf`

An invalid file name is 
`2018010_Kite Runner_Report_Reader Feedback Analysis.pdf`
since it has spaces.

Make sure everyone understand file naming convention, and follow it. 

`Exceptional cases are allowed in real situation`, as the Done Manifesto declares, `Done is better than perfect`.

# Tips

## Special Characters

A number of characters are reserved in some operating file systems. Following characters are disallowed in Windows (See [Naming convention in Windows]), while some of them can be used in Mac. If we are not sure the operating file systems our teammates use, avoid using following characters. 

+ < (less than)
+ \> (greater than)
+ : (colon)
+ " (double quote)
+ / (forward slash)
+ \ (backslash)
+ | (vertical bar or pipe)
+ ? (question mark)
+ \* (asterisk)

It's better to define `whitelist` instead `blacklist`.

### Hyphen (Dash) and Underscore

In programming language, hyphen (dash) in a file name might not be correctly recognized. For example, python [can not import module with dash in filename] although it's documented behavior. 

For software engineers, it's recommended to use `Underscore` instead of `Hyphen (Dash)` in naming source code files. 

### Spaces

Spaces are generally disallowed in file name. Although spaces are good for reading by human beings, it's not good for computers. Many systems/software can not properly handle spaces in file names. 

## Case

Some file systems are case sensitive while some are not, do not use case to distinguish files. 

## DO NOT make file names too long

### Acronym

For sequential numbering, use leading zeros to ensure files sort properly.
For example, use “0001, 0002…1001, etc” instead of “1, 2…1001, etc.”
1. Do not use spaces, because they are not recognized by some software.
Instead use underscores (file_name), dashes (file-name), no separation
(filename), or camel case (FileName).
Check out more information about File Naming Best Practices on the
Data Management Services web site at http://bit.ly/file_naming.
Consider including a README.txt file in your directory that explains your
naming convention along with any abbreviations or codes you have used.

A good format for dates is YYYYMMDD (or YYMMDD). This makes sure
all your files stay in chronological order, even over many years.
2. Don’t make file names too long; longer names do not work well with all
types of software.

# Practices & Examples
## IT Project

File is usually managed in version control system, therefore, version is not included in the file name.

LanguageCode\_DateCreated\_ProjectName\_TypeOfDocument\_Authors\[\_ContentSummary\]\.FileExtension

ContentSummary is optional

en_20180808_PomodoroToDo_Proposal_Ellison.md




Batch file renaming tools



## 
[1]:(http://guides.lib.purdue.edu/c.php?g=353013&p=2378293)

[2]:(https://library.stanford.edu/research/data-management-services/data-best-practices/best-practices-file-naming)

[3]:(file:///C:/Users/ganxiyun/Desktop/FileNamingHandout_v3.pdf)

[Language Code]:https://en.wikipedia.org/wiki/Language_code
https://en.wikipedia.org/wiki/ISO_639-1

[Special characters]:http://emails.illinois.edu/newsletter/158822.html

http://guides.library.illinois.edu/introdata/filenames

http://guides.library.illinois.edu/ld.php?content_id=41568981


https://library.stanford.edu/research/data-management-services/case-studies/case-study-file-naming-done-well

https://www.huridocs.org/2016/07/file-naming-conventions-why-you-want-them-and-how-to-create-them/

[Case studies, has Document Type](https://www.huridocs.org/wp-content/uploads/2016/07/ACHPR-file-naming-system.txt)

[Organizational structure]:https://en.wikipedia.org/wiki/Organizational_structure
[Naming convention in Windows]:https://docs.microsoft.com/en-us/windows/desktop/fileio/naming-a-file#naming-conventions

[can not import module with dash in filename]:https://bugs.python.org/issue909226