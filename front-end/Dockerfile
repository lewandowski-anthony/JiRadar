FROM nginx:stable-alpine

COPY docker/nginx.conf /etc/nginx/conf.d/default.conf

RUN sed -i 's|^pid.*|pid /tmp/nginx.pid;|' /etc/nginx/nginx.conf && \
    sed -i 's|^user |#user |' /etc/nginx/nginx.conf

RUN rm -rf /usr/share/nginx/html/*

COPY dist/ /usr/share/nginx/html/
COPY docker/entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh && \
    chown -R nginx:nginx /usr/share/nginx/html \
                         /var/cache/nginx \
                         /var/log/nginx \
                         /entrypoint.sh

EXPOSE 8080

USER nginx

ENTRYPOINT ["/entrypoint.sh"]