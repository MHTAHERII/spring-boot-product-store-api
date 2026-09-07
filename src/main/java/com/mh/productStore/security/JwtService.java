package com.mh.productStore.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService//سرویس اصلی مدیریت توکن های jwt
{
    private final SecretKey key = Keys.hmacShaKeyFor("mysecretkeymysecretkeymysecretkey123".getBytes());//کلید مخفی سرور

    public String generateToken(String username) {
        return Jwts.builder().subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key).compact();
    }
    //subject: توکن متعلق به چه کسی هست
    //issuedAt: زمان ساخت token
    //expiration: زمان انقضا
    //signwith: امضای دیجیتال رو میسازد
    //compact: در نهایت توکن را میسازد


    //وقتی توکن بهش میده اسم یوزر رو اکسترکت میکنه
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }


    //برای استخراج اطلاعات از یک توکن JWT
    //claims در واقع یک ابجکت شبیه به map هست که تمام داده های ذخیره شده تو توکن رو (مثل نام کاربر,تاریخ انقضا,نقش ها) رو نگه میداره
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
    //نشان دادن تاریخ انقضا
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
    //بررسی منقضی شدن
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    //متد اعتبار سنجی
    public Boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

}
