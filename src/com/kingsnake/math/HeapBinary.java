package com.kingsnake.math;

import java.util.List;

import com.kingsnake.gl.FPSCounter;



public class HeapBinary {
	
	static int counterOperates = 0;
    static public FPSCounter fps = new FPSCounter();;
	
	HeapBinary ()
	{
	}
	
	static int parent (int i)
	{
		return i >> 1;
	}

	static int left (int i)
	{
		return i << 1;
	}
	
	static int right(int i)
	{
		return (i << 1) + 1;
	}
	
	static void heapify(List<AdjListPointers> a, int i)
	{
		int l = left(i);
		int r = right(i);
		int smallest;
		AdjListPointers temp, temp2;
		int size = a.size(); 
		++counterOperates;		
		
		if(l < size && a.get(l).myVert.d < a.get(i).myVert.d)
			smallest = l;
		else
			smallest = i;
		
		if(r < size && a.get(r).myVert.d < a.get(smallest).myVert.d)
			smallest = r;
		
		if(smallest != i)
		{
			//change a[i] and a[largest] and change vert pos in adjList for current vertexes
			temp = a.get(i);
			temp.myVert.index = (short) smallest;		
			temp2 = a.get(smallest);
			temp2.myVert.index = (short) i;
			a.set(i, temp2);			
			a.set(smallest, temp);				
			heapify(a , smallest);
		}
	}
	
	public static void  buildHeap(List<AdjListPointers> a)
	{
		int size = a.size() / 2 - 1;
		
		for(int i = size; i >= 0; --i)
			heapify(a, i);
	}
	
	public static AdjListPointers extractMin(List<AdjListPointers> a)
	{
		int size = a.size();
		
		if(size < 1)
			return null;
				
		AdjListPointers  min = a.get(0);
		AdjListPointers  temp = a.get(size - 1);
		temp.myVert.index = 0;
		a.set(0, temp);
		a.remove(size - 1);
		heapify (a, 0);
		return min;
	}
	
	public static void decreaseKey(List<AdjListPointers> a, int i, int dist)
	{//vertex pops up to it place in heap by his new d (distance) 
		AdjListPointers temp2, temp = a.get(i);
		
		while(i > 0 && a.get(parent(i)).myVert.d > dist)
		{
			temp2 = a.get(parent(i));
			temp2.myVert.index = (short) i;
			a.set(i, temp2);
			i = parent(i);
			++counterOperates;		
		}
		
		temp.myVert.index = (short) i;
		a.set(i, temp);
	}	
}
