package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.menu.HvlMenu;

public class Game {

	public static enum EndState{
		IN_PROGRESS, WIN, LOSS_ENERGY, LOSS_COLLISIONS
	}

	public static final float 
	ENERGY_PULSE_DELAY = 16f,
	ENERGY_PULSE_AMOUNT = 1f/8f;

	public static final float
	RESUPPLY_ENERGY_AMOUNT = 1f/4f,
	RESUPPLY_FUEL_AMOUNT = 1f/10f,
	RESUPPLY_AMMO_AMOUNT = 1/6f;

	public static int selected_level = -1, collisions = 0;

	public static float level_fuel, level_energy, level_ammo;
	public static float energyPulseTimer, endStateTimer, endStateTimerMeta;

	public static EndState state;

	public static Color endStateColor;

	public static void restart(){
		Ship.ships.clear();
		FancyOverlay.gameRestart();
		FlightPath.restart();
		Ship.exps.clear();
		level_fuel = 0f;
		level_energy = 1f;
		level_ammo = 0f;

		collisions = 0;

		state = EndState.IN_PROGRESS;
		endStateTimer = 0f;
		endStateTimerMeta = 5f;
		endStateColor = Color.white;

		energyPulseTimer = ENERGY_PULSE_DELAY;
		LevelShipSequencer.spawnedTutorial = false;
		SpaceStation.restart();
		LevelShipSequencer.reset();
	}

	public static void update(float delta){
		if(state == EndState.IN_PROGRESS){
			energyPulseTimer = HvlMath.stepTowards(energyPulseTimer, delta, 0f);
			if(energyPulseTimer == 0){
				level_energy -= ENERGY_PULSE_AMOUNT;
				energyPulseTimer = ENERGY_PULSE_DELAY;
			}
		}

		if(state == EndState.IN_PROGRESS){
			if(level_energy == 0){
				state = EndState.LOSS_ENERGY;
			}else if(level_fuel == 1){
				state = EndState.WIN;
				endStateTimerMeta = 7f;
			}
		}
		if(state != EndState.IN_PROGRESS){
			endStateTimer = HvlMath.stepTowards(endStateTimer, (state == EndState.WIN ? 0.25f : 1f) * delta, 1f);
			endStateTimerMeta = HvlMath.stepTowards(endStateTimerMeta, delta, 0f);
			if(state == EndState.LOSS_ENERGY){
				float value = HvlMath.map(endStateTimer, 1f, 0f, 0.2f, 1f);
				endStateColor = new Color(value, value, value);
			}
			if(state == EndState.WIN){
				float value = HvlMath.limit(HvlMath.map(endStateTimer, 1f, 0.75f, 0f, 1f), 0f, 1f);
				endStateColor = new Color(1f, 1f, 1f, value);
			}
			if(endStateTimerMeta == 0) HvlMenu.setCurrent(MenuManager.end);
		}

		LevelShipSequencer.updateLevels(delta);

		FlightPath.update(delta);

		ShipSelector.drawZones(delta);
		Ship.updateShips(delta);
		ShipSelector.update(delta);
		SpaceStation.update(delta);

		for(Explosion e : Ship.exps) e.draw(delta);

		if(collisions > 0) Main.font.drawWord("disasters: " + collisions, 8f, Display.getHeight() - 24f, Color.white, 0.125f);

		level_fuel = state == EndState.WIN ? 1f : HvlMath.limit(level_fuel, 0f, 1f);
		level_energy = state == EndState.LOSS_ENERGY ? 0f : HvlMath.limit(level_energy, 0f, 1f);
		level_ammo = HvlMath.limit(level_ammo, 0f, 1f);

		FancyOverlay.drawGameLevels(delta);
		
		if(state == EndState.WIN){
			float value = HvlMath.limit(HvlMath.map(endStateTimer, 0.8f, 0.9f, 0f, 1f), 0f, 1f) - HvlMath.limit(HvlMath.map(endStateTimer, 0.9f, 1f, 0f, 1f), 0f, 1f);
			hvlRotate(Display.getWidth()/2, Display.getHeight()/2, Main.getNewestInstance().getTimer().getTotalTime() * 720f);
			hvlDrawQuadc(Display.getWidth()/2, Display.getHeight()/2, value * 256, value * 256, Main.getTexture(Main.INDEX_BLINK));
			hvlResetRotation();
		}
	}

}
