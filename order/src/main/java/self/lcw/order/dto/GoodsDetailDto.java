package self.lcw.order.dto;

import lombok.Data;

@Data
public class GoodsDetailDto {
    private int miaoshaStatus = 0;
    private int remainSeconds = 0;
    private GoodsDto goodsDto;
}
