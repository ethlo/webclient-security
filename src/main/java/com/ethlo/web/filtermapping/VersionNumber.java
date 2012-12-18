package com.ethlo.web.filtermapping;

/**
 * 
 * @author Morten Haraldsen
 */
public class VersionNumber implements Comparable<VersionNumber>
{
	public final static VersionNumber UNDETERMINED = new VersionNumber(0, 0);
	
	private int major;
	private int minor;
	
	public VersionNumber(int major, int minor)
	{
		this.major = major;
		this.minor = minor;
	}

	public int getMajor()
	{
		return major;
	}

	public int getMinor()
	{
		return minor;
	}
	
	public boolean isGreaterThan(int major, int minor)
	{
		return this.compareTo(new VersionNumber(major, minor)) == 1;
	}
	
	public boolean isLessThan(int major, int minor)
	{
		return this.compareTo(new VersionNumber(major, minor)) == -1;
	}
	
	public boolean isSame(int major, int minor)
	{
		return this.equals(new VersionNumber(major, minor));
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + major;
		result = prime * result + minor;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		VersionNumber other = (VersionNumber) obj;
		if (major != other.major)
		{
			return false;
		}
		if (minor != other.minor)
		{
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(VersionNumber b)
	{
		if (this.major > b.major)
		{
			return 1;
		}
		else
		{
			if (this.minor == b.minor)
			{
				return 0;
			}
			else if (this.minor > b.minor)
			{
				return 1;
			}
			return -1;
		}
	}
}
