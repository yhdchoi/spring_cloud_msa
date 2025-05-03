#!/bin/zsh
set -e

pid=$!

docker build ./eureka-server -t yhdc/eureka-server:1.0.0
wait
echo "[1] Eureka server image created."

docker build ./config-server -t yhdc/config-server:1.0.0
wait
echo "[2] Config server image created."

docker build ./api-gateway -t yhdc/api-gateway:1.0.0
wait
echo "[3] Api Gateway image created."

docker build ./account-service -t yhdc/account-service:1.0.0
wait
echo "[4] Account service image created."

docker build ./store-service -t yhdc/store-service:1.0.0
wait
echo "[5] Store service image created."

docker build ./product-service -t yhdc/product-service:1.0.0
wait
echo "[6] Product service image created."

docker build ./inventory-service -t yhdc/inventory-service:1.0.0
wait
echo "[7] Inventory service image created."

docker build ./order-service -t yhdc/order-service:1.0.0
wait
echo "[8] Order service image created."

docker build ./notification-service -t yhdc/notification-service:1.0.0
wait
echo "[9] Notification service image created."

docker build ./image-service -t yhdc/image-service:1.0.0
wait
echo "[10] Image service image created."

docker build ./video-catalog-service -t yhdc/video-catalog-service:1.0.0
wait
echo "[11] Video catalog service image created."

docker build ./video-stream-service -t yhdc/video-stream-service:1.0.0
wait
echo "[12] Video stream service image created."

wait

docker compose up -d
wait
echo "[13] Finished running containers."
