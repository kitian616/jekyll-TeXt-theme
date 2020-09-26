---
title: Git is going to eat you and how to pretend your know Git.
layout: article
---
## Prologue

This is my first post in a series of posts called "First Day at Work". My aim is to help people on their first day as professional software developers. I'm going to try to introduce tools, concepts, tips and skills you are going to need from day one. None of the posts will be a deep dive into any of the topics, my goal is not to make you an expert, but to make you feel more comfortable, confident and not feel overwhelmed on you first day
at work.


## Git

```Git is a distributed version-control system for tracking changes in source code during software development.``` blah, blah, blah.

There are several other version control systems like SVN & Mercurial. According to [Stack Overflow Developer Survey 2018](https://web.archive.org/web/20190530142357/https://insights.stackoverflow.com/survey/2018/#work-_-version-control) chances are, Git is what you are going to use at work, thats what I use as well so here we are. I'm going to explain what Git does in simple terms and give you a few commands that you are going to use frequently with simple examples.

Git is a very powerful tool to keep a history of file versions and enable collaboration of several people on a set of files. This is extremely useful for software developers, this will become obvious as we go.

## Commits

In its core Git keeps track of changes you make to a file(s). Think about how extremely useful this is. You can create a file, modify it several times and then go trough all the changes you have made. Here is the thing, Git won't remember any of the changes you have made if you don't manually save the state you would like it to remember. Saving a state (or snapshot) is known as creating a commit. A commit remembers what your files look like (basically takes a picture of what all your files look like at that moment and stores a reference to that snapshot - [What is Git?](https://git-scm.com/book/en/v2/Getting-Started-What-is-Git%3F#what_is_git_section)). Let's do some practice.


> Note:
> Please don't diverge from this post, do not try to read the linked tutorials,
or other references. This post is designed in a way to hide Git's implementation details and focus on its usability, especially on features you will be using from day one. I will try to gradually introduce you to more advanced topics but at this point I don't want you to look into Git's internals because you will either get lost and we will never find you again or the topic will become so overwhelming that you will simply decide to 
try random stuff until it works. This is the exact opposite of what I want to achieve with this series of blogs.


### Scenario A

Imagine that you want to develop a computer program which would read a list of numbers from a file, calculate the sum of evens and write the same list of numbers into another file (we are going to output the sum of evens later on as well). I would break such a program in three parts: **a.** read a list of numbers into memory; **b.** calculate the sum of even numbers; **c.** write the initial numbers in a file. This is also how I would organize my commits. This would give me several advantages, some of which I have listed below:

1.  It would be easy to see my thinking process if I wanted to go back and check this code. Imagine that you haven't worked on something for a few days and you want a quick refresher of what you did, reading the commits is a quick way of doing it.
2. It would be easier for the code reviewers to see how was this program built. When you will be reviewing someone's code you are going to notice that looking at a large difference between how a file was and how it became can be really tricky. Sometime it's just easier to look at individual commits and try to understand changes in a very limited scope or at least understand the steps behind the change.
3. You will be surprised how many times you will start building something and at some point realize you have taken a wrong or just not the best direction. If you have committed your code frequently and logically, you will be able to roll back to a point from which you could resume your development process and take it to a different direction without starting completely over (top tip: Sometimes I find myself going into a wrong direction then rolling back and taking a new one, only to realize that the first way was actually better. For this reason do not delete your work until you are absolutely sure you won't need it. The wisest thing to do is to start another history and then roll back so you keep both options available until you are absolutely sure).
4. There are a lot ways to manipulate commits, one of my favorite is cherry-picking. This is literally picking one or more commits from one history and injecting them into another. 


Alright, here is the process of writing the above program (pseudo-python). I strongly suggest that you follow the exact process. This is so you can build the muscle memory for writing `git` commands and the habit of committing frequently.
Let create a directory (I'm assuming your are using a *nix OS).

```bash
Sergiys-MacBook-Pro:blog_all sbagda01$ mkdir code
Sergiys-MacBook-Pro:blog_all sbagda01$ cd code
Sergiys-MacBook-Pro:code sbagda01$ git init # initialize git
Initialized empty Git repository in /Users/sbagda01/Documents/blog_all/code/.git/
```

Alright, lets create a new file:

```bash
Sergiys-MacBook-Pro:code sbagda01$ touch calculate_the_sum.py
```

Write some code:

```python
"""calculate_the_sum.py"""

def read_numbers_from_file():
    file = open("file.txt")
    return list(file.read())
```

I think we have completed an important part of our program (I'm not going to include tests in this post to keep it short and as you can see I'm not following a test driven approach. In fact my code won't even work. My goal here is not to teach programming or any language but to introduce `git`) so lets make our first commit.

```bash
Sergiys-MacBook-Pro:code sbagda01$ git status # check what's up
On branch master

No commits yet

Untracked files:
  (use "git add <file>..." to include in what will be committed)

	calculate_the_sum.py

nothing added to commit but untracked files present (use "git add" to track)
```

Git is not aware of the created file.

```bash
Sergiys-MacBook-Pro:code sbagda01$ git add calculate_the_sum.py
Sergiys-MacBook-Pro:code sbagda01$ git status
On branch master

No commits yet

Changes to be committed:
  (use "git rm --cached <file>..." to unstage)

	new file:   calculate_the_sum.py
```

At this point Git knows that there is a new file called *calculate_the_sum.py* but as per the message above we haven't created any commits yet.

```bash
Sergiys-MacBook-Pro:code sbagda01$ git commit -am "Reading numbers from file.txt to a list."
[master (root-commit) 232cf51] Reading numbers from file.txt to a list.
 1 file changed, 6 insertions(+)
 create mode 100644 calculate_the_sum.py
```

There it is, we have created our first commit. Go on and run `man git-commit` to find out what are the `-am` switches I have used above.

Let's finish our little program and add couple of more commits.

```python
"""calculate_the_sum.py"""

...

def calculate_the_sum_of_evens_in_a_list(list_of_numbers):
    return sum([n for n in list_of_numbers if not n % 2])
```

```bash
Sergiys-MacBook-Pro:code sbagda01$ git status # confirm that we modified the right file
On branch master
Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

	modified:   calculate_the_sum.py

no changes added to commit (use "git add" and/or "git commit -a")
```

```bash
Sergiys-MacBook-Pro:code sbagda01$ git diff # check how has this file changed
diff --git a/calculate_the_sum.py b/calculate_the_sum.py
index 771b89d..b64971b 100644
--- a/calculate_the_sum.py
+++ b/calculate_the_sum.py
@@ -4,3 +4,6 @@ def read_numbers_from_file():
     file = open("file.txt")
     return list(file.read())

+
+def calculate_the_sum_of_evens_in_a_list(list_of_numbers):
+    return sum([n for n in list_of_numbers if not n % 2])
```

Running `git status` and `git diff` every time might look like an overkill, but believe me it will save you some trouble and a lot of time in the long term and it is a really good
idea to build it into your muscle memory. Once, I released a python package thinking that I have include all the necessary changes. This was a dependency on a bigger application, after making use of package's new version I couldn't get the behavior I needed. Now, the hard part comes from the fact everything was working as expected when using my local version of the library but wasn't working as expected using the freshly released version. Finally, after an hour of questioning my life's and career's choices I actually compared the code of the version I realized and the previous one and guess what, the change I really needed wasn't there. I hadn't added and committed the changed file properly. 

A much more frequent example is temporarily modifying a file (to check something on the fly) and then committing the change. You (or your reviewers or the tests) will most probably catch this but there go 15 minutes of wondering what is going on with your code. This kind of errors (and believe me, much trickier ones) happen and tricks like using `git status` and `git diff` for a quick check are a great way to avoid them, for the sake of your own well-being.

```python
Sergiys-MacBook-Pro:code sbagda01$ git commit -am "Calculating the sum of evens in a list."
[master 1da1da1] Calculating the sum of evens in a list.
 1 file changed, 3 insertions(+)
```

## Check the commits log

A great `git` command is `git log`. There are two main applications I have found very useful. First is making sure that the commits history looks it should. For example:

```bash
Sergiys-MacBook-Pro:code sbagda01$ git log
commit 1da1da1f50ab57704a0a591fead0d1694f20d568 (HEAD -> master)
Author: Sergiy Bagdasar <sergio17949@gmail.com>
Date:   Sat Sep 12 12:11:39 2020 +0100

    Calculating the sum of evens in a list.

commit 232cf516e71d3b4b7b346ba0bd3ce788b4e58919
Author: Sergiy Bagdasar <sergio17949@gmail.com>
Date:   Wed Sep 9 23:02:39 2020 +0100

    Reading numbers from file.txt to a list.
```

There is a shorter version as well:

```bash
Sergiys-MacBook-Pro:code sbagda01$ git log --oneline
1da1da1 (HEAD -> master) Calculating the sum of evens in a list.
232cf51 Reading numbers from file.txt to a list.
```

The second application of this command is to check if a piece of work is part of the history. This requires using references in your commit messages. For example if calculating the sum of event in as list was a part of a ticket with `T-100` as a reference 
and reading numbers from a file was part of a ticket with `T-99` as reference, a good practice would be to include that in our commit messages like:

```bash
Sergiys-MacBook-Pro:code sbagda01$ git log --oneline
1da1da1 (HEAD -> master) T-100: Calculating the sum of evens in a list.
232cf51 T-99: Reading numbers from file.txt to a list.
```

Now imagine having hundreds of commits around these two, how would you check if calculating the sum of evens in a list is part of it. 

```bash
Sergiys-MacBook-Pro:code sbagda01$ git log --oneline | grep -i T-99
232cf51 T-99: Reading numbers from file.txt to a list.
```

And here is our answer, T-99 is part of this history (note that in a more realistic scenario T-99 would probably have more than one commits). Anyway, let's continue working on our program.

```python
"""calculate_the_sum.py"""

...

def write_list_of_numbers_to_file(list_of_numbers):
    file = open("output.txt", "w")
    file.write(", ".join(", ".join(str(n) for n in list_of_numbers)))
```

Alright `git status` and `git diff` look good so:

```bash
Sergiys-MacBook-Pro:code sbagda01$ git commit -am "I've made a mess."
[master f5f8540] I've made a mess.
 1 file changed, 5 insertions(+)
```

Oh no, I've literally made a mess out of my commit. Fear not, `git reset HEAD^n` is a great way to softly reset the last `n` commits (softly means that your code changes will still be there, you have just deleted the milestones, be aware that if you have already pushed your code to a repository there will be a conflict when you try to push again after the soft reset, we will take a look at these later). This is very useful if you simply want combine the last several commit into one or do more work before committing or just commit again with a different message (I know you can also do the former by `git commit --ammend` but I refuse to remember more commands than it is necessary). 

```bash
Sergiys-MacBook-Pro:code sbagda01$ git reset HEAD^
Unstaged changes after reset:
M	calculate_the_sum.py
```

Ha, so `git reset HEAD^` tells us that there are modifications to our file and they aren't
staged for commit. Makes sense since we are in the same position as we were just before making a mess.   

```bash
Sergiys-MacBook-Pro:code sbagda01$ git log --oneline # the log confirms it
1da1da1 (HEAD -> master) Calculating the sum of evens in a list.
232cf51 Reading numbers from file.txt to a list.
Sergiys-MacBook-Pro:code sbagda01$
```

Lets try committing again:

```bash
Sergiys-MacBook-Pro:code sbagda01$ git commit -am "Writing a list of numbers to a file."
[master ff12e05] Writing a list of numbers to a file.
 1 file changed, 5 insertions(+)

Sergiys-MacBook-Pro:code sbagda01$ git log --oneline
ff12e05 (HEAD -> master) Writing a list of numbers to a file.
1da1da1 Calculating the sum of evens in a list.
232cf51 Reading numbers from file.txt to a list.
```

### Commit Hashes

You have probably noticed the weird hexadecimal string appearing alongside our commit messages (ff12e05, 1da1da1, 232cf51). These are abbreviations of commit hashes. Git uses the **SHA-1** hash function to calculate commit hashes. The input is data [like your name, the date, the commit message, the changes in the commit, and most importantly, the commit hash of the previous commit](https://pretextbook.org/gfa/html/commit-hashes.html). And before you ask, [there is a possibility of collision between hashes](https://shattered.io/) but it's extremely low and hosting services like GitHub have collision detection mechanisms in place to protect their content. Each hash is a 40 digit string but git usually tries to present a shorter (but still probably unique) version like the 7 digit strings you can see above. Here are the full hashes of our commits:

```bash
Sergiys-MacBook-Pro:code sbagda01$ git log --shortstat --pretty=oneline
ff12e0520b61276fac959dffcb14833a83abad67 (HEAD -> master) Writing a list of numbers to a file.
 1 file changed, 5 insertions(+)
1da1da1f50ab57704a0a591fead0d1694f20d568 Calculating the sum of evens in a list.
 1 file changed, 3 insertions(+)
232cf516e71d3b4b7b346ba0bd3ce788b4e58919 Reading numbers from file.txt to a list.
 1 file changed, 6 insertions(+)
```

To summarize, each time you commit, you create a commit object which represent how the files in the repository look (snapshot), and the commit is identified by a hash string.

Perfect, but don't leave me just yet. From this point on, I'm going to put things in a 
wider context so we can explore more of Git's powers.

## Branches

Branches are a powerful way to diverge from a history. Git automatically creates the
`master` branch when we run `git init`. In the examples above you have seen the following notation: `(HEAD -> master)`. It means that we have been working on the `master` branch, more precisely, `HEAD` is a pointer to our selected (checked-out) branch. In fact the branch itself is a pointer to our last commit or the last checked-out commit. In the last example above `HEAD` points to `master`, and `master` points to the `ff12e05` commit.

To list all of the branches we can run:

```bash
Sergiys-MacBook-Pro:code sbagda01$ git branch -a
* master
```

At this point we only have the `master` branch. The star on the left designates the selected branch. To create a new branch we can run `git branch new_branch_name`
and `git checkout new_branch_name` in order to select it. I prefer to do both in one `git checkout -b new_branch_name` which will automatically create the branch if it does not already exist and then check it out.

### Scenario B

Imagine that we need to calculate the average of the number we read from the list and
print it to a file as well. But and it's a big one, we aren't allowed to modify the code in our master branch, either because it's being used by someone else (we are going to see how this works) or simply because we want to keep the original implementation intact until
the new feature is completed. This is the perfect use for branches. 

```bash
Sergiys-MacBook-Pro:code sbagda01$ git branch
* master
Sergiys-MacBook-Pro:code sbagda01$ git checkout -b calculating_an_average
Switched to a new branch 'calculating_an_average'
Sergiys-MacBook-Pro:code sbagda01$ git branch
* calculating_an_average
  master
```

We have created a new branch called *calculating_an_average* and checked it out. From

```bash
Sergiys-MacBook-Pro:code sbagda01$ git log --oneline
ff12e05 (HEAD -> calculating_an_average, master) Writing a list of numbers to a file.
1da1da1 Calculating the sum of evens in a list.
232cf51 Reading numbers from file.txt to a list.
```

We can see that `HEAD` points to `calculating_an_average` and both `calculating_an_average` and `master` point to `ff12e05`. This makes sense since we haven;t added any commits on top of what we already had in our master branch, but it's time for this to change, here is our new addition:

```python
"""calculate_the_sum.py"""

...

def calculate_the_average_of_numbers_in_a_list(list_of_numbers):
    return sum(list_of_numbers) / len(list_of_numbers)
```

After making sure that everything is in order by running `git status` and `git diff`, we are ready to commit. 

```bash
Sergiys-MacBook-Pro:code sbagda01$ git commit -am "Calculating the average from a list of numbers."
[calculating_an_average 5d75fee] Calculating the average from a list of numbers.
 1 file changed, 3 insertions(+)
```

```bash
Sergiys-MacBook-Pro:code sbagda01$ git log --oneline
5d75fee (HEAD -> calculating_an_average) Calculating the average from a list of numbers.
ff12e05 (master) Writing a list of numbers to a file.
1da1da1 Calculating the sum of evens in a list.
232cf51 Reading numbers from file.txt to a list.
```

Now we can see that `HEAD` points to `calculating_an_average`, which points to `5d75fee` (our new commit) and `master` continues to point to `ff12e05`.

Let's complete this task.


```python
"""calculate_the_sum.py"""

...

def write_list_of_numbers_to_file(list_of_numbers):
    file = open("output.txt", "w")
    list_of_numbers_as_string = ", ".join(", ".join(str(n) for n in list_of_numbers))
    average_as_string = "Avg:" + str(calculate_the_average_of_numbers_in_a_list(list_of_numbers))
    output_string = list_of_numbers_as_string + "\n" + average_as_string
    file.write(output_string)
```

After doing `git status` and `git diff`, I'm ready to commit:

```bash
Sergiys-MacBook-Pro:code sbagda01$ git commit -am "Writing the average to output.txt."
[calculating_an_average 815e2f9] Writing the average to output.txt.
 1 file changed, 4 insertions(+), 1 deletion(-)
```

### Collaboration

Obviously, we don't wan't to keep *work in progress* in our main history as it should always be in a working and consistent state, ready to be deployed through our pipeline (more on pipelines in another post). We also want to give our colleagues the opportunity to review our code and request changes, suggest improvements, ask questions or simply tell us how awesome our work is. As you have probably guessed, branches enable the following pattern:

1. Keep the main code history in branch A (like our master branch).
2. Diverge from the main history to develop a feature or fix a bug by creating a new branch (like we did with the calculating_an_average branch which we started of off master).
3. When the work on the new branch is done it can be **merged** back into the main branch to become part of the main history.


### Scenario B'

Imagine that at the time when you were give **Scenario B**, another developer was given scenario B' which goes like this:

In Scenario A we have calculated the sum of even numbers from the list. Now we need to print it in the output file as well. But and it's a big one, we aren't allowed to modify the code in our master branch, either because it's being used by someone else (we are going to see how this works) or simply because we want to keep the original implementation intact until the new feature is completed.

> I know, you are probably wondering how would they have access to our code at all, but for now let's assume that the poor developers use the same computer.

Alright let's do this. First we need to create our feature branch of off master. You already know how to do that:


```bash
Sergiys-MacBook-Pro:code sbagda01$ git checkout master
Switched to branch 'master'
Sergiys-MacBook-Pro:code sbagda01$ git branch
  calculating_an_average
* master
```

```bash
Sergiys-MacBook-Pro:code sbagda01$ git checkout -b print_the_sum_of_evens
Switched to a new branch 'print_the_sum_of_evens'
Sergiys-MacBook-Pro:code sbagda01$ git log --oneline
ff12e05 (HEAD -> print_the_sum_of_evens, master) Writing a list of numbers to a file.
1da1da1 Calculating the sum of evens in a list.
232cf51 Reading numbers from file.txt to a list.
```

So far, so good. Let's write some code:

```python
"""calculate_the_sum.py"""

...

def write_list_of_numbers_to_file(list_of_numbers):
    file = open("output.txt", "w")
    sum_of_evens_as_string = str(calculate_the_sum_of_evens_in_a_list(list_of_numbers))
    numbers_as_string = ", ".join(", ".join(str(n) for n in list_of_numbers))
    file.write(numbers_as_string + "\n" + "Sum of evens: " + sum_of_evens_as_string)
```

Again, as always `git status`, `git diff` and then commit:


```bash
Sergiys-MacBook-Pro:code sbagda01$ git commit -am "Write the sum of evens to output.txt."
[print_the_sum_of_evens 94cc0b4] Write the sum of evens to output.txt.
 1 file changed, 3 insertions(+), 1 deletion(-)
Sergiys-MacBook-Pro:code sbagda01$ git log --oneline
94cc0b4 (HEAD -> print_the_sum_of_evens) Write the sum of evens to output.txt.
ff12e05 (master) Writing a list of numbers to a file.
1da1da1 Calculating the sum of evens in a list.
232cf51 Reading numbers from file.txt to a list.
```

At some point (usually after passing all of the tests and/or getting approvals from colleagues) both our developers would like to **merge** their features into the main history.

## Merging

[Git merge will combine multiple sequences of commits into one unified history. In the most frequent use cases, git merge is used to combine two branches](https://www.atlassian.com/git/tutorials/using-branches/git-merge). Let's see how that works

Currently we have three branches.

```bash
Sergiys-MacBook-Pro:code sbagda01$ git branch 
  calculating_an_average
* master
  print_the_sum_of_evens
```

And here is how to visualize the current history:

```bash
Sergiys-MacBook-Pro:code sbagda01$ git log --graph --decorate --oneline --all
* 94cc0b4 (print_the_sum_of_evens) Write the sum of evens to output.txt.
| * 815e2f9 (calculating_an_average) Writing the average to output.txt.
| * 5d75fee Calculating the average from a list of numbers.
|/  
* ff12e05 (HEAD -> master) Writing a list of numbers to a file.
* 1da1da1 Calculating the sum of evens in a list.
* 232cf51 Reading numbers from file.txt to a list.
```

Notice how **master** still points to **ff12e05** and **print_the_sum_of_evens** points to **94cc0b4**. The second developer has decided to merge the **print_the_sum_of_evens** branch into **master**.


```bash
Sergiys-MacBook-Pro:code sbagda01$ git merge print_the_sum_of_evens
Updating ff12e05..94cc0b4
Fast-forward
 calculate_the_sum.py | 4 +++-
 1 file changed, 3 insertions(+), 1 deletion(-)
```

Here we can see what we (or maybe it's just me) call a **Fast Forward Merge**. This means that the histories from the two branches (master and print_the_sum_of_evens) can be concatenated linearly. In practice this happens when no commits have been made to the main branch since you created your feature branch.

```bash
Sergiys-MacBook-Pro:code sbagda01$ git log --graph --decorate --oneline --all
* 94cc0b4 (HEAD -> master, print_the_sum_of_evens) Write the sum of evens to output.txt.
| * 815e2f9 (calculating_an_average) Writing the average to output.txt.
| * 5d75fee Calculating the average from a list of numbers.
|/  
* ff12e05 Writing a list of numbers to a file.
* 1da1da1 Calculating the sum of evens in a list.
* 232cf51 Reading numbers from file.txt to a list.
```

The only difference from earlier is that both **master** and **print_the_sum_of_evens** now point to **94cc0b4** commit. Let's now have branches lying around, we don't need **print_the_sum_of_evens** anymore. In order to delete it run:

```bash
Sergiys-MacBook-Pro:code sbagda01$ git branch -d print_the_sum_of_evens
Deleted branch print_the_sum_of_evens (was 94cc0b4).
```

Sometimes, mostly because Git wants to prevent data loss, it won 't let you delete the branch (most of the time it just means that you haven't updated your local history after merging on remote, don't worry if that doesn't mean much to you right now). You can force it with `git -D branch_name`.

The first developer (that would be you, I mean the other developer was also you, but whatever, you get the point) wants to merge the **calculating_an_average** branch into the master as well. There will be two lessons to be learned from the next merge. Here is the first one:

```bash
Sergiys-MacBook-Pro:code sbagda01$ git merge calculating_an_average 
Auto-merging calculate_the_sum.py
CONFLICT (content): Merge conflict in calculate_the_sum.py
Automatic merge failed; fix conflicts and then commit the result.
```

What happened here is that we have modified the same file in two different branches and merging them automatically is not possible. Take a look at our precious file:

```python
"""calculate_the_sum.py"""

def read_numbers_from_file():
    file = open("file.txt")
    return list(file.read())


def calculate_the_sum_of_evens_in_a_list(list_of_numbers):
    return sum([n for n in list_of_numbers if not n % 2])


def write_list_of_numbers_to_file(list_of_numbers):
    file = open("output.txt", "w")
<<<<<<< HEAD
    sum_of_evens_as_string = str(calculate_the_sum_of_evens_in_a_list(list_of_numbers))
    numbers_as_string = ", ".join(", ".join(str(n) for n in list_of_numbers))
    file.write(numbers_as_string + "\n" + "Sum of evens: " + sum_of_evens_as_string)
=======
    list_of_numbers_as_string = ", ".join(", ".join(str(n) for n in list_of_numbers))
    average_as_string = "Avg:" + str(calculate_the_average_of_numbers_in_a_list(list_of_numbers))
    output_string = list_of_numbers_as_string + "\n" + average_as_string
    file.write(output_string)

def calculate_the_average_of_numbers_in_a_list(list_of_numbers):
    return sum(list_of_numbers) / len(list_of_numbers)
>>>>>>> calculating_an_average
```

Let's try to interpret the above. First, **=======** is the center of the conflict. From **<<<<<<< HEAD** to **=======** is the text that exists in the branch pointed by the **HEAD**, in other words the current branch (in this case master). From **=======** to **>>>>>>> calculating_an_average** is the text that exists in the branch we are trying to merge in (in our case calculating_an_average). 

In order to complete our merge we need to resolve the above conflict, but how? We need to look at the requirements from Scenarios A, B and B'. We know that we need to read a list of numbers from a file, find the sum of evens, find the average and write these into **output.txt** Looking at our conflicts, the real issue appears in the `write_list_of_numbers_to_file` function. Everything else is code that just needs to be added in order to fulfill our requirements. 

Let's fix `write_list_of_numbers_to_file`.


```python
"""calculate_the_sum.py"""

...

def write_list_of_numbers_to_file(list_of_numbers):
    file = open("output.txt", "w")
    list_of_numbers_as_string = ", ".join(", ".join(str(n) for n in list_of_numbers))
    average_as_string = "Avg:" + str(calculate_the_average_of_numbers_in_a_list(list_of_numbers))
    sum_of_evens_as_string = str(calculate_the_sum_of_evens_in_a_list(list_of_numbers))
    output_string = list_of_numbers_as_string + "\n" + average_as_string + "\n" + sum_of_evens_as_string
    file.write(output_string)

def calculate_the_average_of_numbers_in_a_list(list_of_numbers):
    return sum(list_of_numbers) / len(list_of_numbers)
```

So `write_list_of_numbers_to_file` will print the list of numbers, the average and the sum of evens, each on a new line.


> Pro tip: This is a logical solution but in this kind of situation, try to confirm the requirements with your manager. You don't want to release the code, only to be told that the sum of evens should appear before the average.


> Pro tip: Before trying to merge a feature branch into the main history, first, merge the main branch into your feature branch. There are two main reasons for this. First you are making sure that there are no conflicts early on and the second is to run all of the tests on the absolute latest version of the code. In other words the tests should run on the code that is the absolute closest to what it will look like after merging. The reason is that, the tests might be working on your feature branch and on the main branch but, they might not work when you put the two together. This is not uncommon and usually happens because a part of the code you are working on, affects another part that was changed and merged to the main branch. Of course you have to actually have good tests but this a topic for another post. Remember, the goal is to keep the main history in a working state (at least with some certainty).

We aren't done yet. In order to finalize the merge we need to commit.

```bash
Sergiys-MacBook-Pro:code sbagda01$ git commit -am "merged calculating_an_average in."
[master 220c017] merged calculating_an_average in.
```

Don't be scared, plenty of tools (PyCharm, VSCOde etc..., I'm going to write another post on the developer's toolbox) help you solve the merge conflicts by visualizing the differences between the files and that makes it a lot easier.

Delete the **calculating_an_average** branch and check that there is only the *master* left.

```bash
Sergiys-MacBook-Pro:code sbagda01$ git branch -d calculating_an_average
Deleted branch calculating_an_average (was 815e2f9).
Sergiys-MacBook-Pro:code sbagda01$ git branch
* master
```

Here is another lesson that needs to be learned from this merge. 

```bash
Sergiys-MacBook-Pro:code sbagda01$ git log --graph --decorate --oneline --all
*   220c017 (HEAD -> master) merged calculating_an_average in.
|\  
| * 815e2f9 Writing the average to output.txt.
| * 5d75fee Calculating the average from a list of numbers.
* | 94cc0b4 Write the sum of evens to output.txt.
|/  
* ff12e05 Writing a list of numbers to a file.
* 1da1da1 Calculating the sum of evens in a list.
* 232cf51 Reading numbers from file.txt to a list.
```

See how two of our histories became one at the **220c017** commit? The merge commit is special because it has two parents (the other special commit is the first commit because it has no parents, all the others have exactly one). 

The more common situation is if we didn't have merge conflicts at all and in this case Git would create a merge commit automatically when we did `git merge` but you would still see a very similar picture.

## Remote

[Most Git operations are done locally. To communicate with the outside world, Git uses what are called remotes. These are repositories other than the one on your local disk which you can push your changes into (so that other people can see them) or pull from (so that you can get others changes)](https://stackoverflow.com/a/5617350/2942762). Usually, such repositories are hosted on a service like GitHub or BitBucket (the two most widely known), but a Git server can be setup on pretty much any computer.

You can list the existing remotes by running:

```bash
Sergiys-MacBook-Pro:code sbagda01$ git remote -v
```

### How to start a Git repository
As you can see we haven't added a remote to our repository yet. At this point it's worth mentioning that there are two ways to get started with a Git repository. The first is the one we used at the beginning of this post: `git init`. This can be used to initialize a Git repository within an existing (on you local disk) directory. If however there already is remote Git repository, you can use `git clone`. Git clone works with `https` (most commonly used) and `ssh` protocols. For example:

```bash
Sergiys-MacBook-Pro:~ sbagda01$ git clone https://github.com/sbagda01/sbagda01.github.io.git
Cloning into 'sbagda01.github.io'...
remote: Enumerating objects: 27673, done.
remote: Total 27673 (delta 0), reused 0 (delta 0), pack-reused 27673
Receiving objects: 100% (27673/27673), 99.25 MiB | 3.47 MiB/s, done.
Resolving deltas: 100% (18765/18765), done.
```

Pulls down and sets up the `https://github.com/sbagda01/sbagda01.github.io.git` repository on your local disk and automatically configures the `origin` remote for you.

```bash
Sergiys-MacBook-Pro:sbagda01.github.io sbagda01$ git remote -v
origin	https://github.com/sbagda01/sbagda01.github.io.git (fetch)
origin	https://github.com/sbagda01/sbagda01.github.io.git (push)
```

This means that you can fetch data from and push data to the `origin` (origin is just a short name for the remote URLs).

### Adding remotes

As you have seen above, our repository, does not have any remotes yet, let's change that. I'm going to use GitHub as our centralized hosting provider in order to store our repository. In order to do that you will have to create an account on [GitHub](https://github.com/) and create a repository. I'm going to create an repository called `git-is-going-to-eat-you-code`.

```bash
Sergiys-MacBook-Pro:code sbagda01$ git remote add origin https://github.com/sbagda01/git-is-going-to-eat-you-code/
Sergiys-MacBook-Pro:code sbagda01$ git remote -v
origin	https://github.com/sbagda01/git-is-going-to-eat-you-code/ (fetch)
origin	https://github.com/sbagda01/git-is-going-to-eat-you-code/ (push)
```

Now let's `push` our local repository to our remote repository on GitHub. 

> Note: You will be asked to enter your username and password unless you have already configured your GitHub credentials or a GitHub personal token (recommended) locally.

```bash
Sergiys-MacBook-Pro:code sbagda01$ git push origin master
Username for 'https://github.com': sbagda01
Password for 'https://sbagda01@github.com': password here
Enumerating objects: 21, done.
Counting objects: 100% (21/21), done.
Delta compression using up to 4 threads
Compressing objects: 100% (14/14), done.
Writing objects: 100% (21/21), 2.12 KiB | 1.06 MiB/s, done.
Total 21 (delta 6), reused 0 (delta 0)
remote: Resolving deltas: 100% (6/6), done.
```

The `git push origin master` command translates to "upload the local repository's selected branch (current HEAD) to origin's master branch". The opposite of pushing is... pulling. Git's `pull` command downloads the remote repository locally.

```bash
Sergiys-MacBook-Pro:code sbagda01$ git pull origin master
From https://github.com/sbagda01/git-is-going-to-eat-you-code
 * branch            master     -> FETCH_HEAD
Already up to date.
```

The `git pull origin master` command translates to "download the remote repository's master branch into local's repository selected branch (current HEAD)." 

Actually `git pull` is a shortcut for two commands, one of them we have already seen. First `git pull` will fetch (`git fetch`) data from the remote (commits, files, tags etc...) to make them visible to your local repository. This is useful (and necessary) because many changes can be caused by other users (or even yourself) to the remote repository, changes of which your local environment is not aware of.

The second command is the already familiar `git merge` which will combine the sequences of commits of the remote repository's master branch and the selected local branch.

## A complete workflow.



### Pull requests


## Manipulating commits

1. cherry-picking
2. rebasing

## Yeah right, everything is so easy

It really isn't and things will go wrong all the time. Believe me, Git makes me question my life choices quite frequently. Part of being a software developer is managing stress, anxiety, developing patience and tolerance. At some point you are going to make a mess using `git`. Here are a few tips to prevent that and a few tips to fix what you did.

### Prevention

1. Comment before execution.
2. Dry run.
3. Ask.

### Fix

1. Research.
2. Try to simulate your fix.
3. Ask.


## Follow on topics

1. Environments & how are they related to git.
2. The psychology of pull requests & how to find a way out when nobody likes your code.


# Good Git sources
Atlasian, git-scm, stackoverflow.
