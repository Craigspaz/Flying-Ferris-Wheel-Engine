package com.graphics.world.util;

import java.util.ArrayList;

public class StringToInt
{
	private String name;
	private int address;
	
	public StringToInt(String name, int address)
	{
		this.name = name;
		this.address = address;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getAddress()
	{
		return address;
	}
	
	public static int getAddressFromString(String key, ArrayList<StringToInt> list)
	{
		for(StringToInt i : list)
		{
			if(i.getName().equals(key))
			{
				return i.getAddress();
			}
		}
		return -1;
	}
}
