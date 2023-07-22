package com.muhammet.config;

import com.muhammet.exception.ErrorType;
import com.muhammet.exception.UserException;
import com.muhammet.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenManager jwtTokenManager;
    @Autowired
    private JwtUserDetails jwtUserDetails;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        /**
         * Burası gelen tüm isteklerin yönlendirildiği yerdir. Burada Header içinde Authorization
         * olup olmadığını kontrol ediyoruz. Eğer varsa token içindeki bilgileri alıp  işlem yapıyoruz.
          */
        final String authHeader = request.getHeader("Authorization");
        /**
         * bearer token olup olmadığının kontrolünü yapıoyorz
         * Bearer [TOKEN]  -> index 7>
         */
        if(authHeader != null && authHeader.startsWith("Bearer ") ){
            /**
             * Bearer token içinden jwt bilgisini ayırıyoruz.
             */
            final String token = authHeader.substring(7);
            /**
             * token bilgisinin geçerliliğini kontrol ediyoruz.
             */
            Optional<Long> authId = jwtTokenManager.getIdFromToken(token);
            if(authId.isPresent()){
                /**
                 * DİKKAT!!! tüm oturum açma işlemleri burada yapılacak ve spring için özel  bir
                 * kullaıncı tanımı oluşturacağız.
                 */
                UserDetails userDetails = jwtUserDetails.loadUserByAuthId(authId.get());
                /**
                 * Araya girme işleminde kimlik doğrulama yöntemi olarak kullandığımız UsernamePasswordAuthenticationFilter sınıfı
                 * iiçin gerekli olan kimlk bilgilerini oluşturuyoruz. bu kimlik bilgileri ile ihtiyyacı olan tüm parametreleri içeren
                 * UsernamePasswordAuthenticationToken nesnesi yaratarak bu nesneyi context içine auth bilgisi olarak geçiyoruz.
                 */
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                /**
                 * Geçerli context içine kimlik bilgilerini ekliyoruz. böylece spring security nin yötecebileceği bir
                 * kmilik yönetimi sağlanmış oluyor.
                 */
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else{
                /**
                 * token bilgisi geçersiz olduğu için token hatası fırlatıyoruz.
                 */
                throw new UserException(ErrorType.ERROR_INVALID_TOKEN);
            }
        }

        filterChain.doFilter(request,response);
    }


}
