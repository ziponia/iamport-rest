package com.ziponia.iamport.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziponia.iamport.IamportService;
import com.ziponia.iamport.data.*;
import com.ziponia.iamport.exception.IamportException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@SuppressWarnings("all")
public class IamportClient implements IamportService {

    private RestTemplate template;
    private HttpHeaders headers;
    private ObjectMapper mapper = new ObjectMapper();

    public IamportClient(String restKey, String secretKey) {
        template = new RestTemplate();
        headers = new HttpHeaders();
        this.requestAccessToken(restKey, secretKey);
    }

    private void requestAccessToken(String restKey, String secretKey) {
        IamportResponse<AccessTokenResponse> token = this.requestToken(restKey, secretKey);
        String access_token = token.getResponse().getAccess_token();
        headers.add("Authorization", access_token);
    }

    @Override
    public IamportResponse<AccessTokenResponse> requestToken(String restKey, String secretKey) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("imp_key", restKey);
        form.add("imp_secret", secretKey);
        HttpEntity<MultiValueMap<String, String>> data = new HttpEntity<>(form, headers);

        ParameterizedTypeReference<IamportResponse<AccessTokenResponse>> responseType =
                new ParameterizedTypeReference<IamportResponse<AccessTokenResponse>>() {
                };
        ResponseEntity<IamportResponse<AccessTokenResponse>> result = template.exchange(IamportService.BASE_URL + "/users/getToken", HttpMethod.POST, data, responseType);
        if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
            return result.getBody();
        } else if (result.getBody() != null) {
            throw new IamportException("토큰 정보를 가져 올 수 없습니다. " + result.getBody().getMessage());
        } else {
            throw new IamportException("기타 오류");
        }
    }

    @Override
    public IamportResponse<Payment> requestPayment(OneTimeRequest data) {
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        StringBuilder errors = new StringBuilder();
        if (data.getMerchant_uid() == null)
            errors.append("[merchant_uid] 는 필수값입니다.");
        if (data.getAmount() == null || data.getAmount() < 1)
            errors.append("\\s[amount] (결제 금액) 은 null 이거나, 1보다 작을 수 없습니다.");
        if (data.getCard_number() == null)
            errors.append("\\s[car_num] 는 null 이 될 수 없습니다.");
        if (data.getExpiry() == null)
            errors.append("\\s[expiry] 카드 유효기간은 null 이 될 수 없습니다.");
        if (data.getBirth() == null)
            errors.append("\\s[birth] 생년월일6자리(법인카드의 경우 사업자등록번호10자리) 는 null 일 수 없습니다.");
        if (data.getCard_quota() != null && data.getCard_quota() > 1 && data.getCard_quota() < 50000) {
            errors.append("\\s[card_quota] 카드 할부는 50000이상부터 가능합니다.");
        }

        if (errors.toString().length() > 0) {
            throw new IamportException(errors.toString());
        }

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Map<String, String> convertValue = mapper.convertValue(data, new TypeReference<Map<String, Object>>() {
        });
        map.setAll(convertValue);
        HttpEntity<MultiValueMap<String, String>> form = new HttpEntity<>(map, headers);

        ParameterizedTypeReference<IamportResponse<Payment>> responseType =
                new ParameterizedTypeReference<IamportResponse<Payment>>() {
                };

        ResponseEntity<IamportResponse<Payment>> result =
                template.exchange(IamportService.BASE_URL + "/subscribe/payments/onetime", HttpMethod.POST, form, responseType);

        if (result.getBody() == null) {
            throw new IamportException("HTTP Client Error. 아임포트 서버를 호출 할 수 없음.");
        }

        if (result.getBody() != null && result.getBody().getCode() != 0) {
            throw new IamportException("결제에 실패하였습니다. " + result.getBody().getMessage());
        }

        if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
            return result.getBody();
        }

        throw new IamportException("기타 오류 [" + result.getStatusCodeValue() + "]");
    }

    @Override
    public IamportResponse<BillingKey> requestBillingKey(BillingKeyRequest data) {
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Map<String, String> convertValue = mapper.convertValue(data, new TypeReference<Map<String, Object>>() {});
        map.setAll(convertValue);
        HttpEntity<MultiValueMap<String, String>> form = new HttpEntity<>(map, headers);

        ParameterizedTypeReference<IamportResponse<BillingKey>> responseType =
                new ParameterizedTypeReference<IamportResponse<BillingKey>>() {
        };

        ResponseEntity<IamportResponse<BillingKey>> result =
                template.exchange(IamportService.BASE_URL + "/subscribe/customers/" + data.getCustomer_uid(), HttpMethod.POST, form, responseType);

        if (result.getBody() == null) {
            throw new IamportException("HTTP Client Error. 아임포트 서버를 호출 할 수 없음.");
        }

        if (result.getBody() != null && result.getBody().getCode() != 0) {
            throw new IamportException("빌링키 발급 실패. " + result.getBody().getMessage());
        }

        if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
            return result.getBody();
        }

        throw new IamportException("기타 오류 [" + result.getStatusCodeValue() + "]");
    }

    @Override
    public IamportResponse<BillingKey> findBillingKeyByCustomer(String customer_uid) {

        ParameterizedTypeReference<IamportResponse<BillingKey>> responseType =
                new ParameterizedTypeReference<IamportResponse<BillingKey>>() {
                };

        HttpEntity form = new HttpEntity<>(null, headers);
        ResponseEntity<IamportResponse<BillingKey>> result =
                template.exchange(IamportService.BASE_URL + "/subscribe/customers/" + customer_uid, HttpMethod.GET, form, responseType);

        if (result.getBody() == null) {
            throw new IamportException("HTTP Client Error. 아임포트 서버를 호출 할 수 없음.");
        }

        if (result.getBody() != null && result.getBody().getCode() != 0) {
            throw new IamportException("빌링키 조회 실패. " + result.getBody().getMessage());
        }

        if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
            return result.getBody();
        }

        throw new IamportException("기타 오류 [" + result.getStatusCodeValue() + "]");
    }

    @Override
    public IamportResponse<Payment> requestPaymentByBillingKey(PaymentBillingRequest data) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Map<String, String> convertValue = mapper.convertValue(data, new TypeReference<Map<String, Object>>() {
        });
        map.setAll(convertValue);
        HttpEntity<MultiValueMap<String, String>> form = new HttpEntity<>(map, headers);

        ParameterizedTypeReference<IamportResponse<Payment>> responseType =
                new ParameterizedTypeReference<IamportResponse<Payment>>() {
                };

        ResponseEntity<IamportResponse<Payment>> result =
                template.exchange(IamportService.BASE_URL + "/subscribe/payments/again", HttpMethod.POST, form, responseType);

        if (result.getBody() == null) {
            throw new IamportException("HTTP Client Error. 아임포트 서버를 호출 할 수 없음.");
        }

        if (result.getBody() != null && result.getBody().getCode() != 0) {
            throw new IamportException("결제에 실패하였습니다. " + result.getBody().getMessage());
        }

        if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
            return result.getBody();
        }

        throw new IamportException("기타 오류 [" + result.getStatusCodeValue() + "]");
    }

    @Override
    public IamportResponse<BillingKey> removeBillingKey(String customer_uid) {

        HttpEntity form = new HttpEntity<>(null, headers);

        ParameterizedTypeReference<IamportResponse<BillingKey>> responseType =
                new ParameterizedTypeReference<IamportResponse<BillingKey>>() {
                };
        // DELETE /subscribe/customers/{customer_uid}
        ResponseEntity<IamportResponse<BillingKey>> result =
                template.exchange(IamportService.BASE_URL + "/subscribe/customers/" + customer_uid, HttpMethod.DELETE, form, responseType);

        if (!result.hasBody()) {
            throw new IamportException("HTTP Client Error. 아임포트 서버를 호출 할 수 없음.");
        }

        if (result.getBody() != null && result.getBody().getCode() != 0) {
            throw new IamportException("빌링키 조회 실패. " + result.getBody().getMessage());
        }

        if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
            return result.getBody();
        }

        throw new IamportException("기타 오류 [" + result.getStatusCodeValue() + "]");
    }
}
