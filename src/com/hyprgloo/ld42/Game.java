package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.hyprgloo.ld42.ships.ShipMerchant;
import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.menu.HvlMenu;

public class Game {

	public static enum EndState{
		IN_PROGRESS, WIN, LOSS_ENERGY, LOSS_COLLISIONS, LOSS_TUTORIAL, WIN_TUTORIAL
	}

	public static final float 
	ENERGY_PULSE_DELAY = 16f,
	ENERGY_PULSE_AMOUNT = 1f/8f;

	public static final float
	RESUPPLY_ENERGY_AMOUNT = 1f/4f,
	RESUPPLY_FUEL_AMOUNT = 1f/10f,
	RESUPPLY_AMMO_AMOUNT = 1/6f,
	SHOOT_AMMO_COST = (1f/6f) * 1.5f;

	public static int selected_level = -1, collisions = 0;

	public static float level_fuel, level_energy, level_ammo;
	public static float energyPulseTimer, endStateTimer, endStateTimerMeta;

	public static EndState state;

	public static Color endStateColor;

	public static int backgroundTexture;

	public static int tutorialStageIndex = 0;

	public static boolean hasPlayedEnergySound = false;
	public static boolean hasPlayedJumpSound = false;

	public static void restart(){
		Ship.ships.clear();
		FancyOverlay.gameRestart();
		FlightPath.restart();
		Ship.exps.clear();
		level_fuel = 0f;
		level_energy = 1f;
		level_ammo = 0f;

		collisions = 0;

		tutorialStageIndex = 0;

		state = EndState.IN_PROGRESS;
		endStateTimer = 0f;
		endStateTimerMeta = 5f;
		endStateColor = Color.white;

		energyPulseTimer = ENERGY_PULSE_DELAY;
		LevelShipSequencer.spawnedTutorial = false;
		SpaceStation.restart();
		LevelShipSequencer.reset();

		backgroundTexture = HvlMath.randomInt(2) == 0 ? Main.INDEX_NEBULA1 : Main.INDEX_NEBULA2;
	}

