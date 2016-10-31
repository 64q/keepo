#!/bin/sh

#
# Redis backend script
#

# versions
VERSION="3.2.4-alpine"

echo " --> Removing previous backend container"

docker stop keepo-backend \
  && docker rm -v keepo-backend

echo " --> Launching redis $VERSION"

docker run --name keepo-backend \
  -p 6379:6379 \
  -d redis:$VERSION
