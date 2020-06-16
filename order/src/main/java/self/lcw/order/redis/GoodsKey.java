package self.lcw.order.redis;

public class GoodsKey extends BasePrefix{

    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");
    public static GoodsKey getGoodsStock = new GoodsKey(0,"gs");
    public static GoodsKey getGoodsIsOver = new GoodsKey(0,"go");
}
