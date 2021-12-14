package io.github.kavahub.learnjava;

import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.transaction.SystemException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.atomikos.icatch.jta.UserTransactionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;

import io.github.kavahub.learnjava.jpa.inventory.Inventory;
import io.github.kavahub.learnjava.jpa.inventory.InventoryRepository;
import io.github.kavahub.learnjava.jpa.order.Order;
import io.github.kavahub.learnjava.jpa.order.OrderRepository;

/**
 * Atomikos + Derby + JPA 示例
 * 
 * @author PinWei Wan
 * @since 1.0.2
 */
public class JPAApplication {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional(rollbackFor = Exception.class)
    public void placeOrder(String productId, int amount) throws Exception {

        String orderId = UUID.randomUUID()
                .toString();
        Inventory inventory = inventoryRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(productId));
        inventory.setBalance(inventory.getBalance() - amount);
        inventoryRepository.save(inventory);
        Order order = new Order();
        order.setOrderId(orderId);
        order.setProductId(productId);
        order.setAmount(Long.valueOf(amount));

        // 校验，Order实体上有校验规则
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        if (violations.size() > 0)
            throw new Exception("Invalid instance of an order.");
        orderRepository.save(order);

    }

    @Configuration
    @EnableTransactionManagement
    public static class Config {
        /**
         * 配置事务管理器
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
         * 配置JTA事务管理器
         * @return
         * @throws SystemException
         */
        @Bean
        public JtaTransactionManager transactionManager() throws SystemException {
            JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
            jtaTransactionManager.setTransactionManager(userTransactionManager());
            jtaTransactionManager.setUserTransaction(userTransactionManager());
            return jtaTransactionManager;
        }

        @Bean
        public JPAApplication application() {
            return new JPAApplication();
        }

    }
}
