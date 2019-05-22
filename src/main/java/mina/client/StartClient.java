package mina.client;

import mina.command.AbstractMiniCmd;
import mina.command.cmd.c2c.ChatCmd;
import mina.command.cmd.c2s.SendMsgCmd;
import mina.utils.Log;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * 客户端启动类
 *
 * @author 科兴第一盖伦
 * @version 2018/12/04
 */
public class StartClient
{
    // default ip
    private static final String DEFAULT_IP = "127.0.0.1";

    // default port
    private static final int DEFAULT_PORT = 8023;

    public static void main(String[] args)
    {
        boolean result = true;
        try
        {
            Log.info("----------ChatClient start begin----------");
            ChatClient chatClient = new ChatClient(DEFAULT_IP, DEFAULT_PORT);
            chatClient.connect();
            Thread thread = new Thread(chatClient, "my client");
            thread.start();
            Log.info("----------ChatClient start successfully----------");

            AbstractMiniCmd cmd = null;
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine())
            {
                String strLine = scanner.nextLine();
                String[] commonds = StringUtils.split(strLine, ' ');
                if (Integer.parseInt(commonds[0]) == 1)
                {
                    SendMsgCmd sendMsgCmd = new SendMsgCmd();
                    sendMsgCmd.setMsg(commonds[1]);
                    cmd = sendMsgCmd;
                }
                else if (Integer.parseInt(commonds[0]) == 2)
                {
                    ChatCmd chatcmd = new ChatCmd();
                    chatcmd.setMsg(commonds[1]);
                    cmd = chatcmd;
                }
                else
                {
                    Log.info("....................................");
                }

                if (cmd != null)
                    chatClient.send(cmd);
            }
        }
        catch (Exception e)
        {
            result = false;
            Log.error("----------ChatClient start failed----------", e);
        }
        finally
        {
            if (result)
            {
                Log.info("----------ChatClient start end----------");
                Log.info("----------happy chat----------");
            }
        }
    }
}
