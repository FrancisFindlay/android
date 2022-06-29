# 启动Activity的方法

# 一. 启动一个Activity

```
val intent = Intent()
startActivity(intent)
```

这是一个典型的启动流程。

# 二. 从startActivity开始

startActivity有很多种启动流程，但是最终都会调用startActivityForResult。

然后，Instrumentation的execStartActivity就会调用，还会调用checkStartActivityResult检查是否注册了该Activity。

然后，IActivityTaskManager的实现，ActivityManagerService（是一个单例）就会调用startActivity。

然后，会通过ActivityStackSupervisor和ActivityStack的一系列操作，调用realStartActivityLocked。

然后，回到了ApplicationThread，ApplicationThread发送了了一个launch_activity的handler。

然后，ActivityThread的H文件接受了handleLaunchActivity。

最后，调用了performLaunchActivity，完成了Activity对象的创建，并且，还通过handleResumeActivity调用了Activity的onResume。

### 2.1 performLaunchActivity做了什么？

通过Instrumentation的newActivity创建Activity对象（反射）。

通过LoadedApk的makeApplication创建Application对象。

创建ContextImpl对象，并通过调用Activity的attach做一些初始化（比如Window的创建）。

调用Activity的onCreate。
