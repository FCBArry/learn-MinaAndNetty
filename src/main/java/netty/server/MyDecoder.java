package netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import netty.common.MyMessage;

import java.util.List;

/**
 * @author 科兴第一盖伦
 * @version 2019/5/8
 */
public class MyDecoder extends ByteToMessageDecoder
{

    @Override
    protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        Object decoded = decode(ctx, in);
        if (decoded != null)
            out.add(decoded);
    }

    private Object decode(ChannelHandlerContext ctx, ByteBuf in)
    {
        if (in.readableBytes() < 4)
            return null;

        in.slice();
        int packetLength = in.getInt(0);
        if (in.readableBytes() < packetLength - 4)
        {
            in.resetReaderIndex();
            return null;
        }

        int code = in.getShort(6);
        MyMessage message = new MyMessage((short) code);
        return message.build(in);
    }
}
