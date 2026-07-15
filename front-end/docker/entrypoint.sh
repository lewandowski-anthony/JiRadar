#!/bin/sh

echo "window.ENV = { JIRADAR_BACKEND_URL: \"${JIRADAR_BACKEND_URL}\" };" > /usr/share/nginx/html/config.js

exec nginx -g "daemon off;"