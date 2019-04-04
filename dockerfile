FROM ubuntu
#RUN apt-get update && apt-get -y upgrade
#RUN apt-get -y install maven
#RUN apt-get -y install nginx

#CMD service nginx start 
CMD echo "CMD start "

ENTRYPOINT echo "reach to entrypoint"
