---
title: Why Google Cpp Testing Framework?
tag: TDD
---



[Doc: Why Google Cpp Testing Framework?](https://chromium.googlesource.com/external/github.com/google/googletest/+/refs/tags/release-1.8.0/googletest/docs/Primer.md#basic-assertions)

나는 비주얼 스튜디오의 Unite test를 쓸 것이지만 참고용으로 정리함.

exe가 아니라 static library로 설정해야 함.

If you put your tests in a static library (not DLL) 이 문구

개념위주 정리

---

# Intro

 what makes a good test, and how does Google C++ Testing Framework fit in? 

1. Tests should be *independent* and *repeatable*. 

   <u>It's a pain to debug a test that succeeds or fails as a result of other tests.</u> 

2. Tests should be well *organized* and reflect the structure of the tested code. 

3. Tests should be *portable* and *reusable*. The open-source community has a lot of code that is platform-neutral, its tests should also be platform-neutral. 

4. When tests fail, they should provide as much *information* about the problem as possible. 

5. The testing framework should liberate test writers from housekeeping chores and let them focus on the test *content*. 

6. Tests should be *fast*. With Google C++ Testing Framework, you can reuse shared resources across tests and pay for the set-up/tear-down only once, without making tests depend on each other.

---

## Setting up a New Test Project

 (for example, in Visual Studio, this is done by adding a dependency on `gtest.vcproj`).

---

## Assertions

//테스트 코드 명령어 

The assertions come in pairs that test the same thing but have different effects on the current function. 

`ASSERT_*` versions generate fatal failures when they fail, and **abort the current function**. `EXPECT_*` versions generate nonfatal failures, which don‘t abort the current function. Usually `EXPECT_*` are preferred, as they allow more than one failures to be reported in a test. However, you should use `ASSERT_*` if it doesn’t make sense to continue when the assertion in question fails.

Since a failed `ASSERT_*` returns from the current function immediately, possibly skipping clean-up code that comes after it, it may cause a space leak. Depending on the nature of the leak, it may or may not be worth fixing - so keep this in mind if you get a heap checker error in addition to assertion errors.

Anything that can be streamed to an `ostream` can be streamed to an assertion macro--in particular, C strings and `string` objects. If a wide string (`wchar_t*`, `TCHAR*` in `UNICODE` mode on Windows, or `std::wstring`) is streamed to an assertion, it will be translated to UTF-8 when printed.

---

## Basic Assertions

|      **Fatal assertion**       |     **Nonfatal assertion**     |     **Verifies**     |
| :----------------------------: | :----------------------------: | :------------------: |
| `ASSERT_TRUE(`*condition*`)`;  | `EXPECT_TRUE(`*condition*`)`;  | *condition* is true  |
| `ASSERT_FALSE(`*condition*`)`; | `EXPECT_FALSE(`*condition*`)`; | *condition* is false |

Remember, when they fail, `ASSERT_*` yields a fatal failure and returns from the current function, while `EXPECT_*` yields a nonfatal failure, allowing the function to continue running. In either case, an assertion failure means its containing test fails.

*Availability*: Linux, Windows, Mac.

---

## Binary Comparison

This section describes assertions that compare two values.

|       **Fatal assertion**       |     **Nonfatal assertion**      |    **Verifies**    |
| :-----------------------------: | :-----------------------------: | :----------------: |
| `ASSERT_EQ(`*val1*`,`*val2*`);` | `EXPECT_EQ(`*val1*`,`*val2*`);` | *val1* `==` *val2* |
| `ASSERT_NE(`*val1*`,`*val2*`);` | `EXPECT_NE(`*val1*`,`*val2*`);` | *val1* `!=` *val2* |
| `ASSERT_LT(`*val1*`,`*val2*`);` | `EXPECT_LT(`*val1*`,`*val2*`);` | *val1* `<` *val2*  |
| `ASSERT_LE(`*val1*`,`*val2*`);` | `EXPECT_LE(`*val1*`,`*val2*`);` | *val1* `<=` *val2* |
| `ASSERT_GT(`*val1*`,`*val2*`);` | `EXPECT_GT(`*val1*`,`*val2*`);` | *val1* `>` *val2*  |
| `ASSERT_GE(`*val1*`,`*val2*`);` | `EXPECT_GE(`*val1*`,`*val2*`);` | *val1* `>=` *val2* |

In the event of a failure, Google Test prints both *val1* and *val2*.

Value arguments must be comparable by the assertion‘s comparison operator or you’ll get a compiler error. 

These assertions can work with a user-defined type, but only if you define the corresponding comparison operator (e.g. `==`, `<`, etc). If the corresponding operator is defined, prefer using the `ASSERT_*()` macros because they will print out not only the result of the comparison, but the two operands as well.

Arguments are always evaluated exactly once. Therefore, it‘s OK for the arguments to have side effects. However, as with any ordinary C/C++ function, the arguments’ evaluation order is undefined (i.e. the compiler is free to choose any order) and your code should not depend on any particular argument evaluation order.

Macros in this section work with both narrow and wide string objects (`string` and `wstring`).

*Availability*: Linux, Windows, Mac.

---

## String Comparison

|          **Fatal assertion**           |         **Nonfatal assertion**         |                      **Verifies**                       |
| :------------------------------------: | :------------------------------------: | :-----------------------------------------------------: |
|   `ASSERT_STREQ(`*str1*`,`*str2*`);`   |   `EXPECT_STREQ(`*str1*`,`_str_2`);`   |         the two C strings have the same content         |
|   `ASSERT_STRNE(`*str1*`,`*str2*`);`   |   `EXPECT_STRNE(`*str1*`,`*str2*`);`   |        the two C strings have different content         |
| `ASSERT_STRCASEEQ(`*str1*`,`*str2*`);` | `EXPECT_STRCASEEQ(`*str1*`,`*str2*`);` | the two C strings have the same content, ignoring case  |
| `ASSERT_STRCASENE(`*str1*`,`*str2*`);` | `EXPECT_STRCASENE(`*str1*`,`*str2*`);` | the two C strings have different content, ignoring case |

---

# Important note for Visual C++ users

If you put your tests into a library and your `main()` function is in a different library or in your .exe file, those tests will not run. The reason is a [bug](https://connect.microsoft.com/feedback/viewfeedback.aspx?FeedbackID=244410&siteid=210) in Visual C++. When you define your tests, Google Test creates certain static objects to register them. These objects are not referenced from elsewhere but their constructors are still supposed to run. When Visual C++ linker sees that nothing in the library is referenced from other places it throws the library out. You have to reference your library with tests from your main program to keep the linker from discarding it. Here is how to do it. Somewhere in your library code declare a function:

```
__declspec(dllexport) int PullInMyLibrary() { return 0; }
```

If you put your tests in a static library (not DLL) then `__declspec(dllexport)` is not required. Now, in your main program, write a code that invokes that function:

```
int PullInMyLibrary();
static int dummy = PullInMyLibrary();
```

This will keep your tests referenced and will make them register themselves at startup.

In addition, if you define your tests in a static library, add `/OPT:NOREF` to your main program linker options. If you use MSVC++ IDE, go to your .exe project properties/Configuration Properties/Linker/Optimization and set References setting to `Keep Unreferenced Data (/OPT:NOREF)`. This will keep Visual C++ linker from discarding individual symbols generated by your tests from the final executable.

There is one more pitfall, though. If you use Google Test as a static library (that's how it is defined in gtest.vcproj) your tests must also reside in a static library. If you have to have them in a DLL, you *must* change Google Test to build into a DLL as well. Otherwise your tests will not run correctly or will not run at all. The general conclusion here is: make your life easier - do not write your tests in libraries!