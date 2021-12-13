package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.kavahub.learnjava.SpringApplication.Config;

/**
 * {@link SpringApplication} 测试用例
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Config.class })
public class SpringApplicationTest {
    
    private static String productId = UUID.randomUUID()
        .toString();

    @Autowired
    SpringApplication application;

    @Autowired
    DataSource inventoryDataSource;

    @Autowired
    DataSource orderDataSource;

    @Test
    public void testPlaceOrderSuccess() throws Exception {
        int amount = 1;
        long initialBalance = getBalance(inventoryDataSource, productId);
        application.placeOrder(productId, amount);
        long finalBalance = getBalance(inventoryDataSource, productId);
        assertEquals(initialBalance - amount, finalBalance);
    }

    @Test
    public void testPlaceOrderFailure() throws Exception {
        int amount = 10;
        long initialBalance = getBalance(inventoryDataSource, productId);
        try {
            application.placeOrder(productId, amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        long finalBalance = getBalance(inventoryDataSource, productId);
        assertEquals(initialBalance, finalBalance);
    }

    @BeforeAll
    public void setUp() throws SQLException {
        Connection inventoryConnection = inventoryDataSource.getConnection();
        Connection orderConnection = orderDataSource.getConnection();
        String createInventoryTable = "create table Inventory ( " + " productId VARCHAR ( 100 ) PRIMARY KEY, balance INT )";
        String createInventoryRow = "insert into Inventory values ( '" + productId + "', 10000 )";
        Statement s1 = inventoryConnection.createStatement();
        try {
            s1.executeUpdate(createInventoryTable);
        } catch (Exception e) {
            System.out.println("Inventory table exists");
        }
        try {
            s1.executeUpdate(createInventoryRow);
        } catch (Exception e) {
            System.out.println("Product row exists");
        }
        s1.close();
        String createOrderTable = "create table Orders ( orderId VARCHAR ( 100 ) PRIMARY KEY, productId VARCHAR ( 100 ), amount INT NOT NULL CHECK (amount <= 5) )";
        Statement s2 = orderConnection.createStatement();
        try {
            s2.executeUpdate(createOrderTable);
        } catch (Exception e) {
            System.out.println("Orders table exists");
        }
        s2.close();
        inventoryConnection.close();
        orderConnection.close();
    }

    private static long getBalance(DataSource inventoryDataSource, String productId) throws Exception {

        Connection inventoryConnection = inventoryDataSource.getConnection();
        Statement s1 = inventoryConnection.createStatement();
        String q1 = "select balance from Inventory where productId='" + productId + "'";
        ResultSet rs1 = s1.executeQuery(q1);
        if (rs1 == null || !rs1.next())
            throw new Exception("Product not found: " + productId);
        long balance = rs1.getLong(1);
        inventoryConnection.close();
        return balance;

    }

}
