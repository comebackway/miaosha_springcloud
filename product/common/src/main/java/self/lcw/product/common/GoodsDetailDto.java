package self.lcw.product.common;

import lombok.Data;

@Data
public class GoodsDetailDto {
    private int miaoshaStatus = 0;
    private int remainSeconds = 0;
    private GoodsDto goodsDto;
}
