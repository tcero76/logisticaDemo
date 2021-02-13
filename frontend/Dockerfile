FROM node:12.4.0-alpine as build
WORKDIR /app
#Copiar package.json y descargar dependencias.
COPY frontend/package.json frontend/package-lock.json ./
RUN npm install
#Copiar la fuente.
COPY frontend/ ./

ARG REACT_APP_API_BASE_URL
ENV REACT_APP_API_BASE_URL=${REACT_APP_API_BASE_URL}

# Tests
RUN npm run test

# Build
RUN npm run build

#### Stage 2: Serve the React application from Nginx 
FROM nginx:1.17.0-alpine

# Copy the react build from Stage 1
COPY --from=build /app/build /var/www

# Copy our custom nginx config
COPY frontend/nginx.conf /etc/nginx/nginx.conf

# Expose port 80 to the Docker host, so we can access it 
# from the outside.
EXPOSE 80

ENTRYPOINT ["nginx","-g","daemon off;"]
