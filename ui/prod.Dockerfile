# build stage
FROM node:18.11-alpine3.16 as build-stage
# set working directory
WORKDIR /app
# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH
# install app dependencies
COPY package.json  ./
COPY package-lock.json ./
RUN npm install
RUN npm install react-scripts@5.0.1 -g
# add app
COPY . ./
# start app
RUN npm run build

# production stage
FROM nginx:1.23.1-alpine as production-stage
COPY --from=build-stage /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
