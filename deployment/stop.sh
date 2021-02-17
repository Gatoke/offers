#!/bin/bash

function stopWithVolumesCleanup() {
  docker-compose down -v
}

function main() {
  stopWithVolumesCleanup
}

main
