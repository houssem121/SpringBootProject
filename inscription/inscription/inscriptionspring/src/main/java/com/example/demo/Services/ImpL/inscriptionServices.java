package com.example.demo.Services.ImpL;

import com.example.demo.Execeptions.EmailLreadyExistsException;
import com.example.demo.Repository.PaymentDetailsRepository;
import com.example.demo.Repository.inscriRepository;
import com.example.demo.RequestResponse.AuthenticationRequest;
import com.example.demo.RequestResponse.AuthenticationResponse;
import com.example.demo.RequestResponse.RegisterRequest;
import com.example.demo.Services.interfaces.InscriptionService;
import com.example.demo.inscriptionEntity.Role;
import com.example.demo.inscriptionEntity.inscription;
import com.example.demo.productEN.PaymentDetails;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

@Service


public class inscriptionServices implements InscriptionService {

    @Autowired
    public inscriRepository inscriReposetory;
    @Autowired
    private inscriRepository repository;
    @Autowired
    private JwtServices jwtService;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Value("${google.clientId}")
    String googleClientId;
    @Value("${secretPsw}")
    String secretPsw;
    @Value("${appTokenss}")
    String appToken;
    @Value("${appSecret}")
    String appSecret;

@Override
    public AuthenticationResponse register(RegisterRequest request)  {
        if (repository.existsByEml(request.getEmail())) {
                  throw new  EmailLreadyExistsException();
        }
        var user = inscription.builder()
                .nn(request.getFirstname())
                .pnm(request.getLastname())
                .eml(request.getEmail())
                .adrs(request.getAddress())
                .nmp(request.getPortable())
                .pwd(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse .builder()
                .build();
    }

@Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(

                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEml(request.getEmail())
                .orElseThrow();
//        var user =findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);


        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

@Override
    public AuthenticationResponse Googlelogin(AuthenticationResponse request) throws IOException {
    final NetHttpTransport transport = new NetHttpTransport();
    final JacksonFactory jacksonFactory = new JacksonFactory();
    GoogleIdTokenVerifier.Builder verifier =
            new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                    .setAudience(Collections.singletonList(googleClientId));
    final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), request.getToken());
    final GoogleIdToken.Payload payload = googleIdToken.getPayload();
    inscription user;
    if (repository.existsByEml(payload.getEmail())) {
        user = repository.findByEml(payload.getEmail()).get();
    } else {
        user = inscription.builder()
                .nn(payload.get("family_name").toString())
                .pnm(payload.get("given_name").toString())
                .eml(payload.getEmail())
                .adrs(null)
                .nmp(0)
                .pwd(passwordEncoder.encode(secretPsw))
                .role(Role.valueOf("USER"))
                .build();

        var savedUser = repository.save(user);
    }

    var jwtToken = login(user);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();

}
@Override
    public ResponseEntity<?> createPayment() throws IOException {

    String apiUrl = "https://developers.flouci.com/api/generate_payment";
    String successLink = "http://localhost:4200/paysuccess";
    String failLink = "http://localhost:4200/payfail";
    String developerTrackingId = "9ef9eecb-89ef-47fd-9910-483d398b77cd";

    String requestBody = "{\n" +
            "    \"app_token\": \"" + appToken + "\",\n" +
            "    \"app_secret\": \"" + appSecret + "\",\n" +
            "    \"amount\": \"500\",\n" +
            "    \"accept_card\": \"true\",\n" +
            "    \"session_timeout_secs\": 1200,\n" +
            "    \"success_link\": \"" + successLink + "\",\n" +
            "    \"fail_link\": \"" + failLink + "\",\n" +
            "    \"developer_tracking_id\": \"" + developerTrackingId + "\"\n" +
            "}";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
    return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());





}



@Override
    public ResponseEntity<?> verifyPayment(String paymentId) throws IOException {



    String verifyUrl = "https://developers.flouci.com/api/verify_payment/" + paymentId;

    HttpHeaders verifyHeaders = new HttpHeaders();
    verifyHeaders.set("apppublic", appToken);
    verifyHeaders.set("appsecret", appSecret);

    HttpEntity<String> verifyRequestEntity = new HttpEntity<>(verifyHeaders);

    RestTemplate verifyRestTemplate = new RestTemplate();

    ResponseEntity<String> verifyResponseEntity = verifyRestTemplate.exchange(//exchange is used to send a request more flexible than postForEntity or getForEntity
            verifyUrl,
            HttpMethod.GET,
            verifyRequestEntity,
            String.class
    );

    if (verifyResponseEntity.getStatusCode() == HttpStatus.OK) {
        String verifyResponse = verifyResponseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(verifyResponse);

        boolean isSuccess = jsonResponse.path("success").asBoolean();
        String status = jsonResponse.path("result").path("status").asText();

        if (isSuccess && "SUCCESS".equals(status)) {
            JsonNode details = jsonResponse.path("result").path("details");

            String orderNumber = details.path("order_number").asText();
            String name = details.path("name").asText();


            String  email = details.path("email").asText();
            double price = 0.500;  // Set the appropriate price value
            String paymentMethod = details.path("type").asText();

            var PaymentDetail = PaymentDetails.builder()
                    .payId(orderNumber)
                    .serviceN("MADHMOUN")
                    .eml(email)
                    .date(new Date())
                    .price(price)
                    .paymentMethod(paymentMethod)
                    .build();


            paymentDetailsRepository.save(PaymentDetail);
            return ResponseEntity.ok(verifyResponse);

        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification failed");
        }


    }else
    {
        return ResponseEntity.status(verifyResponseEntity.getStatusCode()).body("Verification failed");
    }


}









private String login(inscription user){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEml(), secretPsw)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
       return jwtService.generateToken(user);

    }

}




