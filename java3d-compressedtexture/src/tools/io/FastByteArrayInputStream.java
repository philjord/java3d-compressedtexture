package tools.io;

import java.io.ByteArrayInputStream;

public class FastByteArrayInputStream extends ByteArrayInputStream
{
	public FastByteArrayInputStream(byte[] buf, int offset, int length)
	{
		super(buf, offset, length);
	}

	public FastByteArrayInputStream(byte[] buf)
	{
		super(buf);
	}

	public byte[] getBuf()
	{
		return buf;
	}

}
