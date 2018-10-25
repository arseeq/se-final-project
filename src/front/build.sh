#!/bin/bash
shopt -s extglob
curDir=pwd
rm ../main/webapp/!(WEB-INF) -rf
npm run build
cp -a ./build/* ../main/webapp
