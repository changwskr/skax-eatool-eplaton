# MBC Java 404 오류 해결 가이드

## 문제 상황
- `http://localhost:8000/mbc/web/user-management` → 404 오류
- `http://localhost:8000/mbc/web/report-management` → 404 오류

## 원인 분석
`HomeController`가 `/web/admin` 경로로 매핑되어 있는데, HTML 템플릿에서는 `/mbc/web/user-management`와 `/mbc/web/report-management`로 링크가 되어 있었습니다.

### 실제 매핑 구조
```
HomeController: @RequestMapping("/web/admin")
├── /web/admin/user-management → showUserManagementPage()
├── /web/admin/report-management → showReportManagementPage()
└── /web/admin/account-management → showAccountManagementPage()
```

### 잘못된 링크
```
HTML 템플릿:
├── /mbc/web/user-management (404)
└── /mbc/web/report-management (404)
```

## 해결 방법

### 1. HTML 템플릿 링크 수정

#### 수정된 파일들:
- `mbc-java/src/main/resources/templates/home.html`
- `mbc-java/src/main/resources/templates/web/home.html`
- `mbc-java/src/main/resources/templates/report/user-statistics.html`

#### 변경 내용:
```html
<!-- 변경 전 -->
<a href="/mbc/web/user-management" class="btn btn-primary">
<a href="/mbc/web/report-management" class="btn btn-success">

<!-- 변경 후 -->
<a href="/mbc/web/admin/user-management" class="btn btn-primary">
<a href="/mbc/web/admin/report-management" class="btn btn-success">
```

### 2. 리다이렉트 컨트롤러 추가

#### 새로 생성된 파일:
- `mbc-java/src/main/java/com/skax/eatool/mbc/web/home/controller/RedirectController.java`

#### 기능:
- `/web/user-management` → `/web/admin/user-management` 리다이렉트
- `/web/report-management` → `/web/admin/report-management` 리다이렉트

## 수정된 URL 구조

### 올바른 접근 경로
```
✅ http://localhost:8000/mbc/web/admin/user-management
✅ http://localhost:8000/mbc/web/admin/report-management
✅ http://localhost:8000/mbc/web/admin/account-management
✅ http://localhost:8000/mbc/web/admin/monitoring/dashboard
```

### 리다이렉트 지원 (하위 호환성)
```
🔄 http://localhost:8000/mbc/web/user-management → 리다이렉트
🔄 http://localhost:8000/mbc/web/report-management → 리다이렉트
```

## 컨트롤러 매핑 확인

### HomeController (`/web/admin`)
```java
@Controller
@RequestMapping("/web/admin")
public class HomeController {
    
    @GetMapping("/user-management")
    public String showUserManagementPage(Model model) { ... }
    
    @GetMapping("/report-management")
    public String showReportManagementPage(Model model) { ... }
    
    @GetMapping("/account-management")
    public String showAccountManagementPage(Model model) { ... }
    
    @GetMapping("/monitoring/dashboard")
    public String showMonitoringDashboard(Model model) { ... }
}
```

### RedirectController (`/web`)
```java
@Controller
@RequestMapping("/web")
public class RedirectController {
    
    @GetMapping("/user-management")
    public String redirectToUserManagement() {
        return "redirect:/web/admin/user-management";
    }
    
    @GetMapping("/report-management")
    public String redirectToReportManagement() {
        return "redirect:/web/admin/report-management";
    }
}
```

## 테스트 방법

### 1. 직접 접근 테스트
```bash
# 올바른 경로로 직접 접근
curl -I http://localhost:8000/mbc/web/admin/user-management
curl -I http://localhost:8000/mbc/web/admin/report-management
```

### 2. 리다이렉트 테스트
```bash
# 기존 경로로 접근하여 리다이렉트 확인
curl -I http://localhost:8000/mbc/web/user-management
curl -I http://localhost:8000/mbc/web/report-management
```

### 3. 브라우저 테스트
1. `http://localhost:8000/mbc/home` 접속
2. 빠른 링크 버튼 클릭
3. 정상적으로 페이지 이동 확인

## 추가 확인 사항

### 1. 템플릿 파일 존재 확인
```
✅ mbc-java/src/main/resources/templates/user/user-management.html
✅ mbc-java/src/main/resources/templates/report/report-management.html
```

### 2. 컨트롤러 메서드 확인
```java
// HomeController에서 다음 메서드들이 존재하는지 확인
showUserManagementPage(Model model)
showReportManagementPage(Model model)
```

### 3. 로그 확인
```bash
# 애플리케이션 로그에서 리다이렉트 메시지 확인
docker-compose logs -f mbc-service | grep "리다이렉트"
```

## 예상 결과

### 수정 후 정상 동작
- ✅ 빠른 링크 버튼 클릭 시 정상 페이지 이동
- ✅ URL이 올바르게 표시됨
- ✅ 404 오류 해결

### 하위 호환성 유지
- ✅ 기존 URL로 접근 시 자동 리다이렉트
- ✅ 기존 북마크나 링크도 정상 동작

## 문제가 지속될 경우

### 1. 애플리케이션 재시작
```bash
docker-compose restart mbc-service
```

### 2. 로그 확인
```bash
docker-compose logs -f mbc-service
```

### 3. 컨트롤러 매핑 확인
```bash
# 애플리케이션 시작 시 매핑 정보 확인
docker-compose logs mbc-service | grep "RequestMapping"
```

### 4. 브라우저 캐시 클리어
- 브라우저에서 Ctrl+F5 (강제 새로고침)
- 개발자 도구에서 캐시 비활성화 