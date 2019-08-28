package com.ziponia.iamport.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@Builder
public class OneTimeRequest implements Serializable {

    private static final long serialVersionUID = 4826297970451952415L;

    /**
     * 가맹점 거래 고유번호
     */
    @NonNull
    private String merchant_uid;

    /**
     * 결제 금액
     * 0 보다 커야 합니다.
     */
    private Double amount;

    /**
     * amount 중 면세공급가액.
     * 기본값은 0이므로(amount 모두 과세대상이므로) amount의 1/11 이 부가세로 처리됩니다.
     */
    private Integer tax_free;

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
    private String pwd_2digit;

    /**
     * string 타입의 고객 고유번호.
     *
     * (결제에 사용된 카드정보를 빌링키 형태로 저장해두고 재결제에 사용하시려면 customer_uid를 지정해주세요. /subscribe/payments/again, /subscribe/payments/schedule로 재결제를 진행하실 수 있습니다.)
     */
    private String customer_uid;

    /**
     * API 방식 비인증 PG설정이 2개 이상인 경우, 결제가 진행되길 원하는 PG사를 지정하실 수 있습니다. pg 파라메터는 {PG사} 혹은 {PG사}.{PG상점아이디} 형태의 문자열입니다.
     * 지정하지 않거나 유효하지 않은 값이 전달되면 기본PG설정된 값을 이용해 결제하게 됩니다.
     */
    private PGProvider pg;

    /**
     * 주문명
     */
    private String name;

    /**
     * 주문자명
     */
    private String buyer_name;

    /**
     * 주문자 E-mail주소
     */
    private String buyer_email;

    /**
     * 주문자 전화번호
     */
    private String buyer_tel;

    /**
     * 주문자 주소
     */
    private String buyer_addr;

    /**
     * 주문자 우편번호
     */
    private String buyer_postcode;

    /**
     * 카드할부개월수. 2 이상의 integer 할부개월수 적용(결제금액 50,000원 이상 한정)
     */
    private Integer card_quota;

    /**
     * 거래정보와 함께 저장할 추가 정보
     */
    private String custom_data;

    /**
     * 결제성공 시 통지될 Notification URL(Webhook URL)
     */
    private String notice_url;

    public String getPg() {
        return pg == null ? null : pg.getName();
    }
}
