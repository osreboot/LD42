package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

public class SpaceStation {

	public static final float GRID_SIZE = 24f;

	public static ArrayList<SpaceStationPart> stationParts = new ArrayList<>();

	public static void restart(){
		//0,0 is the center of the screen
		if(Game.selected_level == 1) {
			stationParts.add(new SpaceStationPart(0f, 0f, 0f, Main.INDEX_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(1f, 0f, 90f, Main.INDEX_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(0f, 1f, 0f, Main.INDEX_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(1f, 1f, 0f, Main.INDEX_SOLAR_PANEL));
		}else if(Game.selected_level == 2) {
			stationParts.add(new SpaceStationPart(0f, 0f, 0f, Main.INDEX_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(1f, 1f, 0f, Main.INDEX_SOLAR_PANEL));
		}else if(Game.selected_level == 3) {
			stationParts.add(new SpaceStationPart(1f, 0f, 0f, Main.INDEX_SOLAR_PANEL));

		}
	}



	public static void update(float delta){

		for(SpaceStationPart part : stationParts) {
			hvlRotate(part.x, part.y, part.rotation);
			hvlDrawQuadc(part.x, part.y, GRID_SIZE, GRID_SIZE, Main.getTexture(part.textureIndex));
			hvlResetRotation();
		}


	}

}
