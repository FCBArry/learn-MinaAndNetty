package netty.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.common.MyMessage;
import netty.common.UnixTime;

/**
 * @author 科兴第一盖伦
 * @version 2019/1/8
 */
public class TimeServerHandler extends SimpleChannelInboundHandler<MyMessage> {
//    @Override
//    public void channelActive(final ChannelHandlerContext ctx) { // (1)
////        final ByteBuf time = ctx.alloc().buffer(4); // (2)
////        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
//
//        final ChannelFuture f = ctx.writeAndFlush(new UnixTime()); // (3)
//        f.addListener(ChannelFutureListener.CLOSE);
////        f.addListener(new ChannelFutureListener() {
////            @Override
////            public void operationComplete(ChannelFuture future) {
////                assert f == future;
////                ctx.close();
////            }
////        }); // (4)
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        cause.printStackTrace();
//        ctx.close();
//    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage packet) throws Exception
    {
        System.out.println(packet.toString());
    }
}
