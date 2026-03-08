#!/bin/bash

echo "$(date): Starting deploy..."

docker-compose up -d --build

docker image prune -f

echo "$(date): Deploy finished successfully."