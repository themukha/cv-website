#!/bin/bash

cd /opt/cv-website

# 1. Get updates from repo
git fetch origin master

# 2. Get hashes of last local and remote commits
LOCAL_HASH=$(git rev-parse HEAD)
REMOTE_HASH=$(git rev-parse origin/master)

# 3. Compare them
if [ "$LOCAL_HASH" = "$REMOTE_HASH" ]; then
    echo "$(date): No changes detected. Skipping build."
    # 3.1. Stop script if no changes
    exit 0
fi

echo "$(date): Changes detected! Starting deploy..."

# 4. If hashes are different - update repo
git reset --hard origin/master

# 5. Rebuild
docker-compose up -d --build

# 6. Clean
docker image prune -f

echo "$(date): Deploy finished successfully."