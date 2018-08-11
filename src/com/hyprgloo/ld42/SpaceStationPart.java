package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;

public class SpaceStationPart {
	public static final float GRID_SIZE = 24f;

	public float x, y, rotation;
	public int textureIndex;
	
	public SpaceStationPart(float xArg, float yArg, float rotationArg, int textureIndexArg){
		x = xArg;
		y = yArg;
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
