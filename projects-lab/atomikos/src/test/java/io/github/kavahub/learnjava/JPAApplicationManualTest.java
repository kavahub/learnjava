package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.kavahub.learnjava.JPAApplication.Config;
import io.github.kavahub.learnjava.jpa.inventory.Inventory;
import io.github.kavahub.learnjava.jpa.inventory.InventoryConfig;
import io.github.kavahub.learnjava.jpa.inventory.InventoryRepository;
import io.github.kavahub.learnjava.jpa.order.OrderConfig;
import io.github.kavahub.learnjava.jpa.order.OrderRepository;

/**
 * {@link JPAApplication} 测试用例
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Config.class, InventoryConfig.class, OrderConfig.class })
public class JPAApplicationManualTest {
    
    private static String productId = UUID.randomUUID()
        .toString();

    @Autowired
    JPAApplication application;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void testPlaceOrderSuccess() throws Exception {
        int amount = 1;
        long initialBalance = getBalance(inventoryRepository, productId);
        application.placeOrder(productId, amount);
        long finalBalance = getBalance(inventoryRepository, productId);
        assertEquals(initialBalance - amount, finalBalance);
    }

    @Test
    public void testPlaceOrderFailure() throws Exception {
        int amount = 10;
        long initialBalance = getBalance(inventoryRepository, productId);
        try {
            application.placeOrder(productId, amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        long finalBalance = getBalance(inventoryRepository, productId);
        assertEquals(initialBalance, finalBalance);
    }

    @BeforeAll
    public void setUp() throws SQLException {

        Inventory inventory = new Inventory();
        inventory.setProductId(productId);
        inventory.setBalance(Long.valueOf(10000));
        inventoryRepository.save(inventory);

    }

    private static long getBalance(InventoryRepository inventoryRepository, String productId) throws Exception {
        return inventoryRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId))
            .getBalance();

    }
}
