package mina.command;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractMiniCmd implements ICmd
{
	public abstract String getName();

	public abstract byte[] bodyToBytes() throws Exception;

	public abstract void bodyFromBytes(byte[] bytes) throws Exception;

	public byte[] toBytes() throws Exception
	{
		byte[] body = bodyToBytes();
		int commandNameLength = 23;
		int len = commandNameLength + body.length;
		byte[] bytes = new byte[len];
		String name = StringUtils.rightPad(getName(), commandNameLength, ' ');
		name = name.substring(0, commandNameLength);

		System.arraycopy(name.getBytes(), 0, bytes, 0, commandNameLength);
		System.arraycopy(body, 0, bytes, commandNameLength, body.length);
		return bytes;
	}

	public String toString()
	{
		return "Command: " + getName();
	}
}
