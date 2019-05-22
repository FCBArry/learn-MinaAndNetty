package mina.command.cmd.c2c;

import mina.command.AbstractMiniCmd;

/**
 * 聊天消息c2c
 *
 * @author 科兴第一盖伦
 * @version 2018/12/05
 */
public class ChatCmd extends AbstractMiniCmd
{
    public static final String NAME = "ChatCmd";

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
