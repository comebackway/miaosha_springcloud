package self.lcw.product.server.entity;

import lombok.Data;

@Data
public class Goods {
    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private String goodsPrice;
    private Integer goodsStock;
}
