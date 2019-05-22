package netty.common;

import com.google.protobuf.GeneratedMessage.Builder;
import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;

/**
 * @author 科兴第一盖伦
 * @version 2019/5/22
 */
public class MyMessage
{
    private static final short HDR_SIZE = 16;

    private short checksum;

    private short code;

    private long userId;

    private byte[] bodyData = null;

    private Message pbMessage;

    public MyMessage(short code)
    {
        this.code = code;
    }

    public short getCode()
    {
        return code;
    }

    public void setCode(short code)
    {
        this.code = code;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    public void writeTo(ByteBuf buffer)
    {
        int length = getLen();
        buffer.writeInt(length);
        buffer.writeShort((short) 1);
        buffer.writeShort(code);
        buffer.writeLong(userId);

        if (bodyData != null)
            buffer.writeBytes(bodyData);

        int oldPosition = buffer.writerIndex();
        short sum = calcChecksum(buffer);
        buffer.writerIndex(4);
        buffer.writeShort(sum);
        buffer.writerIndex(oldPosition);
    }

    public short calcChecksum(ByteBuf buffer)
    {
        int val = 0x77;
        int i = 6;
        int size = getLen();
        while (i < size)
        {
            val += (buffer.getByte(i) & 0xFF);
            i++;
        }

        return (short) (val & 0x7F7F);
    }

    public int getLen()
    {
        if (bodyData != null)
        {
            int bodyLen = bodyData.length;
            return HDR_SIZE + bodyLen;
        }

        return HDR_SIZE;
    }

    public void setBuilder(Builder<?> builder)
    {
        this.pbMessage = builder.build();
    }

    public MyMessage build(ByteBuf in)
    {
        int length = in.readInt();
        this.checksum = in.readShort();
        this.code = in.readShort();
        this.userId = in.readLong();
        int bodyLen = length - HDR_SIZE;
        if (bodyLen > 0)
        {
            this.bodyData = new byte[bodyLen];
            in.readBytes(this.bodyData, 0, bodyLen);
            short getCS = calcChecksum(in);
            if (this.checksum != getCS)
                return null;
        }

        return this;
    }
}
