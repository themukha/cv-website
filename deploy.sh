#!/bin/bash

# Update repo from GitHub
git fetch origin master
git reset --hard origin/master

# Rebuild and run
docker-compose up -d --build

# Clean disk
docker image prune -f