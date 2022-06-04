#!/bin/bash
IMAGE_NAME=lionlight/dgexchange

pushd ../../

gradle build -DskipTests=true

docker build -t $IMAGE_NAME .

# get image info
docker image ls | grep $IMAGE_NAME