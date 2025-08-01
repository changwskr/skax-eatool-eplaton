# 전문 JSON 샘플 문서

이 디렉토리에는 통합전문 구조에 맞는 JSON 전문 샘플들이 포함되어 있습니다.

## 📁 파일 목록

### 1. 요청전문 (request-message-sample.json)
- **용도**: 클라이언트에서 서버로 전송하는 요청 전문
- **특징**: 
  - `rqstRspDvcd`: "S" (요청)
  - `gramRspDtti`: null (응답 시에만 설정)
  - `rspRsltDvcd`: null (응답 시에만 설정)
  - `errorInfo`: null (오류 없음)

### 2. 응답전문 (response-message-sample.json)
- **용도**: 서버에서 클라이언트로 전송하는 성공 응답 전문
- **특징**:
  - `rqstRspDvcd`: "R" (응답)
  - `gramRspDtti`: 응답 시간 설정
  - `rspRsltDvcd`: "1" (정상)
  - `errorInfo`: null (오류 없음)
  - `businessData`: 처리 결과 정보 포함

### 3. 에러전문 (error-message-sample.json)
- **용도**: 서버에서 클라이언트로 전송하는 오류 응답 전문
- **특징**:
  - `rqstRspDvcd`: "R" (응답)
  - `gramRspDtti`: 응답 시간 설정
  - `rspRsltDvcd`: "2" (오류)
  - `errorInfo`: 상세 오류 정보 포함
  - `businessData`: 오류 메시지 포함

## 🔧 전문 구조 설명

### 표준 헤더 영역 (standardHeader)
```json
{
  "stdGramLen": "00000000",        // 표준전문길이
  "gramEncDvcd": "2",              // 전문암호화구분코드 (SHA-256)
  "gblId": "202401011234567890123456789000", // 글로벌ID
  "gramRqstDtti": "20240101123456789",       // 전문요청일시
  "gramRspDtti": "20240101123457890",        // 전문응답일시
  "rqstRspDvcd": "S",              // 요청응답구분코드 (S=요청, R=응답)
  "ttlUseDvcd": "2",               // TTL사용구분코드
  "fstSti": "123456",              // 최초시작시각
  "keepHrSecCnt": "060"            // 유지시간초수
}
```

### 시스템/채널 전송 정보 (transmissionInfo)
```json
{
  "chnlTpcd": "01",                // 채널유형코드 (인터넷뱅킹)
  "cliIp": "192.168.0.101",        // Client IP
  "cliMac": "00A0C9FFEE11",        // Client MAC
  "confInfoDvcd": "P",             // 환경정보구분코드 (P=운영)
  "fstTrsSysCd": "MCI",            // 최초전송시스템코드
  "trsSysCd": "CBS",               // 전송시스템코드
  "trsNodeNo": "01",               // 전송노드번호
  "mciNodeNo": "01",               // MCI노드번호
  "fepNodeNo": "02",               // FEP노드번호
  "eaiNodeNo": "03",               // EAI노드번호
  "rcvSvCd": "DEP01001",           // 수신서비스코드
  "rsltRcvSvCd": "DEP01002",       // 결과수신서비스코드
  "frgMciIfId": "MCI001",          // FEP인터페이스ID
  "eaiIfId": "EAI001"              // EAI인터페이스ID
}
```

### 거래/트랜잭션 정보 (transactionInfo)
```json
{
  "xaTrnsDvcd": "2",               // XA거래구분코드 (XA)
  "trnsSyncDvcd": "S",             // 거래동기화구분코드 (Sync)
  "gramLangDvcd": "KR",            // 전문언어구분코드 (한글)
  "chnlGramCompartLen": "0056"     // 채널전문공통부 길이
}
```

