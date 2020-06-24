package self.lcw.user.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.lcw.product.client.ProductClient;
import self.lcw.product.common.GoodsDto;

import java.util.List;

@Service
public class GoodsClientService {

    @Autowired
    ProductClient productClient;

    public List<GoodsDto> showlist() {
        List<GoodsDto> goodsDtos = productClient.showList();
        return goodsDtos;
    }
}
