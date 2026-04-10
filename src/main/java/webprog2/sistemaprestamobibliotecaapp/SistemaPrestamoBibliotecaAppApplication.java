package webprog2.sistemaprestamobibliotecaapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import webprog2.sistemaprestamobibliotecaapp.interceptor.TimingInterceptor;

@SpringBootApplication
public class SistemaPrestamoBibliotecaAppApplication implements WebMvcConfigurer {

    private final TimingInterceptor timingInterceptor;

    public SistemaPrestamoBibliotecaAppApplication(TimingInterceptor timingInterceptor) {
        this.timingInterceptor = timingInterceptor;
    }

    public static void main(String[] args) {
        SpringApplication.run(SistemaPrestamoBibliotecaAppApplication.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/book/menu").setViewName("bookmenu");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timingInterceptor)
                .addPathPatterns("/**");
    }

}
