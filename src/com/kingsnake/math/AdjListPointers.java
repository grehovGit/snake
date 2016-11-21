package com.kingsnake.math;

public class AdjListPointers {
	
	VertsPointers myVert;
	VertsPointers [] myNeighbs;
	short[] myNeighbsLocalD;
	
	AdjListPointers(VertsPointers myVert, VertsPointers[] myNeighbs)
	{
		this.myVert = myVert;
		this.myNeighbs = myNeighbs;
		this.myNeighbsLocalD = new short [8];
	}

}
