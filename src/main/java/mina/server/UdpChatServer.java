package mina.server;

import mina.codec.MiniCodecFactory;
import mina.handler.ServerHandler;
import mina.handler.ServerStateMachineHandler;
import mina.utils.Log;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.statemachine.StateMachine;
import org.apache.mina.statemachine.StateMachineFactory;
import org.apache.mina.statemachine.StateMachineProxyBuilder;
import org.apache.mina.statemachine.annotation.IoHandlerTransition;
import org.apache.mina.statemachine.context.IoSessionStateContextLookup;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author 科兴第一盖伦
 * @version 2018/12/06
 */
public class UdpChatServer
{
    private int port;

    private int threadPoolSize;

    private int handlerType;

    private NioDatagramAcceptor acceptor;

    UdpChatServer(int port, int threadPoolSize, int handlerType)
    {
        this.port = port;
        this.threadPoolSize = threadPoolSize;
        this.handlerType = handlerType;

        initialize();
    }

    private void initialize()
    {
        acceptor = new NioDatagramAcceptor();
        IoHandler handler;
        if (handlerType == 1)
            handler = new ServerHandler();
        else
            handler = createStateMachineIoHandler();
        acceptor.setHandler(handler);
        DatagramSessionConfig config = acceptor.getSessionConfig();
        config.setReceiveBufferSize(1024 * 32);
        config.setSendBufferSize(1024 * 32);
        config.setReuseAddress(true);
        config.setBothIdleTime(60 * 8);

        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        // acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MiniCodecFactory()));
        acceptor.getFilterChain().addLast("executor", new ExecutorFilter(threadPoolSize));
    }

    private static IoHandler createStateMachineIoHandler()
    {
        StateMachine sm = StateMachineFactory.getInstance(
                IoHandlerTransition.class).create(ServerStateMachineHandler.NOT_CONNECTED, new ServerStateMachineHandler());

        return new StateMachineProxyBuilder().setStateContextLookup(
                new IoSessionStateContextLookup(ServerStateMachineHandler.TetrisServerContext::new)).create(IoHandler.class, sm);
    }

    void start() throws IOException
    {
        acceptor.bind(new InetSocketAddress(port));
        Log.info("ChatServer Listening at port:[{}]", port);
    }

    public void stop()
    {
        acceptor.dispose();
    }
}
