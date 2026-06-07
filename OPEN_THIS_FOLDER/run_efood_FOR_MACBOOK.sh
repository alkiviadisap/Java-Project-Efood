#!/bin/bash
echo "Starting EfoodApp......"
echo "= = = = = = = = = = = = = = = ="
echo "Made By Alkiviadis and Giannis"
echo "= = = = = = = = = = = = = = = ="
# Μεταβαίνουμε στον φάκελο του script για να βρει το jar
cd "$(dirname "$0")"
java -jar eFoodApp.jar
read -p "Press any key to continue..."