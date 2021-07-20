# ConstraintLayout

# 1. 约束：

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <EditText
        android:id="@+id/edt_username"
        android:layout_width="0dp"
        android:layout_height="50dp"
        android:layout_marginStart="25dp"
        android:layout_marginTop="200dp"
        android:layout_marginEnd="25dp"
        android:hint="请输入用户名"
        android:inputType="text"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/edt_password"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="25dp"
        android:hint="请输入密码"
        android:inputType="textPassword"
        app:layout_constraintEnd_toEndOf="@id/edt_username"
        app:layout_constraintStart_toStartOf="@id/edt_username"
        app:layout_constraintTop_toBottomOf="@id/edt_username" />

    <Button
        android:id="@+id/btn_login"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="30dp"
        android:text="登 录"
        app:layout_constraintEnd_toEndOf="@id/edt_password"
        app:layout_constraintStart_toStartOf="@id/edt_password"
        app:layout_constraintTop_toBottomOf="@id/edt_password" />


</androidx.constraintlayout.widget.ConstraintLayout>
```


在用户名view中，设置了marginTop 和 marginStart，需要注意，，如果不设置top_toTopOf属性，marginTop属性是不起作用的，任何没有增加约束的边设置margin属性都是不起作用的。

另外，这时候的width是0，如果设置为0，view的大小就完全取决于约束的大小。而如果规定了width的值为wrap_content，那么，控件大小就取决于内部大小。


另外一个例子：

```xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <EditText
        android:id="@+id/edt_username"
        android:layout_width="0dp"
        android:layout_height="50dp"
        android:layout_marginStart="25dp"
        android:layout_marginTop="200dp"
        android:layout_marginEnd="25dp"
        android:hint="请输入用户名"
        android:inputType="text"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/edt_password"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="25dp"
        android:hint="请输入密码"
        android:inputType="textPassword"
        app:layout_constraintEnd_toEndOf="@id/edt_username"
        app:layout_constraintStart_toStartOf="@id/edt_username"
        app:layout_constraintTop_toBottomOf="@id/edt_username" />

    <Button
        android:id="@+id/btn_login"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="30dp"
        android:layout_marginStart="50dp"
        android:text="登 录"
        app:layout_constraintStart_toStartOf="@id/edt_password"
        app:layout_constraintTop_toBottomOf="@id/edt_password" />

    <Button
        android:id="@+id/btn_sign_up"
        android:layout_marginTop="30dp"
        android:layout_marginEnd="50dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="注册"
        app:layout_constraintEnd_toEndOf="@id/edt_password"
        app:layout_constraintTop_toBottomOf="@id/edt_password" />


</androidx.constraintlayout.widget.ConstraintLayout>
```

为了实现登陆和注册相对居中，可以使用登陆marginStart、注册marginEnd。

也可以这样：

```xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <EditText
        android:id="@+id/edt_username"
        android:layout_width="0dp"
        android:layout_height="50dp"
        android:layout_marginStart="25dp"
        android:layout_marginTop="200dp"
        android:layout_marginEnd="25dp"
        android:hint="请输入用户名"
        android:inputType="text"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/edt_password"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="25dp"
        android:hint="请输入密码"
        android:inputType="textPassword"
        app:layout_constraintEnd_toEndOf="@id/edt_username"
        app:layout_constraintStart_toStartOf="@id/edt_username"
        app:layout_constraintTop_toBottomOf="@id/edt_username" />

    <Button
        android:id="@+id/btn_login"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="30dp"
        android:text="登 录"
        app:layout_constraintEnd_toStartOf="@id/btn_sign_up"
        app:layout_constraintStart_toStartOf="@id/edt_password"
        app:layout_constraintTop_toBottomOf="@id/edt_password" />

    <Button
        android:id="@+id/btn_sign_up"
        android:layout_marginTop="30dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="注册"
        app:layout_constraintStart_toEndOf="@id/btn_login"
        app:layout_constraintEnd_toEndOf="@id/edt_password"
        app:layout_constraintTop_toBottomOf="@id/edt_password" />


</androidx.constraintlayout.widget.ConstraintLayout>
```

# 2. chains

使用chains时，需要把view设置为wrap。

存在多个View，他们的宽度是可变的，但它们之间的间隔是相等的，并且要平均分配整个屏幕的宽度。

对于这个需求，Linearlayout的weight可以实现，在constraint中，可以用chain实现。

chain有五个模式：

* Spread Chain：伸展链，默认设置，形成链的 View 分散排列，间隔相等。
* Spread Inside Chain：内部伸展链，也是平均分配间隔，但 View 和 parent 直接没有间隔。
* Weighted Chain：权重链，与 LinearLayout 的 weight 相似，按比例分配空间大小。
* Packed Chain：打包链，将所有 View 打包在一起，当做整体，居中。
* Packed Chain with Bias：带偏斜的打包链。


