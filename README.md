# tools [![](https://jitpack.io/v/ak-star/tools.svg)](https://jitpack.io/#ak-star/tools)
Ui适配，及常用工具

# 导入依赖：

Step 1. 在根项目的build.gradle中添加仓库
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Step 2. 在使用module中build.gradle中增加依赖
```
dependencies {
	implementation 'com.github.ak-star:tools:v1.0.0'
}
```

# 效果：
![手机1](https://user-images.githubusercontent.com/16659573/41703676-bfafade0-7566-11e8-98d4-a7d2a4ffd99b.png)
![手机2](https://user-images.githubusercontent.com/16659573/41703680-c12b8824-7566-11e8-815e-db368a2e1521.jpg)
![手机3](https://user-images.githubusercontent.com/16659573/41703681-c29ab4c8-7566-11e8-97e5-4d2470b22674.jpg)

autolayout包下，提供一套自动适配UI布局
适配方案：按比例进行view的大小设置，适配比例为-屏幕宽度/设计宽度【仅考虑宽度比例】

在你的项目的AndroidManifest中注明你的设计稿的尺寸。

```
<meta-data android:name="design_width" android:value="750" />
<meta-data android:name="open_debug" android:value="true" />
```
或者：
在自定义的Application的onCreate中注明你的设计稿的尺寸
```
AutoLayoutConfig.getInstance().designWidth(750).setDebug(false);
```

使用方式可以参考 https://github.com/hongyangAndroid/AndroidAutoLayout
