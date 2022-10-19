# build stage
FROM node:17.8-alpine3.14 as build-stage
# set working directory
WORKDIR /app
# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH
# install app dependencies
COPY /ui/package.json ./
COPY /ui/package-lock.json ./
RUN npm install --silent
RUN npm install react-scripts@5.0.1 -g --silent
# add app
COPY /ui /app
# start app
RUN npm run build

# production stage
FROM nginx:1.23.1-alpine as production-stage
COPY /docker/prod/nginx/nginx.conf /etc/nginx/nginx.conf
COPY --from=build-stage /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]