#!/bin/bash

docker run -p 127.0.0.1:5432:5432 --name postgres \
  -e TZ=Asia/Bangkok \
  -e POSTGRES_PASSWORD=securedpassword \
  -d --restart=always \
  -v pgdata:/var/lib/postgresql/data \
  postgres:latest

