package self.lcw.order.common;


import lombok.Data;
import self.lcw.product.common.GoodsDto;

import java.util.Date;

@Data
public class OrderDetailDto {
    private GoodsDto goodsDto;
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer status;
    private Date createDate;
    private Date payDate;
}
