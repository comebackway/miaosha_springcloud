package self.lcw.order.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import self.lcw.order.server.entity.MiaoshaOrder;
import self.lcw.order.server.entity.OrderInfo;


@Mapper
public interface OrderDao {
    public MiaoshaOrder getMiaoshaOrderByUserIdAndGoodsId(@Param("goodsId") long goodsId);

    public long insertOrderInfo(OrderInfo orderInfo);

    public int insertMiaoShaOrder(MiaoshaOrder miaoshaOrder);

    public OrderInfo getOrderById(long orderId);
}
