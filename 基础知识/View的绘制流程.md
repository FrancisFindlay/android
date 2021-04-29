# View的绘制流程

https://mp.weixin.qq.com/s?__biz=MzU4NDc1MjI4Mw==&mid=2247484180&idx=1&sn=080cccd32f0c58dd68f743b3c78a7e1f&chksm=fd944ee0cae3c7f67f20b82e7a505808fc424a34c737bd0c944478ee7af1162cd98e78d716f2&scene=21#wechat_redirect

https://www.jianshu.com/p/9da7bfe18374

https://www.jianshu.com/p/8766babc40e0


# 1. Activity
Activity并不负责视图控制，它只是控制生命周期和处理事件。真正控制视图的是Window。一个Activity包含了一个Window，Window才是真正代表一个窗口。Activity就像一个控制器，统筹视图的添加与显示，以及通过其他回调方法，来与Window、以及View进行交互。

# 2. Window

https://www.jianshu.com/p/16f10f1a6fc6

Window是视图的承载器，内部持有一个 DecorView，而这个DecorView才是 view 的根布局。Window是一个抽象类，实际在Activity中持有的是其子类PhoneWindow。PhoneWindow中有个内部类DecorView，通过创建DecorView来加载Activity中设置的布局R.layout.activity_main。Window 通过WindowManager将DecorView加载其中，并将DecorView交给ViewRoot，进行视图绘制以及其他交互。

# 3. DecorView：绑定在WindowManager（DecorView仍然是个View，仍然会被添加到WindowManager中去）
DecorView是FrameLayout的子类，它可以被认为是Android视图树的根节点视图。
DecorView作为顶级View，一般情况下它内部包含一个竖直方向的LinearLayout，在这个LinearLayout里面有上下三个部分，上面是个ViewStub,延迟加载的视图（应该是设置ActionBar,根据Theme设置），中间的是标题栏(根据Theme设置，有的布局没有)，下面的是内容栏。具体情况和Android版本及主体有关，以其中一个布局为例，如下所示：


在Activity中通过setContentView所设置的布局文件其实就是被加到内容栏之中的，成为其唯一子View，就是上面的id为content的FrameLayout中，在代码中可以通过content来得到对应加载的布局。

# 4. ViewRoot：WindowManagerGlobal连接viewRootImpl 和 WindowManager

ViewRoot是连接View和Window连接的纽带,View的三大流程(测量（measure），布局（layout），绘制（draw）)均通过ViewRoot来完成。

ViewRoot并不属于View树的一份子。从源码实现上来看，它既非View的子类，也非View的父类，但是，它实现了ViewParent接口，这让它可以作为View的名义上的父视图。RootView继承了Handler类，可以接收事件并分发，Android的所有触屏事件、按键事件、界面刷新等事件都是通过ViewRoot进行分发的。

调用顺序：WindowManagerImpl -> WindowManagerGlobal -> ViewRootImpl。


# 5. View的绘制流程

### 5.1 ViewRootImpl()的setView()

https://blog.csdn.net/lu1024188315/article/details/73992164

在ViewRootImpl中的setView()中，首先会调用一次requestLayout()。

### 5.2 performTraversals()

https://blog.csdn.net/lu1024188315/article/details/80691235

View的绘制流程从ViewRoot的 performTraversals() 开始，经过measure、layout和draw三个过程最终绘制出来一个view。

其中measure确定view的高，layout确定view相对于父布局的位置，draw负责绘制。


### 5.3 performTraversals()中的performMeasure()

```
    private void performMeasure(int childWidthMeasureSpec, int childHeightMeasureSpec) {
        if (mView == null) {
            return;
        }
        Trace.traceBegin(Trace.TRACE_TAG_VIEW, "measure");
        try {
            //mView就是DecorView,从顶级View开始了测量流程
            mView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        } finally {
            Trace.traceEnd(Trace.TRACE_TAG_VIEW);
        }

```

说明，在这里我需要看下childWidthMeasureSpe，childHeightMeasureSpec形参是怎么来的，如下：

```
    int childWidthMeasureSpec = getRootMeasureSpec(mWidth, lp.width);
    int childHeightMeasureSpec = getRootMeasureSpec(mHeight, lp.height);
```

看一下getRootMeasureSpec()：

```

    private static int getRootMeasureSpec(int windowSize, int rootDimension) {
        int measureSpec;
        switch (rootDimension) {
 
        case ViewGroup.LayoutParams.MATCH_PARENT:
            // 如果View的高宽属性为match_parent那么它的mode将会是EXACTLY
            measureSpec = MeasureSpec.makeMeasureSpec(windowSize, MeasureSpec.EXACTLY);
            break;
        case ViewGroup.LayoutParams.WRAP_CONTENT:
            // 如果View的高宽属性为 wrap_conent 那么它的mode将会是 AT_MOST
            measureSpec = MeasureSpec.makeMeasureSpec(windowSize, MeasureSpec.AT_MOST);
            break;
        default:
            // 如果View的高宽属性为具体数值那么它的mode将会是 EXACTLY
            measureSpec = MeasureSpec.makeMeasureSpec(rootDimension, MeasureSpec.EXACTLY);
            break;
        }
        return measureSpec;

```

这里，出现了MeasureSpec对象。

### 5.4 MeasureSpec对象

MeasureSpec中，利用Int的高2位代表SpecMode，其余30位代表SpecSize，SpecMode是测量模式，SpecSize代表规格大小。

说明，在这里我门看到了一个对象MeasureSpec，MeasureSpec的作用是在在Measure流程中，系统将View的LayoutParams根据父容器所施加的规则转换成对应的MeasureSpec（规格），然后在onMeasure中根据这个MeasureSpec来确定view的测量宽高。同时可以从函数可以看出:

(1).如果View的属性为match_parent|确定值,那么它对应的mode为exactly;

(2).如果View的属性为wrap_parent,那么它对应的mode为at_most;

##### 5.4.1 MeasureSpec的测量模式

* UNSPECIFIED：父容器不对view有任何限制，要多大给多大。

* EXACTLY：父容器已经测量出view所需要的精确大小，这个时候view的大小等于SpecSize所指定的值，对应于match_parent和精确数值这两种模式。

* AT_MOST：父容器给定了一个可用大小SpecSize，View的大小不能超过这个值，对应wrap_content这种模式。

### 5.5 performMeasure()中的measure()

```
    private void performMeasure(int childWidthMeasureSpec, int childHeightMeasureSpec) {
        if (mView == null) {
            return;
        }
        Trace.traceBegin(Trace.TRACE_TAG_VIEW, "measure");
        try {
            mView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        } finally {
            Trace.traceEnd(Trace.TRACE_TAG_VIEW);
        }
    }
```

这时候，mView是DecorView，也就说明，View的measure是从顶到下测量的。

### 5.6 measure()中的onMeasure()

