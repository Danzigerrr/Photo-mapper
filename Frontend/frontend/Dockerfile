# Use a Node.js base image
FROM node:20

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package.json package-lock.json ./

# Install dependencies
RUN npm ci

# Copy the rest of the frontend files
COPY .. .

# Print directory after copying files
RUN echo "After copying files:" && ls -la

# Build the application
RUN npm run build

# Print directory after building the app
RUN echo "After building the app:" && ls -la

# Expose the port the app runs on
EXPOSE 3000

# Command to run the application
CMD ["npm", "start"]
