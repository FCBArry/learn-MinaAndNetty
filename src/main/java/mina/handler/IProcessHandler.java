package mina.handler;

import mina.command.AbstractMiniCmd;
import org.apache.mina.core.session.IoSession;

/**
 * 消息处理handler interface
 *
 * @author 科兴第一盖伦
 * @version 2018/12/05
 */
public interface IProcessHandler
{
    void process(IoSession session, AbstractMiniCmd abstractMiniCmd);
}
