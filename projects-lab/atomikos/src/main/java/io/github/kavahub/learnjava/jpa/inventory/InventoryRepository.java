package io.github.kavahub.learnjava.jpa.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 库存 数据访问接口
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
    
}
