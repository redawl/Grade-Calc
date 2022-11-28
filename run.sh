#!/usr/bin/env bash

# Generate JAR
if mvn clean package;
then
  # Build docker container
  docker build -t gradecalc .

  # Compose containers
  docker compose up -d
fi
