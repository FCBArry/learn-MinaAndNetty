package mina.handler;

import mina.command.AbstractMiniCmd;
import mina.command.cmd.c2c.ChatCmd;
import mina.command.cmd.s2c.ReplyCmd;
import mina.utils.Log;
import org.apache.mina.core.session.IoSession;

/**
 * @author 科兴第一盖伦
 * @version 2018/12/05
 */
public class ClientProcessHandler implements IProcessHandler
{
    @Override
    public void process(IoSession session, AbstractMiniCmd abstractMiniCmd)
    {
        if (abstractMiniCmd instanceof ReplyCmd)
        {
            Log.info("received message from server:[{}]", ((ReplyCmd) abstractMiniCmd).getRep());
        }
        else if (abstractMiniCmd instanceof ChatCmd)
        {
            Log.info("received message from client:[{}]", ((ChatCmd) abstractMiniCmd).getMsg());
        }
    }
}
