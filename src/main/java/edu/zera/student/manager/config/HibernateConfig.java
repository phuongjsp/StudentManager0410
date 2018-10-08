package edu.zera.student.manager.config;


import edu.zera.student.manager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.core.env.Environment;
import java.util.Properties;
import static org.hibernate.cfg.Environment.*;
import static org.hibernate.cfg.AvailableSettings.DRIVER;

@Configuration
@PropertySource("classpath:db.properties")

@EnableTransactionManagement
public class HibernateConfig {
    @Autowired
    private Environment env;

    @Bean
//    @Resource(name = "getSessionFactory")
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

        Properties props = new Properties();
        // Setting JDBC properties
        props.put(DRIVER, env.getProperty("mysql.driver"));
        props.put(URL, env.getProperty("mysql.url"));
        props.put(USER, env.getProperty("mysql.user"));
        props.put(PASS, env.getProperty("mysql.password"));
        // Setting Hibernate properties
        props.put(SHOW_SQL, env.getProperty("hibernate.show_sql"));
        props.put(HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));
        /*
        validate : chi cho phep crud
        update : cho phep tao them table
        create-drop : tao them cac bang co trong phan entites,va xoa cac bang khong duoc anh xa ve
        */
        props.put(DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        props.put("hibernate.enable_lazy_load_no_trans", true);
        // Setting C3P0 properties
        props.put(C3P0_MIN_SIZE, env.getProperty("hibernate.c3p0.min_size"));
        props.put(C3P0_MAX_SIZE, env.getProperty("hibernate.c3p0.max_size"));
        props.put(C3P0_ACQUIRE_INCREMENT,
                env.getProperty("hibernate.c3p0.acquire_increment"));
        props.put(C3P0_TIMEOUT, env.getProperty("hibernate.c3p0.timeout"));
        props.put(C3P0_MAX_STATEMENTS, env.getProperty("hibernate.c3p0.max_statements"));
        factoryBean.setHibernateProperties(props);

        //Mapping
        factoryBean.setPackagesToScan("edu.zera.student.manager.model");
//        factoryBean.setAnnotatedClasses(User.class);
        return factoryBean;
    }
    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
}
