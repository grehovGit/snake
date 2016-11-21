package com.kingsnake.math;

public class HeapDary {
	
	int dAry;
	
	HeapDary(int d)
	{
		dAry = d;
	}
	
	int parent(int i)
	{
		return i / dAry;
	}
	
	int left(int i)
	{
		return dAry * i;
	}
	

}
