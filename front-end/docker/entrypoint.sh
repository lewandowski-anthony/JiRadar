#!/bin/sh

TARGET_DIR="/usr/share/nginx/html"

if [ -n "$APP_BASE_PATH" ]; then
  TARGET_DIR="/usr/share/nginx/html${APP_BASE_PATH}"
  mkdir -p "$TARGET_DIR"
  cp -r /usr/share/nginx/html/dist/* "$TARGET_DIR"/
else
  cp -r /usr/share/nginx/html/dist/* "$TARGET_DIR"/
fi

echo "window.ENV = { JIRADAR_BACKEND_URL: \"${JIRADAR_BACKEND_URL}\" };" > "${TARGET_DIR}/config.js"

exec nginx -g "daemon off;"