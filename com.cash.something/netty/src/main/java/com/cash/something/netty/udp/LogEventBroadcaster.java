package com.cash.something.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

/**
 * 打开一个文件,通过UDP将每一行作为一个消息广播到一个指定的端口
 * 广播
 * author cash
 * create 2019-03-31-16:54
 **/
public class LogEventBroadcaster {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final File file;

    public LogEventBroadcaster(InetSocketAddress address,File file){
        group=new NioEventLoopGroup();
        bootstrap=new Bootstrap();
        bootstrap.group(group).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new LogEventEncoder(address));
        this.file=file;
    }

    public void run() throws  Exception{
        //绑定channel
        Channel ch=bootstrap.bind(0).sync().channel();
        long pointer=0;
        for(;;){
            long len=file.length();
            if(len<pointer){
                pointer=len;
            }else if(len>pointer){
                RandomAccessFile raf=new RandomAccessFile(file,"r");
                raf.seek(pointer);
                String line;
                while((line=raf.readLine())!=null){
                    ch.writeAndFlush(new LogEvent(null,line,file.getAbsolutePath(),-1));
                }
                pointer=raf.getFilePointer();
                raf.close();
            }
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                Thread.interrupted();
                break;
            }
        }

    }

    public void stop(){
        group.shutdownGracefully();
    }


    public static void main(String[] args) throws  Exception {

        int port=8999;
        String path=LogEventBroadcaster.class.getProtectionDomain().getCodeSource().getLocation().toURI()+"udpTest.txt";
        path=path.contains("file:")?path.substring(5):path;
        File file=new File(path);
        LogEventBroadcaster broadcaster=
                //255.255.255.255 广播地址
                new LogEventBroadcaster(new InetSocketAddress("255.255.255.255",port),file);
        try{
            broadcaster.run();
        }finally {
            broadcaster.stop();
        }

    }
}
