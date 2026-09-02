package com.mh.productStore;

import com.mh.productStore.controller.HelloController;
import com.mh.productStore.security.JwtService;
import com.mh.productStore.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(HelloController.class) //فقط کنترلر رو بالا بیار نه کل اسپرینگ بوت
class HelloControllerTest {
    @Autowired
    private MockMvc mockMvc;//مثل postman عمل میکنه ولی رو تست

    @MockitoBean
    private ProductService service;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private UserService userService;

    @Test
    void testHelloEndPoint() throws Exception{
        mockMvc.perform(get("/hello")).andExpect(status().isOk())
                .andExpect(content().string("hello spring boot"));
        //اکسپت اول ببرسی میکند 200 درومده یا نه اکسپت دوم بررسی میکنه متن خروجی چی درومده
    }
}
