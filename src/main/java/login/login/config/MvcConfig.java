package login.login.config;

import login.login.LoginAnnotation.LoginArgumentResolver;
import login.login.LoginAnnotation.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/auth/login", "/auth/logout",
                        "/css/**", "/*.ico", "/error", "/test", "/auth/sign-up","/auth/id-validation", "/member/register",
                        "/auth/mail-validation", "/auth/test", "/member/**/name", "/auth/user", "/member/**/my-page");
    }
}
