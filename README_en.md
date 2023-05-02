### [简体中文](./README.md) | English

![License](https://img.shields.io/badge/license-MIT-green)
[![release](https://img.shields.io/github/v/release/kylelin1998/CountStatistics)](https://github.com/kylelin1998/CountStatistics/releases/latest)

## Introduction
See Think Say App is a cross-platform software built on ChatGPT.

This web version of See Think Say App preface is open-source for the backend, while the UI part remains closed-source. You can try to set it up and use it directly through your browser, or use it with the cross-platform software of See Think Say App.

Let's talk about the benefits of using them together. Currently, the software directly requests ChatGPT’s API, which means that you need to use a proxy in order to use it. However, if you set up this web version and change the domain name in the software, you can request your deployed machine instead, and then your deployed machine can request ChatGPT. This way, using them together becomes less sensitive to network environment.

The UI resources for this web version are currently too large, so the initial loading may be slow. Please be patient.

<https://github.com/kylelin1998/See-Think-Say-App>

![ad1ffd15df24e5616625aa421d975e2f9fd51e93.png](https://openimg.kylelin1998.com/img/ad1ffd15df24e5616625aa421d975e2f9fd51e93.png)

## About
My Telegram: <https://t.me/KyleLin1998>

My Telegram Channel(Software, if have a new version, will be in this channel to notify everyone. Welcome to subscribe to it.): <https://t.me/KyleLin1998Channel>

My email: email@kylelin1998.com

## Install & Deploy
Dockerfile and Jar file to save the same directory for building docker image.
```
docker build -t stsw .
```
You need to build logs, config directory on your personal server.

Then, Need to create a file named application.yml in config directory.
```
docker run --name stsw -p 9049:9049 -d -v $(pwd)/logs:/logs -v $(pwd)/See-Think-Say-Web-universal.jar:/app.jar -v $(pwd)/config:/config  --restart=always stsw
```

## Usage
application.yml:
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