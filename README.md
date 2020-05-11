# safechat
safechat是基于Spring Boot和Netty实现的一款简单的网络聊天室。**来自[Kanarienvogels/Chatroom](https://github.com/Kanarienvogels/Chatroom)的开源项目。**

在学习Netty后，找到了原作者的聊天室项目，理解了代码运行逻辑后，出于熟练使用Spring Boot的打算，遂使用Spring Boot重写了后端，
原项目是Spring写的。

相较于原项目：  
- 整合MySQL数据库，原项目数据是写死的  
- 增加心跳机制，避免客户端网络异常导致服务端资源耗尽  
- 增加添加好友功能  
- ~~删除发送文件功能~~


该项目仅用于学习。
## 数据库
本地创建MySQL数据库
```sql
create database safechat
```
在项目根目录下执行
```bash
mvn flyway:migrate
```

## 启动
浏览器输入 http://localhost:9999/

## 致谢
[Kanarienvogels/Chatroom](https://github.com/Kanarienvogels/Chatroom)