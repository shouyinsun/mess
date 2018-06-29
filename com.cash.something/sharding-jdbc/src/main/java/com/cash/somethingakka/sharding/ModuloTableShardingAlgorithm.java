package com.cash.somethingakka.sharding;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 表路由算法
 * author cash
 * create 2017-05-19-10:23
 **/

public class ModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {

    /**
     *  select * from t_order from t_order where order_id = 11
     *          └── SELECT *  FROM t_order_1 WHERE order_id = 11
     *  select * from t_order from t_order where order_id = 44
     *          └── SELECT *  FROM t_order_0 WHERE order_id = 44
     */
    public String doEqualSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
        for(String tablename:tableNames){
            if(tablename.endsWith((shardingValue.getValue()%2)+"")){
                return tablename;
            }
        }
        return null;
    }

    /**
     *  select * from t_order from t_order where order_id in (11,44)
     *          ├── SELECT *  FROM t_order_0 WHERE order_id IN (11,44)
     *          └── SELECT *  FROM t_order_1 WHERE order_id IN (11,44)
     *  select * from t_order from t_order where order_id in (11,13,15)
     *          └── SELECT *  FROM t_order_1 WHERE order_id IN (11,13,15)
     *  select * from t_order from t_order where order_id in (22,24,26)
     *          └──SELECT *  FROM t_order_0 WHERE order_id IN (22,24,26)
     */
    public Collection<String> doInSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
        Collection<String> tables=new LinkedHashSet<String>();
        for(Integer sharding:shardingValue.getValues()){
            for(String tablename:tableNames){
                if(tablename.endsWith((sharding%2)+"")){
                    tables.add(tablename);
                }
            }
        }
        return tables;
    }

    /**
     *  select * from t_order from t_order where order_id between 10 and 20
     *          ├── SELECT *  FROM t_order_0 WHERE order_id BETWEEN 10 AND 20
     *          └── SELECT *  FROM t_order_1 WHERE order_id BETWEEN 10 AND 20
     */
    public Collection<String> doBetweenSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
        Collection<String> tables=new LinkedHashSet<String>();
        Range<Integer> sharding=shardingValue.getValueRange();
        for(Integer i=sharding.lowerEndpoint();i<=sharding.upperEndpoint();i++){
            for(String tablename:tableNames){
                if(tablename.endsWith((i%2)+"")){
                    tables.add(tablename);
                }
            }
        }
        return tables;
    }
}
