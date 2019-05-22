package mina.command.cmd.s2c;

import mina.command.AbstractMiniCmd;

/**
 * 回复消息s2c
 *
 * @author 科兴第一盖伦
 * @version 2018/12/04
 */
public class ReplyCmd extends AbstractMiniCmd
{
    public static final String NAME = "ReplyCmd";

    private String rep;

    public String getRep()
    {
        return rep;
    }

    public void setRep(String rep)
    {
        this.rep = rep;
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public byte[] bodyToBytes() throws Exception
    {
        return this.rep.getBytes();
    }

    @Override
    public void bodyFromBytes(byte[] bytes) throws Exception
    {
        this.rep = new String(bytes);
    }
}
