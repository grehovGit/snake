package com.kingsnake.gl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class TextGL {
	
	MySpriteBatcher sBatcher;
	float[] nWidth = {1, 0.73f, 1, 0.73f, 0.97f, 0.73f, 0.9f, 0.87f, 0.8f, 0.9f}; //h x w = 2.2 x 1

	
	/*static float FONT_HEIGHT_SYMBOLS = 1f;		//185px
	static int FONT_INDEX_SYMBOLS = 0;		
	static float FONT_HEIGHT_NUMBERS = 0.98f;	//182px
	static int FONT_INDEX_NUMBERS = 15;	
	static float FONT_HEIGHT_CAPITAL_LETTER = 0.92f;		//170px
	static int FONT_INDEX_CAPITAL_LETTER = 31;	
	static float FONT_HEIGHT_LETTER = 0.81f;		//150px
	static int FONT_INDEX_LETTER = 63;	
	static int FONT_INDEX_FINISH = 92;
	
	static float GAP_WIDTH = 0.5f;
	
	//width 1.0 = 100px
	float[] nTextWidth = {0.9f, 1.05f, 1.25f, 1.15f, 1.7f, 1.4f, 0.65f, 0.95f, 1f, 1.15f, 1.3f, 0.85f, 0.95f, 0.8f, 0.9f,	//symbols
			1.05f, 0.8f, 1.1f, 0.8f, 1.1f, 0.85f, 1.03f, 0.95f, 0.92f, 1f, 0.45f, 0.43f, 0.87f, 0.9f, 0.93f, 0.67f,						//numbers
			1.27f, 1.33f, 1.15f, 1.35f, 1.5f, 1.05f, 1.05f, 1.35f, 1.25f, 0.8f, 0.85f, 1.25f, 1f, 1.55f, 1.4f, 1.55f,			//capital letters
			1.05f, 1.65f, 1.55f, 1.2f, 1.45f, 1.3f, 1.35f, 1.55f, 1.2f, 1.2f, 1.2f, 0.8f, 0.85f, 0.9f, 1.05f, 1.1f,				//capital letters
			0.45f, 1.1f, 1.1f, 1.25f, 1.35f, 1.15f, 1f, 1.2f, 1.3f, 0.8f, 0.85f, 1.25f, 1.1f, 1.5f, 1.25f, 1.35f,			//letters
			0.95f, 1.4f, 1.2f, 1f, 1.1f, 1.2f, 1.2f, 1.35f, 1.25f, 1.1f, 1.15f, 1f, 0.75f, 0.95f}; // 1 = 100px				//letters

	
	float koefHRatio = 2.2f; */
	
	static float FONT_HEIGHT_SYMBOLS = 2.89f;		//185px; 1 = 64px
	static int FONT_INDEX_SYMBOLS = 0;		
	static float FONT_HEIGHT_NUMBERS = 2.9f;	//182px
	static int FONT_INDEX_NUMBERS = 15;	
	static float FONT_HEIGHT_CAPITAL_LETTER = 2.66f;		//170px
	static int FONT_INDEX_CAPITAL_LETTER = 31;	
	static float FONT_HEIGHT_LETTER = 2.34f;		//150px
	static int FONT_INDEX_LETTER = 63;	
	static int FONT_INDEX_FINISH = 92;
	
	static float GAP_WIDTH = 0.6f;
	static float TAB_WIDTH = 2 * GAP_WIDTH;
	
	//constants for rectangle view
	static float RECT_BORDER_SIZE = 0.15f;
	static float DISTANCE_BETWEEN_LINES_KOEF = 0.9f;
	
	//width 1.0 = 64px
	float[] nTextWidth = {1.4f, 1.64f, 1.95f, 1.79f, 2.65f, 2.18f, 1.01f, 1.48f, 1.56f, 1.79f, 2.03f, 1.33f, 1.48f, 1.25f, 1.4f,	//symbols
			1.64f, 1.25f, 1.72f, 1.25f, 1.72f, 1.33f, 1.61f, 1.48f, 1.44f, 1.56f, 0.7f, 0.67f, 1.36f, 1.4f, 1.45f, 1.05f,						//numbers
			1.98f, 2.07f, 1.79f, 2.11f, 2.34f, 1.64f, 1.64f, 2.11f, 1.95f, 1.25f, 1.33f, 1.95f, 1.56f, 2.42f, 2.18f, 2.42f,			//capital letters
			1.64f, 2.57f, 2.42f, 1.87f, 2.26f, 2.03f, 2.11f, 2.42f, 1.87f, 1.87f, 1.87f, 1.25f, 1.33f, 1.4f, 1.64f, 1.72f,				//capital letters
			0.7f, 1.72f, 1.72f, 1.95f, 2.11f, 1.79f, 1.56f, 1.87f, 2.03f, 1.25f, 1.33f, 1.95f, 1.72f, 2.34f, 1.95f, 2.11f,			//letters
			1.48f, 2.18f, 1.87f, 1.56f, 1.72f, 1.87f, 1.87f, 2.11f, 1.95f, 1.72f, 1.79f, 1.56f, 1.17f, 1.48f}; // 1 = 100px				//letters

	
	//float koefHRatio = 2.2f; 
	
	
	public TextGL(MySpriteBatcher sBatcher)
	{
		this.sBatcher = sBatcher;
	}
	
	public void ViewNumber(TextureRegion[] numbers, String str, float x, float y)
	{//use the same Assets as game play
		int len = str.length();
		float charPos = 0;
		
		for (int i=0; i<len; i++)
		{
			int index = str.charAt(i) - 48;		//"48" - index of "0"
			if(index < 0 || index > 9)
				index = 0;
			
			sBatcher.draw( numbers[index], x + charPos, y, 0.9f * nWidth[index], 1.98f);
			charPos +=  0.82f * nWidth[index];
		}
	}
	
	public void ViewNumber(TextureRegion[] numbers, String str, float x, float y, float scale) //scale: 0 - 1
	{//use the same Assets as game play
		int len = str.length();
		float charPos = 0;
		
		for (int i=0; i<len; i++)
		{
			int index = str.charAt(i) - 48;		//"48" - index of "0"
			if(index < 0 || index > 9)
				index = 0;
			
			sBatcher.draw(numbers[index], x + charPos, y, 0.9f * nWidth[index] * scale, 1.98f * scale);
			charPos +=  0.82f * nWidth[index] * scale;
		}
	}
	
	public void beginBatch()
	{
		if(sBatcher!= null)
			sBatcher.begin();	
	}
	
	public void endBatch()
	{
		if(sBatcher != null)
			sBatcher.end();	
	}
	
	public void ViewText(TextureRegion[] text, String str, float x, float y, float scale)
	{//use the own assets
		int len = str.length();
		float charPos = 0;		            
		
		for (int i=0; i<len; i++)
		{
			int index = str.charAt(i) - 33;		//"33" - index of "!"
			if(index < 0 || index > FONT_INDEX_FINISH)
				continue;
			
			if(index > 0)
			{
				float fHeight = 1f;
				
				if(index < FONT_INDEX_NUMBERS)
					fHeight = FONT_HEIGHT_SYMBOLS;
				else if(index < FONT_INDEX_CAPITAL_LETTER)
					fHeight = FONT_HEIGHT_NUMBERS;
				else if(index < FONT_INDEX_LETTER)
					fHeight = FONT_HEIGHT_CAPITAL_LETTER;
				else
					fHeight = FONT_HEIGHT_LETTER;
				
				sBatcher.draw(text[index], x + charPos, y, scale * nTextWidth[index], scale * fHeight);			
				charPos += scale * 0.7 * nTextWidth[index];
			}
			else
				charPos +=  GAP_WIDTH;				
		}		
	}
	
	public void ViewTextMiddleAligned(TextureRegion[] text, String str, float x, float y, float scale)
	{//use the own assets
		int len = str.length();
		float charPos = 0;
		float strWidth = 0;
		
		
		for (int i=0; i<len; i++)
		{
			int index = str.charAt(i) - 33;		//"33" - index of "!"
			if(index < 0 || index > FONT_INDEX_FINISH)
				continue;
			
			if(index > 0 && i > 0)
				strWidth += scale * 0.7 * nTextWidth[index];
			else if(i > 0)
				strWidth +=  scale * GAP_WIDTH;					
				
		}
		
		x -= strWidth /2;		            
		
		for (int i=0; i<len; i++)
		{
			int index = str.charAt(i) - 33;		//"33" - index of "!"
			if(index < 0 || index > FONT_INDEX_FINISH)
				continue;
			
			if(index > 0)
			{
				float fHeight = 1f;
				
				if(index < FONT_INDEX_NUMBERS)
					fHeight = FONT_HEIGHT_SYMBOLS;
				else if(index < FONT_INDEX_CAPITAL_LETTER)
					fHeight = FONT_HEIGHT_NUMBERS;
				else if(index < FONT_INDEX_LETTER)
					fHeight = FONT_HEIGHT_CAPITAL_LETTER;
				else
					fHeight = FONT_HEIGHT_LETTER;
				
				sBatcher.draw(text[index], x + charPos, y, scale * nTextWidth[index], scale * fHeight);			
				charPos += scale * 0.7 * nTextWidth[index];
			}
			else
				charPos +=  scale * GAP_WIDTH;				
		}	
	}
	
	public void ViewTextMiddleAlignedAutoscaled(TextureRegion[] text, String str, float x, float y, float scale, float width)
	{//use the own assets
		int len = str.length();
		float charPos = 0;
		float strWidth = 0;
		
		
		for (int i=0; i<len; i++)
		{
			int index = str.charAt(i) - 32;		//"32" - index of " "
			if(index < 0 || index > FONT_INDEX_FINISH)
				continue;
			
			if(index > 0 && i > 0)
				strWidth += scale * 0.7 * nTextWidth[index-1];
			else if(i > 0)
				strWidth +=  scale * GAP_WIDTH;
			
				
		}
		
		if(strWidth > width)
		{
			scale *= (0.85f * width / strWidth);
			strWidth = 0.85f * width;
		}
		
		x -= strWidth /2;	            
		
		for (int i=0; i<len; i++)
		{
			int index = str.charAt(i) - 32;		//"32" - index of " "
			if(index < 0 || index > FONT_INDEX_FINISH)
				continue;
			
			if(index > 0)
			{
				float fHeight = 1f;
				
				if(index - 1 < FONT_INDEX_NUMBERS)
					fHeight = FONT_HEIGHT_SYMBOLS;
				else if(index - 1 < FONT_INDEX_CAPITAL_LETTER)
					fHeight = FONT_HEIGHT_NUMBERS;
				else if(index - 1 < FONT_INDEX_LETTER)
					fHeight = FONT_HEIGHT_CAPITAL_LETTER;
				else
					fHeight = FONT_HEIGHT_LETTER;
				
				sBatcher.draw(text[index-1], x + charPos, y, scale * nTextWidth[index-1], scale * fHeight);			
				charPos += scale * 0.7 * nTextWidth[index-1];
			}
			else
				charPos +=  scale * GAP_WIDTH;				
		}
	}
	
	public void ViewTextinRectangleRightAlign(TextureRegion[] text, String str, float x, float y,
			float rWidth, float rHeight, float tScale, boolean startTab)
	{//use the own assets
		int len = str.length();
		float xEnd = x + rWidth - RECT_BORDER_SIZE;
		int maxStrNumber = (int) ((rHeight - 2 * RECT_BORDER_SIZE) / (FONT_HEIGHT_NUMBERS * tScale));
		int lineIndex = 0;
		int indexChar = 0;
		
		boolean isEnd = false;
		boolean newStr = false;
		float wordWidth = 0;
		int wordSize = 0;
		
		float xStart = x + RECT_BORDER_SIZE + TAB_WIDTH * tScale;
		float yStart = y + (rHeight - RECT_BORDER_SIZE - FONT_HEIGHT_NUMBERS * tScale / 2);	
		
		float xChar = xStart;		
		if(startTab)
			xChar += TAB_WIDTH * tScale;
		
		float yChar = yStart;            
		
        while(indexChar < len && !isEnd)
        {
    		for (int i=indexChar; i<len; i++)
    		{
    			int index = str.charAt(i);
    			
    			if(index != 10)			//enter
    				index -= 32;		//"32" - index of " "
    			else
    			{
    				newStr = true;
    				break;
    			}
    			
    			++wordSize;
    			
    			if(index < 0 || index > FONT_INDEX_FINISH)
    				continue;
    			
    			if(index > 0 && i > 0)
    				wordWidth += tScale * 0.7 * nTextWidth[index-1];
    			else if(i > 0)
    			{
    				wordWidth +=  tScale * GAP_WIDTH;
    				break;
    			}
    		}
    		
    		if(xChar + wordWidth <= xEnd && !newStr)
    		{// we have enough space in current string for new word;
    			
    			for (int i=indexChar; i<indexChar + wordSize; i++)
    			{
    				int index = str.charAt(i) - 32;		//"32" - index of " "
    				if(index < 0 || index > FONT_INDEX_FINISH)
    					continue;
    				
    				if(index > 0)
    				{
    					float fHeight = 1f;
    					
    					if(index - 1< FONT_INDEX_NUMBERS)
    						fHeight = FONT_HEIGHT_SYMBOLS;
    					else if(index - 1 < FONT_INDEX_CAPITAL_LETTER)
    						fHeight = FONT_HEIGHT_NUMBERS;
    					else if(index - 1< FONT_INDEX_LETTER)
    						fHeight = FONT_HEIGHT_CAPITAL_LETTER;
    					else
    						fHeight = FONT_HEIGHT_LETTER;
    					
    					sBatcher.draw(text[index-1], xChar, yChar, tScale * nTextWidth[index-1], tScale * fHeight);			
    					xChar += tScale * 0.7 * nTextWidth[index-1];
    				}
    				else
    				{
    					xChar +=  tScale * GAP_WIDTH;	
    					break;
    				}
    			}   			
    		}
    		else if(lineIndex < maxStrNumber)
    		{// we don't have enough space in current string for new word, but we have space for new lines
    			
    			xChar = xStart;
    			if(newStr)
    				xChar += TAB_WIDTH * tScale;
    			
    			yChar -= FONT_HEIGHT_NUMBERS * tScale * DISTANCE_BETWEEN_LINES_KOEF;
    			++lineIndex;
    			newStr = false;
    			
    			for (int i=indexChar; i<indexChar + wordSize; i++)
    			{
    				int index = str.charAt(i) - 32;		//"32" - index of " "
    				if(index < 0 || index > FONT_INDEX_FINISH)
    					continue;
    				
    				if(index > 0)
    				{
    					float fHeight = 1f;
    					
    					if(index  - 1< FONT_INDEX_NUMBERS)
    						fHeight = FONT_HEIGHT_SYMBOLS;
    					else if(index - 1 < FONT_INDEX_CAPITAL_LETTER)
    						fHeight = FONT_HEIGHT_NUMBERS;
    					else if(index  - 1< FONT_INDEX_LETTER)
    						fHeight = FONT_HEIGHT_CAPITAL_LETTER;
    					else
    						fHeight = FONT_HEIGHT_LETTER;
    					
    					sBatcher.draw(text[index-1], xChar, yChar, tScale * nTextWidth[index-1], tScale * fHeight);			
    					xChar += tScale * 0.7 * nTextWidth[index-1];
    				}
    				else
    				{
    					xChar +=  tScale * GAP_WIDTH;
    					break;
    				}
    			} 
    			
    		}
    		else
    			break;
    		
    		indexChar += wordSize;
    		wordSize = 0;
    		wordWidth = 0;   					
        }			
	}

}
