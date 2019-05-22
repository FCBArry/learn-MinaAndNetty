package netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import netty.common.MyMessage;

import java.util.List;

/**
 * @author 科兴第一盖伦
 * @version 2019/5/8
 */
public class MyEncoder extends MessageToMessageEncoder<MyMessage>
{
    @Override
    protected void encode(ChannelHandlerContext ctx, MyMessage pkg, List<Object> out) throws Exception
    {
        try
        {
            int dataLength = pkg.getLen();
            if (dataLength < 0)
                return;

            ByteBuf buffer = ctx.alloc().buffer(dataLength);
            pkg.writeTo(buffer);
            out.add(buffer);
        }
        catch (Exception ex)
        {
            ctx.channel().close();
        }
    }
}
