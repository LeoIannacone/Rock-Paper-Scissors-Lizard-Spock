#!/bin/bash

# Use this script to get apache-jena jars

SCRIPTS_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
BASE_URL="http://archive.apache.org/dist/jena/binaries"
JENA_VER="2.11.1"
FILENAME="apache-jena-${JENA_VER}.tar.gz"

cd $SCRIPTS_DIR

echo "Downloading jena ${JENA_VER} ..."
curl -o ${FILENAME} ${BASE_URL}/${FILENAME}

echo "Extracting ${FILENAME} ..."
tar xzf ${FILENAME}

if [ ! -d jena ] ; then mkdir jena ; fi
mv apache-jena*/lib/*jar jena/

echo "Cleaning directory ..."
rm -r apache-jena*

echo "Done."
