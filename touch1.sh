#!/bin/bash
mkdir -p /var/www/html
echo $date >> /var/www/html/1.txt
chmod +x /var/www/html
chmod +r /var/www/html/*
