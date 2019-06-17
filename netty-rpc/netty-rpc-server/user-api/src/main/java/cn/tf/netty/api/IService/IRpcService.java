package cn.tf.netty.api.IService;

public interface IRpcService {

    /**
     * 加
     * @param a
     * @param b
     * @return
     */
    int add(int a, int b);

    /**
     * 减
     * @param a
     * @param b
     * @return
     */
    int sub(int a, int b);

    /**
     * 乘
     * @param a
     * @param b
     * @return
     */
    int mutiply(int a, int b);

    /**
     * 除
     * @param a
     * @param b
     * @return
     */
    int divide(int a, int b);

}
