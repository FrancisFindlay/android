# Window

# 一. Window和WindowManager

Window是一个抽象类，唯一实现是PhoneWindow。

创建一个Window通过WindowManager就可以完成，WindowManager是外界访问Window的入口，Window的具体实现在WindowManagerService里面，WindowManager和WindowManagerService是一个IPC过程。

### 1. Flags

* FLAG_NOT_FOCUSABLE 表示Window不需要获得焦点，也不接受各种事件，会传递到下层能接受的Window。

* FLAG_NOT_TOUCH_MODAL 表示Window区域以内的事件Window自己处理。

* FLAG_SHOW_WHEN_LOCKED Window可以显示在锁屏的桌面上。

### 2. Type

Window有三种类型，应用Window、子Window、系统Window。

应用Window对应Activity；子Window对应Dialog，它不能单独存在；系统Window需要声明权限才能创建，比如Toast和系统通知栏。

每个Window都有自己的层级，层级大的会覆盖层级小的。

声明权限需要SYSTEM_ALERT_WINDOW。

### 3. WindowManager的功能

WindowManager提供的功能很简单，常用的有三个方法：

* addView

* removeView

* updateViewLayout

# 二. Window的内部机制

每一个Window都对应一个decorView和ViewRootImpl。Window和View通过ViewRootImpl来联系。

### 1. Window的添加过程

Window的添加过程需要WindowManager的addView实现，WindowManager的真正实现是WindowManagerImpl。

WindowManagerImpl又通过WindowManagerGlobal来处理，WindowManagerGlobal通过工厂向外提供实例。

这是典型的桥接模式。

addView的过程：

### 1.1 检查参数是否合法。

### 1.2 创建ViewRootImpl并将View添加到list里面。

```

    @UnsupportedAppUsage
    private final ArrayList<View> mViews = new ArrayList<View>();
    @UnsupportedAppUsage
    private final ArrayList<ViewRootImpl> mRoots = new ArrayList<ViewRootImpl>();
    @UnsupportedAppUsage
    private final ArrayList<WindowManager.LayoutParams> mParams =
            new ArrayList<WindowManager.LayoutParams>();
```

mView存储所有Window对应的view，mRoots存储所有对应的viewRootImpl，mParams存储所有的Window对应的布局参数，mDyingViews存储正在被删除的view对象。

在addView的时候，通过一系列操作将这些操作完成：

```
            root = new ViewRootImpl(view.getContext(), display);
            view.setLayoutParams(wparams);
            mViews.add(view);
            mRoots.add(root);
            mParams.add(wparams);
```

### 1.3 通过ViewRootImpl来更新页面并完成Window的添加

更新界面由ViewRootImpl的setView完成，通过setView会开始requestLayout。

Window的添加通过WindowSession来完成，WindowSession的类型是IWindowSession，它是一个Binder对象，真正实现类是Session。

Session内部通过WindowManagerService来实现。WindowManagerService会为每个应用保留一个单独的Session。

### 2. Window的删除过程

和添加一样，先通过WindowManagerImpl然后通过WindowManagerGlobal实现。

removeView的逻辑：

先通过findViewLocked查找对应的View的索引，然后通过removeViewLocked来删除。

removeView通过ViewRootImpl完成，有同步和异步两种方法，ViewRootImpl调用die是异步删除，然后将删除的view添加到mDyingViews，然后，通过handler发送消息。异步调用要发handler，同步不需要发handler。

真正删除的逻辑在dispatchDetachedFromWindow里面。这里做了两件事：

1. 移除回调。

2. 通过Session的remove删除Window。

3. 调用View的dispatchDetachedFromWindow，然后调用View的onDetachFromWindow。

### 3. Window的更新过程

从WindowManagerGlobal的updateViewLayout开始，更新了View的LayoutParams，然后更新ViewRootImpl的LayoutParams，然后ViewRootImpl通过WindowSession来更新View的视图。

# 三. Window的创建过程

### 1. Activity的Window创建过程

在Activity的attach里面，系统会创建Activity所属的Window对象，并且设置回调，设置回调的时候，能看到一些熟悉的接口，比如onAttachedToWindow、dispatchTouchEvent等。

### 2. Activity的View是怎么附属在Window上的？

setContentView。

这个方法最终会调用到window的setContentView，其实就是PhoneWindow的setContentView。

如果没有DecorView，那就创建DecorView。

创建DecorView需要通过generateDecor来完成，然后到