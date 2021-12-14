package io.github.kavahub.learnjava.jpa.order;

import java.nio.file.Paths;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import com.atomikos.jdbc.AtomikosDataSourceBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * 订单配置：数据源，EntityManagerFactory，数据访问接口 等等
 * 
 * @author PinWei Wan
 * @since 1.0.2
 */

@Configuration
@EnableJpaRepositories(basePackages = "io.github.kavahub.learnjava.jpa.order", entityManagerFactoryRef = "orderEntityManager", transactionManagerRef = "transactionManager")
public class OrderConfig {
    static {
        // 设置derby数据目录
        System.setProperty("derby.system.home", Paths.get("target").toAbsolutePath().toString());
    }
    
    /**
     * 订单 数据源
     * @return
     */
    @Bean(initMethod = "init", destroyMethod = "close")
    public AtomikosDataSourceBean orderDataSource() {
        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        dataSource.setLocalTransactionMode(true);
        dataSource.setUniqueResourceName("db2");
        dataSource.setXaDataSourceClassName("org.apache.derby.jdbc.EmbeddedXADataSource");
        Properties xaProperties = new Properties();
        xaProperties.put("databaseName", "db2");
        xaProperties.put("createDatabase", "create");
        dataSource.setXaProperties(xaProperties);
        dataSource.setPoolSize(10);
        return dataSource;
    }

    /**
     * 订单 实体管理器
     * 
     * @return
     */
    @Bean
    public EntityManagerFactory orderEntityManager() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("io.github.kavahub.learnjava.jpa.order");
        factory.setDataSource(orderDataSource());
        Properties jpaProperties = new Properties();
        //jpaProperties.put("hibernate.show_sql", "true");
        //jpaProperties.put("hibernate.format_sql", "true");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
        jpaProperties.put("hibernate.current_session_context_class", "jta");
        jpaProperties.put("javax.persistence.transactionType", "jta");
        jpaProperties.put("hibernate.transaction.manager_lookup_class", "com.atomikos.icatch.jta.hibernate3.TransactionManagerLookup");
        // 自动创建表
        jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");
        factory.setJpaProperties(jpaProperties);
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
