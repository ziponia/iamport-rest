package com.ziponia.iamport.data;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class BillingKeyRequest {

    /**
     * 구매자 고유 번호
     */
    @NonNull
    private String customer_uid;

    /**
     * API 방식 비인증 PG설정이 2개 이상인 경우, 결제가 진행되길 원하는 PG사를 지정하실 수 있습니다. pg 파라메터는 {PG사} 혹은 {PG사}.{PG상점아이디} 형태의 문자열입니다.
     * 지정하지 않거나 유효하지 않은 값이 전달되면 기본PG설정된 값을 이용해 결제하게 됩니다.
     */
    private PGProvider pg;

    /**
     * 카드번호(dddd-dddd-dddd-dddd)
     */
    @NonNull
    private String card_number;

    /**
     * 카드 유효기간(YYYY-MM)
     */
    @NonNull
    private String expiry;

    /**
     * 생년월일6자리(법인카드의 경우 사업자등록번호10자리)
     */
    @NonNull
    private String birth;

    /**
     * 카드비밀번호 앞 2자리
     */
    @NonNull
    private String pwd_2digit;

    /**
     * 고객(카드소지자) 관리용 성함
     */
    private String customer_name;

    /**
     * 고객(카드소지자) 전화번호
     */
    private String customer_tel;

    /**
     * 고객(카드소지자) Email
     */
    private String customer_email;

    /**
     * 고객(카드소지자) 주소
     */
    private String customer_addr;

    /**
     * 고객(카드소지자) 우편번호
     */
    private String customer_postcode;
}
