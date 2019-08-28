package com.ziponia.iamport.data;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class PaymentBillingRequest {

    /**
     * string 타입의 고객 고유번호
     */
    @NonNull
    private String customer_uid;

    /**
     * 가맹점 거래 고유번호
     */
    @NonNull
    private String merchant_uid;

    /**
     * 결제금액
     *
     * 1보다 커야함. (카드의 경우 100<amount)
     */
    @NonNull
    private double amount;

    /**
     * amount 중 면세공급가액.
     * 기본값은 0이므로(amount 모두 과세대상이므로) amount의 1/11 이 부가세로 처리됩니다
     */
    private Double tax_free;

    /**
     * 주문명
     */
    @NonNull
    private String name;

    /**
     * 주문자명
     */
    private String buyer_name;

    /**
     * 주문자 E-mail 주소
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
}
