package com.hyprgloo.ld42;

import org.lwjgl.opengl.Display;

public class SpaceStationPart {
	public static final float GRID_SIZE = 24f;

	public float x, y, rotation;
	public int textureIndex;
	
	public SpaceStationPart(float xGridArg, float yGridArg, float rotationArg, int textureIndexArg){
		x = Display.getWidth()/2 + (GRID_SIZE * xGridArg);
		y = Display.getHeight()/2 + (GRID_SIZE * yGridArg);
		
		rotation = rotationArg;
		textureIndex = textureIndexArg;
	}
	
	public void setTextureIndex(int indexArg){
		textureIndex = indexArg;
	}

	public int getTextureIndex(){
		return textureIndex;
	}
	
}
