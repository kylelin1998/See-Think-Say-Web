### 简体中文 | [English](./README_en.md)

![License](https://img.shields.io/badge/license-MIT-green)
[![release](https://img.shields.io/github/v/release/kylelin1998/CountStatistics)](https://github.com/kylelin1998/CountStatistics/releases/latest)

## 简介
见思千言是基于ChatGPT的跨平台软件

这是见思前言的web版本， 后台开源， UI部分还是闭源的。

你可以试着搭建它， 通过浏览器直接使用它， 也可以搭配见思前言的跨平台软件一起使用。

重点说说搭配起来使用的好处： 目前软件是直接请求ChatGPT的API， 这也就是说你要开代理才能使用， 如果搭建了此web版本， 
在软件中更改掉域名即可请求到你部署的那台机子， 再由部署的机子请求ChatGPT， 这样搭配起来使用也就无所谓网络环境了。

目前此web版本ui部分资源太大了， 所以首次加载会比较慢， 请耐心等待

<https://github.com/kylelin1998/See-Think-Say-App>

![ad1ffd15df24e5616625aa421d975e2f9fd51e93.png](https://openimg.kylelin1998.com/img/ad1ffd15df24e5616625aa421d975e2f9fd51e93.png)

## 关于
我的Telegram: <https://t.me/KyleLin1998>

我的Telegram频道(软件最新通知会在此频道通知， 欢迎关注): <https://t.me/KyleLin1998Channel>

我的邮箱: email@kylelin1998.com

## 安装 & 部署
Dockerfile 与 jar文件放置到同一目录中进行打包镜像
```
docker build -t stsw .
```
需要在服务器上建立好对应目录， logs, config

需要先在config目录下创建一个application.yml文件
```
docker run --name stsw -p 9049:9049 -d -v $(pwd)/logs:/logs -v $(pwd)/See-Think-Say-Web-universal.jar:/app.jar -v $(pwd)/config:/config  --restart=always stsw
```

## 使用说明
application.yml示例:
```yml
spring:
  application:
    name: See-Think-Say-Web
  mvc:
    favicon:
      enabled: false
  thymeleaf:
    cache: false

server:
  port: 9049
  servlet:
    context-path: /

logging:
  config: classpath:logback-spring.xml

app:
  proxy:
    on-proxy: false
    proxy-host: 127.0.0.1
    proxy-port: 7890
```