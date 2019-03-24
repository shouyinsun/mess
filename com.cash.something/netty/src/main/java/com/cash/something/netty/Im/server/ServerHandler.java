package com.cash.something.netty.im.server;

import com.cash.something.netty.im.protocal.Packet;
import com.cash.something.netty.im.protocal.PacketCodeC;
import com.cash.something.netty.im.protocal.request.LoginRequestPacket;
import com.cash.something.netty.im.protocal.request.MessageRequestPacket;
import com.cash.something.netty.im.protocal.response.LoginResponsePacket;
import com.cash.something.netty.im.protocal.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * author cash
 * create 2019-01-15-23:07
 **/
public class ServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        System.out.println(new Date()+":客户端开始登录。。。");
        ByteBuf byteBuf= (ByteBuf) msg;
        Packet packet= PacketCodeC.INSTANCE.decode(byteBuf);
        if(packet instanceof LoginRequestPacket){//login request
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket=new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            if(valid(loginRequestPacket)){
                loginResponsePacket.setSuccess(true);
                loginResponsePacket.setReason("登录成功");
                System.out.println(new Date()+":登录成功");
            }else{
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("登录失败");
                System.out.println(new Date()+":登录失败");
            }

            //相应
            ByteBuf respByteBuf=PacketCodeC.INSTANCE.encode(ctx.alloc(),loginResponsePacket);
            ctx.channel().writeAndFlush(respByteBuf);

        }else if(packet instanceof MessageRequestPacket){
            MessageRequestPacket messageRequestPacket= (MessageRequestPacket) packet;
            System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket=new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
            ByteBuf responseByteBuf=PacketCodeC.INSTANCE.encode(ctx.alloc(),messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);


        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
