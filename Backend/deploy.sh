#!/bin/bash

function trap_ctrlc() {
	echo "Shutting down docker."
	sudo docker-compose down
	echo "Stopped bash opeation."
	exit 2
}

trap "trap_ctrlc" 2

cd user_management_app/ &&
 sudo mvn clean package -DskipTests &&
 sudo docker-compose build &&
 sudo docker-compose up
