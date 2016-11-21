/**
 * 
 */
package com.kingsnake.math;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author Grehov
 *
 */
public class MyMathTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
	
	/*
	 * Testing strategy:
	 * 
	 * Partitions:
	 * verts.size: =1, >1
	 * verts.item coord x <0, =0, >0, MIN, MAX
	 * verts.item coord y <0, =0, >0, MIN, MAX
	 * scale: =0, =0.5, =1, >1, MAX
	 * 
	 * #return vert.size
     * verts.size: =1, >1
     * verts.item coord x <0, =0, >0, MIN, MAX
	 * verts.item coord y <0, =0, >0
	 */
	
	// Test cases:
	// [0,0] [0] -> [0,0]
	// [[-1,-1] [1,1]] [0.5] -> [[-0.5,-0.5][0.5,0.5]]
	// [[MIN, MIN][MAX, MAX]] [MAX] -> throws ArithmeticException
	
    // Covers verts.size =1;  verts.item x =0, verts.item y =0; scale =0;
    // scaled verts.size =1, verts.item x =0, verts.item y =0
    @Test
    public void testScaleVerticesSingleZeroVert() {      
        assertTrue("expected [0,0]", Arrays.equals(MyMath.scaleVertices(new float[] {0f,  0f}, 0), new float[] {0f, 0f}));
    }	
    
    // Covers verts.size >1;  verts.item x <0, >0, verts.item y <0, >0; scale =0.5
    // scaled verts.size >1, scaled verts.item x <0, >0, verts.item y <0, >0;
    @Test
    public void testScaleVerticesTwoVertsNegativePositive() {   
    	float [] res = MyMath.scaleVertices(new float[] {-1f,  -1f, 1f, 1f}, .5f);
        assertTrue("expected  [[-0.5,-0.5][0.5,0.5]]", Arrays.equals(new float[] {-.5f, -.5f, .5f, .5f}, res));
    }
    
    // verts.item x =MIN, =MAX;  y =MIN, =MAX; scale = MAX
    // scaled verts.size >1, scaled verts.item x <0, >0, verts.item y <0, >0;
    @Test(expected=ArithmeticException.class)
    public void testScaleVerticesTwoVertsMinMax() {       
        MyMath.scaleVertices(new float[] {-Float.MAX_VALUE, -Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE}, Float.MAX_VALUE);
    }
    
    
	/*
	 * getTwoStagedHurtBittenScale
	 * Testing strategy:
	 * 
	 * Partitions:
	 * @param curTime 											=0, >0, MAX, <endTime - exPeriod, >endTime - exPeriod, =endTime - exPeriod 
	 * @param endTime 											=0, >0, MAX, >curTime, <curTime, =curTime
	 * @param exPeriod excited period before endTime 			=0, >0, MAX
	 * @param beatFreq - exactly beaten frequency 				=0, >0, MAX
	 * @param normalFreq - beaten frequency during normal period=2*beatFeq, >2*beatFeq, MAX
	 * @param exFreq - beaten frequency during excited period 	=2*beatFeq, >2*beatFeq, MAX
	 * @param ampDelta max delta from original scale (1)		=0, >0, <0, MIN, MAX
	 * 
	 * #return scale =0, >0, =original scale, =original scale + ampDelta, =original scale - ampDelta 

	 */
	
	// Test cases:
	// 0, 0, 0, 0, 0, 0, 0 -> 1
	// 1, 5, 2, 1, 2, 1 -> curTime
	// [[MIN, MIN][MAX, MAX]] [MAX] -> throws ArithmeticException

}
