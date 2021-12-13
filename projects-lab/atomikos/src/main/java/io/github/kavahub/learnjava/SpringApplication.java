package io.github.kavahub.learnjava;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;
import java.util.UUID;

import javax.sql.DataSource;
import javax.transaction.SystemException;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * Atomikos + Derby + Spring 示例
 * 
 * @author PinWei Wan
 * @since 1.0.2
 */
public class SpringApplication {
    private DataSource inventoryDataSource;
    private DataSource orderDataSource;

    public SpringApplication(DataSource inventoryDataSource, DataSource orderDataSource) {
        this.inventoryDataSource = inventoryDataSource;
        this.orderDataSource = orderDataSource;
    }

    @Transactional(rollbackFor = Exception.class)
    public void placeOrder(String productId, int amount) throws Exception {

        String orderId = UUID.randomUUID()
                .toString();
        Connection inventoryConnection = inventoryDataSource.getConnection();
        Connection orderConnection = orderDataSource.getConnection();
        Statement s1 = inventoryConnection.createStatement();
        String q1 = "update Inventory set balance = balance - " + amount + " where productId ='" + productId + "'";
        s1.executeUpdate(q1);
        s1.close();
        Statement s2 = orderConnection.createStatement();
        String q2 = "insert into Orders values ( '" + orderId + "', '" + productId + "', " + amount + " )";
        s2.executeUpdate(q2);
        s2.close();
        inventoryConnection.close();
        orderConnection.close();

    }

    @Configuration
    @EnableTransactionManagement
    public static class Config {
        static {
            // 设置derby数据目录
            System.setProperty("derby.system.home", Paths.get("target").toAbsolutePath().toString());
        }

        /**
         * 库存 数据源
         * @return
         */
        @Bean(initMethod = "init", destroyMethod = "close")
        public AtomikosDataSourceBean inventoryDataSource() {
            AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
            dataSource.setLocalTransactionMode(true);
            dataSource.setUniqueResourceName("db1");
            dataSource.setXaDataSourceClassName("org.apache.derby.jdbc.EmbeddedXADataSource");
            Properties xaProperties = new Properties();
            xaProperties.put("databaseName", "db1");
            xaProperties.put("createDatabase", "create");
            dataSource.setXaProperties(xaProperties);
            dataSource.setPoolSize(10);
            return dataSource;
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
         * 事务管理器
         * 
         * @return
         * @throws SystemException
         */
        @Bean(initMethod = "init", destroyMethod = "close")
        public UserTransactionManager userTransactionManager() throws SystemException {
            UserTransactionManager userTransactionManager = new UserTransactionManager();
            userTransactionManager.setTransactionTimeout(300);
            userTransactionManager.setForceShutdown(true);
            return userTransactionManager;
        }

        /**
         * JTA事务管理器
         * 
         * @return
         * @throws SystemException
         */
        @Bean
        public JtaTransactionManager jtaTransactionManager() throws SystemException {
            JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
            jtaTransactionManager.setTransactionManager(userTransactionManager());
            jtaTransactionManager.setUserTransaction(userTransactionManager());
            return jtaTransactionManager;
        }

        @Bean
        public SpringApplication application() {
            return new SpringApplication(inventoryDataSource(), orderDataSource());
        }
    }
}
