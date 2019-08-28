# 아임포트 PG 폼페이 방식 결제 샘플

비인증결제 API호출 시 기본적으로 사용할 PG설정값입니다. API 호출 시 pg 파라메터를 지정함으로써 추가 PG설정값을 활용해 결제진행할 수도 있습니다.(단, 이미 등록된 빌링키 결제는 최초 빌링키 발급 당시 설정값을 사용합니다.)

KG이니시스 폼페이 방식은, 이니시스와 별도 협의된 가맹점만 사용하실 수 있는 모듈입니다.(일반적으로는 KG이니시스 웹표준결제창을 통한 빌링키 발급방식을 사용하셔야 합니다)

_사전 필수 발급조건_

- PG 상점 아이디
- PG 상점 Secret

아임포트 관리자 -> PG설정(정기결제 및 키인결제) -> PG 사 선택 란에 항목 등록 

테스트 설정시 (카드사 심사 전 개발용 계정 설정) 등록

Iamport 로 결제 시 아래와 같이 사전 설정해야 합니다.

```java
public class MyApplication {
    
    private static final String REST_KEY = "IAMPORT_REST_KEY";
    private static final String SECRET_KEY = "IAMPORT_SECRET_KEY";

    public static void main(String[] args) {
        // 아임포트의 기능을 사용하기위해, IamportClient 를 초기화 해야 합니다.
        IamportClient client = new IamportClient(REST_KEY, SECRET_KEY);

        // 결제를 요청하기위해, OenTimeRequest 객체를 build 합니다.
        OneTimeRequest payment = OneTimeRequest.builder()
                .amount(5000)
                .merchant_uid("ORDR-" + new Date().getTime())
                .card_number("***-***-***-***") // 카드 정보를 등록합니다.
                .expiry("****-**") // 카드 유효기간을 등록합니다. (YYYY-MM)
                .birth("880801") // 생년월일을 등록합니다.
                .pg(PGProvider.INICIS)
                .name("결제 테스트")
                .buyer_email("thtjwls@gmail.com")
                .pwd_2digit("09") // 카드 비밀번호 앞 두자리
                .build();
    
        // 결제를 진행하기 위해 아래와 같이 client 객체의 requestPayment 메서드로 요청합니다.
        IamportResponse<OneTimeResponse> result = client.requestPayment(payment);        
    }
}
```

각 결제 호출에 대해서, 오류가 발생 하였을 경우, IamportException 객체를 호출합니다.

각 항목에 대한 프로퍼티의 설명의 경우, iamport.data 안에 오브젝트를 확인 하세요.

빌링키 발급 항목은

[`{project}/src/test/java/com.ziponia.iamport.IamportApplicationTests.java`](https://github.com/ziponia/iamport-rest/blob/master/src/test/java/com/ziponia/iamport/IamportApplicationTests.java) 파일을 참고하세요.
