package com.cash.es;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * es测试
 * author cash
 * create 2017-09-20-17:02
 *
 *
 *  index: es里的index相当于一个数据库。
     type: 相当于数据库里的一个表。
     id： 唯一,相当于主键。
     node:节点是es实例,一台机器可以运行多个实例
 **/

public class ElasticSearchTest {

    private TransportClient client;
    private IndexRequest source;

    //@Before
    public void before()throws  Exception{
      /*  Map<String,String> map=new HashMap<>();
        map.put("cluster.name","es-cash");
        Settings.Builder settings=Settings.builder().put(map);
        client=TransportClient.builder().settings(settings).build().addTransportAddress(
                new InetSocketTransportAddress(new InetSocketAddress("192.168.211.58", 9300))
        );*/


    }

    @Before
    public void before11() throws Exception {
        // 通过setting对象指定集群配置信息, 配置的集群名
/*        Settings settings = Settings.settingsBuilder().put("cluster.name", "es-cash") // 设置集群名
                .put("client.transport.ignore_cluster_name", true) // 忽略集群名字验证,打开后集群名字不对也能连接上
                .build();
        client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("192.168.211.58", 9300)));*/

        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "es-cash") // 设置集群名
                .put("client.transport.ignore_cluster_name", true)// 忽略集群名字验证,打开后集群名字不对也能连接上
                .build();
        client = new TransportClient(settings);
        client.addTransportAddress(new
                InetSocketTransportAddress("192.168.211.58", 9300));
        System.out.println("success connect");
    }

    /**
     * 查看集群信息
     */
    @Test
    public void NodesInfo(){
        List<DiscoveryNode> nodes=client.connectedNodes();
        for (DiscoveryNode node:nodes){
            System.out.println(node.getHostAddress());
        }
    }

    /**
     * 通过map创建json
     * @return
     */
    public  Map<String,Object> createMapJson(){
        Map<String,Object> json = new HashMap<String, Object>();
        json.put("name", "kay");
        json.put("age", 26);
        json.put("sex", 0);
        return json;
    }

    /**
     * 使用fastjson创建json
     * @return
     */
    public JSONObject CreateJson() {
        JSONObject json = new JSONObject();
        json.put("name", "kay");
        json.put("age", 26);
        json.put("sex", 0);
        return json;
    }

    /**
     * 使用es的帮助类
     */
    public XContentBuilder createJson2() throws Exception {
        // 创建json对象, 其中一个创建json的方式
        XContentBuilder source = XContentFactory.jsonBuilder()
                .startObject()
                .field("name", "kay")
                .field("age", 26)
                .field("sex", 0)
                .endObject();
        return source;
    }

    /***
     * 新建
     * @throws Exception
     */
    @Test
    public void putTest() throws Exception {
        XContentBuilder source=createJson2();
        //存json到索引中
        IndexResponse response=client.prepareIndex("cashcorp","employee","3").setSource(source).get();
        //获取结果
        String index=response.getIndex();
        String type=response.getType();
        String id=response.getId();
        long version=response.getVersion();
        boolean created=response.isCreated();
        System.out.println(index + " : " + type + ": " + id + ": " + version + ": " + created);
    }

    /**
     * 查询
     */
    @Test
    public void getTest() {
        GetResponse response = client.prepareGet("cashcorp", "employee", "1")
                .setOperationThreaded(false)    // 线程安全
                .get();
        System.out.println(response.getSourceAsString());
    }


    /**
     *
     */
    @Test
    public void deleteTest() {
        DeleteResponse response = client.prepareDelete("cashcorp", "employee", "4").get();
        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        long version = response.getVersion();
        System.out.println(index + " : " + type + ": " + id + ": " + version);
    }

    /**
     * 测试更新 update API
     * 使用 updateRequest 对象
     */
    @Test
    public void updateTest() throws Exception {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("cashcorp");
        updateRequest.type("employee");
        updateRequest.id("1");
        updateRequest.doc(XContentFactory.jsonBuilder()
                .startObject()
                 //对没有的字段添加, 对已有的字段替换
                .field("name", "cash")
                .field("age", "26")
                .field("salary", "10000000")
                .endObject());
        UpdateResponse response = client.update(updateRequest).get();

        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        long version = response.getVersion();
        System.out.println(index + " : " + type + ": " + id + ": " + version);
    }



    @Test
    public void updateTest2() throws Exception {

        // 使用updateRequest对象及documents进行更新
        UpdateResponse response = client.update(new UpdateRequest("cashcorp", "employee", "1")
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("level", "10000")
                        .endObject()
                )).get();
        System.out.println(response.getIndex());
    }

    @Test
    public void testUpsert() throws Exception {
        // 设置查询条件, 查找不到则添加生效
        IndexRequest indexRequest = new IndexRequest("cashcorp", "employee", "5")
                .source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("name", "jack")
                        .field("age", "26")
                        .field("salary", "10000")
                        .endObject());
        // 设置更新, 查找到更新下面的设置
        UpdateRequest upsert = new UpdateRequest("cashcorp", "employee", "5")
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("level", "100")
                        .endObject())
                .upsert(indexRequest);

        client.update(upsert).get();
    }

    @Test
    public void multiGetTest() {
        MultiGetResponse multiGetResponse = client.prepareMultiGet()
                .add("cashcorp", "employee", "1")
                .add("cashcorp", "employee", "2", "3", "4")
                .add("anothoer", "type", "foo")
                .get();

        for (MultiGetItemResponse itemResponse : multiGetResponse) {
            GetResponse response = itemResponse.getResponse();
            if (null!=response && response.isExists()) {
                String sourceAsString = response.getSourceAsString();
                System.out.println(sourceAsString);
            }
        }
    }

    @Test
    public void testBulkProcessor() throws Exception {
        // 创建BulkPorcessor对象
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            public void beforeBulk(long paramLong, BulkRequest paramBulkRequest) {
                // TODO Auto-generated method stub
            }
            // 执行出错时执行
            public void afterBulk(long paramLong, BulkRequest paramBulkRequest, Throwable paramThrowable) {
                // TODO Auto-generated method stub
            }
            public void afterBulk(long paramLong, BulkRequest paramBulkRequest, BulkResponse paramBulkResponse) {
                // TODO Auto-generated method stub
            }
        }).setBulkActions(10000) //1w次请求执行一次bulk
                // 1gb的数据刷新一次bulk
                .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
                // 固定5s必须刷新一次
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                // 并发请求数量, 0不并发, 1并发允许执行
                .setConcurrentRequests(1)
                .build();

        // 添加单次请求
        bulkProcessor.add(new IndexRequest("cashcorp", "employee", "1"));
        bulkProcessor.add(new DeleteRequest("cashcorp", "employee", "2"));
        // 关闭
        bulkProcessor.awaitClose(10, TimeUnit.MINUTES);
        // 或者
        bulkProcessor.close();
    }



}
