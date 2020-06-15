package self.lcw.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.lcw.user.FeginClient.ProductClient;
import self.lcw.user.dto.GoodsDto;

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
