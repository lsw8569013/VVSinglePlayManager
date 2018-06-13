# VVSinglePlayManager
videoPlay,videoCache,keyboard,videoPlayManager，边下边播

https://blog.csdn.net/lsw8569013/article/details/74231530 联系作者

## 视频播放库 修改自 VideoPlayerManager ![VideoPlayerManager](https://github.com/danylovolokh/VideoPlayerManager)
在videoPlayerManager基础上，添加缓存AndroidVideoCache ![AndroidVideoCache](https://github.com/danikula/AndroidVideoCache) ，可以边下边播
视频列表 ，保持只有一个视频在播放，视频点击变全屏~


  ![image](https://github.com/lsw8569013/VVSinglePlayManager/blob/master/%E5%9B%BE/img.gif ) 


## 仿照朋友圈，弹出键盘，视频继续播放，键盘添加一些大小表情~~

![image](https://github.com/lsw8569013/VVSinglePlayManager/blob/master/%E5%9B%BE/key.gif ) 


# Library introduction

# VideoPlayerManager ![VideoPlayerManager](https://github.com/danylovolokh/VideoPlayerManager)
This is a project designed to help controlling Android MediaPlayer class. It makes it easier to use MediaPlayer ListView and RecyclerView.
Also it tracks the most visible item in scrolling list. When new item in the list become the most visible, this library gives and API to track it.

It consists from two libraries:

1. Video-Player-Manager - it gives the ability to invoke MediaPlayer methods in a background thread. It has utilities to have only one playback when multiple media files are in the list.
Before new playback starts, it stops the old playback and releases all the resources.

2. List-Visibility-Utils - it's a library that tracks the most visible item in the list and notifies when it changes.
NOTE: there should be the most visible item.
If there will be 3 or more items with the same visibility percent the result might be unpredictable.
Recommendation is to have few views visible on the screen. View that are big enough so that only one view is the most visible, look at the demo below.

These two libraries combined are the tool to get a Video Playback in the scrolling list: ListView, RecyclerView.

# Details of implementation

[![Medium](https://img.shields.io/badge/Meduim-Implementing%20video%20playback%20in%20a%20scrolled%20list%20(ListView%20%26%20RecyclerView)-blue.svg)](https://medium.com/@v.danylo/implementing-video-playback-in-a-scrolled-list-listview-recyclerview-d04bc2148429)

[![Android_weekly](https://img.shields.io/badge/Android%20Weekly-%09Implementing%20video%20playback%20in%20a%20scrolled%20list-green.svg)](http://androidweekly.net/issues/issue-189)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-VideoPlayerManager-green.svg?style=true)](https://android-arsenal.com/details/1/3073)


# Problems with video list
1. We cannot use usual VideoView in the list. VideoView extends SurfaceView, and SurfaceView doesn't have UI synchronization buffers. All this will lead us to the situation where video that is playing is trying to catch up the list when you scroll it. Synchronization buffers are present in TextureView but there is no VideoView that is based on TextureView in Android SDK version 15. So we need a view that extends TextureView and works with Android MediaPlayer.

2. Almost all methods (prepare, start, stop etc...) from MediaPlayer are basically calling native methods that work with hardware. Hardware can be tricky and if will do any work longer than 16ms (And it sure will) then we will see a lagging list. That's why need to call them from background thread.


# ![AndroidVideoCache](https://github.com/danikula/AndroidVideoCache)

## Why AndroidVideoCache?
Because there is no sense to download video a lot of times while streaming!
`AndroidVideoCache` allows to add caching support to your `VideoView/MediaPlayer`, [ExoPlayer](https://github.com/danikula/AndroidVideoCache/tree/exoPlayer) or any another player with help of single line!

## Features
- caching to disk during streaming;
- offline work with cached resources;
- partial loading;
- cache limits (max cache size, max files count);
- multiple clients for same url.


