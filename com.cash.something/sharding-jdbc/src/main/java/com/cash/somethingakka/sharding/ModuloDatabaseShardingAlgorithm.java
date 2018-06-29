package com.cash.somethingakka.sharding;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 数据源路由算法
 * author cash
 * create 2017-05-19-10:06
 **/

public class ModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Integer> {

    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {

        for(String targetName:availableTargetNames){
            if(targetName.endsWith((shardingValue.getValue()%2)+"")){
                return targetName;
            }
        }
        return null;
    }

    public Collection<String> doInSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
        Collection<String> targetNames=new LinkedHashSet<String>();
        for(Integer sharding:shardingValue.getValues()){
            for(String targetName:availableTargetNames){
                if(targetName.endsWith((sharding%2)+"")){
                    targetNames.add(targetName);
                }
            }
        }
        return targetNames;
    }

    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
        Collection<String> targetNames=new LinkedHashSet<String>();
        Range<Integer> range =shardingValue.getValueRange();
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for(String targetName:availableTargetNames){
                if(targetName.endsWith((i%2)+"")){
                    targetNames.add(targetName);
                }
            }
        }
        return targetNames;
    }
}
