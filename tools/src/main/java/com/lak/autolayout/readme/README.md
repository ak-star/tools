autolayout包下，提供一套自动适配UI布局
适配方案：按比例进行view的大小设置，适配比例为-屏幕宽度/设计宽度【仅考虑宽度比例】

参考项目：https://github.com/hongyangAndroid/AndroidAutoLayout


# 第一步：
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