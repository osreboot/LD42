package com.hyprgloo.ld42;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlMath;

public class Game {

	public static final float 
	ENERGY_PULSE_DELAY = 20f,
	ENERGY_PULSE_AMOUNT = 1f/8f;

	public static final float
	RESUPPLY_ENERGY_AMOUNT = 1f/4f,
	RESUPPLY_FUEL_AMOUNT = 1f/10f,
	RESUPPLY_AMMO_AMOUNT = 1/6f;

	public static int selected_level = -1, collisions = 0;

	public static float level_fuel, level_energy, level_ammo;
	public static float energyPulseTimer;

	public static void restart(){
		Ship.ships.clear();
		FancyOverlay.gameRestart();
		FlightPath.restart();
		Ship.exps.clear();
		level_fuel = 0f;
		level_energy = 1f;
		level_ammo = 0f;

		collisions = 0;

		energyPulseTimer = ENERGY_PULSE_DELAY;
		LevelShipSequencer.spawnedTutorial = false;
		SpaceStation.restart();

	}

	public static void update(float delta){
		energyPulseTimer = HvlMath.stepTowards(energyPulseTimer, delta, 0f);
		if(energyPulseTimer == 0){
			level_energy -= ENERGY_PULSE_AMOUNT;
			energyPulseTimer = ENERGY_PULSE_DELAY;
		}
		LevelShipSequencer.updateLevels(delta);

		FlightPath.update(delta);
		
		ShipSelector.drawZones(delta);
		Ship.updateShips(delta);
		ShipSelector.update(delta);
		SpaceStation.update(delta);
		
		
		for(Explosion e : Ship.exps) e.draw(delta);
		
		if(collisions > 0) Main.font.drawWord("disasters: " + collisions, 8f, Display.getHeight() - 24f, Color.white, 0.125f);

		level_fuel = HvlMath.limit(level_fuel, 0f, 1f);
		level_energy = HvlMath.limit(level_energy, 0f, 1f);
		level_ammo = HvlMath.limit(level_ammo, 0f, 1f);

		FancyOverlay.drawGameLevels(delta);
	}

}
