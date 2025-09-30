# JPATester

Spring Boot와 JPA를 활용한 데이터베이스 연동 테스트 프로젝트입니다.

## 📋 프로젝트 개요

JPATester는 Spring Boot 2.5.10과 Spring Data JPA를 사용하여 데이터베이스 연동 기능을 테스트하는 프로젝트입니다.
앨범, 곡, 사용자, 기사 등의 엔터티를 통해 JPA의 다양한 기능을 학습하고 테스트할 수 있습니다.

## 🛠 기술 스택

-   **프레임워크**: Spring Boot 2.5.10
-   **데이터베이스**: H2 Database (인메모리)
-   **ORM**: Spring Data JPA
-   **빌드 도구**: Maven
-   **Java 버전**: Java 8
-   **기타**: Lombok, Spring Boot Test

## 📁 프로젝트 구조

```
src/
├── main/
│   ├── java/
│   │   └── com/example/jpatester/
│   │       ├── JpaTesterApplication.java    # 메인 애플리케이션 클래스
│   │       ├── controller/
│   │       │   └── JpaController.java       # REST API 컨트롤러
│   │       ├── entity/
│   │       │   ├── Album.java               # 앨범 엔터티
│   │       │   ├── Article.java             # 기사 엔터티
│   │       │   ├── Song.java                # 곡 엔터티
│   │       │   └── User.java                # 사용자 엔터티
│   │       ├── model/
│   │       │   └── ResponseInfo.java        # 응답 모델
│   │       ├── repository/
│   │       │   ├── AlbumRepository.java     # 앨범 리포지토리
│   │       │   ├── ArticleRepository.java   # 기사 리포지토리
│   │       │   ├── SongRepository.java      # 곡 리포지토리
│   │       │   └── UserRepository.java      # 사용자 리포지토리
│   │       └── service/
│   │           └── JpaService.java          # JPA 서비스
│   └── resources/
│       └── application.yml                  # 애플리케이션 설정
└── test/
    └── java/
        └── com/example/jpatester/
            ├── AlbumTest.java               # 앨범 테스트
            └── UserTest.java                # 사용자 테스트
```

## ⚙️ 설정

### 데이터베이스 설정

-   H2 인메모리 데이터베이스 사용
-   MySQL 호환 모드로 설정
-   H2 콘솔 활성화 (`/h2` 경로에서 접근 가능)

### 서버 설정

-   포트: 8080
-   JPA 자동 DDL 생성 활성화
-   SQL 로그 출력 활성화

## 🚀 실행 방법

### 1. 프로젝트 클론 및 빌드

```bash
# Maven을 사용한 빌드
./mvnw clean install
```

### 2. 애플리케이션 실행

```bash
# Maven을 사용한 실행
./mvnw spring-boot:run
```

또는

```bash
# JAR 파일 직접 실행
java -jar target/JPATester-0.0.1-SNAPSHOT.jar
```

### 3. 애플리케이션 접근

-   **메인 애플리케이션**: http://localhost:8080
-   **H2 데이터베이스 콘솔**: http://localhost:8080/h2
-   **API 엔드포인트**: http://localhost:8080/jpa/\*

## 🔍 API 엔드포인트

REST API는 `/jpa` 경로 하위에서 제공됩니다. 자세한 API 사용법은 `JpaController.java` 파일을 참조하세요.

## 🧪 테스트

프로젝트에는 다음과 같은 테스트가 포함되어 있습니다:

-   `AlbumTest.java`: 앨범 관련 기능 테스트
-   `UserTest.java`: 사용자 관련 기능 테스트

```bash
# 테스트 실행
./mvnw test
```

## 📚 주요 기능

1. **엔터티 관리**: Album, Song, User, Article 엔터티를 통한 JPA 매핑 학습
2. **리포지토리 패턴**: Spring Data JPA를 활용한 데이터 액세스
3. **REST API**: RESTful 웹 서비스 제공
4. **데이터베이스 콘솔**: H2 콘솔을 통한 데이터베이스 직접 조회
5. **테스트 환경**: JUnit을 활용한 단위 테스트

## 🔧 개발 환경 설정

### 필요 사항

-   Java 8 이상
-   Maven 3.6 이상

### IDE 설정

-   Lombok 플러그인 설치 필요
-   Annotation Processing 활성화

## 📄 라이센스

이 프로젝트는 학습 목적으로 만들어진 예제 프로젝트입니다.

## 🤝 기여

이슈나 개선사항이 있다면 언제든지 Pull Request를 보내주세요!
