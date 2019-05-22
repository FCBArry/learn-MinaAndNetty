package mina.handler;

import mina.command.AbstractMiniCmd;
import mina.server.ClientConnection;
import mina.utils.Log;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义服务器消息处理handler
 *
 * @author 科兴第一盖伦
 * @version 2018/12/04
 */
public class ServerHandler extends IoHandlerAdapter
{
    private ScheduledThreadPoolExecutor executorService;

    public static Map<String, ClientConnection> clients = new ConcurrentHashMap<>();

    public ServerHandler()
    {
        executorService = new ScheduledThreadPoolExecutor(2);
        executorService.setRemoveOnCancelPolicy(true);
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

        ClientConnection clientConnection = new ClientConnection(session);
        clients.put(clientConnection.getKey(), clientConnection);
        session.setAttribute("key", clientConnection.getKey());
        executorService.scheduleAtFixedRate(clientConnection,
                0, 1000, TimeUnit.MILLISECONDS);
        Log.info("message sessionOpened... add:[{}]", clientConnection.getKey());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception
    {
        super.sessionClosed(session);

        String key = (String) session.getAttribute("key");
        clients.remove(key);
        Log.info("message sessionClosed... remove:[{}]", key);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception
    {
        super.sessionIdle(session, status);

        session.closeNow();
        Log.info("message sessionIdle...");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception
    {
        super.exceptionCaught(session, cause);

        session.closeNow();
        Log.info("message exceptionCaught...");
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception
    {
        super.messageReceived(session, message);

        if (message instanceof AbstractMiniCmd)
        {
            String key = (String) session.getAttribute("key");
            ClientConnection clientConnection = clients.get(key);
            if (clientConnection != null)
            {
                clientConnection.getCmdQueue().put((AbstractMiniCmd) message);
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
