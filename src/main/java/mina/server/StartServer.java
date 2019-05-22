package mina.server;

import mina.utils.Log;

/**
 * 服务器启动类
 *
 * @author 科兴第一盖伦
 * @version 2018/12/03
 */
public class StartServer
{
    // default port
    private static final int DEFAULT_PORT = 8023;

    // default thread pool size
    private static final int DEFAULT_THREADPOOL_SIZE = 2;

    // default handler type
    private static final int DEFAULT_HANDLER_TYPE = 1;

    public static void main(String[] args)
    {
        boolean result = true;
        try
        {
            Log.info("----------ChatServer start begin----------");
            ChatServer chatServer = new ChatServer(DEFAULT_PORT, DEFAULT_THREADPOOL_SIZE, DEFAULT_HANDLER_TYPE);
            chatServer.start();
            Log.info("----------ChatServer start successfully----------");
        }
        catch (Exception e)
        {
            result = false;
            Log.error("----------ChatServer start failed----------", e);
        }
        finally
        {
            if (result)
            {
                Log.info("----------ChatServer start end----------");
                Log.info("----------happy chat----------");
            }
        }
    }
}
