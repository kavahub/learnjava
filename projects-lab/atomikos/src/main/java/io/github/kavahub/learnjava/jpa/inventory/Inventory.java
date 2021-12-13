package io.github.kavahub.learnjava.jpa.inventory;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 库存实体
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
@Data
@Entity
@Table(name = "INVENTORY")
public class Inventory {
    @Id
    private String productId;
    private Long balance;
}
