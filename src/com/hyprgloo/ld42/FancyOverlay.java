package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawLine;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;

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
	GAME_LEVEL_FUEL_X = 16f,
	GAME_LEVEL_ENERGY_X = GAME_LEVEL_FUEL_X + 128f + 48f,
	GAME_LEVEL_AMMO_X = GAME_LEVEL_ENERGY_X + 128f + 48f;

	private static float 
	level_fuel_track = 0f,
	level_energy_track = 1f,
	level_ammo_track = 1f;

	public static void gameRestart(){
		level_fuel_track = 0f;
		level_energy_track = 1f;
		level_ammo_track = 0f;
		TextParticle.particles.clear();
	}

	public static void drawGameLevels(float delta){
		for(TextParticle p : TextParticle.particles){
			p.update(delta);
			if(p.life == 0) TextParticle.particleTrash.add(p);
		}
		for(TextParticle p : TextParticle.particleTrash){
			TextParticle.particles.remove(p);
		}
		
		level_fuel_track = HvlMath.stepTowards(level_fuel_track, delta, Game.level_fuel);
		level_energy_track = HvlMath.stepTowards(level_energy_track, delta, Game.level_energy);
		level_ammo_track = HvlMath.stepTowards(level_ammo_track, delta, Game.level_ammo);
		
		hvlDrawQuadc(GAME_LEVEL_FUEL_X, 12, 16, 16, Main.getTexture(Main.INDEX_CANISTER_FUEL));
		hvlDrawQuad(GAME_LEVEL_FUEL_X + 12 - 1, 8 - 1, 128f + 2, 8f + 2, Color.gray);
		hvlDrawQuad(GAME_LEVEL_FUEL_X + 12, 8, 128f, 8f, Color.black);
		hvlDrawQuad(GAME_LEVEL_FUEL_X + 12, 8, level_fuel_track * 128f, 8f, Color.magenta);

		float energySize = HvlMath.map(HvlMath.limit(Game.energyPulseTimer, 0f, 3f), 0f, 3f, 24f, 16f);
		hvlDrawQuadc(GAME_LEVEL_ENERGY_X, 12, energySize, energySize, Main.getTexture(Main.INDEX_CANISTER_ENERGY));
		hvlDrawQuad(GAME_LEVEL_ENERGY_X + 12 - 1, 8 - 1, 128f + 2, 8f + 2, Color.gray);
		hvlDrawQuad(GAME_LEVEL_ENERGY_X + 12, 8, 128f, 8f, Color.black);
		hvlDrawQuad(GAME_LEVEL_ENERGY_X + 12, 8, level_energy_track * 128f, 8f, Main.COLOR_BLUE0);

		hvlDrawQuadc(GAME_LEVEL_AMMO_X, 12, 16, 16, Main.getTexture(Main.INDEX_CANISTER_AMMO));
		hvlDrawQuad(GAME_LEVEL_AMMO_X + 12 - 1, 8 - 1, 128f + 2, 8f + 2, Color.gray);
		hvlDrawQuad(GAME_LEVEL_AMMO_X + 12, 8, 128f, 8f, Color.black);
		hvlDrawQuad(GAME_LEVEL_AMMO_X + 12, 8, level_ammo_track * 128f, 8f, Color.green);
	}
	
	public static final float 
	PARTICLE_EXPLOSION_SPEED = 20f,
	PARTICLE_EXPLOSION_COUNT = 8f;

	public static void spawnFuelTextExplosion(boolean plus){
		for(float f = 0; f < PARTICLE_EXPLOSION_COUNT; f++){
			new TextParticle(plus ? "+" : "-", plus ? Color.green : Color.red, 
					HvlMath.randomFloatBetween(GAME_LEVEL_FUEL_X + 12, GAME_LEVEL_FUEL_X + 12 + 128), 16, 
					HvlMath.randomFloatBetween(-PARTICLE_EXPLOSION_SPEED, PARTICLE_EXPLOSION_SPEED), HvlMath.randomFloatBetween(-PARTICLE_EXPLOSION_SPEED, PARTICLE_EXPLOSION_SPEED));
		}
	}
	
	public static void spawnEnergyTextExplosion(boolean plus){
		for(float f = 0; f < PARTICLE_EXPLOSION_COUNT; f++){
			new TextParticle(plus ? "+" : "-", plus ? Color.green : Color.red, 
					HvlMath.randomFloatBetween(GAME_LEVEL_ENERGY_X + 12, GAME_LEVEL_ENERGY_X + 12 + 128), 16, 
					HvlMath.randomFloatBetween(-PARTICLE_EXPLOSION_SPEED, PARTICLE_EXPLOSION_SPEED), HvlMath.randomFloatBetween(-PARTICLE_EXPLOSION_SPEED, PARTICLE_EXPLOSION_SPEED));
		}
	}
	
	public static void spawnAmmoTextExplosion(boolean plus){
		for(float f = 0; f < PARTICLE_EXPLOSION_COUNT; f++){
			new TextParticle(plus ? "+" : "-", plus ? Color.green : Color.red, 
					HvlMath.randomFloatBetween(GAME_LEVEL_AMMO_X + 12, GAME_LEVEL_AMMO_X + 12 + 128), 16, 
					HvlMath.randomFloatBetween(-PARTICLE_EXPLOSION_SPEED, PARTICLE_EXPLOSION_SPEED), HvlMath.randomFloatBetween(-PARTICLE_EXPLOSION_SPEED, PARTICLE_EXPLOSION_SPEED));
		}
	}
	
	public static class TextParticle{
		
		public static ArrayList<TextParticle> particles = new ArrayList<>();
		public static ArrayList<TextParticle> particleTrash = new ArrayList<>();
		
		public String text;
		public Color color;
		public float x, y, xs, ys, life;
		
		public TextParticle(String textArg, Color colorArg, float xArg, float yArg, float xsArg, float ysArg){
			text = textArg;
			color = colorArg;
			x = xArg;
			y = yArg;
			xs = xsArg;
			ys = ysArg;
			life = 1.5f;
			particles.add(this);
		}
		
		public void update(float delta){
			life = HvlMath.stepTowards(life, delta, 0f);
			x += xs * delta;
			y += ys * delta;
			Main.font.drawWordc(text, x, y, new Color(color.r, color.g, color.b, life), 0.125f);
		}
		
	}
	
}
