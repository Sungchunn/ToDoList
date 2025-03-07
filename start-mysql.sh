#!/bin/bash

docker run -d \
    --name mariadb \
    -p 127.0.0.1:3306:3306 \
    -p 172.17.0.1:3306:3306 \
    -e MARIADB_ROOT_PASSWORD=securedpassword \
    --restart=always \
    mariadb:latest

