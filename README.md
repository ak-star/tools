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
	implementation 'com.github.ak-star:tools:v1.0.1'
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

```
<?xml version="1.0" encoding="utf-8"?>
<com.lak.autolayout.layout.AutoFrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/c_eeeeee">

    <com.lak.autolayout.layout.AutoToolbar
        android:layout_width="match_parent"
        android:layout_height="80px"
        android:background="@color/c_white"
        app:contentInsetStart="0dp">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:text="收入报表"
            android:textColor="@color/c_2d2d2d"
            android:textSize="30px" />
    </com.lak.autolayout.layout.AutoToolbar>

    <android.support.v4.widget.NestedScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="80px">

        <com.lak.autolayout.layout.AutoRelativeLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <android.support.v7.widget.AppCompatImageView
                android:id="@+id/ef_top_bg"
                android:layout_width="match_parent"
                android:layout_height="300px"
                android:scaleType="fitXY"
                android:src="@mipmap/i_income_top_bg" />

            <android.support.v7.widget.AppCompatTextView
                android:id="@+id/ef_total"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentTop="true"
                android:layout_centerHorizontal="true"
                android:layout_marginTop="48px"
                android:text="90.56"
                android:textColor="@color/c_white"
                android:textSize="86px" />

            <TextView
                android:id="@+id/ef_hint"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_below="@id/ef_total"
                android:layout_centerHorizontal="true"
                android:layout_marginTop="4px"
                android:text="劳伦斯帮我赚了（元）"
                android:textColor="@color/c_white"
                android:textSize="24px" />

            <com.lak.autolayout.layout.AutoLinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_alignBottom="@id/ef_top_bg"
                android:layout_marginBottom="20px"
                android:orientation="horizontal">

                <TextView
                    android:id="@+id/ef_today"
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:gravity="center_horizontal"
                    android:text="今日"
                    android:textColor="@color/c_white"
                    android:textSize="34px" />

                <TextView
                    android:id="@+id/ef_yesterday"
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:gravity="center_horizontal"
                    android:text="昨日"
                    android:textColor="@color/c_white"
                    android:textSize="34px" />

                <TextView
                    android:id="@+id/ef_current_month"
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:gravity="center_horizontal"
                    android:text="本月"
                    android:textColor="@color/c_white"
                    android:textSize="34px" />

                <TextView
                    android:id="@+id/ef_last_month"
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:gravity="center_horizontal"
                    android:text="上月"
                    android:textColor="@color/c_white"
                    android:textSize="34px" />
            </com.lak.autolayout.layout.AutoLinearLayout>

            <View
                android:layout_width="36px"
                android:layout_height="6px"
                android:layout_alignBottom="@id/ef_top_bg"
                android:layout_marginBottom="6px"
                android:layout_marginLeft="68px"
                android:background="@color/c_white" />

            <com.lak.autolayout.layout.AutoFrameLayout
                android:id="@+id/ef_earning"
                android:layout_width="match_parent"
                android:layout_height="100px"
                android:layout_below="@id/ef_top_bg"
                android:layout_marginBottom="10px"
                android:background="@color/c_white">

                <View
                    android:layout_width="4px"
                    android:layout_height="26px"
                    android:layout_gravity="center_vertical"
                    android:layout_marginLeft="20px"
                    android:background="@color/c_ff783e" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center_vertical"
                    android:layout_marginLeft="44px"
                    android:text="收益：200元"
                    android:textColor="@color/c_2d2d2d"
                    android:textSize="30px" />
            </com.lak.autolayout.layout.AutoFrameLayout>

            <com.lak.autolayout.layout.AutoFrameLayout
                android:id="@+id/ef_order"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_below="@id/ef_earning"
                android:background="@color/c_white">

                <View
                    android:layout_width="4px"
                    android:layout_height="26px"
                    android:layout_gravity="center_vertical"
                    android:layout_marginLeft="20px"
                    android:background="@color/c_ffb74d" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="36px"
                    android:layout_marginLeft="44px"
                    android:layout_marginTop="36px"
                    android:text="我的订单收益"
                    android:textColor="@color/c_2d2d2d"
                    android:textSize="30px" />
            </com.lak.autolayout.layout.AutoFrameLayout>

            <com.lak.autolayout.layout.AutoRelativeLayout
                android:id="@+id/ef_order_content"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_below="@id/ef_order"
                android:layout_marginBottom="10px"
                android:background="@color/c_white">

                <com.lak.autolayout.layout.AutoLinearLayout
                    android:id="@+id/ef_order_1"
                    android:layout_width="210px"
                    android:layout_height="160px"
                    android:layout_below="@id/ef_order"
                    android:layout_marginBottom="50px"
                    android:layout_marginLeft="30px"
                    android:background="@mipmap/i_income_num"
                    android:gravity="center"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="56.09"
                        android:textColor="@color/c_white"
                        android:textSize="46px" />

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="1px"
                        android:text="预估收入（元）"
                        android:textColor="@color/c_white"
                        android:textSize="24px" />
                </com.lak.autolayout.layout.AutoLinearLayout>

                <com.lak.autolayout.layout.AutoLinearLayout
                    android:id="@+id/ef_order_2"
                    android:layout_width="210px"
                    android:layout_height="160px"
                    android:layout_below="@id/ef_order"
                    android:layout_marginBottom="50px"
                    android:layout_marginLeft="30px"
                    android:layout_toRightOf="@id/ef_order_1"
                    android:background="@mipmap/i_click_num"
                    android:gravity="center"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="2300"
                        android:textColor="@color/c_white"
                        android:textSize="46px" />

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="1px"
                        android:text="点击数（个）"
                        android:textColor="@color/c_white"
                        android:textSize="24px" />
                </com.lak.autolayout.layout.AutoLinearLayout>

                <com.lak.autolayout.layout.AutoLinearLayout
                    android:id="@+id/ef_order_3"
                    android:layout_width="210px"
                    android:layout_height="160px"
                    android:layout_below="@id/ef_order"
                    android:layout_marginBottom="50px"
                    android:layout_marginLeft="30px"
                    android:layout_toRightOf="@id/ef_order_2"
                    android:background="@mipmap/i_order_num"
                    android:gravity="center"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="56900"
                        android:textColor="@color/c_white"
                        android:textSize="46px" />

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="1px"
                        android:text="订单数（单）"
                        android:textColor="@color/c_white"
                        android:textSize="24px" />
                </com.lak.autolayout.layout.AutoLinearLayout>
            </com.lak.autolayout.layout.AutoRelativeLayout>

            <com.lak.autolayout.layout.AutoFrameLayout
                android:id="@+id/ef_order_team"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_below="@id/ef_order_content"
                android:background="@color/c_white">

                <View
                    android:layout_width="4px"
                    android:layout_height="26px"
                    android:layout_gravity="center_vertical"
                    android:layout_marginLeft="20px"
                    android:background="@color/c_ffb74d" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="36px"
                    android:layout_marginLeft="44px"
                    android:layout_marginTop="36px"
                    android:text="团队订单收益"
                    android:textColor="@color/c_2d2d2d"
                    android:textSize="30px" />
            </com.lak.autolayout.layout.AutoFrameLayout>


            <com.lak.autolayout.layout.AutoRelativeLayout
                android:id="@+id/ef_order_team_content"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_below="@id/ef_order_team"
                android:layout_marginBottom="10px"
                android:background="@color/c_white">

                <com.lak.autolayout.layout.AutoLinearLayout
                    android:id="@+id/ef_order_team_1"
                    android:layout_width="210px"
                    android:layout_height="160px"
                    android:layout_below="@id/ef_order"
                    android:layout_marginBottom="50px"
                    android:layout_marginLeft="30px"
                    android:background="@mipmap/i_income_num"
                    android:gravity="center"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="56.09"
                        android:textColor="@color/c_white"
                        android:textSize="46px" />

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="1px"
                        android:text="预估收入（元）"
                        android:textColor="@color/c_white"
                        android:textSize="24px" />
                </com.lak.autolayout.layout.AutoLinearLayout>

                <com.lak.autolayout.layout.AutoLinearLayout
                    android:id="@+id/ef_order_team_2"
                    android:layout_width="210px"
                    android:layout_height="160px"
                    android:layout_below="@id/ef_order"
                    android:layout_marginBottom="50px"
                    android:layout_marginLeft="30px"
                    android:layout_toRightOf="@id/ef_order_team_1"
                    android:background="@mipmap/i_click_num"
                    android:gravity="center"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="2300"
                        android:textColor="@color/c_white"
                        android:textSize="46px" />

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="1px"
                        android:text="点击数（个）"
                        android:textColor="@color/c_white"
                        android:textSize="24px" />
                </com.lak.autolayout.layout.AutoLinearLayout>
            </com.lak.autolayout.layout.AutoRelativeLayout>
        </com.lak.autolayout.layout.AutoRelativeLayout>
    </android.support.v4.widget.NestedScrollView>
</com.lak.autolayout.layout.AutoFrameLayout>
```

使用方式可以参考 https://github.com/hongyangAndroid/AndroidAutoLayout
