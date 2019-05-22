package mina.codec;

import mina.command.AbstractMiniCmd;
import mina.command.MiniCmdFactory;
import mina.utils.Log;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class CommandDecoder extends CumulativeProtocolDecoder
{
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception
	{
		if (in.prefixedDataAvailable(4, 4192))
		{
			int length = in.getInt();
			byte[] bytes = new byte[length];
			in.get(bytes);
			int commandNameLength = 23;
			byte[] cmdNameBytes = new byte[commandNameLength];
			System.arraycopy(bytes, 0, cmdNameBytes, 0, commandNameLength);
			String cmdName = StringUtils.trim(new String(cmdNameBytes));
			AbstractMiniCmd command = MiniCmdFactory.newCommand(cmdName);
			if (command != null)
			{
				byte[] cmdBodyBytes = new byte[length - commandNameLength];
				System.arraycopy(bytes, commandNameLength, cmdBodyBytes, 0,
						length - commandNameLength);
				command.bodyFromBytes(cmdBodyBytes);
				out.write(command);
				Log.info("Command:[{}] received", cmdName);
			}
			else
			{
				Log.info("Unknown command:[{}] received", cmdName);
			}

			return true;
		}
		else
		{
			return false;
		}
	}
}
