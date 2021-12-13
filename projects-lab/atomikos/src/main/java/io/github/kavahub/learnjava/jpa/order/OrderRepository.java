package io.github.kavahub.learnjava.jpa.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * 订单 数据访问接口
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    
}
