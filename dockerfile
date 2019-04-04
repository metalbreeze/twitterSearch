FROM ubuntu
RUN apt-get update && apt-get -y upgrade
#RUN apt-get -y install maven
RUN apt-get -y install nginx

CMD service nginx start 

#ENTRYPOINT echo "reach to entrypoint"
