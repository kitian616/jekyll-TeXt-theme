---
Title: About the iOS File System
tag: SE
---


> 아이폰의 파일 접근 권한 및 접근을 어떻게 해야 얻을 수 있는가? 부터 출발하는 파일 시스템  
> Mac OS에서의 파일은 하나가 완전한 파일로 구축되어 있어서 설치, 삭제가 편하다는 것(원터치?)만 알고 있었기에 궁금하기도 했다.  

## pre-Post
### About BASIC (HFS+, B-Tree)의 간략한 정리
* [Mac OS - DongSub_Joung’s Developer Life](https://dongsub-joung.github.io/2021/02/07/macOS.html)

- - - -
## APFS (Apple File System)
HFS+에서 탈피하고 APFS로 교체됨. (IOS 10.3 부터)
기존의 HFS+의 파일 시스템은 보안을 위해서 개량을 해왔지만 근본부터 개선해야 할 필요성이 존재했음. 

### Improved File System Fundamentals
* Flash / SSD-optimized
* Crash-protected
* Modern 64-bit native fields
* Extensible design for at a structure growth
* Optimized for Apple software ecosystem
* Low-latency design
* Native encryption support

### 플래시 기반 저장장치에 최적화된 파일 시스템
1. (물리적으로 동작하는 장치)단일 메타데이터가 지정하는 파일들이 저장장치의 이곳저곳에 흩어져 있을 경우 그 파일을 읽을 때 성능저하 발생.
* 낸드 플래시 메모리 기반의 저장장치(SSD)는 근본적으로 HDD와 관리가 다름.
* 셀이 다 차면 셀을 초기 상태로 돌리는 작업 필요, 하지만 이 작업이 W, R 속도에 비해 김. 이 때 버벅임이 발생
* 해결 => `TRIM`(메타 데이터가 변경되어 더 이상 의미가 없어진 데이터를 실제 삭제해 주는 기능) => `TRIM`의 비동기식 지원이 가능해짐.

> 비동기식 TRIM은 항상 백그라운드에서 Garbage Collector가 동작하는 방식으로 작동하는 것이 아니라 _메타데이터의 변경이 감지된 시점에서만_ 비동기적으로 TRIM 명령을 실행하는 방식으로 더 안정적으로 저장장치를 유지할 수 있도록 해 준다.  

2. 가용 셀의 한계
* 파일 시스템 단위에서 쓰기 동작을 최소화 -> 부담을 줄이는 방법 채택
**Copy-On-Write’ 방식의 쓰기 정책**
기존: 물리적인 복제본 형성 후 쓰기 작업, 수정된 부분에 대해서 다시 쓰기 동작 발생
Copy-On-Write: 실제 쓰기 동작은 그 복제된 파일에 수정이 일어난 시점에 발생함. 트랜잭션 서브시스템과 함께 구동되어 데이터 안정성을 보장함.

**64bit I-node 지원**
> I-node: 유닉스 파일 시스템에서 사용하는 자료구조로, 파일마다 한 개의 아이 노드가 할당되어 있다.   
32bit I-node -> 64bit I-node (파일 하나의 용량이 늘어나고, 파일의 타임스탬프를 나노초 단위로 기록가능하게 됨)

**유연함**
실제 W작업을 수행하지 않고 파일을 생성할 수 있는 메타데이터상으로만 존재하는 파일을 생성할 수 있음.

**호환성**


> M1의 기술적 문제?로  SSD의 수명문제가 거론되는 것으로 알고 있는데 이것에 대해서 따로 정리해야할 필요성이 있을 것 같다.  

[**Apple File System에 대해서 자세한 설명**]()

- - - -
## About the iOS File System
> the persistent storage of data files, apps, and the files associated with the operating system itself.   

영구적인 저장공간

## iOS Standard Directories
보안상의 목적으로 IOS 앱의 상호작용은 앱의 sandbox 디렉토리 내에서 제한되어 져 있다.
새로운 앱을 설치하는 동안에 인스톨러는 새로운 디렉토리 컨테이너를 만들고 각각의 컨테이너는 특별한 룰을 가진다. 
아래의 그림을 참고하자.

![An IOS app operating within its own sandbox directory](https://developer.apple.com/library/archive/documentation/FileManagement/Conceptual/FileSystemProgrammingGuide/art/ios_app_layout_2x.png)

**Bundle Container**
: 앱의 번들을 담는다.
**Data Container**
: 앱과 유저의 데이터를 담는다. (서브 디렉토리로 나누어져 데이터들을 관리한다.)
**iCloud Container**
: 실행 시에 iCloud와 관련된 것을 담는다.

앱은 일반적으로 accessing or creating files outside its container directories. 하는 것을 금지한다. (the system frameworks가 도움을 주는public system interfaces가 접근할 때는 예외)

- - - -

### bundle의 정의
* (실행가능한 코드를 묶어주는 그리고 자원과 연관되어있는) 파일 시스템 내의 directory이다.
* 예를 들자면 In iOS and OS X, applications, frameworks, plug-ins, and other types of software 등등
* 번들은 유저와 개발자에게 쉬운 설치 혹은 경로 바꾸기 혹은 다른 곳으로 이동시키기의 이점을 제공한다.
* 로컬라이징 파일을 번들의 서브 디렉토리로 넣음으로써 유저의 언어 설정에 따라 자동적으로 변환된다. Xcode projects의 대부분의 type들은 bundle을 생성함. 손수 빌드 할 수 도 있음.

![bundle: iPhoneOS, Mac OS X](https://developer.apple.com/library/archive/documentation/General/Conceptual/DevPedia-CocoaCore/Art/bundle_2x.png)

**번들 내 포함 시킬 수 있는 것들** 
* executable code
*  images
* sounds
*  nib files
*  private frameworks and libraries
*  plug-ins
*  loadable bundles, or any other type of code or resource
*  + a runtime-configuration file called the information property list (Info.plist)

#### Accessing Bundle Resources
* a main bundle <= contains the application code
* 앱 실행 시에 코드를 찾고 즉시 필요한 자원을 메모리에 적재함. 그리고 동적으로 코드와 자원들을 로드함.

The  [NSBundle](https://developer.apple.com/library/archive/documentation/LegacyTechnologies/WebObjects/WebObjects_3.5/Reference/Frameworks/ObjC/Foundation/Classes/NSBundle/Description.html#//apple_ref/occ/cl/NSBundle)  class and, for procedural code, the  [CFBundleRef](https://developer.apple.com/documentation/corefoundation/cfbundle)  opaque type of Core Foundation give your application the means to locate resources in a bundle. In Objective-C, you first must obtain an instance of NSBundle that corresponds to the physical bundle. To get an application’s main bundle, call the class method  [mainBundle](https://developer.apple.com/library/archive/documentation/LegacyTechnologies/WebObjects/WebObjects_3.5/Reference/Frameworks/ObjC/Foundation/Classes/NSBundle/Description.html#//apple_ref/occ/clm/NSBundle/mainBundle) . Other NSBundle methods return paths to bundle resources when given a filename, extension, and (optionally) a bundle subdirectory. After you have a path to a resource, you can load it into memory using the appropriate class.

**Loadable Bundles**

- - - -

### subdirectories
#### AppName.app
* An App’s bundle. 수정할 수 없는 디렉토리, 대신 어느 자원에 대해서 읽기 전용으로 접근 가능.  참고 [Resource Programming Guide](https://developer.apple.com/library/archive/documentation/Cocoa/Conceptual/LoadingResources/Introduction/Introduction.html#//apple_ref/doc/uid/10000051i) 
* iTunes or iCloud.에 백업 안됨.

#### Documents/ 
* User-generated content를 저장하는 디렉토리.
* 유저가 앱을 통해 생성한 문서나 데이터, 또는 외부 앱에서 받은 파일을 저장한다.
* 유저에게 노출되는 파일만 저장해야 하며 디렉토리의 파일들은 iTunes 와 iCloud로 백업됨.
* iTunes를 통한 `File sharing` 지원, `UIFileSharingEnabled` 키를 `YES`로 하면 사용 가능 
=> 자세한 설명은 [iOS 파일 시스템. Android와 다르게 iOS는 그 내부가 잘 보이지 않는 듯하다… | by Alpaca | Medium](https://medium.com/@Alpaca_iOSStudy/ios-%ED%8C%8C%EC%9D%BC-%EC%8B%9C%EC%8A%A4%ED%85%9C-35e61a85a3f8) 참조 

#### Documents/Inbox
* 너의 앱이 외부 속성에 의해서 열릴지를 결정하는 파일.
* 이 디렉토리에서 파일을 읽고, 삭제할 수 있지만 새로운 파일을 만들거나 존재하는 파일들을 수정할 수 없다.  
* iTunes and iCloud에 백업 가능 

#### Library/
* 유저 데이터 파일 이외의 파일들이 저장되는 가장 높은 위치에 존재하는 디렉토리 
* standard subdirectories의 파일을 추가할 수 있음
* IOS 앱들은 일반적으로 Application Support and Caches subdirectories를 사용함.
 **Application Support**
:  사용자의 documents를 제외한 나머지 앱의 데이터파일들을 저장하는데 사용. 앱이 생성하고 관리하는 데이터, 설정, 리소스등이 저장되며 이 디렉토리의 모든 contents는 앱의 `bundle identifier`나 회사 이름의 서브 디렉토리에 위치함.
**Caches**
: 앱이 쉽게 재생성 할 수 있는 파일, 쉽게 다운로드 받을 수 있는 파일들이 저장된다. 앱의 성능을 위한 목적으로 존재함. 앱이 실행 중에는 삭제되지 않는 것이 보장되며 백업은 되지 않음. `caches/snapshots` 디렉토리에는 앱이 백그라운도로 넘어갈 때 저장되는 앱의 스냅샷이 저장된다.
**Preferences**
: 앱의 중요 설정이 담겨 있는 디렉토리, `NSUserDefalts`클래스를 사용해 파일을 만들어 저장할 수 있다.

* iTunes and iCloud 백업 가능
* For additional information about the Library directory and its commonly used subdirectories, see  [The Library Directory Stores App-Specific Files](https://developer.apple.com/library/archive/documentation/FileManagement/Conceptual/FileSystemProgrammingGuide/FileSystemOverview/FileSystemOverview.html#//apple_ref/doc/uid/TP40010672-CH2-SW1) .

#### tmp/
앱 시작에 필요하지 않는 파일들을 임시적으로 보관하는데 쓰임.
사용 후 필요 없어진 파일은 직접 삭제해주는 것을 권장

> IOS app은 아마 추가적인 디렉토리를 만들꺼임. (in the Documents, Library, and tmp directories.)   

For information about how to get references to the preceding directories from your iOS app, see  [Locating Items in the Sta](https://developer.apple.com/library/archive/documentation/FileManagement/Conceptual/FileSystemProgrammingGuide/AccessingFilesandDirectories/AccessingFilesandDirectories.html#//apple_ref/doc/uid/TP40010672-CH3-SW3) 

- - - -
### Where You Should Put Your App’s Files
IOS 기기에서 자동으로 동기화 및 백업 프로세스가 진행되기 때문에 상위 설명한 폴더에 적절한 데이터를 사용해야 함. 대용량 파일의 경우, 시스템 이용에 불편을 가져올 수 있음.

> 자세한 사항은 공홈 참조  

* Put user data in Documents/. 
* Put app-created support files in the Library_Application support_ directory.
* Remember that files in Documents/ and Application Support/ are backed up by default.
* Put temporary data in the tmp/ directory. 
* Put data cache files in the Library_Caches_ directory. 

- - - -

## IOS Data Storage Guidelines

위의 그림을 알기 쉬운 트리 그림으로 표현한 것 

![iOS 파일시스템(File System) - jinShine](https://i.imgur.com/B9HDxms.png)

> 코드도 있어서 보기 좋음  
> 파일 접근 시 , String 대신 URL 사용 권장  

[iOS 파일시스템(File System) - jinShine](https://jinshine.github.io/2019/01/19/iOS/UserDefaults.1/)  

- - - -

## 참고 사이트
[Apple Developer - File System Programming Guide](https://developer.apple.com/library/archive/documentation/FileManagement/Conceptual/FileSystemProgrammingGuide/FileSystemOverview/FileSystemOverview.html)  
[iOS 파일 시스템. Android와 다르게 iOS는 그 내부가 잘 보이지 않는 듯하다… | by Alpaca | Medium](https://medium.com/@Alpaca_iOSStudy/ios-%ED%8C%8C%EC%9D%BC-%EC%8B%9C%EC%8A%A4%ED%85%9C-35e61a85a3f8)  
[애플의 새로운 파일 시스템, APFS의 모든 것 :: Back to the Mac 블로그](https://macnews.tistory.com/4603)  

## 다음 토픽: About the macOS File System
