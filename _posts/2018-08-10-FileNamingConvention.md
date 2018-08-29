---
title:  "File Naming Convention"
date:   2018-08-10 16:18:32 +0800
tags: File Naming Convention 
key: 2018-08-10-FileNamingConvention
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

The most important things to remember about file naming are to be `consistent` and `descriptive` in naming and organizing your files so that it’s obvious where to find a file and what it contains. \(From [File naming convention in Purdue Libraries]\)

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

+ Case (lowercase or uppercase)
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

## Have Several Naming Conventions in One Team/Project

It's quite possible that in the same team/project, we have several naming conventions for different document types. Date is better to be included in the name of documents for meeting summary while it is not important for document like `business plan` in a project.

## Special Characters

A number of characters are reserved in some operating file systems. Following characters are disallowed in Windows (See [Naming convention in Windows]), while some of them can be used in Mac. If we are not sure the operating file systems our teammates use, avoid using following characters. 

+ < (less than)
+ \> (greater than)
+ : (colon)
+ " (double quote)
+ / (forward slash)
+ \ (backslash)
+ \| (vertical bar or pipe)
+ ? (question mark)
+ \* (asterisk)

It's better to define `whitelist` instead of `blacklist`.

### Hyphen (Dash) and Underscore

In programming language, hyphen (dash) in a file name might not be correctly recognized. For example, python can not import module with dash in filename \(See issue [Can not import a module with DASH in filename]) although it's documented behavior. 

For software engineers, it's recommended to use `Underscore` instead of `Hyphen (Dash)` in naming source code files. 

### Spaces

Spaces are generally disallowed in file name. Although spaces are good for reading by human beings, it's not good for computers. Many systems/software can not properly handle spaces in file names. 

## DO NOT make file names too long

Long file name is not supported in some systems/software. Some ways to shorten the file name:

1. Use acronyms 
2. Use short date format
3. Use [language code]

## Sequential numbering

Use leading zeros to ensure files be sorted properly. For example, `PartialData_001, PartialData_002, etc.` instead of `PartialData_1, PartialData_2, etc.`.

## Format for Date

Use `YYYYMMDD` (or `YYMMDD`) to make sure files are sorted in chronological order. 

## Case

Some file systems are case sensitive while some are not, do not use case to distinguish files. 

# Practices & Examples
## Software and Its Engineering

Matrix organizational structure is widely used in software development, in such [organizational structure], 

+ In a team whose members are developers (programmers)
  + For documents like `Software Requirement Specification` of which the number is fixed, use
    +  `ProjectName_TypeOfDocument_Authors[_ContentSummary].FileExtension`
  + For documents like meeting summary of which the number will increase as time flies, use 
    + `ProjectName_TypeOfDocument_DateCreated_Authors[_ContentSummary].FileExtension`
+ In a team whose members are testers or quality engineers, following sounds good
  + `TypeOfDocument_ProjectName[_DateCreated]_Authors[_ContentSummary].FileExtension`

\* \[_ContentSummary is optional\] means it's optional. 

File is usually managed in version control system or there is a revision history table in the content, therefore, version is not included in the file name.

# Prospects

Due to the limitation of the traditional file systems, files are organized in a tree structure. However, our work looks like graphs, so do our brains. One kind of research and implementation is called [Semantic file system]. Some modern online file systems can create tags for each file, versions can also be controlled. 

We can expect better ways in document management. In any case, `keep consistent` and `be descriptive` are vital.

# Appendix

## Acronym in File Name

An Example

|Definition|Acronym|
|---|---|
|Investigation Report|IR|
|Daily/Weekly Operating Report|DWOR|

# References
\[1\] [File naming convention in Purdue Libraries]

\[2\] [Organizational structure]

\[3\] [Naming convention in Windows]

\[4\] [Can not import a module with DASH in filename]

\[5\] [Language Code]

\[6\] [Semantic file system]

\[7\] [Best practices for file naming in Stanford Libraries]

\[8\] [File Naming Conventions in Illinois University]

\[9\] [File Naming Case Study in Stanford]

[File naming convention in Purdue Libraries]: http://guides.lib.purdue.edu/c.php?g=353013&p=2378293

[Best practices for file naming in Stanford Libraries]:(https://library.stanford.edu/research/data-management-services/data-best-practices/best-practices-file-naming)

[Language Code]:https://en.wikipedia.org/wiki/Language_code

[File Naming Conventions in Illinois University]: http://guides.library.illinois.edu/introdata/filenames

[File Naming Case Study in Stanford]: https://library.stanford.edu/research/data-management-services/case-studies/case-study-file-naming-done-well

[Organizational structure]:https://en.wikipedia.org/wiki/Organizational_structure

[Naming convention in Windows]:https://docs.microsoft.com/en-us/windows/desktop/fileio/naming-a-file#naming-conventions

[Can not import a module with DASH in filename]:https://bugs.python.org/issue909226

[Semantic file system]: https://en.wikipedia.org/wiki/Semantic_file_system