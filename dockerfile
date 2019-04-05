FROM ubuntu
RUN apt-get update && apt-get -y upgrade
#RUN apt-get -y install maven
RUN apt-get -y install nginx

ENTRYPOINT service nginx start ; cat /1.txt ; while true ; do sleep 100; done;

