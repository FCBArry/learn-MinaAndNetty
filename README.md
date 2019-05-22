# learn-MinaAndNetty
learn mina and netty......

------------------------------------
project:gradle project

------------------------------------
java:jdk8

------------------------------------
author:https://github.com/FCBArry

------------------------------------
mina包：简易的mina聊天程序

使用方法：

（1）运行StartServer.java

（2）运行StartClient.java，可以运行多个

（3）可以在服务器与客户端，或者客户端与客户端之间聊天

聊天格式目前写定了（可以自行修改）：

与服务器聊天：1 + port-内容    eg：1 我是客户端

与客户端聊天：2 ip:port-内容    eg：2 127.0.0.1:62805-收到请回答over！

包结构：

client：客户端

codec：编解码

commond：消息

handler：消息处理

server：服务器

utils：工具类

------------------------------------
netty包：netty通信实例，使用自定义编解码，可集成pb