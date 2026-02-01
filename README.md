# 🌱 FossilFuel: Sustainable Self-Hosting Project

> **"Cloud Native에서 On-Premise로, 비용 최적화와 인프라 제어권 확보를 위한 여정"**

<div align="center">
<img width="1024" height="1024" alt="Image" src="https://github.com/user-attachments/assets/8ca75076-8cfb-4b94-a2ae-844a687a0f8c" />
  <p><i>실제 운영 중인 FossilFuel 미니 서버 시스템 아키텍처</i></p>
</div>

AWS 프리티어 종료와 과금 정책 변화라는 현실적인 문제를 기술적인 도전으로 승화시켜, **운영 비용 0원**의 지속 가능한 인프라를 구축한 프로젝트입니다. 단순한 개발을 넘어 시스템 운영과 인프라 최적화에 집중했습니다.

---

### 🚀 1. Project Overview & Evolution

* **서비스 성격:** 컴퓨터공학과 동아리 'FossilFuel' 아카이빙 및 구성원 소통 플랫폼
* **핵심 가치:** 지속 가능한 운영(Sustainability), 비용 효율성(Cost Efficiency), 인프라 제어권(Full Control)
* **운영 단계의 진화:**
* **Phase 1 (Cloud):** AWS(EC2, RDS, ALB) 기반 고가용성 아키텍처 구축 및 관리 경험 확보.
* **Phase 2 (Self-Hosted):** 로컬 미니 서버 마이그레이션을 통한 **고정 운영비 100% 절감** 및 서버 커스터마이징.



---

### 🛠 2. Tech Stack

* **Backend:** `Java 17`, `Spring Boot`
* **Infrastructure:** `Ubuntu Server (Mini PC)`, `Docker`, `Docker Compose`
* **Database:** `MySQL->PostgreSQL (Containerized)`
* **Network:** `Nginx (Reverse Proxy)`, `DDNS`, `Port Forwarding`
* **DevOps:** `GitHub Actions (CI/CD)`, `Shell Scripting`, `Crontab`

---

### 🏗 3. Architecture Migration: Cloud to Local

분산되어 있던 클라우드 리소스를 단일 미니 서버 내 가상화 환경(Docker)으로 통합하여 관리 효율을 극대화했습니다.

| 구분 | AWS Cloud Architecture (Legacy) | Mini Server Architecture (Current) |
| --- | --- | --- |
| **Compute** | AWS EC2 (t2.micro) | **Ubuntu Server (Physical Mini PC)** |
| **Database** | AWS RDS (Managed MySQL) | **Dockerized PostgreSQL Container** |
| **Networking** | Route53 + Application Load Balancer | **DDNS + Port Forwarding + Nginx** |
| **Security** | AWS Security Group | **UFW / Firewalld + Router Firewall** |
| **Cost** | 매월 고정 비용 발생 (IP, RDS 등) | **유지비 0원 (Hardware Only)** |

---

### 💡 4. Key Implementation & Troubleshooting

#### 🔄 AWS to On-Premise 마이그레이션

* **Background:** 클라우드 서비스의 유료화 및 RDS 비용 이슈 해결을 위한 독립 인프라 필요성.
* **Migration:** `dump`를 활용한 데이터 정합성 유지 및 Docker 기반 환경 이관 성공.
* **Outcome:** 클라우드 종속성을 제거하고 하드웨어 리소스를 100% 활용하는 독자적 인프라 확보.

#### 🐳 Docker 기반 환경 일관성 및 최적화

* **Multi-arch Build:** 개발 환경(ARM)과 배포 환경(x86)의 차이를 `docker buildx`로 해결하여 배포 안정성 확보.
* **Resource Tuning:** 제한된 리소스를 고려한 `JVM Heap Memory(Xmx)` 최적화 및 불필요한 레이어 제거.

#### 🌐 홈 네트워크 한계 극복 및 보안

* **Availability:** 유동 IP 환경에서 서비스 연속성을 위해 `DDNS` 자동 갱신 스크립트 구축 및 `Crontab` 관리.
* **Security:** `Nginx Reverse Proxy`를 적용하여 내부 포트를 은닉하고, ISP 업체 차단 포트(80/443) 대체 및 최적화 처리.

---

### 🛠 5. Engineering Notes (Troubleshooting in AWS)

> **"환경의 제약을 기술적 장치로 극복하다"**

1. **메모리 스왑(Swap) 전략:** 물리 메모리 부족으로 인한 OOM(Out Of Memory) 방지를 위해 SSD 기반 Swap 메모리 4GB 할당.
2. **배포 자동화 파이프라인:** GitHub Actions와 셀 스크립트를 연동하여 **코드 푸시 → 빌드 → 도커 롤링 리스타트** 과정을 자동화하여 다운타임 최소화.

---

### 🎓 What I Learned

클라우드 인프라의 추상화된 편리함 뒤에 숨은 비용 구조와 네트워크 메커니즘을 깊이 있게 이해하게 되었습니다. 직접 온프레미스 서버를 구축하며 **L4(네트워크)부터 L7(애플리케이션)까지 아우르는 풀스택 인프라 역량**을 쌓았으며, 효율적인 자원 관리의 중요성을 깨달았습니다.

---

### 🔗 Reference

* **Official Website:** 
* **GitHub Repository:** 
  
