#基础镜像
FROM openjdk:11.0-jre-slim-bullseye
#SpringBoot项目必须使用/tmp目录
VOLUME /tmp
#将项目的jar包拷贝并命名
COPY sfs-0.0.1.jar sfs.jar
RUN bash -c "touch /sfs.jar"
#暴露的端口
EXPOSE 8888
#执行命令运行项目
ENTRYPOINT ["java","-jar","sfs.jar"]
#解决时间不正确的问题
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone \