package xyz.f3l1x.open_audio_books_api.config;

import org.omnifaces.filter.FacesExceptionFilter;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class JsfConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        final FilterRegistrationBean filterRegistrationBean  = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new FacesExceptionFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setEnabled(true);
        return filterRegistrationBean;
    }

    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return new ErrorPageRegistrar() {
            @Override
            public void registerErrorPages(ErrorPageRegistry registry) {
                registry.addErrorPages(new ErrorPage("/error.xhtml"));
                registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/not_found.xhtml"));
            }
        };
    }
}
