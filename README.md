# safechat
safechat是基于Spring Boot和Netty实现的一款简单的网络聊天室。
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