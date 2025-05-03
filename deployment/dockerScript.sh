#!/bin/bash

# BELOW SCRIPT needs to be executed at the folder top level

# Stop and remove the front container (if it exists)
if docker ps -aq -f name=angular-container | grep -q .; then
    echo "Stopping and removing the existing container..."
    docker stop angular-container
    docker rm angular-container
fi

# Stop and remove server container (if it exists)
if docker ps -aq -f name=spring-container | grep -q .; then
    echo "Stopping and removing the existing container..."
    docker stop spring-container
    docker rm spring-container
fi

# Remove dangling images safely
echo "Cleaning up dangling images..."
docker image prune -f

# Build a Docker image
docker build --no-cache -t poke-app-front -f ./deployment/angular/Dockerfile .

# Build server
docker build -t poke-app-server -f ./deployment/server/Dockerfile .

# Run front container
docker run -d -p 80:80 --name angular-container poke-app-front

# Run server container
docker run -d -p 4445:4445 spring-container poke-app-server

# Verify the container is running
docker ps