package mina.command.cmd.c2s;

import mina.command.AbstractMiniCmd;

/**
 * 发送消息c2s
 *
 * @author 科兴第一盖伦
 * @version 2018/12/04
 */
public class SendMsgCmd extends AbstractMiniCmd
{
    public static final String NAME = "SendMsgCmd";

    private String msg;

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public byte[] bodyToBytes() throws Exception
    {
        return this.msg.getBytes();
    }

    @Override
    public void bodyFromBytes(byte[] bytes) throws Exception
    {
        this.msg = new String(bytes);
    }
}
