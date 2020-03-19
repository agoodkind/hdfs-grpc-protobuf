FROM openjdk:11
COPY . /var/www/java
WORKDIR /var/www/java
RUN make all
CMD ["./run.sh", "config/test.txt"]