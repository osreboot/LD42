package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

public class SpaceStation {

	public static final float GRID_SIZE = 24f;

	public static ArrayList<SpaceStationPart> stationParts = new ArrayList<>();

	public static void restart(){
		if(Game.selected_level == 1) {
			stationParts.add(new SpaceStationPart(0f, 0f, 0f, 4));
		//}else if(level == 2) {
		//	stationParts.add(new SpaceStationPart(xArg, yArg, rotationArg, textureIndexArg));
		//}else if(level == 3) {
		//	stationParts.add(new SpaceStationPart(xArg, yArg, rotationArg, textureIndexArg));

		}
	}


	
	public static void update(float delta){
		
			for(SpaceStationPart part : stationParts) {
			hvlDrawQuadc(part.x, part.y, GRID_SIZE, GRID_SIZE, Main.getTexture(part.textureIndex));
			}
		
		
	}

}
