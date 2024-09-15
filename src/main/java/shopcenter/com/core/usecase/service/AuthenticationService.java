package shopcenter.com.core.usecase.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import shopcenter.com.adapter.dto.request.auth.RefreshTokenRequest;
import shopcenter.com.core.entity.InvalidatedToken;
import shopcenter.com.core.entity.User;
import shopcenter.com.framework.exception.AppException;
import shopcenter.com.framework.exception.ErrorCode;
import shopcenter.com.repository.InvalidatedTokenRepository;
import shopcenter.com.repository.UserRepository;
import shopcenter.com.adapter.dto.request.auth.AuthenticationRequest;
import shopcenter.com.adapter.dto.request.auth.IntrospectRequest;
import shopcenter.com.adapter.dto.request.auth.LogoutRequest;
import shopcenter.com.adapter.dto.response.auth.AuthenticationResponse;
import shopcenter.com.adapter.dto.response.auth.IntrospectResponse;
import shopcenter.com.shared.utils.ProcessPassword;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    //chữ kí
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected Long validDuration;

    @NonFinal
    @Value("${jwt.refresh-duration}")
    protected Long refreshDuration;

    //đăng nhập -> trả về token để truy cập
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NAME_NOT_EXISTED));

        boolean authenticated = ProcessPassword.isPasswordMatch(request.getUserPassword(), user.getUserPassword());

        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        //đăng nhập thành công -> tạo token
        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    //refresh token -> trả về token mới để truy cập
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        //check hiệu lực của token
        var signedJwt = verifytoken(request.getToken(), true);

        var jit = signedJwt.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJwt.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .tokenId(jit)
                .expirytime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);

        //sau khi thực hiện tương tự như đăng xuất thì sẽ tạo ra 1 token mới
        var userName = signedJwt.getJWTClaimsSet().getSubject();
        var user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var tokenNew = generateToken(user);
        return AuthenticationResponse.builder()
                .token(tokenNew)
                .authenticated(true)
                .build();
    }

    //check token: verify token (kiểm tra tính hợp lệ)
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        boolean isValid = true;

        try {
            verifytoken(token, false);
        }
        catch (AppException e){
            isValid = false; //do hết hiệu lực/sai chữ ký hoặc đã đăng xuất
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    //đăng xuất
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try{
            var signedJWT = verifytoken(request.getToken(), true);
            String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .tokenId(jwtId)
                    .expirytime(expiryTime)
                    .build();

            //lưu vào DB
            invalidatedTokenRepository.save(invalidatedToken);
        }
        catch (AppException e){
            log.info("Token already expired");
        }
    }

    //tạo token trả về
    private String generateToken(User user){
        //tạo header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        //tạo payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
//                .subject(user.getUserName())
                .subject(user.getUserName())
                .issuer("shopcenter.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(validDuration, ChronoUnit.SECONDS).toEpochMilli() //thời hạn 1 giờ
                ))
                .jwtID(UUID.randomUUID().toString()) //tạo tokenId
                .claim("scope", buildScope(user))
                .build();


        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        //kí token :đùng thuật toán kí tạo và kí giải mã
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Connot create token");
            throw new RuntimeException(e);
        }
    }

    //hàm tách role -> phân cách bởi " "
    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getRoleName());
                if(!CollectionUtils.isEmpty(role.getPermissions())){
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getPermissionName());
                    });
                }
            });
        }
        return stringJoiner.toString();
    }

    //hàm trả về thông tin của token lấy từ request
    private SignedJWT verifytoken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        //check thời hạn token
        Date expityTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                .toInstant().plus(refreshDuration, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        //xác minh chữ ký
        var verified = signedJWT.verify(verifier);

        //check token từ reqquest : thời hạn + chữ kí
        if(!(verified && expityTime.after(new Date()))){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        //kiểm tra token đã bị đăng xuất chưa
        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }
}
