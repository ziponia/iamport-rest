package com.ziponia.iamport.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BillingKey {

    /**
     * 고객 고유번호
     */
    private String customer_uid;

    /**
     * 카드사 명칭
     */
    private String card_name;

    /**
     * 카드사 코드번호(금융결제원 표준코드번호) = ['361(BC카드)', '364(광주카드)', '365(삼성카드)', '366(신한카드)', '367(현대카드)', '368(롯데카드)', '369(수협카드)', '370(씨티카드)', '371(NH카드)', '372(전북카드)', '373(제주카드)', '374(하나SK카드)', '381(KB국민카드)', '041(우리카드)', '071(우체국)']
     */
    private String card_code;

    /**
     * 부분적으로 마스킹된 카드번호
     */
    private String card_number;

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

    /**
     * 빌키가 등록된 시각 UNIX timestamp
     */
    private int inserted;

    /**
     * 빌키가 업데이트된 시각 UNIX timestamp
     */
    private int updated;
}
