# Gradle

# 1. Gradle初探


在一个安卓项目里，根目录下包括：

* build.gradle

* gradle.properties

* local.properties

* seeting.gradle

此外，在每一个module中还有一个build.gradle。除了build.gradle，其他的gradle相关文件都是配置文件。

### 1.1 项目全局 build.gradle

```
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    ext.kotlin_version = '1.3.50'
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.5.1'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

```

build.gradle中最重要的就是buildscript的代码，在这里gradle指定了使用jcenter代码仓库，同时声明了依赖的gradle插件的版本。

在allprojects中，可以为项目整体配置一些属性。

### 1.2 module build.gradle

```
apply plugin: 'com.android.application'

apply plugin: 'kotlin-android'

apply plugin: 'kotlin-android-extensions'

android {
    compileSdkVersion 29
    defaultConfig {
        applicationId "com.sunnyweather.android"
        minSdkVersion 21
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.1.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test:runner:1.2.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    implementation "androidx.lifecycle:lifecycle-extensions:2.1.0"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.1.0"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0-alpha05"
    implementation "androidx.work:work-runtime:2.2.0"
    implementation 'com.google.android.material:material:1.0.0'
    implementation 'com.squareup.retrofit2:retrofit:2.6.1'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.1'
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.1"
}

```

### 1.2.1 apply plugin 

描述了gradle引入的插件。

apply plugin: 'com.android.application'  表明该module是一个Android Application，这个插件包含了Android项目相关的所有工具。

### 1.2.2 android 

描述了module构建过程中所用到的所有参数，默认有compileSdk Version 和 buildToolsVersion两个参数，对应编译sdk版本和android build tools版本。

### 1.2.3 dependencies

描述了module依赖的库，可以是jar的形式，也可以是aar的形式。aar相对jar，依赖更为简单，而且可以将图片的资源文件放入aar。

# 1.4 local.properties

```
## This file must *NOT* be checked into Version Control Systems,
# as it contains information specific to your local configuration.
#
# Location of the SDK. This is only used by Gradle.
# For customization when using a Version Control System, please read the
# header note.
#Mon Apr 26 18:57:46 CST 2021
sdk.dir=/Users/Francis/Library/Android/sdk

```

这里配置了gradle插件所需要使用的sdk路径。

# 1.5 Gradle Task

### 1.5.1 assemble task

用于组合项目的所有输出，包含了assembleDebug和assembleRelease两个Task，通过执行gradle assembale执行，可以编译处两个apk，debug和release。

### 1.5.2 Check

用来执行检查任务。

# 2. Gradle 进阶

### 2.1 更改项目结构

Android Studio对默认的项目结构是有一定约束的，这个约束就是gradle规定的。Gradle的基本项目结构开始于src/main目录。

```
    sourceSets {
        main {
            java.srcDirs = ['src']
            res.srcDirs = ['res']
            assets.srcDirs = ['assets']
            manifest.srcFile 'AndroidManifest.xml'
        }
    }
```

上面的sourceSets 就可以既兼容eclipse的开发，又可以兼容Android Studio的开发。除了兼容性以外，最大的用处是可以自定义项目结构，便于代码整理。

例如：

```
    sourceSets {
        main {
            res.srcDirs = [
                    'src/main/res',
                    'src/main/res/layout/activity',
                    'src/main/res/layout/fragment'
            ]
        }
    }

```

在这个脚本中，我们给src/main/res下添加了两个新的文件夹。

### 2.2 构建全局配置

### 2.2.1 定义全局常量

在多个module中都要使用的常量，通常会提取出来一个全局常量。

对于android 领域中的 compileSdkVersion 等，就可以提取出来。

在项目根目录下的build.gradle中，通过ext领域可以指定全局的配置信息。

```
ext {
    compileSdkVersion = 30
    buildToolsVersion = "30.0.2"
}
```

### 2.2.2 引用全局常量

```
    compileSdkVersion rootProject.ext.compileSdkVersion
    buildToolsVersion rootProject.ext.buildToolsVersion

```

当然，也可以写入allprojects领域，这样每个module就可以直接引用声明的变量了。


### 2.3 构建defaultConfig

在module build.gradle中，android领域中有一个defaultConfig领域，默认提供了以下配置。

```
    defaultConfig {
        applicationId "com.hello.android"
        minSdkVersion 28
        targetSdkVersion 30
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

```

gradle可以动态控制编译过程，例如动态控制versionName的生成。

```
    defaultConfig {
        applicationId "com.hello.android"
        minSdkVersion 28
        targetSdkVersion 30
        versionCode 1
        versionName getCustomVersionName()

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
```

```
def getCustomVersionName() {
    return "helo"
}
```

### 2.4 构建buildTypes

```
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

```

buildTypes可以生成不同的apk，可以实现只有在debug类型下才开启调试和Log功能。上面是默认的buildTypes。

release 和 debug都是系统默认会生成的类型。

gradle还支持生成自定义创建类型，例如，在脚本中增加一个xys类型，同时设置该雷总的applicationIdSuffix 为 .xys。

```

        xys {
            applicationIdSuffix ".xys"
        }
```

之后，执行gradle clean 和 gradle build 就会生成新的apk。

# 3. Gradle task 性能检测


### 3.1 lint test

Gradle内置了性能分析工具-profile。执行：

```
./gradlew build -profile   // Mac内置了./gradlew命令，gradle需要本地gradle化才能使用。
```

根目录下的build目录里就会有一个profile文件。

在给出的分析结果里，Task execution中，Lint相对耗时，但是，Lint在debug时一般是不需要的，因此，我们可以关闭Lint：

```
./gradlew build -x lint
```

或者，在build.gradle脚本中动态增加编译参数：

```
project.gradle.startParameter.excludedTaskNames.add('lint')
```

在之后的profile里，确实没有发现lint task执行的痕迹。


### 3.2 aapt

在debug版本中，可以使用(module/build.gradle/android领域中)：

```
    // debug时使用，正式版本一定要取消，因为取消了aapt优化，可能出现运行问题
    aaptOptions {
        cruncherEnabled = false
        cruncherEnabled = false
    }
```

# 4. Gradle 加速

前面介绍了如何对gradle 的Task进行优化。

Gradle在编译时会开启大量的task，同时生成很多中间文件，因此磁盘IO会让编译速度缓慢。

为了提高速度，最好减少本地项目库的依赖，多使用aar。

其次，可以在gradle.properties中增加如下代码开启gradle的多线程和多核心支持：
```

org.gradle.daemon=true 
        
org.gradle.parallel=true

org.gradle.configureondemand=true
```

同时，在build.gradle中表示开启增量编译。

```
dexOptions {
    incremental true
}
```


# 5. Gradle 生命周期

gradle从编译开始到编译完毕，一共经历了三个生命周期。

* init：gradle的初始化配置选项，执行setting.gradle脚本。

* configration：解析每一个project的build.gradle脚本，也就是解析所有projec的编译选项，然后生成taskgraph，这里包含了整个task的依赖关系。。

* build：按照taskgraph进行编译。