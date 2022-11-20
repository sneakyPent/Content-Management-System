#############
### build ###
#############

# base image
FROM node:18.12.1-alpine3.16 as build

# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# install and cache app dependencies
COPY package.json /app/package.json

COPY package-lock.json /app/package-lock.json
RUN npm install
RUN npm install -g @angular/cli@15.0.0

# add app
COPY . /app

# generate build
RUN ng build --output-path=dist --configuration=production

############
### prod ###
############

# base image
FROM nginx:1.23.2-alpine

# copy artifact build from the 'build environment'
COPY --from=build /app/dist /usr/share/nginx/html

# expose port 80
EXPOSE 80

# run nginx
CMD ["nginx", "-g", "daemon off;"]
