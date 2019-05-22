package mina.handler;

import mina.command.AbstractMiniCmd;
import mina.command.cmd.c2c.ChatCmd;
import mina.command.cmd.c2s.SendMsgCmd;
import mina.command.cmd.s2c.ReplyCmd;
import mina.server.ClientConnection;
import mina.utils.Log;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;

import java.util.Map;

/**
 * 消息处理handler
 *
 * @author 科兴第一盖伦
 * @version 2018/12/04
 */
public class ServerProcessHandler implements IProcessHandler
{
    @Override
    public void process(IoSession session, AbstractMiniCmd abstractMiniCmd)
    {

    }

    public void process(IoSession session, AbstractMiniCmd miniCmd, Map<String, ClientConnection> clients)
    {
        if (miniCmd instanceof SendMsgCmd)
        {
            Log.info("server received message:[{}]", ((SendMsgCmd) miniCmd).getMsg());

            ReplyCmd replyCmd = new ReplyCmd();
            replyCmd.setRep("世界聚焦于你7777777");
            session.write(replyCmd);
        }
        else if (miniCmd instanceof ChatCmd)
        {
            String[] msg = StringUtils.split(((ChatCmd) miniCmd).getMsg(), '-');
            ClientConnection clientConnection = clients.get(msg[0]);
            if (clientConnection != null)
            {
                ChatCmd chatcmd = new ChatCmd();
                chatcmd.setMsg(msg[1]);
                clientConnection.send(chatcmd);
            }
        }
        else
        {
            Log.info("....................................");
        }
    }
}
