---
layout: article
show_title: true
tags:
- Python
- tutorial
- advanced
title: "Advanced Python: Complete tutorial"
---

Hey! This time I came with a new and complete tutorial, for everything in Python that I consider "advanced". If you have not read yet `Advanced Python: The basic blocks of everything`, check it out, because this guide is the II part.

As always, let's go to the heart of the matter.

## What great Pythoners know: Basic Python

###






## The must-know of [Standard Libraries](https://docs.python.org/3/library/) outside the CORE.


### [re](https://docs.python.org/3/library/re.html)
This module provides regular expression matching operations similar to those found in Perl.

Both patterns and strings to be searched can be Unicode strings (str) as well as 8-bit strings (bytes). However, Unicode strings and 8-bit strings cannot be mixed: that is, you cannot match a Unicode string with a byte pattern or vice-versa; similarly, when asking for a substitution, the replacement string must be of the same type as both the pattern and the search string.


### [datetime](https://docs.python.org/3/library/datetime.html)
The datetime module supplies classes for manipulating dates and times.

While date and time arithmetic is supported, the focus of the implementation is on efficient attribute extraction for output formatting and manipulation.

### [collections](https://docs.python.org/3/library/collections.html)
This module implements specialized container datatypes providing alternatives to Python’s general purpose built-in containers, dict, list, set, and tuple.

- namedtuple()
- deque
- ChainMap
- Counter
- OrderedDict
- defaultdict
- UserDict
- UserList
- UserString

### [enum](https://docs.python.org/3/library/enum.html)
An enumeration is a set of symbolic names (members) bound to unique, constant values. Within an enumeration, the members can be compared by identity, and the enumeration itself can be iterated over.

### [math](https://docs.python.org/3/library/math.html)
This module provides access to the mathematical functions defined by the C standard.

These functions cannot be used with complex numbers; use the functions of the same name from the cmath module if you require support for complex numbers. The distinction between functions which support complex numbers and those which don’t is made since most users do not want to learn quite as much mathematics as required to understand complex numbers. Receiving an exception instead of a complex result allows earlier detection of the unexpected complex number used as a parameter, so that the programmer can determine how and why it was generated in the first place.

### [random](https://docs.python.org/3/library/random.html)
This module implements pseudo-random number generators for various distributions.

For integers, there is uniform selection from a range. For sequences, there is uniform selection of a random element, a function to generate a random permutation of a list in-place, and a function for random sampling without replacement.

On the real line, there are functions to compute uniform, normal (Gaussian), lognormal, negative exponential, gamma, and beta distributions. For generating distributions of angles, the von Mises distribution is available.

### [itertools](https://docs.python.org/3/library/itertools.html)
This module implements a number of iterator building blocks inspired by constructs from APL, Haskell, and SML. Each has been recast in a form suitable for Python.

The module standardizes a core set of fast, memory efficient tools that are useful by themselves or in combination. Together, they form an “iterator algebra” making it possible to construct specialized tools succinctly and efficiently in pure Python.

### [functools](https://docs.python.org/3/library/functools.html)
The functools module is for higher-order functions: functions that act on or return other functions. In general, any callable object can be treated as a function for the purposes of this module.

### [glob](https://docs.python.org/3/library/glob.html)
The glob module finds all the pathnames matching a specified pattern according to the rules used by the Unix shell, although results are returned in arbitrary order. No tilde expansion is done, but \*, ?, and character ranges expressed with [] will be correctly matched. This is done by using the os.scandir() and fnmatch.fnmatch() functions in concert, and not by actually invoking a subshell. Note that unlike fnmatch.fnmatch(), glob treats filenames beginning with a dot (.) as special cases. (For tilde and shell variable expansion, use os.path.expanduser() and os.path.expandvars().)

### [pickle](https://docs.python.org/3/library/pickle.html)
This library allows you to write a bytestream into the filesystem, allowing you to store objects externally.

ML engineers used to store their large and training models and hiperparameters in this format, allowing them to recover the whole thing in a matter of seconds. Well, it's one of its many uses, but a good one.

```
import pickle
import numpy as np

my_array = np.random.randn(1000)
with open("my_array.pkl", "wb") as f:
    f.write(pickle.load(my_array))
```
### [os](https://docs.python.org/3/library/os.html)
This module provides a portable way of using operating system dependent functionality. If you just want to read or write a file see open(), if you want to manipulate paths, see the os.path module, and if you want to read all the lines in all the files on the command line see the fileinput module. For creating temporary files and directories see the tempfile module, and for high-level file and directory handling see the shutil module.

### [io](https://docs.python.org/3/library/io.html)
The io module provides Python’s main facilities for dealing with various types of I/O. There are three main types of I/O: text I/O, binary I/O and raw I/O. These are generic categories, and various backing stores can be used for each of them. A concrete object belonging to any of these categories is called a file object. Other common terms are stream and file-like object.


### [threading](https://docs.python.org/3/library/threading.html)
This module constructs higher-level threading interfaces on top of the lower level \_thread module. See also the \_queue module. This module provides low-level primitives for working with multiple threads (also called light-weight processes or tasks) \— multiple threads of control sharing their global data space. 

