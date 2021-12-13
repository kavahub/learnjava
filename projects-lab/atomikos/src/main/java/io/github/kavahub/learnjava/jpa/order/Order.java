package io.github.kavahub.learnjava.jpa.order;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import lombok.Data;

/**
 * 订单实体
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
@Data
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    private String orderId;
    private String productId;
    @Max(5)
    private Long amount;
}
