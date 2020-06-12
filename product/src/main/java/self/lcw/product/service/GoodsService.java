package self.lcw.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.lcw.product.dto.GoodsDto;
import self.lcw.product.dao.GoodsDao;
import self.lcw.product.entity.MiaoShaGoods;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsDto> listGoodsDto(){
        return goodsDao.listGoodsDtoList();
    }

    public GoodsDto getGoodsDtoByGoodsId(long id){return goodsDao.getGoodsDtoByGoodsId(id);}

    public boolean reduceStock(GoodsDto goodsDto) {
        MiaoShaGoods g = new MiaoShaGoods();
        g.setGoodsId(goodsDto.getId());
        int ret = goodsDao.reduceStock(g);
        return ret>0;
    }
}
