#!/bin/bash

echo "Fetching latest changes..."

git fetch
git pull origin master

echo "$(date): Starting deploy..."

docker-compose up -d --build

docker image prune -f

echo "$(date): Deploy finished successfully."