package lv.ami.fuelmaster.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import ch.qos.logback.classic.*;

import org.slf4j.LoggerFactory;

@Configuration
public class AppConfig implements WebMvcConfigurer {

	@Autowired
	private ApplicationContext applicationContext;

	//@Autowired
	//private DataSource dataSource;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}

	/*
	 * @Bean public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	 * LocalContainerEntityManagerFactoryBean em = new
	 * LocalContainerEntityManagerFactoryBean(); em.setDataSource(dataSource);
	 * em.setPackagesToScan(new String[] { "lv.ami.fuelmaster" });
	 *
	 * // configure JpaVendorAdapter if needed // em.setJpaVendorAdapter(new
	 * HibernateJpaVendorAdapter());
	 *
	 * return em; }
	 *
	 * @Bean public PlatformTransactionManager
	 * transactionManager(EntityManagerFactory emf) { JpaTransactionManager
	 * transactionManager = new JpaTransactionManager();
	 * transactionManager.setEntityManagerFactory(emf);
	 *
	 * return transactionManager; }
	 */

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");


		return resolver;
	}

	@Bean
	public ISpringTemplateEngine emailTemplateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(emailTemplateResolver());
		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}

	@Bean
	public ITemplateResolver emailTemplateResolver() {
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setOrder(1);
		templateResolver.setCheckExistence(true);

		templateResolver.setCacheable(false);


		return templateResolver;
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

	private ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		resolver.setCacheable(false);
		resolver.setCharacterEncoding("UTF-8");

		resolver.setCacheable(false);
		return resolver;
	}

   /* @Bean
    public ConsoleHandler consoleHandler() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        // Set the desired logging level for the ConsoleHandler
        //consoleHandler.setLevel(Level.INFO); 
        consoleHandler.setLevel(Level.FINEST);

        return consoleHandler;
    }*/
	
    @Bean
    @Profile("development")
    public void configureDevelopmentLogging() {
        // Configure logging for the "development" profile
    	Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.DEBUG);
    }

    @Bean
    @Profile("production")
    public void configureProductionLogging() {
        // Configure logging for the "production" profile
    	Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.DEBUG);
    }

}
