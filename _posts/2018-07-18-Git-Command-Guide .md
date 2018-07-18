---
layout: article
title: Git Command Guide
key: Git-Notes
tags: git, computer science
---

# Lesson 1 

 Initializing git project:
```sh
$ git init
```

Three parts of git workflow:
 - Working Directory
 - Staging Area
 - Repository

Checking status of changes:
```sh
$ git status
```
<!--more-->
Adding files to staging area:
```sh
$ git add filename
```
Checking the differences between the working directory and the staging area:
```sh
$ git diff filename
```

Storing changes from the staging area inside the repository:
```sh
$ git commit -m "commit message"
```
 Standard Conventions for Commit Messages:
+ Must be in quotation marks
+ Written in the present tense
+ Should be brief (50 characters or less) when using _-m_

Viewing commits stored chronologically in the repository:
```sh
$ git log
```
You can press *q* to quit
# Lesson 2

Viewing head commit (the commit you are currently on, mostly the most recent commit):
```sh
$ git show HEAD
```
Restoring the file in working directory from the last commit:
```sh
$ git checkout HEAD filename
```
Adding multiple files into the staging area in one command:
```sh
$ git add filename_1 filename_2
```
Unstaging files from the staging area
```sh
$ git reset HEAD filename
```
Rewinding to previous commit:
```sh
$ git reset (the first 7 characters of the SHA)
```
# Lesson 3
Viewing all branches:
```sh
$ git branch
```

Creating new branch:
```sh
$ git branch <new_branch>
```
Switching to new branch:
```sh
$ git checkout <new_branch>
```
Merging the branch into master:
*Merge conflict occurs when you commit different changes in both new branch and the master.*
```sh
$ git merge <branch_name>
```
Deleting a branch:
```sh
$ git branch -d <branch_name>
```
# Lesson 4
Cloning a remote repository:
__Remark__: Git will give the remote address the name *origin*
```sh
$ git clone <remote_location> <local_clone_name>
```
Viewing a list of a Git project's remotes:
```sh
$ cd <local_clone_name>
$ git remote -v
```
Viewing if changes have been made to the remote and bring the changes down to your local copy:
```sh
$ cd <local_clone_name>
$ git fetch
```
Integrating origin/master into your local master branch:
```sh
$ cd <local_clone_name>
$ git merge origin/master
```
Now that you've merged *origin/master* into your local *master* branch, you're ready to contribute some work of your own. The workflow for Git collaborations typically follows this order:
1. Fetch and merge changes from the remote
2. Create a branch to work on a new project feature
3. Develop the feature on your branch and commit your work
4. Fetch and merge from the remote again (in case new commits were made while you were working)
5. Push your branch up to the remote for review

Steps 1 and 4 are a safeguard against merge conflicts, which occur when two branches contain file changes that cannot be merged with the git merge command. 
Step 5:
```sh
$ git push origin <local_clone_name>
```
