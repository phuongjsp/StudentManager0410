package edu.zera.student.manager.config;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "edu.zera.student.manager"})
public class WebMvcConfig implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;
    /*
    * trong mo hinh mvc :
    * v : views
    * m : model
    * c: controller
    * */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
    @Bean
    public PasswordEncoder encoder(){
            return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        // SpringResourceTemplateResolver automatically integrates with Spring's own
        // resource resolution infrastructure, which is highly recommended.
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver ();
        templateResolver.setApplicationContext (this.applicationContext);
        templateResolver.setPrefix ("/WEB-INF/views/");
        templateResolver.setSuffix (".html");
        // HTML is the default value, added here for the sake of clarity.
        templateResolver.setTemplateMode (TemplateMode.HTML);
        templateResolver.setCharacterEncoding ("UTF-8");
        templateResolver.setCheckExistence (true);
        // Template cache is true by default. Set to false if you want
        // templates to be automatically updated when modified.
        templateResolver.setCacheable (false);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine ();
        templateEngine.setTemplateResolver (templateResolver ());
        templateEngine.setEnableSpringELCompiler (true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver ();
        resolver.setTemplateEngine (templateEngine ());
        registry.viewResolver (resolver);
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver ();
        viewResolver.setTemplateEngine (templateEngine ());
        // NOTE 'order' and 'viewNames' are optional
        viewResolver.setOrder (1);
        viewResolver.setViewNames (new String[]{".html", ".xhtml"});
        return viewResolver;
    }


}
