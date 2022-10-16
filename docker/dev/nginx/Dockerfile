FROM nginx:1.13.12-alpine
LABEL maintainer="Nikolas Zacharioudakis"

COPY ./nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]