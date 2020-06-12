package self.lcw.product.dto;

import lombok.Data;
import self.lcw.product.entity.Goods;

import java.util.Date;

@Data
public class GoodsDto extends Goods {
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
    private Double miaoshaPrice;
}