### 승인자/책임자 정보 (approvalInfo)
```json
{
  "apvlTypeDvcd": "2",             // 승인유형구분코드 (책임자)
  "apperRspRscd": "2",             // 승인자응답결과코드 (확인)
  "otm1ApvlEnob": "1234567",       // N1차승인자행번
  "otm1ApvlTuno": "002",           // N1차승인단말번호
  "otm2ApvlEnob": null,            // N2차승인자행번
  "otm2ApvlTuno": null             // N2차승인단말번호
}
```

### 전문 제어 정보 (controlInfo)
```json
{
  "screnId": "A001000001",         // 화면번호
  "inputGramId": "DEP01001",       // 입력전문ID
  "inputGramLwrId": "01",          // 입력전문하위ID
  "msgCotiCd": "1",                // 메시지연속코드 (없음)
  "nextPgeReuCd": "1",             // 다음페이지요구코드 (없음)
  "nonRqstMsgOutptCd": "1",        // 비요청메시지출력코드 (없음)
  "canCorrDvcd": "1",              // 취소구분코드 (정상)
  "autoTrnsReuCd": "1",            // 자동거래요구코드 (없음)
  "inputGramEditDvcd": "1",        // 입력전문편집구분코드 (없음)
  "smltTrnsDvcd": "1",             // 시뮬레이션거래구분코드 (일반거래)
  "vdTrnsDvcd": "1",               // 리바운드거래구분코드 (일반)
  "gramCotiSeq": "00",             // 전문연속일련번호
  "outptGramTpcd": "1",            // 출력전문유형코드 (일반거래전문)
  "rspRsltDvcd": "1"               // 응답결과구분코드 (1=정상, 2=오류)
}
```

### 단말 정보 (terminalInfo)
```json
{
  "blgCmgrCd": "01",               // 소속그룹사코드
  "ttBrno": "123",                 // 취급점번
  "mtbcno": "100",                 // 모점점번
  "enob": "654321",                // 행번
  "shpDvcd": "1",                  // 점포구분코드 (지점)
  "trnsCmgrCd": "01",              // 거래그룹사코드
  "tuBrno": "123",                 // 단말점번
  "trnsTuno": "002",               // 거래단말번호
  "veBrno": "000"                  // 대행점번
}
```

### 대외거래 정보 (externalInfo)
```json
{
  "frgOrgtcd": "BANK",             // 대외기관코드
  "frgBizCd": "TR01",              // 대외업무코드
  "ttOpenDvcd": "1"                // 취급개설구분코드 (취급)
}
```

### 고객정보 보호 (securityInfo)
```json
{
  "rrnoInfoPrtcDvcd": "2",         // 주민등록번호정보보호구분코드 (Masking)
  "custNmInfoPrtcDvcd": "2",       // 고객명정보보호구분코드 (Masking)
  "acnoInfoPrtcDvcd": "2",         // 계좌번호정보보호구분코드 (Masking)
  "cdnoInfoPrtcDvcd": "2",         // 카드번호정보보호구분코드 (Masking)
  "tlnoInfoPrtcDvcd": "2",         // 전화번호정보보호구분코드 (Masking)
  "addrInfoPrtcDvcd": "2"          // 주소정보보호구분코드 (Masking)
}
```

### 기타 정보 (miscInfo)
```json
{
  "aitmRnwlModeDvcd": "1",         // 계정갱신모드구분코드 (당일마감전)
  "tadyTrnsNdvCanReuCd": "1",      // 당일거래개별취소요구코드 (없음)
  "bnkbPrtrCnntDvcd": "2",         // 통장프린터접속구분코드 (접속)
  "iccrdMdmDvcd": "2",             // IC카드매체구분코드 (CONTACT)
  "msUseDvcd": "1",                // MS 사용구분코드 (MS 미사용)
  "trlgKeyVal": "KEY20240101123456789", // 거래로그키값
  "dlngStcd": "L",                 // 처리상태코드 (Lock)
  "ttRspInputRsrnDvcd": "1",       // 취급응답입력복원구분코드 (전체복원)
  "ttRspInputRsrnEditId": "EDT0001", // 취급응답입력복원편집ID
  "outptWatStcd": "1"              // 출력대기상태코드 (출력대기)
}
```

