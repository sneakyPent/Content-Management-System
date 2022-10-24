FROM node:18.11.0-alpine3.15

# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH
# install app dependencies
COPY package.json ./
COPY package-lock.json ./
RUN npm install --silent
RUN npm install react-scripts@5.0.1 -g --silent
RUN mkdir -p node_modules/.cache && chmod -R 777 node_modules/.cache

# add app
COPY ../.. ./

# start app
CMD ["npm", "start"]