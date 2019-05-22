package mina.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MiniCodecFactory implements ProtocolCodecFactory
{
	private ProtocolEncoder encoder;

	private ProtocolDecoder decoder;

	public MiniCodecFactory()
	{
		encoder = new CommandEncoder();
		decoder = new CommandDecoder();
	}

	public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception
	{
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception
	{
		return encoder;
	}
}
