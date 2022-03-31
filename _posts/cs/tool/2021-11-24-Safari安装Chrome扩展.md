---
tags: Mac Safari
categories:
  - cs
  - tool
---

> 原视频链接：[How to PERMANENTLY Add Google Chrome Extensions to Safari](https://www.youtube.com/watch?v=tFbBiEdFVUA)

> macOS Monterey 对签名方法进行了限制，导致方法在 macOS上失灵，详细信息请观看这个视频:[How to get Chrome Extensions in Safari on iPhone and iPad (iOS 15)](https://www.youtube.com/watch?v=9h6OQ5IGmKI)

# 获取扩展源代码

1. 进入这个网站：https://crxextractor.com
2. <img src="https://cdn.jsdelivr.net/gh/wholon/image@main/uPic/image-20211124190210589.png" alt="image-20211124190210589" style="zoom:33%;" />
	点击 **START FOR FREE**
3. <img src="https://cdn.jsdelivr.net/gh/wholon/image@main/uPic/image-20211124190421317.png" alt="image-20211124190421317" style="zoom:33%;" />
	粘贴 Chrome 扩展的 url 到图示输入框
4. 依次点击 **DOWNLOAD** -> **GET .CRX**
5. <img src="https://cdn.jsdelivr.net/gh/wholon/image@main/uPic/image-20211124190733186.png" alt="image-20211124190733186" style="zoom:33%;" />
	把下载的 crx 文件拖拽到网页中这个地方进行上传，然后点击 **GET SOURCE CODE** 下载转化后的 .zip 文件，并解压，复制解压后的文件路径备用
	
# 使用 Safari Web Extension Converter 工具

6. 通过App Store 安装 Xcode，如果已安装请忽略此步
7. 终端运行以下命令
	```shell
	sudo xcode-select -s /Applications/Xcode.app
	```
8. 如第 7 步没有报错，接着运行以下命令
	```shell
	xcrun /Applications/Xcode.app/Contents/Developer/usr/bin/safari-web-extension-converter [替换为第5步复制的文件夹路径]
	```

# 发行扩展

9. 使用 Xcode 打开第 8 步之后的项目，如果找不到文件，可以重复运行第 8 步，然后终端会提示你是否覆盖，输入 Y，然后 Xcode 会自动帮你打开项目

10. <img src="https://cdn.jsdelivr.net/gh/wholon/image@main/uPic/image-20211124202102856.png" alt="image-20211124202102856" style="zoom:33%;" />
	点击左上角的**运行**图标，那个三角形
	
	> 这里可能出现文件夹不存在的错误，仔细阅读说明，然后创建相应的文件夹即可
	
11. 等待第 10 步构建成功后，点击菜单栏 Product-> Archive

11. <img src="https://cdn.jsdelivr.net/gh/wholon/image@main/uPic/image-20211124202913738.png" alt="image-20211124202913738" style="zoom:33%;" />
	在弹出的这个窗口中点击 **Distribute App**
	
12. <img src="https://cdn.jsdelivr.net/gh/wholon/image@main/uPic/image-20211124203023459.png" alt="image-20211124203023459" style="zoom:33%;" />
	在弹出的这个窗口中选中 **Development**，点击 **Next**
	
13. <img src="https://cdn.jsdelivr.net/gh/wholon/image@main/uPic/image-20211124203159895.png" alt="image-20211124203159895" style="zoom:33%;" />
	添加一个开发团队（如果你没有的话），需要登录你的 Apple ID，然后退出那个登录过后的窗口，回到图示这里，选择刚才添加的账户，点击 Next

> macOS Monterey 对签名方法进行了限制，导致方法在 macOS上失灵，详细信息请观看这个视频:[How to get Chrome Extensions in Safari on iPhone and iPad (iOS 15)](https://www.youtube.com/watch?v=9h6OQ5IGmKI)