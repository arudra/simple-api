FROM java:8-jre
MAINTAINER Abhishek Rudra <arudra13@gmail.com>
RUN apt-get update && apt-get install -y ssh
ADD build/install/hello/bin /opt/hello-server/bin
ADD build/install/hello/lib /opt/hello-server/lib
ENV JAVA_OPTS='-Xms64m -Xmx64m -Xss256k -XX:+UseSerialGC -server -XX:+ExitOnOutOfMemoryError'
ENTRYPOINT ["/opt/hello-server/bin/hello"]