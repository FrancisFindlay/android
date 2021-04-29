# LinearLayout


https://www.cnblogs.com/xqz0618/p/linearlayout.html

https://www.jianshu.com/p/0e3206eb80f8

https://www.twle.cn/l/yufei/android/android-basic-linearlayout4.html


# 1. LinearLayout的属性

### 1.1 baselineAligned

主要用来作不同大小文字之间的对齐，设置为true时为对齐。


### 1.2 baselineAlignedChilIndexd

设置基线对齐的对象（第几个控件）


### 1.3 设置分割线

* android:divider="@drawable/shape"

* android:showDividers="middle|beginning|end"

divider必须使用shape，直接使用颜色或者Color是没有用的。
divider必须和showDividers一起使用，否则没有效果。

使用shape的时候要注意设置size属性不设置宽高分割线就不会显示出来。


### 1.4 对齐


* android:gravity="center"：设置容器内子元素的位置。

* android:layout_gravity="center"：设置当前布局相对于父布局的位置。


### 1.4.1 对齐失效的几个问题

1. view的宽度（或高度）必须大于其子view。 否则gravity将不会有任何影响。 因此， wrap_content和gravity没有意义。

2. 视图的宽度（或高度）必须小于父视图。 否则， layout_gravity将不起作用。 因此， match_parent和layout_gravity在一起毫无意义。

对于在水平方向上，想把一个按钮放在左边，一个按钮放在最右边的需求。可以使用:


```xml
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">


        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="保存"/>
        <Button
            android:layout_width="0dp"
            android:layout_height="0dp"
            android:layout_weight="4"></Button>
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="清空"/>
    </LinearLayout>
```

如果使用android:layout_gravity="right"会不起作用，因为orientation="horizontal"会让所有水平方向控制失效。


### 1.4.2 分割线