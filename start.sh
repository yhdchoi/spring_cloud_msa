#!/bin/zsh
set -e

pid=$!

docker build ./eureka-server -t yhdc/eureka-server:1.0.0
wait
echo "Eureka server is containerized. Sart running the image..."
docker run -d -p 8761:8761 yhdc/eureka-server:1.0.0

docker build ./config-server -t yhdc/config-server:1.0.0
wait
echo "Config server is containerized. Sart running the image..."
docker run -d -p 8888:8888 yhdc/config-server:1.0.0

docker build ./api-gateway -t yhdc/api-gateway:1.0.0
wait
echo "Api Gateway is containerized. Sart running the image..."
docker run -d -p 8080:8080 yhdc/api-gateway:1.0.0

docker build ./account-service -t yhdc/account-service:1.0.0
wait
echo "Account service is containerized. Sart running the image..."
docker run -d -p 8081:8081 yhdc/account-service:1.0.0

docker build ./store-service -t yhdc/store-service:1.0.0
wait
echo "Store service is containerized. Sart running the image..."
docker run -d -p 8082:8082 yhdc/store-service:1.0.0

docker build ./product-service -t yhdc/product-service:1.0.0
wait
echo "Product service is containerized. Sart running the image..."
docker run -d -p 8083:8083 yhdc/product-service:1.0.0

docker build ./inventory-service -t yhdc/inventory-service:1.0.0
wait
echo "Inventory service is containerized. Sart running the image..."
docker run -d -p 8084:8084 yhdc/inventory-service:1.0.0

docker build ./order-service -t yhdc/order-service:1.0.0
wait
echo "Order service is containerized. Sart running the image..."
docker run -d -p 8085:8085 yhdc/order-service:1.0.0

docker build ./notification-service -t yhdc/notification-service:1.0.0
wait
echo "Notification service is containerized. Sart running the image..."
docker run -d -p 8086:8086 yhdc/notification-service:1.0.0

docker build ./image-service -t yhdc/image-service:1.0.0
wait
echo "Image service is containerized. Sart running the image..."
docker run -d -p 8100:8100 yhdc/image-service:1.0.0

docker build ./video-catalog-service -t yhdc/video-catalog-service:1.0.0
wait
echo "Video catalog service is containerized. Sart running the image..."
docker run -d -p 8101:8101 yhdc/video-catalog-service:1.0.0

docker build ./video-stream-service -t yhdc/video-stream-service:1.0.0
wait
echo "Video stream service is containerized. Sart running the image..."
docker run -d -p 8102:8102 yhdc/video-stream-service:1.0.0

echo "All services are containerized and running."

wait

docker compose up -d
wait
echo "All services are containerized and running."
