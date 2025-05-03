# Use Node.js as the base image
FROM node:20

# Set the working directory
WORKDIR /app

# Copy package.json and install dependencies
COPY ./angular-app/package.json ./angular-app/package-lock.json ./
RUN npm install

# Copy the rest of the application
COPY ./angular-app ./

# Build the Angular app
RUN npm run build --non-interactive --output-hashing=all

# Use Nginx to serve the built app
FROM nginx:latest
COPY ./deployment/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=0 /app/dist/poke-app/browser/* /usr/share/nginx/html/

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]