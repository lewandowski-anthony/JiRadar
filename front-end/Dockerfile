FROM nginx:stable-alpine

COPY docker/nginx.conf /etc/nginx/conf.d/default.conf

RUN rm -rf /usr/share/nginx/html/*

COPY dist/ /usr/share/nginx/html/
COPY docker/entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh && \
    chown -R nginx:nginx /usr/share/nginx/html /var/cache/nginx /var/run /var/log/nginx /entrypoint.sh

EXPOSE 80

USER nginx
ENTRYPOINT ["/entrypoint.sh"]