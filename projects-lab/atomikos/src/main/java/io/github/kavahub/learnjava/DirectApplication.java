package io.github.kavahub.learnjava;

import java.sql.Connection;
import java.sql.Statement;
import java.util.UUID;

import javax.sql.DataSource;

import com.atomikos.icatch.jta.UserTransactionImp;

/**
 * Atomikos + Derby 示例
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
public class DirectApplication {
    
    private DataSource inventoryDataSource;
    private DataSource orderDataSource;

    public DirectApplication(DataSource inventoryDataSource, DataSource orderDataSource) {
        this.inventoryDataSource = inventoryDataSource;
        this.orderDataSource = orderDataSource;
    }

    public void placeOrder(String productId, int amount) throws Exception {

        UserTransactionImp utx = new UserTransactionImp();
        String orderId = UUID.randomUUID()
            .toString();
        boolean rollback = false;
        try {
            utx.begin();
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            rollback = true;
        } finally {
            if (!rollback)
                utx.commit();
            else
                utx.rollback();
        }

    }

}
