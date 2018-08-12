package com.hyprgloo.ld42;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import com.hyprgloo.ld42.ships.ShipMerchantLight;
import com.hyprgloo.ld42.ships.ShipMerchantMedium;
import com.hyprgloo.ld42.ships.ShipRaiderLight;
import com.osreboot.ridhvl.HvlMath;

public class LevelShipSequencer {
	
	public static final float SHIP_SPAWN_EDGE_SPACING = 192f;
	
	public static final float SMALL_TIME = 6;
	public static final float MED_TIME = 12;
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
			new ShipRaiderLight(raiderCompParts.get(targetIndex).x, -128, raiderCompParts.get(targetIndex).x, SHIP_SPAWN_EDGE_SPACING, 0, raiderCompParts.get(targetIndex));
		} else if (raiderCompParts.get(targetIndex).y > 360) {
			new ShipRaiderLight(raiderCompParts.get(targetIndex).x, Display.getHeight() + 128, raiderCompParts.get(targetIndex).x, Display.getHeight() - SHIP_SPAWN_EDGE_SPACING, 0, raiderCompParts.get(targetIndex));
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
				float startY;
				do{
					startY = HvlMath.randomInt(2) == 0 ? HvlMath.randomFloatBetween(0, SHIP_SPAWN_EDGE_SPACING) : HvlMath.randomFloatBetween(Display.getHeight() - SHIP_SPAWN_EDGE_SPACING, Display.getHeight());
				}while(Ship.shipInProximity(-128, startY, ShipMerchantLight.COLLISION_SIZE));
				Cargo cargo = Cargo.EMPTY;
				int cargoSelect = HvlMath.randomInt(3);
				if(cargoSelect == 0) cargo = Cargo.FUEL;
				if(cargoSelect == 1) cargo = Cargo.ENERGY;
				if(cargoSelect == 2) cargo = Cargo.AMMO;
				new ShipMerchantLight(-128, startY , Display.getWidth() + 128, startY, 0, cargo);
				smallShipTimer = SMALL_TIME;
			}
			if(medShipTimer <= 0) {
				float startY;
				do{
					startY = HvlMath.randomInt(2) == 0 ? HvlMath.randomFloatBetween(0, SHIP_SPAWN_EDGE_SPACING) : HvlMath.randomFloatBetween(Display.getHeight() - SHIP_SPAWN_EDGE_SPACING, Display.getHeight());
				}while(Ship.shipInProximity(-128, startY, ShipMerchantMedium.COLLISION_SIZE));
				Cargo cargo = Cargo.EMPTY;
				int cargoSelect = HvlMath.randomInt(3);
				if(cargoSelect == 0) cargo = Cargo.FUEL;
				if(cargoSelect == 1) cargo = Cargo.ENERGY;
				if(cargoSelect == 2) cargo = Cargo.AMMO;
				new ShipMerchantMedium(-128, startY , Display.getWidth() + 128, startY, 0, cargo);
				medShipTimer = MED_TIME;
			}
			if(raiderShipTimer <= 0) {
				//spawnRaider();
				raiderShipTimer = RAID_TIME;
			}
			
		} else if(Game.selected_level == 2) {
			
		} else if(Game.selected_level == 3) {
			
		}
	}
	
}