### [platform](https://docs.python.org/3/library/platform.html)
Access to underlying platform’s identifying data

### [subprocess](https://docs.python.org/3/library/subprocess.html)
It allows your machine to run code as it were the CLI.

### [asyncio](https://docs.python.org/3/library/asyncio.html)
asyncio is a library to write concurrent code using the async/await syntax.

asyncio is used as a foundation for multiple Python asynchronous frameworks that provide high-performance network and web-servers, database connection libraries, distributed task queues, etc.

asyncio is often a perfect fit for IO-bound and high-level structured network code.

### [socket](https://docs.python.org/3/library/socket.html)
This module provides access to the BSD socket interface. It is available on all modern Unix systems, Windows, MacOS, and probably additional platforms.

### [json](https://docs.python.org/3/library/json.html)
JSON (JavaScript Object Notation), specified by RFC 7159 (which obsoletes RFC 4627) and by ECMA-404, is a lightweight data interchange format inspired by JavaScript object literal syntax (although it is not a strict subset of JavaScript 1 ).

json exposes an API familiar to users of the standard library marshal and pickle modules.

### [html.parser](https://docs.python.org/3/library/html.parser.html)
This module defines a class HTMLParser which serves as the basis for parsing text files formatted in HTML (HyperText Mark-up Language) and XHTML.

class html.parser.HTMLParser(*, convert_charrefs=True)¶

### [xml.sax](https://docs.python.org/3/library/xml.sax.html)
The xml.sax package provides a number of modules which implement the Simple API for XML (SAX) interface for Python. The package itself provides the SAX exceptions and the convenience functions which will be most used by users of the SAX API.

### [urllib.request](https://docs.python.org/3/library/urllib.request.html#module-urllib.request) or [request](https://requests.readthedocs.io/en/master/)
The urllib.request module defines functions and classes which help in opening URLs (mostly HTTP) in a complex world — basic and digest authentication, redirections, cookies and more.

### [locale](https://docs.python.org/3/library/locale.html)
The locale module opens access to the POSIX locale database and functionality. The POSIX locale mechanism allows programmers to deal with certain cultural issues in an application, without requiring the programmer to know all the specifics of each country where the software is executed.

The locale module is implemented on top of the \_locale module, which in turn uses an ANSI C locale implementation if available.

### [tkinter](https://docs.python.org/3/library/tkinter.html)
The tkinter package (“Tk interface”) is the standard Python interface to the Tk GUI toolkit. Both Tk and tkinter are available on most Unix platforms, as well as on Windows systems. (Tk itself is not part of Python; it is maintained at ActiveState.)

Running python -m tkinter from the command line should open a window demonstrating a simple Tk interface, letting you know that tkinter is properly installed on your system, and also showing what version of Tcl/Tk is installed, so you can read the Tcl/Tk documentation specific to that version.

### [pydoc](https://docs.python.org/3/library/pydoc.html)
The pydoc module automatically generates documentation from Python modules. The documentation can be presented as pages of text on the console, served to a Web browser, or saved to HTML files.

### [pdb](https://docs.python.org/3/library/pdb.html)
The module pdb defines an interactive source code debugger for Python programs. It supports setting (conditional) breakpoints and single stepping at the source line level, inspection of stack frames, source code listing, and evaluation of arbitrary Python code in the context of any stack frame. It also supports post-mortem debugging and can be called under program control.

The debugger is extensible – it is actually defined as the class Pdb. This is currently undocumented but easily understood by reading the source. The extension interface uses the modules bdb and cmd.

The debugger’s prompt is (Pdb).

### [distutils](https://docs.python.org/3/library/distutils.html)
The distutils package provides support for building and installing additional modules into a Python installation. The new modules may be either 100%-pure Python, or may be extension modules written in C, or may be collections of Python packages which include modules coded in both Python and C.

### [sys](https://docs.python.org/3/library/sys.html)
This module provides access to some variables used or maintained by the interpreter and to functions that interact strongly with the interpreter. It is always available.

### [contextlib](https://docs.python.org/3/library/contextlib.html)
This module provides utilities for common tasks involving the with statement. For more information see also Context Manager Types and With Statement Context Managers.

### [inspect](https://docs.python.org/3/library/inspect.html)
The inspect module provides several useful functions to help get information about live objects such as modules, classes, methods, functions, tracebacks, frame objects, and code objects. For example, it can help you examine the contents of a class, retrieve the source code of a method, extract and format the argument list for a function, or get all the information you need to display a detailed traceback.

There are four main kinds of services provided by this module: type checking, getting source code, inspecting classes and functions, and examining the interpreter stack.

### [importlib](https://docs.python.org/3/library/importlib.html)
The purpose of the importlib package is two-fold. One is to provide the implementation of the import statement (and thus, by extension, the __import__() function) in Python source code. This provides an implementation of import which is portable to any Python interpreter. This also provides an implementation which is easier to comprehend than one implemented in a programming language other than Python.

Two, the components to implement import are exposed in this package, making it easier for users to create their own custom objects (known generically as an importer) to participate in the import process.







