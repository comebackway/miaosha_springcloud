package self.lcw.product.common;

import lombok.Data;

import java.util.Date;

/**
 * 改造成多模块，该模块是作为公共类，最好就不要extends server里边的实体类了，直接将里边的属性重写一次吧
 */
@Data
public class GoodsDto {
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
    private Double miaoshaPrice;

    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private String goodsPrice;
    private Integer goodsStock;
}
