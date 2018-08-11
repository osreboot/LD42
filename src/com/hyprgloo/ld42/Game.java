package com.hyprgloo.ld42;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.hyprgloo.ld42.ships.ShipMerchantLight;
import com.hyprgloo.ld42.ships.ShipMerchantMedium;
import com.osreboot.ridhvl.HvlMath;

public class Game {

	public static final float 
	ENERGY_PULSE_DELAY = 20f,
	ENERGY_PULSE_AMOUNT = 1f/8f;

	public static final float
	RESUPPLY_ENERGY_AMOUNT = 1f/4f,
	RESUPPLY_FUEL_AMOUNT = 1f/10f;

	public static int selected_level = -1, collisions = 0;

	public static float level_fuel, level_energy, level_ammo;
	public static float energyPulseTimer;

	public static void restart(){
		Ship.ships.clear();
		FancyOverlay.gameRestart();

		level_fuel = 0f;
		level_energy = 1f;
		level_ammo = 1f;

		collisions = 1;

		energyPulseTimer = ENERGY_PULSE_DELAY;

//		new ShipMerchantMedium(-128, 256, 64, 192, 0, Cargo.FUEL);
//
//
//		new ShipMerchantLight(-128, 256, 64, 256 + 96, 0, Cargo.AMMO);

		SpaceStation.restart();
		//		if(Game.selected_level == 1) {
		//			new Raider(200, 800, SpaceStation.stationParts.get(5).x, SpaceStation.stationParts.get(5).y, 0, Raider.MAX_SPEED);
		//			new Raider(400, 800, SpaceStation.stationParts.get(7).x, SpaceStation.stationParts.get(7).y, 0, Raider.MAX_SPEED);
		//			new Raider(600, 800, SpaceStation.stationParts.get(9).x, SpaceStation.stationParts.get(9).y, 0, Raider.MAX_SPEED);
		//		}
	}

	public static void update(float delta){
		energyPulseTimer = HvlMath.stepTowards(energyPulseTimer, delta, 0f);
		if(energyPulseTimer == 0){
			level_energy -= ENERGY_PULSE_AMOUNT;
			energyPulseTimer = ENERGY_PULSE_DELAY;
		}
		LevelShipSequencer.updateLevels(delta);

		Ship.updateShips(delta);
		ShipSelector.update(delta);
		SpaceStation.update(delta);
		if(collisions > 0) Main.font.drawWord("collisions: " + collisions, 8f, Display.getHeight() - 24f, Color.white, 0.125f);

		level_fuel = HvlMath.limit(level_fuel, 0f, 1f);
		level_energy = HvlMath.limit(level_energy, 0f, 1f);
		level_ammo = HvlMath.limit(level_ammo, 0f, 1f);

		FancyOverlay.drawGameLevels(delta);
	}

}
