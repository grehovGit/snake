package com.kingsnake.math;

public class VertsPointers {
	
	int d;
	VertsPointers parent;
	public short indexByBits;
	short index;
	short weight;
	
	VertsPointers(short index, short indexByBits)
	{
		d = LabOrientDejkstra.LAB_ORIENT_START_MAX_WEIGHT;
		parent = null;
		this.indexByBits = indexByBits;
		this.index = index;			//use to find it place after heapify
	}	
}
