package com.zhentao.draw.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/***
 * 处理服务器端通道
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        //Discard the received data silently
        //ByteBuf是一个引用计数对象实现ReferenceCounted，他就是在有对象引用的时候计数+1，无的时候计数-1，当为0对象释放内存
        ByteBuf in=(ByteBuf)msg;
        try {
            while(in.isReadable()){
                System.out.println((char)in.readByte());
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
