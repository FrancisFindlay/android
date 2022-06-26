# 屏幕显示系统知识

### 一. 基础知识

1. 在一个典型的显示系统中，一般包括CPU、GPU、Display三个部分， CPU负责计算帧数据，把计算好的数据交给GPU，GPU会对图形数据进行渲染，渲染好后放到buffer(图像缓冲区)里存起来，然后Display（屏幕或显示器）负责把buffer里的数据呈现到屏幕上

2. 基础概念：

* 屏幕刷新频率

一秒内屏幕刷新的次数（一秒内显示了多少帧的图像），单位 Hz（赫兹），如常见的 60 Hz。刷新频率取决于硬件的固定参数（不会变的）。

* 逐行扫描

显示器并不是一次性将画面显示到屏幕上，而是从左到右边，从上到下逐行扫描，顺序显示整屏的一个个像素点，不过这一过程快到人眼无法察觉到变化。以 60 Hz 刷新率的屏幕为例，这一过程即 1000 / 60 ≈ 16ms。

* 帧率 （Frame Rate）

表示 GPU 在一秒内绘制操作的帧数，单位 fps。例如在电影界采用 24 帧的速度足够使画面运行的非常流畅。而 Android 系统则采用更加流程的 60 fps，即每秒钟GPU最多绘制 60 帧画面。帧率是动态变化的，例如当画面静止时，GPU 是没有绘制操作的，屏幕刷新的还是buffer中的数据，即GPU最后操作的帧数据。

### 二. 双缓存

1. 画面撕裂：屏幕刷新频是固定的，比如每16.6ms从buffer取数据显示完一帧，理想情况下帧率和刷新频率保持一致，即每绘制完成一帧，显示器显示一帧。但是CPU/GPU写数据是不可控的，所以会出现buffer里有些数据根本没显示出来就被重写了，即buffer里的数据可能是来自不同的帧的， 当屏幕刷新时，此时它并不知道buffer的状态，因此从buffer抓取的帧并不是完整的一帧画面，即出现画面撕裂。
   
2. 双缓存：

让绘制和显示器拥有各自的buffer：GPU 始终将完成的一帧图像数据写入到 Back Buffer，而显示器使用 Frame Buffer，当屏幕刷新时，Frame Buffer 并不会发生变化，当Back buffer准备就绪后，它们才进行交换。

### 三. Vsync

屏幕刷新完一帧就会发起Vsync信号。

# Android 屏幕刷新机制

发送Vsync后，cpu和gpu先后开始计算，然后在一帧内完成渲染。

# Choreographer

监控帧率，view绘制的handle。

### 1. ViewRootImpl的scheduleTraversals():

Activity启动 走完onResume方法后，会进行window的添加。window添加过程会 调用ViewRootImpl的setView()方法，setView()方法会调用requestLayout()方法来请求绘制布局，requestLayout()方法内部又会走到scheduleTraversals()方法，最后会走到performTraversals()方法，接着到了我们熟知的测量、布局、绘制三大流程了。

另外，查看源码发现，当我们使用 ValueAnimator.start()、View.invalidate()时，最后也是走到ViewRootImpl的scheduleTraversals()方法。

所有UI的变化都是走到ViewRootImpl的scheduleTraversals()方法。

##### requestLayout到scheduleTraversals

* Activity进行Window添加 

* window调用ViewRootImpl

* ViewRootImpl进行requestLayout

* requestLayout调用scheduleTraversals。

##### 从scheduleTraversals到performTraversals中经过了什么？

* scheduleTraversals调用Choreographer进行postCallBack 

* Choreographer发送handler，通过一系列操作调用到DisplayEventReceiver的scheduleVsync

* vSync信号到达时，触发DisplayEventReceiver的onVsync，其具体实现是FrameDisplayEventReceiver

* FrameDisplayEventReceiver使用Handler机制，执行Choreographer的doFrame

* doFrame里依次执行input，animation，traversal等回调

* 执行TraversalRunnable

* 执行ViewRootImpl的doTraversal，然后开始performTraversals








