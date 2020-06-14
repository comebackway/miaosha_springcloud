package self.lcw.order.redis;

public class MiaoshaGoodsKey extends BasePrefix{

    public MiaoshaGoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaGoodsKey getGoodsStock = new MiaoshaGoodsKey(0,"gs");
    public static MiaoshaGoodsKey getGoodsIsOver = new MiaoshaGoodsKey(0,"go");
}
