package mina.handler;

import mina.client.ChatClient;
import mina.command.AbstractMiniCmd;
import mina.utils.Log;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 自定义客户端消息处理handler
 *
 * @author 科兴第一盖伦
 * @version 2018/12/04
 */
public class ClientHandler extends IoHandlerAdapter
{
    private ChatClient client;

    public ClientHandler(ChatClient chatClient)
    {
        this.client = chatClient;
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception
    {
        super.sessionCreated(session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception
    {
        super.sessionOpened(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception
    {
        super.sessionClosed(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception
    {
        super.sessionIdle(session, status);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception
    {
        super.exceptionCaught(session, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception
    {
        super.messageReceived(session, message);

        if (message instanceof AbstractMiniCmd)
        {
            if (client != null)
            {
                client.getCmdQueue().put((AbstractMiniCmd) message);
            }
        }
        else
        {
            Log.info("message received wrong...");
        }

        Log.info("message received...");
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception
    {
        super.messageSent(session, message);
    }

    @Override
    public void inputClosed(IoSession session) throws Exception
    {
        super.inputClosed(session);
    }
}