### 업무 데이터 영역 (businessData)
```json
{
  "accountNumber": "110234567890",  // 계좌번호
  "accountHolder": "홍길동",         // 예금주명
  "transactionAmount": 500000,      // 거래금액
  "currencyCode": "KRW",           // 통화코드
  "transactionType": "DEP",         // 거래유형 (입금)
  "description": "급여이체",         // 거래메모
  "referenceNo": "PAY20240101-1234567890", // 참조번호
  "requestedBy": {                  // 요청자정보
    "userId": "user001",
    "department": "인사팀"
  }
}
```

### 전문 종료부 (footer)
```json
{
  "stdGramEndVal": "@@"            // 전문종료문자
}
```

### 오류 정보 (errorInfo)
```json
{
  "errorCode": "E001",              // 오류 코드
  "errorMessage": "계좌번호가 존재하지 않습니다.", // 오류 메시지
  "errorDetail": "계좌번호: 110234567890", // 오류 상세 정보
  "errorTime": "20240101123457890", // 오류 발생 시간
  "errorSystem": "CBS",             // 오류 발생 시스템
  "errorModule": "AccountService",   // 오류 발생 모듈
  "errorFunction": "validateAccount", // 오류 발생 함수
  "errorLevel": "2",                // 오류 레벨 (2=오류)
  "errorType": "B",                 // 오류 타입 (B=업무)
  "errorSolution": "올바른 계좌번호를 입력해주세요.", // 오류 해결 방법
  "errorTrace": "AccountNotFoundException: Account not found with number 110234567890" // 오류 추적 정보
}
```

## 🚀 사용 방법

### 1. 요청전문 생성
```java
// JSON 파일을 CompleteMessageDTO로 변환
ObjectMapper mapper = new ObjectMapper();
CompleteMessageDTO requestMessage = mapper.readValue(
    new File("request-message-sample.json"), 
    CompleteMessageDTO.class
);
```

### 2. 응답전문 생성
```java
// 요청전문을 기반으로 응답전문 생성
CompleteMessageDTO responseMessage = CompleteMessageDTO.fromMessageDTO(
    requestMessage.toMessageDTO()
);

// 응답 정보 설정
responseMessage.getStandardHeader().setRqstRspDvcd("R");
responseMessage.getStandardHeader().setGramRspDtti("20240101123457890");
responseMessage.getControlInfo().setRspRsltDvcd("1");

// 업무 데이터 설정
Map<String, Object> responseData = new HashMap<>();
responseData.put("resultMessage", "급여이체가 성공적으로 처리되었습니다.");
responseMessage.setBusinessData(responseData);
```

### 3. 에러전문 생성
```java
// 오류 발생 시 에러전문 생성
CompleteMessageDTO errorMessage = CompleteMessageDTO.fromMessageDTO(
    requestMessage.toMessageDTO()
);

// 오류 정보 설정
errorMessage.getControlInfo().setRspRsltDvcd("2");
errorMessage.setErrorInfo("E001", "계좌번호가 존재하지 않습니다.", "계좌번호: 110234567890");

// 오류 업무 데이터 설정
Map<String, Object> errorData = new HashMap<>();
errorData.put("resultMessage", "계좌번호가 존재하지 않습니다.");
errorData.put("errorCode", "E001");
errorMessage.setBusinessData(errorData);
```

## 📝 주의사항

1. **전문 길이**: `stdGramLen`은 실제 전문 길이로 계산해야 합니다.
2. **글로벌 ID**: `gblId`는 고유한 값이어야 합니다.
3. **시간 정보**: 모든 시간 정보는 정확한 형식을 따라야 합니다.
4. **오류 처리**: 오류 발생 시 `errorInfo`와 `businessData` 모두에 오류 정보를 포함해야 합니다.
5. **보안**: 실제 운영 환경에서는 민감한 정보가 마스킹되어야 합니다. 