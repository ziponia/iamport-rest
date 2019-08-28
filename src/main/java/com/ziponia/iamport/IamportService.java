package com.ziponia.iamport;

import com.ziponia.iamport.data.*;

/**
 * 아임포트 비인증 요청 서비스
 * @see <a href="https://api.iamport.kr/#/">https://api.iamport.kr/#/</a>
 * @see ./README.md
 */
public interface IamportService {
    String BASE_URL = "https://api.iamport.kr";

    /**
     * 아임포트 토큰발행을 요청합니다.
     */
    IamportResponse<AccessTokenResponse> requestToken(String restKey, String secretKey);

    /**
     * 카드 결제를 요청합니다.
     *
     * @param data 요청에 필요 한 데이터를 설정합니다.
     * @see OneTimeRequest
     * @see Payment
     */
    IamportResponse<Payment> requestPayment(OneTimeRequest data);

    /**
     * 사용자의 빌링키를 발급합니다.
     *
     * @see BillingKeyRequest
     * @see BillingKey
     */
    IamportResponse<BillingKey> requestBillingKey(BillingKeyRequest data);

    /**
     * 사용자의 빌링키를 조회합니다.
     */
    IamportResponse<BillingKey> findBillingKeyByCustomer(String customer_uid);

    /**
     * 발급된 빌링키로, 결제를 요청합니다.
     *
     */
    IamportResponse<Payment> requestPaymentByBillingKey(PaymentBillingRequest data);

    /**
     * 저장 된 빌링키를 삭제합니다.
     */
    IamportResponse<BillingKey> removeBillingKey(String customer_uid);
}
