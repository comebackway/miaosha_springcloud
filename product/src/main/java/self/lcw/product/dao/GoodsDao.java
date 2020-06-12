package self.lcw.product.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import self.lcw.product.dto.GoodsDto;
import self.lcw.product.entity.MiaoShaGoods;

import java.util.List;

@Mapper
public interface GoodsDao {
    public List<GoodsDto> listGoodsDtoList();

    public GoodsDto getGoodsDtoByGoodsId(@Param("goodsId") long id);

    public int reduceStock(MiaoShaGoods g);
}
