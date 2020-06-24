package self.lcw.product.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import self.lcw.product.common.GoodsDto;
import self.lcw.product.server.entity.MiaoShaGoods;

import java.util.List;

@Mapper
public interface GoodsDao {
    public List<GoodsDto> listGoodsDtoList();

    public GoodsDto getGoodsDtoByGoodsId(@Param("goodsId") long id);

    public int reduceStock(MiaoShaGoods g);
}
