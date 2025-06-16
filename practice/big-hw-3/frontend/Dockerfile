FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
RUN ls -la /app/dist

FROM busybox:1.36.0

COPY --from=builder /app/dist /static
EXPOSE 80
CMD ["busybox", "httpd", "-f", "-p", "80", "-h", "/static"]
