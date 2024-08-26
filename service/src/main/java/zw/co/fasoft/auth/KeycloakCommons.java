package zw.co.fasoft.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import zw.co.fasoft.exceptions.FailedToProcessRequestException;
import zw.co.fasoft.exceptions.IncorrectUsernameOrPasswordException;
import zw.co.fasoft.useraccount.UserAccount;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakCommons {

    private final RestTemplate restTemplate;
    LoginResponse loginResponse;
    String userId;
    String userGroupID;
    String roleId;
    String password;
    @Value("${keycloak.token-uri}")
    private String tokenUrl;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.grant-type}")
    private String grantType;
    @Value("${keycloak.admin-client-id}")
    private String adminClientId;
    @Value("${keycloak.admin-username}")
    private String adminUsername;
    @Value("${keycloak.admin-password}")
    private String adminPassword;
    @Value("${keycloak.masterUrl}")
    private String masterUrl;
    @Value("${keycloak.client-user-uri}")
    private String clientUserUrl;
    @Value("${keycloak.client-uuid}")
    private String clientUUID;
    @Value("${notifications.service-url}")
    private String notificationsUrl;
    @Value("${api-urls.frontend-login-link}")
    private String loginLink;
    @Value("${keycloak.realm-base-url}")
    private String realmBaseUrl;

    public String findUserIdByUsername(String username) {
        getAdminToken();

        String url = realmBaseUrl + "/users?username=" + username;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(loginResponse.getAccess_token());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); // Set Accept header to JSON
        assert loginResponse != null;

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            UserDetails[] userDetails = restTemplate.exchange(url, HttpMethod.GET, requestEntity, UserDetails[].class).getBody();

            assert userDetails != null;
            return userDetails[0].getId();

        } catch (HttpClientErrorException httpClientErrorException) {
            throw new IncorrectUsernameOrPasswordException("Error fetching user ID " + httpClientErrorException.getStatusCode() + " - " + httpClientErrorException.getResponseBodyAsString());
        } catch (Exception e) {
            throw new FailedToProcessRequestException("unable to process request");
        }
    }

    public LoginResponse getAdminToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", grantType);
        map.add("client_id", "admin-cli");
        map.add("username", "admin");
        map.add("password", "admin");

        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
//        log.info("request : {}", httpEntity);
        try {
            return restTemplate.postForObject(masterUrl, httpEntity, LoginResponse.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            throw new IncorrectUsernameOrPasswordException("password or username is incorrect");
        } catch (Exception e) {
            throw new FailedToProcessRequestException("unable to process request");
        }
    }

    public void activateUser(String username, Boolean status) {
        getAdminToken();
        assert loginResponse != null;
        userId = findUserIdByUsername(username);

        String url = realmBaseUrl + "/users/" + userId;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(loginResponse.getAccess_token());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("enabled", status);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class);
            log.info("response : {}", response);
            log.info("Activation or deactivation successful");
        } catch (HttpClientErrorException httpClientErrorException) {
            throw new IncorrectUsernameOrPasswordException("Error activating or deactivating user");
        } catch (Exception e) {
            throw new FailedToProcessRequestException("unable to process request");
        }
    }
}