	public static void update(float delta){
		if(selected_level == 0 && collisions > 0){
			state = EndState.LOSS_TUTORIAL;
		}else if(state == EndState.IN_PROGRESS && Ship.ships.size() > 0 && selected_level == 0){
			if(tutorialStageIndex < 2 || Ship.ships.get(0).xGoal < 128f){
				Ship.ships.get(1).x = -128;
			}

			if(Ship.ships.size() > 1 && ((ShipMerchant)Ship.ships.get(1)).cargo == Cargo.ENERGY) level_energy = 1f - ENERGY_PULSE_AMOUNT;
			else level_energy = 1f;

			if((tutorialStageIndex == 2 || tutorialStageIndex == 3) && Ship.ships.get(1).x > 0){
				boolean dockFound = false;
				for(SpaceStationPart p : SpaceStation.stationParts){
					if(Ship.ships.get(0).xGoal == p.x && Ship.ships.get(0).yGoal == p.y) dockFound = true;
				}
				if(!dockFound && Ship.ships.get(0).xGoal > 128f){
					state = EndState.LOSS_TUTORIAL;
					endStateTimer = 1f;
				}
			}
			if(tutorialStageIndex < 6){
				if(Ship.ships.size() > 1){
					if(((ShipMerchant)Ship.ships.get(1)).isLeaving){
						state = EndState.LOSS_TUTORIAL;
						endStateTimer = 1f;
					}
				}else{
					state = EndState.LOSS_TUTORIAL;
					endStateTimer = 1f;
				}
			}
		}
		if(state == EndState.IN_PROGRESS && selected_level == 0 && level_energy == 1f && level_fuel > 0f){
			boolean win = true;
			for(Ship s : Ship.ships) if(s.x < Display.getWidth()) win = false;
			if(win){
				state = EndState.WIN_TUTORIAL;
				endStateTimer = 0f;
			}
		}

		float backgroundOffset = Main.getNewestInstance().getTimer().getTotalTime()*-10f;
		backgroundOffset = backgroundOffset%Display.getWidth();
		hvlDrawQuad(backgroundOffset, 0, Display.getWidth(), Display.getHeight(), Main.getTexture(backgroundTexture));
		hvlDrawQuad(Display.getWidth() + backgroundOffset, 0, Display.getWidth(), Display.getHeight(), Main.getTexture(backgroundTexture));
		float backgroundOffset2 = Main.getNewestInstance().getTimer().getTotalTime()*-20f;
		backgroundOffset2 = backgroundOffset2%Display.getWidth();
		hvlDrawQuad(backgroundOffset2, 0, Display.getWidth(), Display.getHeight(), Main.getTexture(Main.INDEX_STARS));
		hvlDrawQuad(Display.getWidth() + backgroundOffset2, 0, Display.getWidth(), Display.getHeight(), Main.getTexture(Main.INDEX_STARS));
		if(state == EndState.IN_PROGRESS){
			if(energyPulseTimer < 1.5 && hasPlayedEnergySound == false && !(Game.selected_level == 0)) {
				if(Main.settings.soundEnabled) Main.getSound(Main.INDEX_ELECTRIC3).playAsSoundEffect(1.5f, 0.075f, false);
				hasPlayedEnergySound = true;
			}
			energyPulseTimer = HvlMath.stepTowards(energyPulseTimer, delta, 0f);
			if(energyPulseTimer == 0){
				hasPlayedEnergySound = false;
				level_energy -= ENERGY_PULSE_AMOUNT;
				energyPulseTimer = ENERGY_PULSE_DELAY;
			}
		}

		if(state == EndState.IN_PROGRESS){
			if(hasPlayedJumpSound) {
				hasPlayedJumpSound = false;
			}
			if(level_energy == 0){
				state = EndState.LOSS_ENERGY;
				if(Main.settings.soundEnabled) Main.getSound(Main.INDEX_BATTERY_DEATH).playAsSoundEffect(1f, 0.7f, false);
			}else if(level_fuel == 1){
				state = EndState.WIN;
				endStateTimerMeta = 7f;
			}
		}
		if(state != EndState.IN_PROGRESS){
			endStateTimer = HvlMath.stepTowards(endStateTimer, (state == EndState.WIN ? 0.25f : 1f) * delta, 1f);
			endStateTimerMeta = HvlMath.stepTowards(endStateTimerMeta, delta, 0f);
			if(state == EndState.LOSS_ENERGY){
				float value = HvlMath.map(endStateTimer, 1f, 0f, 0.4f, 1f);
				endStateColor = new Color(value, value, value);
			}
			if(state == EndState.WIN){

				float value = HvlMath.limit(HvlMath.map(endStateTimer, 1f, 0.75f, 0f, 1f), 0f, 1f);
				endStateColor = new Color(1f, 1f, 1f, value);
			}
			if(state == EndState.LOSS_COLLISIONS){
				float value = HvlMath.limit(HvlMath.map(endStateTimer, 1f, 0.75f, 1f, 0f), 0f, 1f);
				endStateColor = new Color(1f, 1f - value, 1f - value);
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

		if(collisions > 0) Main.font.drawWord("disasters: " + collisions, 8f, Display.getHeight() - 24f, collisions >= 8 ? Color.red : Color.white, 0.125f);
		if(collisions > 9 && state == EndState.IN_PROGRESS) state = EndState.LOSS_COLLISIONS;

		level_fuel = state == EndState.WIN ? 1f : HvlMath.limit(level_fuel, 0f, 1f);
		level_energy = state == EndState.LOSS_ENERGY ? 0f : HvlMath.limit(level_energy, 0f, 1f);
		level_ammo = HvlMath.limit(level_ammo, 0f, 1f);

		FancyOverlay.drawGameLevels(delta);


		if(state == EndState.WIN){
			if(hasPlayedJumpSound == false && endStateTimerMeta < 3.75) {
				if(Main.settings.soundEnabled) Main.getSound(Main.INDEX_JUMP).playAsSoundEffect(1.0f, 0.5f, false);
				hasPlayedJumpSound = true;
			}
			float value = HvlMath.limit(HvlMath.map(endStateTimer, 0.8f, 0.9f, 0f, 1f), 0f, 1f) - HvlMath.limit(HvlMath.map(endStateTimer, 0.9f, 1f, 0f, 1f), 0f, 1f);
			hvlRotate(Display.getWidth()/2, Display.getHeight()/2, Main.getNewestInstance().getTimer().getTotalTime() * 720f);
			hvlDrawQuadc(Display.getWidth()/2, Display.getHeight()/2, value * 256, value * 256, Main.getTexture(Main.INDEX_BLINK));
			hvlResetRotation();
		}
	}

}
