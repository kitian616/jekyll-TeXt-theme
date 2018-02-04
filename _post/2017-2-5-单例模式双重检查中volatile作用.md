# **单例模式双重检查中volatile作用**

# 1. volatile作用

1. volatile可以保证变量和可见性，让修饰的变量直接从主存中获取值；
2. 2.volatile可以防止指令重排序

# 2. volatile的误区

volatile无法保证复合操作的原子性。

# 3. 单例模式双重检查中Volatile的作用

在单例模式的饿汉式实现中，为了缩小锁的范围，使用双重检查。

```java
public class DoubleCheckMode {
    /**
     * volatile 保证可见性(双重检查用synchronized保证可见性)，防止指令重排序
     */
    private static volatile DoubleCheckMode instance = null;

    /**
     * 构造函数私有化
     */
    private DoubleCheckMode() {
    }

    public static DoubleCheckMode getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckMode.class) {
                /**
                 * 双重检查，缩小锁的范围
                 */
                if (instance == null) {
                    /**
                     * volatile防止如下重排序
                     * 指令执行顺序：
                     * 1.分配内存空间; 2.实例化对象; 3.将内存空间地址赋值给instance引用
                     * 可能被重排序为：
                     * 1.分配内存空间; 2.将内存空间地址赋值给instance引用; 3.实例化对象
                     * 使用volatile修饰，防止指令重排序
                     */
                    instance = new DoubleCheckMode();
                }
            }
        }
        return instance;
    }
}
```

在

instance = new DoubleCheckMode();

这行代码中，包含如下3条指令：

1. 分配内存空间；
2. 实例化对象；
3. 将内存地址赋值给instance引用

可能被重排序为：

1. 分配内存空间；
2. 将内存地址赋值给instance引用；
3. 实例化对象

在多线程模型中，如果Thread A刚执行完重排序后的1、2条指令；线程Thread B进行双重检查判断时，instance将不为null，此时会获得不完整的实例化对象。此时使用volatile修饰instance，防止指令重排序，保证多线程模型下能正确获得实例对象。