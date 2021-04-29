# Android Studio 使用技巧

### 1. 快速查找

* 连续按两次shift 

* command + o

### 2. search action

搜索指令的入口，比如"open recent"

* command + shift + A

### 3. 单词跳跃

按住 option 左右进行选择就可，androidStudio可以选择驼峰跳跃。

* use camelhumps words 进行设置

### 4. 显示最近修改和操作

* command + e：显示最近浏览过的文件

* command + shift + e：显示最近编辑过的文件

### 5. 移动当前文件

* command + option + 左/右

### 6. 查找调用

查找一个方法或者属性在哪里被调用。

* 选中方法然后右键，点击 find usage

### 7. 将一行上下移动

* command + shift + 上/下

### 8. 快速删除一行

* command + backspace

### 9. 快速剪贴一行

* command + X

### 10. 快速断点

* 设置普通断点后，点击断点，然后在condition处设置类似 "i == 5" 的条件。

另外，点击Enabled 可以选择是否让该断点生效。

### 11. 异常断点

假设某场景下App崩溃了需要处理，常见做法是先复现问题，然后adb抓log找出具体的异常原因。

例如，对于NullPointException来说，我们可以在 Run -> View breakpoints界面点击左上角的 "+"，添加"Java Exception BreakPoints"
就可以了。

### 12. 日志断点


### 13. 快速完成

* command + shift + enter

if语句的代码补全，格式化等操作的快捷键。

### 14. 调试中计算变量的值

* option

在debug中，按住option，然后点击代码中的表达式就可以计算表达式的值。

### 15. 查看大纲

* command + F12 

项目比较大的时候，可以按住command + F12 查看方法和属性列表。

### 16. 书签

* F3

经常需要记录一些关键代码，快捷键F3就可以将一处代码添加到书签里，然后在favorites标签中找到。

* command + F3

可以使用command + f3 显示所有的书签内容。

### 17. 附加调试

* attach to debugger 

使用附加调试可以让程序以正常方法运行，然后进行调试。

### 18. 预览方法定义

* command + y

想查看某个方法定义，但是又不想跳到方法所在类，可以使用command + y 在当面页面进行预览。 

### 19. 拆分窗口

* window -> editor tabs -> split vertical\horizontal 

可以进行界面切分，进行代码对比。


### 20. 相关文件

在每个类的左边有一个Related xml file，可以用来查看相关联的XML文件。

### 21. 查找引用实例化的位置

case：查找Activity中mWindow实例化的位置：

1. command + shift + f

2. 点击scope，输入mWindow = 

3. 寻找对应代码

### 22. 快速重构

* control + t

### 23. surround with

* command + option + t

### 24. 快速提示

* option + enter

### 25. 代码模版

* command + J

设置里找到 Live Templates 可以自行设置。

### 26. 代码分析

* Analyze -> inspect code：检查整个工程，会给出修改意见，也可以使用code cleanup功能进行自动代码修复。

* Dependencies：可以检查项目中的依赖。

* Analyze Data flow：可以追踪数据流，了解变量的来龙去脉。

* 方法调用栈：control + option + h。


### 27. 分配内存

* applications -> android studio -> contents -> bin -> android.vmoptions

