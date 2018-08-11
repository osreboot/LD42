package com.hyprgloo.ld42;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import com.hyprgloo.ld42.ships.ShipMerchantLight;
import com.hyprgloo.ld42.ships.ShipRaiderLight;
import com.osreboot.ridhvl.HvlMath;

public class LevelShipSequencer {
	
	public static final float SMALL_TIME = 10;
	public static final float MED_TIME = 10;
	public static final float LAR_TIME = 10;
	public static final float RAID_TIME = 15;

	
	static float smallShipTimer = 0;
	static float medShipTimer = 0;
	static float largeShipTimer = 0;
	static float raiderShipTimer = 5;
	
	public static boolean spawnedTutorial = false;
	
	public static ArrayList<SpaceStationPart> raiderCompParts = new ArrayList<>();
	
	public static void spawnRaider() {
		for(SpaceStationPart allParts : SpaceStation.stationParts) {
			if(allParts.raiderCompatible) {
				raiderCompParts.add(allParts);
			}
		}
		int targetIndex = HvlMath.randomInt(raiderCompParts.size());
		
		if(raiderCompParts.get(targetIndex).y <= 360) {
			new ShipRaiderLight(raiderCompParts.get(targetIndex).x, -128, raiderCompParts.get(targetIndex).x, 128, 0, raiderCompParts.get(targetIndex));
		} else if (raiderCompParts.get(targetIndex).y > 360) {
			new ShipRaiderLight(raiderCompParts.get(targetIndex).x, Display.getHeight() + 128, raiderCompParts.get(targetIndex).x, Display.getHeight() - 128, 0, raiderCompParts.get(targetIndex));
		}
	}

	public static void updateLevels(float delta) {
		smallShipTimer -= delta;
		medShipTimer -= delta;
		largeShipTimer -= delta;
		raiderShipTimer -= delta;
		if(Game.selected_level == 0) {
			//TODO tutorial????
			if(!spawnedTutorial) {
				new ShipMerchantLight(-128, 250, 64, 250, 0, Cargo.FUEL);
				new ShipMerchantLight(-500, 500, 64, 500 , 0, Cargo.ENERGY);
				spawnedTutorial = true;
			}

		} else if(Game.selected_level == 1){
			if(smallShipTimer <= 0) {
				float startY = HvlMath.randomFloatBetween(100,600);
				new ShipMerchantLight(-128, startY , 250, startY, 0, Cargo.FUEL);
				smallShipTimer = SMALL_TIME;
			}
			if(raiderShipTimer <= 0) {
				spawnRaider();
				raiderShipTimer = RAID_TIME;
			}
			
		} else if(Game.selected_level == 2) {
			
		} else if(Game.selected_level == 3) {
			
		}
	}
	
}
