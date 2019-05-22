package mina.server;

import mina.command.AbstractMiniCmd;
import mina.handler.ServerHandler;
import mina.handler.ServerProcessHandler;
import org.apache.mina.core.session.IoSession;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 客户端连接
 *
 * @author 科兴第一盖伦
 * @version 2018/12/04
 */
public class ClientConnection implements Runnable
{
    private String key;

    private IoSession ioSession;

    private ServerProcessHandler serverProcessHandler;

    private BlockingQueue<AbstractMiniCmd> cmdQueue = new LinkedBlockingQueue<>();

    public ClientConnection(IoSession session)
    {
        this.ioSession = session;
        this.serverProcessHandler = new ServerProcessHandler();
        String ip = ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
        int port = ((InetSocketAddress) session.getRemoteAddress()).getPort();
        this.key = ip + ':' +port;
    }

    public String getKey()
    {
        return key;
    }

    public IoSession getIoSession()
    {
        return ioSession;
    }

    public void send(AbstractMiniCmd command)
    {
        if (ioSession != null)
        {
            ioSession.write(command);
        }
    }

    public ServerProcessHandler getServerProcessHandler()
    {
        return serverProcessHandler;
    }

    public BlockingQueue<AbstractMiniCmd> getCmdQueue()
    {
        return cmdQueue;
    }

    @Override
    public void run()
    {
        while (true)
        {
            AbstractMiniCmd cmd = cmdQueue.poll();
            if (cmd == null)
                break;

            serverProcessHandler.process(ioSession, cmd, ServerHandler.clients);
        }
    }
}
