package mina.client;


import mina.codec.MiniCodecFactory;
import mina.command.AbstractMiniCmd;
import mina.handler.ClientHandler;
import mina.handler.ClientProcessHandler;
import mina.utils.Log;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 客户端
 *
 * @author 科兴第一盖伦
 * @version 2018/12/04
 */
public class ChatClient implements Runnable
{
    private static final long CONNECT_TIMEOUT = 30 * 1000L;

    private String ip;

    private int port;

    private SocketConnector connector;

    // udp client
    // private NioDatagramConnector connector;

    private IoSession session;

    private ClientProcessHandler clientProcessHandler;

    private BlockingQueue<AbstractMiniCmd> cmdQueue = new LinkedBlockingQueue<>();

    public ChatClient(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
        this.clientProcessHandler = new ClientProcessHandler();

        initialize();
    }

    private void initialize()
    {
        // udp client
        // connector = new NioDatagramConnector();

        connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        // acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MiniCodecFactory()));
        ClientHandler clientHandler = new ClientHandler(this);
        connector.setHandler(clientHandler);
    }

    void connect()
    {
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress(ip, port));
        connectFuture.awaitUninterruptibly(CONNECT_TIMEOUT);

        try
        {
            session = connectFuture.getSession();
        }
        catch (RuntimeIoException e)
        {
            Log.error("ChatClient connect error", e);
        }
    }

    public void disconnect()
    {
        if (session != null)
        {
            session.closeOnFlush();
            session = null;
        }
    }

    public IoSession getSession()
    {
        return this.session;
    }

    public void send(AbstractMiniCmd command)
    {
        if (session != null)
        {
            session.write(command);
        }
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
            if (cmdQueue.isEmpty())
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            AbstractMiniCmd cmd = cmdQueue.poll();
            if (cmd == null)
                continue;

            clientProcessHandler.process(session, cmd);
        }
    }
}
