# Animation

# 1. View动画


动画有五种标签经常使用：

* alpha：渐变色

* scale：尺寸伸缩

* translate：平移

* rotate：旋转

* set：动画集

所有动画继承自Animation类，内部实现类一些公用的属性：

* android:duration：一次动画的持续时间。

* android:fillAfter：设置为true，动画结束时，将保持动画结束时的状态。

* android：fillBefore：设置为true，动画还原到初始状态。

* android:fillEnabled：和上面的相同。

* android:repeatCount：指定动画的重复次数，取值为infinite，表示无限循环。

* android:repeatMode：有reverse和restart两个值。

* android:interpolator：设置插值器。





### 1.1 动画存放位置

一般在res/anim文件夹下，访问使用 R.anim.XXX。

### 1.2 使用动画

定义布局：

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="start">
    </Button>

    <TextView
        android:id="@+id/textView"
        android:layout_height="100dp"
        android:layout_width="100dp"
        android:layout_gravity="center_horizontal"
        android:background="@android:color/darker_gray"
        android:text="Hello World">
    </TextView>

</LinearLayout>
```

定义动画：
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:fillAfter="true">


    <scale
        android:fromXScale="0"
        android:toXScale="2.0"
        android:fromYScale="0.0"
        android:toYScale="2.0"
        android:duration="3000"
        android:pivotX="50"
        android:pivotY="50">
    </scale>

</set>
```

在Activity中应用：

```kotlin
package com.hello.android

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("ObjectAnimatorBinding")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.view_anim)
            textView.startAnimation(animation)
        }
    }
}

```
 
### 1.3 scale标签

scale用来缩放动画，动态调整空间大小。

* android:fromXScale：动画开始时控件在x轴的缩放比例。

* android:fromYScale：动画开始时控件在y轴的缩放比例。

* android:toXScale：动画结束时控件在x轴的缩放比例。

* android:toYScale：动画结束时控件在y轴的缩放比例。

* android:pivotX：缩放起始点的x坐标。

* android:pivotY：缩放起始点的y坐标。

### 1.4 alpha标签

0表示全透明，1表示完全不透明。

* android:fromAlpha：初始透明度。

* android:toAlpha：结束透明度。

### 1.5 rotate标签

* android:fromDegrees：开始旋转的角度，正表示顺时针。

* android:toDegrees：结束旋转的角度。

* android:pivotX/Y：旋转中心。

### 1.6 translate标签

### 1.7 set标签

定义动画集，注意，对set使用repeatCount是无效的。

### 1.8 View动画的代码实现

##### 1.8.1 ScaleAnimation

```kotlin

class MainActivity : AppCompatActivity() {

    @SuppressLint("ObjectAnimatorBinding")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val scaleAnim = ScaleAnimation(0f, 1.4f, 0f, 1.4f)
            scaleAnim.duration = 700
            textView.startAnimation(scaleAnim)
        }
    }
}
```

# 2. Animation

### 2.1 属性

* void cancel()：取消动画

* void reset()：将控件重置

* void setAnimationLisener(Animation.AnimationListener listener)：设置监听

```kotlin

class MainActivity : AppCompatActivity() {

    @SuppressLint("ObjectAnimatorBinding")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val scaleAnim = ScaleAnimation(0f, 1.4f, 0f, 1.4f)
            scaleAnim.duration = 700
            textView.startAnimation(scaleAnim)
            scaleAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                    println(1)
                }

                override fun onAnimationEnd(animation: Animation?) {
                    println(2)
                }

                override fun onAnimationStart(animation: Animation?) {
                    println(3)
                }
            })
        }
    }
}
```


# 3. 插值器

Interpolator是一个接口，实现这个接口可以定义动画的变化速率。

### 3.1 AccelerateDecelerateInterpolator

加速减速插值器，开始和结束的地方速率较慢，中间速率较快。

```kotlin
            scaleAnim.interpolator = AccelerateDecelerateInterpolator()
```

### 3.1 AccelerateInterpolator

加速插值器，动画开始的地方速率慢，之后加速。

### 3.2 DecelerateInterpolator

减速插值器。

### 3.3 LinearInterpolator

匀速插值器。

### 3.4 BounceInterpolator

弹跳插值器。

# 4. 例子

### 4.1 镜头由远到近效果

```xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <ImageView
        android:id="@+id/img"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:src="@mipmap/ic_launcher"
        android:scaleType="fitStart">
    </ImageView>
</LinearLayout>
```


```kotlin
class MainActivity : AppCompatActivity() {

    @SuppressLint("ObjectAnimatorBinding")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val scaleAnim = ScaleAnimation(0.5f, 1.01f, 0.5f, 1.01f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleAnim.fillAfter = true
        scaleAnim.interpolator = BounceInterpolator()
        scaleAnim.duration = 6000
        img.startAnimation(scaleAnim)
    }
}


```

需要注意，pivotX 和 pivotY 都要设置为0.5，让控件从中心开始扩散。

### 4.2 加载框效果

让一张图片围绕中心不停旋转。

```kotlin
class MainActivity : AppCompatActivity() {

    @SuppressLint("ObjectAnimatorBinding")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rotateAnim = RotateAnimation(0F, 360F, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnim.apply {
            repeatCount = Animation.INFINITE
            duration = 2000
            interpolator = LinearInterpolator()
        }
        img.startAnimation(rotateAnim)
    }
}
```
# 5. ValueAnimation


