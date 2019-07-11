package cn.tf.rpc.discovery;

import java.util.List;

public interface LoadBalanceStrategy {

    String selectHost(List<String> repos);
}
