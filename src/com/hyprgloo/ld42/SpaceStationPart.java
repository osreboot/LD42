package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.lwjgl.opengl.Display;

public class SpaceStationPart {
	public static final float GRID_SIZE = 24f;

	public float x, y, rotation;
	public int textureIndex;
	
	public SpaceStationPart(float xArg, float yArg, float rotationArg, int textureIndexArg){
		x = Display.getWidth() + (GRID_SIZE/2 * xArg);
		x = Display.getHeight() + (GRID_SIZE/2 * yArg);
		
		rotation = rotationArg;
		textureIndex = textureIndexArg;
	}
	
	public void draw(float delta) {
		for(SpaceStationPart part : SpaceStation.stationParts) {
		hvlDrawQuadc(part.x, part.y, GRID_SIZE, GRID_SIZE, Main.getTexture(part.textureIndex));
		}
	}
	
	public void setTextureIndex(int indexArg) {
		textureIndex = indexArg;
	}

	public int getTextureIndex() {
		return textureIndex;
	}
	
}
