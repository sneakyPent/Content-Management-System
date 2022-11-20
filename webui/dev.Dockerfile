FROM node:18.12.1-alpine3.16

# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH
# install app dependencies
COPY package.json ./
COPY package-lock.json ./
RUN npm install
RUN npm install -g @angular/cli@15.0.0
RUN mkdir -p node_modules/.cache && chmod -R 777 node_modules/.cache

# add app
COPY . /app
EXPOSE 4200
# start app
CMD ["ng", "serve", "--host", "0.0.0.0", "--poll", "1", "--disable-host-check"]
