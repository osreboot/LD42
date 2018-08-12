package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlMath;

public class SpaceStation {

	public static final float GRID_SIZE = 24f;

	public static ArrayList<SpaceStationPart> stationParts = new ArrayList<>();

	public static void restart(){
		//0,0 is the center of the screen
		//stationParts.add(new SpaceStationPart(xGridArg, yGridArg, rotationArg, textureIndexArg));
		stationParts.clear();
		if(Game.selected_level == 0) {
			stationParts.add(new SpaceStationPart(0f, 0f, 0f, Main.INDEX_STATION_TRUSS));
			stationParts.add(new SpaceStationPart(1f, 0f, 0f, Main.INDEX_STATION_TRUSS));
			stationParts.add(new SpaceStationPart(-1f, 0f, 0f, Main.INDEX_STATION_TRUSS));
			stationParts.add(new SpaceStationPart(-2f, 0f, 0f, Main.INDEX_STATION_TRUSS));
			stationParts.add(new SpaceStationPart(-3f, 0f, 270f, Main.INDEX_STATION_DOCKING_PORT_MED));
			stationParts.add(new SpaceStationPart(2f, 1f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(2f, 2f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(2f, -1f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(2f, -2f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(2f, 0f, 0f, Main.INDEX_STATION_TRUSS));
			stationParts.add(new SpaceStationPart(3f, 0f, 0f, Main.INDEX_STATION_TRUSS));

		}
		if(Game.selected_level == 1) {
			stationParts.add(new SpaceStationPartTurret(0f, 0f, 0f));
			stationParts.add(new SpaceStationPart(1f, 0f, 0f, Main.INDEX_STATION_TRUSS));
			stationParts.add(new SpaceStationPart(2f, 0f, 0f, Main.INDEX_STATION_TRUSS));
			stationParts.add(new SpaceStationPart(-1f, 0f, 0f, Main.INDEX_STATION_TRUSS));
			stationParts.add(new SpaceStationPart(-2f, 0f, 0f, Main.INDEX_STATION_TRUSS));
			stationParts.add(new SpaceStationPart(-3f, 0f, 270f, Main.INDEX_STATION_DOCKING_PORT_MED));
			stationParts.add(new SpaceStationPart(-4f, 0f, 270f, -2));
			stationParts.add(new SpaceStationPart(-2f, -1f, 0f, Main.INDEX_STATION_DOCKING_PORT));
			stationParts.add(new SpaceStationPart(-2f, -2f, 0f, -1, true));
			//stationParts.add(new SpaceStationPart(-2f, -1f, 0f, Main.INDEX_STATION_DOCKING_PORT));
			//stationParts.add(new SpaceStationPart(-2f, -2f, 0f, -1, true));

			stationParts.add(new SpaceStationPart(2f, 1f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(2f, 2f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(2f, -1f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(2f, -2f, 0f, Main.INDEX_STATION_SOLAR_PANEL));

			stationParts.add(new SpaceStationPart(3f, 0f, 90f, Main.INDEX_STATION_DOCKING_PORT_LRG));
			stationParts.add(new SpaceStationPart(4f, 0f, 90f, -3));

			stationParts.add(new SpaceStationPart(0f, 1f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(0f, 2f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(0f, -1f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(0f, -2f, 0f, Main.INDEX_STATION_SOLAR_PANEL));

			stationParts.add(new SpaceStationPart(0f, 3f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(0f, 4f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(0f, -3f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(0f, -4f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
		}else if(Game.selected_level == 2) {
			stationParts.add(new SpaceStationPart(0f, 0f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
			stationParts.add(new SpaceStationPart(1f, 1f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
		}else if(Game.selected_level == 3) {
			stationParts.add(new SpaceStationPart(1f, 0f, 0f, Main.INDEX_STATION_SOLAR_PANEL));
		}
	}

	public static void update(float delta){
		for(SpaceStationPart part : stationParts){
			hvlRotate(part.x, part.y, part.rotation);
			if(part.textureIndex == -1 || part.textureIndex == -2 || part.textureIndex == -3){
				if(ShipSelector.selectedShip != null && ShipSelector.selectedShip.canDock(part.textureIndex) && !Ship.shipInProximity(part.x, part.y, 32f, ShipSelector.selectedShip)){
					float size = HvlMath.map((float)Math.sin(Main.getNewestInstance().getTimer().getTotalTime() * 10f), -1f, 1f, 10f, 20f);
					hvlDrawQuadc(part.x, part.y, size, size, Color.blue);
				}
			}else{
				hvlDrawQuadc(part.x, part.y, GRID_SIZE, GRID_SIZE, Main.getTexture(part.textureIndex), Game.endStateColor);
			}
			hvlResetRotation();

			if(part instanceof SpaceStationPartTurret){
				((SpaceStationPartTurret)part).updateTurretRotation(delta);
			}
		}
	}

}
