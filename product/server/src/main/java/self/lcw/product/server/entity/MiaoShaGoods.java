package self.lcw.product.server.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MiaoShaGoods {
    private Long id;
    private Long goodsId;
    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;

}
