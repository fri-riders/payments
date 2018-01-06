[![Build Status](https://travis-ci.org/fri-riders/payments.svg?branch=master)](https://travis-ci.org/fri-riders/payments)
# Payment Service

## Run with Docker Compose
1. Build app: `mvn clean package`
1. Run: `docker-compose up --build`

App is accessible on port `8087`

# Registered endpoints
## Payments
* `GET: /v1/payments` Returns list of payments
* `POST: /v1/payments` Create new payment (params: `userId:string, bookingId:integer, amount:float, paypalPayment:boolean`) \***
* `GET: /v1/payments/{paymentId}` Get info about payment \***
## Config
* `GET: /v1/config` Returns list of config values
* `GET: /v1/config/info` Returns info about project
## Health
* `GET: /health` Returns health status
* `GET: /v1/health-test/instance` Returns info about instance
* `POST: /v1/health-test/update` Update property `healthy` (params: `healthy:boolean`)
* `GET: /v1/health-test/dos/{n}` Execute calculation of n-th Fibonacci number
## Metrics
* `GET: /metrics` Returns metrics

#### Notes
\*** must be authenticated with `authToken` header present and supplied with valid JWT from login