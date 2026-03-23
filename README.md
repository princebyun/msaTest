# msa-demo (Java 17 / MySQL / Kubernetes)

Java 17 기반의 간단한 MSA 시작 프로젝트입니다.  
전자정부프레임워크(eGovFrame)에서 권장하는 계층형 구조(Controller-Service-Repository-Domain)와 패키지 네이밍(`egovframework.msa...`)을 반영했습니다.

## 구성

- `api-gateway`: 외부 진입점 (Spring Cloud Gateway)
- `user-service`: 사용자 CRUD 서비스
- `order-service`: 주문 CRUD 서비스
- `infra/mysql`: 로컬 DB 초기화 스크립트
- `infra/k8s`: 쿠버네티스 배포 매니페스트

## 사전 요구사항

- JDK 17
- Maven 3.9+
- Docker / Docker Compose
- Kubernetes 클러스터 (Oracle Cloud OKE 또는 k3s 등)
- NGINX Ingress Controller (Ingress 사용 시)

## 로컬 실행 (Docker Compose)

```bash
docker compose up --build
```

실행 후:

- Gateway: `http://localhost:8080`
- User API: `http://localhost:8080/users`
- Order API: `http://localhost:8080/orders`
- User 화면(Thymeleaf): `http://localhost:8080/user-ui`
- Order 화면(Thymeleaf): `http://localhost:8080/order-ui`

예시:

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name":"홍길동","email":"hong@test.com"}'

curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"productName":"키보드","quantity":2}'
```

브라우저에서 기능 확인:

- `http://서버IP:8080/user-ui`
- `http://서버IP:8080/order-ui`

## Oracle Cloud ARM 배포 절차

### 1) ARM 이미지 빌드/푸시

아래 `YOUR_REGISTRY`를 실제 레지스트리로 변경하세요.

```bash
docker buildx create --name multiarch --use
docker buildx build --platform linux/arm64 -t YOUR_REGISTRY/user-service:0.0.1 -f user-service/Dockerfile . --push
docker buildx build --platform linux/arm64 -t YOUR_REGISTRY/order-service:0.0.1 -f order-service/Dockerfile . --push
docker buildx build --platform linux/arm64 -t YOUR_REGISTRY/api-gateway:0.0.1 -f api-gateway/Dockerfile . --push
```

### 2) 이미지 경로 수정

아래 파일의 `YOUR_REGISTRY/...` 값을 실제 값으로 수정:

- `infra/k8s/user-service.yaml`
- `infra/k8s/order-service.yaml`
- `infra/k8s/api-gateway.yaml`

### 3) 쿠버네티스 배포

```bash
kubectl apply -k infra/k8s
```

상태 확인:

```bash
kubectl get all -n msa-demo
kubectl get ingress -n msa-demo
```

## 확장 포인트 (다음 단계)

- 서비스 디스커버리(Eureka) 및 Config Server 도입
- JWT 기반 인증/인가 + Gateway 필터
- OpenFeign 기반 서비스 간 호출
- Kafka/RabbitMQ 이벤트 기반 비동기 처리
- 관측성(Zipkin/Prometheus/Grafana) 추가

## 참고

- 현재 예제는 빠른 시작 목적의 최소 구조입니다.
- 운영 환경에서는 Secret 관리(OCI Vault), HPA, 리소스 제한, DB 백업 정책을 반드시 추가하세요.
