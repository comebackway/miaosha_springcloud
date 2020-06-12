package self.lcw.order.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import self.lcw.order.entity.MiaoshaOrder;
import self.lcw.order.entity.OrderInfo;


@Mapper
public interface OrderDao {
    public MiaoshaOrder getMiaoshaOrderByUserIdAndGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    public long insertOrderInfo(OrderInfo orderInfo);

    public int insertMiaoShaOrder(MiaoshaOrder miaoshaOrder);

    public OrderInfo getOrderById(long orderId);
}
