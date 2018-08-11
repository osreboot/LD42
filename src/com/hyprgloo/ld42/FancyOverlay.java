package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawLine;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.menu.component.collection.HvlLabeledButton;

public class FancyOverlay {

	public static final float MAIN_CROSSHAIR_SPEED = 1024f;

	private static HvlCoord2D mainCrosshair = new HvlCoord2D(), mainCrossHairGoal = new HvlCoord2D();

	public static void updateMain(float delta){
		mainCrosshair.x = HvlMath.stepTowards(mainCrosshair.x, delta * MAIN_CROSSHAIR_SPEED, mainCrossHairGoal.x);
		mainCrosshair.y = HvlMath.stepTowards(mainCrosshair.y, delta * MAIN_CROSSHAIR_SPEED, mainCrossHairGoal.y);

		hvlDrawLine(0, Display.getHeight()/2, 16f, Display.getHeight()/2, Main.COLOR_BLUE0);
		hvlDrawLine(16f, Display.getHeight()/2, 16f, mainCrosshair.y, Main.COLOR_BLUE0);

		mainCrossHairGoal = new HvlCoord2D();
	}

	public static void drawMainButton(float delta, HvlLabeledButton b){
		mainCrossHairGoal.x = b.getX();
		mainCrossHairGoal.y = b.getY() + (b.getHeight()/2);
	}

	public static final float 
	GAME_LEVEL_FUEL_X = 32f,
	GAME_LEVEL_ENERGY_X = GAME_LEVEL_FUEL_X + 128f + 48f,
	GAME_LEVEL_AMMO_X = GAME_LEVEL_ENERGY_X + 128f + 48f;

	public static void drawGameLevels(float delta){
		hvlDrawQuadc(GAME_LEVEL_FUEL_X, 12, 16, 16, Main.getTexture(Main.INDEX_CANISTER_FUEL));
		hvlDrawQuad(GAME_LEVEL_FUEL_X + 12 - 1, 8 - 1, 128f + 2, 8f + 2, Color.gray);
		hvlDrawQuad(GAME_LEVEL_FUEL_X + 12, 8, 128f, 8f, Color.black);
		hvlDrawQuad(GAME_LEVEL_FUEL_X + 12, 8, Game.level_fuel * 128f, 8f, Color.magenta);
		
		hvlDrawQuadc(GAME_LEVEL_ENERGY_X, 12, 16, 16, Main.getTexture(Main.INDEX_CANISTER_ENERGY));
		hvlDrawQuad(GAME_LEVEL_ENERGY_X + 12 - 1, 8 - 1, 128f + 2, 8f + 2, Color.gray);
		hvlDrawQuad(GAME_LEVEL_ENERGY_X + 12, 8, 128f, 8f, Color.black);
		hvlDrawQuad(GAME_LEVEL_ENERGY_X + 12, 8, Game.level_energy * 128f, 8f, Main.COLOR_BLUE0);
		
		hvlDrawQuadc(GAME_LEVEL_AMMO_X, 12, 16, 16, Main.getTexture(Main.INDEX_CANISTER_AMMO));
		hvlDrawQuad(GAME_LEVEL_AMMO_X + 12 - 1, 8 - 1, 128f + 2, 8f + 2, Color.gray);
		hvlDrawQuad(GAME_LEVEL_AMMO_X + 12, 8, 128f, 8f, Color.black);
		hvlDrawQuad(GAME_LEVEL_AMMO_X + 12, 8, Game.level_ammo * 128f, 8f, Color.green);
	}

}
