---
tags: Java
categories:
  - cs
  - lang
  - java
---

```java
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Scanner
 *
 * @author holon
 * @date 2022/01/23 5:04 PM
 */
public class ScannerSample {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter("myfile.txt", StandardCharsets.UTF_8);
        out.println("test");
        // 需要调用 clos() 后写入才会生效
        out.close();
        Scanner in = new Scanner(Path.of("myfile.txt"), StandardCharsets.UTF_8);
        // out: text
        System.out.println(in.nextLine());
    }
}
```