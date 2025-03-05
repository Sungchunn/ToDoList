#!/bin/bash

docker run -p 127.0.0.1:13306:3306 --name mariadb \
  -e TZ=Asia/Bangkok \
  -e MARIADB_ROOT_PASSWORD=securedpassword \
  -d --restart=always \
  -v fdbe8a9b16d174b35eb278e2bb9bf23114fa22dc23e64de182d3b4afa2a4b06b:/var/lib/mysql \
  mariadb:latest