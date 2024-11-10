package xyz.f3l1x.open_audio_books_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class IndexWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/").setViewName("forward:/list.xhtml");
        // Redirects / to /index homepage
        registry.addViewController("/").setViewName("redirect:/index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
