# SPRING BOOT APP

## Prerequisites
* Java 8
* Docker
* Docker Compose

# Install dependencies
```shell
mvn clean install
```

# Run MariaDB container
```shell
cd mariadb-docker
docker-compose up -d
```

# Run the application
```shell
mvn spring-boot:run
```