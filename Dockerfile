FROM maven:3.5.2-alpine as builder
MAINTAINER Dominikyang
WORKDIR /tmp/workdir
COPY src .
COPY pom.xml .
# 编译打包 (jar包生成路径：/app/target)
RUN mvn package -Dmaven.test.skip=true


FROM openjdk:8-jre-alpine
MAINTAINER Dominikyang
VOLUME /tmp/workdir/docker/output
WORKDIR /tmp/workdir
COPY --from=builder /tmp/workdir/target/jsonschema2pojo-1.0-SNAPSHOT.jar .

ENTRYPOINT ["/entrypoint.sh"]