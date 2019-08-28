package com.ziponia.iamport;

import com.ziponia.iamport.data.*;
import com.ziponia.iamport.service.IamportClient;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IamportApplicationTests {

    /**
     * 테스트를 하기 위한 프로퍼티
     */
    private static final String REST_KEY = "{아임포트에서 발급 한 Rest key}";
    private static final String SECRET_KEY = "{아임포트에서 발급 한 Secret key}";
    private static final String merchant_uid = "ORDR-" + new Date().getTime(); // 주문번호
    private static final String card_number = "****-****-****-****"; // 카드번호 dddd-dddd-dddd-dddd
    private static final String birth = "******"; // 생년월일 6자리 또는 사업자번호 10자리
    private static final String expiry = "****-**"; // 카드만료일 YYYY-MM
    private static final String pwd_2digit = "**"; // 카드비밀번호 앞 2자리
    private static final String customer_uid = "any"; // 사용자 UID

    private static final String name = "테스트 결제"; // 주문이름
    private static final double amount = 5000; // 결제금액

    private IamportClient iamportClient;

    @Before
    public void iamportClientBuild() {
        iamportClient = new IamportClient(REST_KEY, SECRET_KEY);
    }

    /**
     * 빌링키를 발급합니다.
     */
    @Test
    public void AbillingKeyRequestTest() {
        BillingKeyRequest request = BillingKeyRequest.builder()
                .card_number(card_number)
                .expiry(expiry)
                .birth(birth)
                .pwd_2digit(pwd_2digit)
                .customer_uid(customer_uid)
                .build();
        IamportResponse<BillingKey> result = iamportClient.requestBillingKey(request);
        System.out.println(result.getResponse().toString());
    }

    /**
     * 사용자의 빌링키를 조회합니다.
     */
    @Test
    public void BfindBillingKeyByCustomerTest() {
        IamportResponse<BillingKey> result = iamportClient.findBillingKeyByCustomer(customer_uid);
        System.out.println(result.getResponse().toString());
    }

    /**
     * 발급된 빌링키로 결제합니다.
     */
    @Test
    public void CrequestPaymentByBillingKeyTest() {
        PaymentBillingRequest request = PaymentBillingRequest.builder()
                .customer_uid(customer_uid)
                .name(name)
                .merchant_uid(merchant_uid)
                .amount(amount)
                .build();
        IamportResponse<Payment> result = iamportClient.requestPaymentByBillingKey(request);
        System.out.println(result.getResponse().toString());
    }

    /**
     * 등록 된 빌링키를 삭제합니다.
     */
    @Test
    public void DremoveBillingKeyTest() {
        IamportResponse<BillingKey> result = iamportClient.removeBillingKey(customer_uid);
        System.out.println(result.getResponse().toString());
    }

    /**
     * 결제를 요청합니다. ( 1회용. 빌링키 없이 결제 )
     */
    @Test
    public void EpaymentRequestTest() {
        OneTimeRequest payment = OneTimeRequest.builder()
                .amount(amount)
                .merchant_uid("ORDER-" + new Date().getTime())
                .card_number(card_number) // 카드 정보를 등록합니다.
                .expiry(expiry) // 카드 유효기간을 등록합니다. (YYYY-MM)
                .birth(birth) // 생년월일을 등록합니다.
                .pg(PGProvider.INICIS)
                .name(name)
                .buyer_email("thtjwls@gmail.com")
                .pwd_2digit(pwd_2digit)
                .build();

        IamportResponse<Payment> result = iamportClient.requestPayment(payment);
        System.out.println(result.getResponse().toString());
    }
}
