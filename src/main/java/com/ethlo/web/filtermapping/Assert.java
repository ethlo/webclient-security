package com.ethlo.web.filtermapping;

public class Assert
{
	public static void state(boolean result, String msg)
	{
		if (! result)
		{
			throw new IllegalArgumentException(msg);
		}
	}

	public static void isTrue(boolean b, String msg)
	{
		state(b, msg);
	}
}
