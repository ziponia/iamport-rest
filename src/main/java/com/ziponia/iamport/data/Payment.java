package com.ziponia.iamport.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class Payment {

    /**
     * 아임포트 결제 고유 UID
     */
    private String imp_uid;

    /**
     * 가맹점에서 전달한 거래 고유 UID
     */
    private String merchant_uid;

    /**
     * samsung : 삼성페이 / card : 신용카드 / trans : 계좌이체 / vbank : 가상계좌
     */
    private String pay_method;

    /**
     * 결제가 발생된 경로. pc:(인증방식)PC결제, mobile:(인증방식)모바일결제, api:정기결제 또는 비인증방식결제 = ['pc', 'mobile', 'api']
     */
    private String channel;

    /**
     * PG사 명칭. inicis(이니시스) / nice(나이스정보통신)
     */
    private String pg_provider;

    /**
     * PG사 승인정보
     */
    private String pg_tid;

    /**
     * 거래가 처리된 PG사 상점아이디
     */
    private String pg_id;

    /**
     * 에스크로결제 여부
     */
    private boolean escrow;

    /**
     * 카드사 승인정보(계좌이체/가상계좌는 값 없음)
     */
    private String apply_num;

    /**
     * 은행 표준코드 - (금융결제원기준)
     */
    private String bank_code;

    /**
     * 은행 명칭 - (실시간계좌이체 결제 건의 경우)
     */
    private String bank_name;

    /**
     * 카드사 코드번호(금융결제원 표준코드번호) = ['361(BC카드)', '364(광주카드)', '365(삼성카드)', '366(신한카드)', '367(현대카드)', '368(롯데카드)', '369(수협카드)', '370(씨티카드)', '371(NH카드)', '372(전북카드)', '373(제주카드)', '374(하나SK카드)', '381(KB국민카드)', '041(우리카드)', '071(우체국)']
     */
    private String card_code;

    /**
     * 카드사 명칭 - (신용카드 결제 건의 경우)
     */
    private String card_name;

    /**
     * 할부개월 수(0이면 일시불)
     */
    private int card_quota;

    /**
     * 가상계좌 은행 표준코드 - (금융결제원기준)
     */
    private String vbank_code;

    /**
     * 입금받을 가상계좌 은행명
     */
    private String vbank_name;

    /**
     * 입금받을 가상계좌 계좌번호
     */
    private String vbank_num;

    /**
     * 입금받을 가상계좌 예금주
     */
    private String vbank_holder;

    /**
     * 입금받을 가상계좌 마감기한 UNIX timestamp
     */
    private int vbank_date;

    /**
     * 가상계좌 생성 시각 UNIX timestamp
     */
    private int vbank_issued_at;

    /**
     * 주문명칭
     */
    private String name;

    /**
     * 주문(결제)금액
     */
    private int amount;

    /**
     * 결제취소금액
     */
    private int cancel_amount;

    /**
     * 결제승인화폐단위(KRW:원, USD:미화달러, EUR:유로)
     */
    private String currency;

    /**
     * 주문자명
     */
    private String buyer_name;

    /**
     * 주문자 Email주소
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
     * 가맹점에서 전달한 custom data. JSON string으로 전달
     */
    private String custom_data;

    /**
     * 구매자가 결제를 시작한 단말기의 UserAgent 문자열
     */
    private String user_agent;

    /**
     * 결제상태. ready:미결제, paid:결제완료, cancelled:결제취소, failed:결제실패 = ['ready', 'paid', 'cancelled', 'failed']
     */
    private PaymentStatus status;

    /**
     * 결제완료시점 UNIX timestamp. 결제완료가 아닐 경우 0
     */
    private int paid_at;

    /**
     * 결제실패시점 UNIX timestamp. 결제실패가 아닐 경우 0
     */
    private int failed_at;

    /**
     * 결제취소시점 UNIX timestamp. 결제취소가 아닐 경우 0
     */
    private int cancelled_at;

    /**
     * 결제실패 사유
     */
    private String fail_reason;

    /**
     * 결제취소 사유
     */
    private String cancel_reason;

    /**
     * 신용카드 매출전표 확인 URL
     */
    private String receipt_url;

    /**
     * 취소/부분취소 내역
     */
    private ArrayList<CancelHistory> cancel_history = new ArrayList<>();

    /**
     * @deprecated cancel_history 사용 권장
     * 취소/부분취소 시 생성되는 취소 매출전표 확인 URL. 부분취소 횟수만큼 매출전표가 별도로 생성됨
     */
    @Deprecated
    private ArrayList<String> cancel_receipt_urls = new ArrayList<>();

    /**
     * 현금영수증 자동발급 여부
     */
    private boolean cash_receipt_issued;

    @Getter
    @Setter
    public static class CancelHistory {

        /**
         * PG사 승인취소번호
         */
        private String pg_tid;

        /**
         * 취소 금액
         */
        private int amount;

        /**
         * 결제취소된 시각 UNIX timestamp
         */
        private int cancelled_at;

        /**
         * 결제취소 사유
         */
        private String reason;

        /**
         * 취소에 대한 매출전표 확인 URL. PG사에 따라 제공되지 않는 경우도 있음
         */
        private String receipt_url;
    }

    public enum PaymentStatus {
        ready, // 미결제
        paid, // 결제 완료
        cancelled, // 결제취소
        failed // 결제 실패
    }
}
