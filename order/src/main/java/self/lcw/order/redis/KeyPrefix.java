package self.lcw.order.redis;

public interface KeyPrefix {
    //设置超时时间
    public int expireSeconds();
    //获取key前缀
    public String getPrefix();
}
