package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawLine;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class FancyOverlay {
	
	public static float mainTimer = 0f;
	public static int backgroundTexture;
	
	public static void resetMainBackground(){
		mainTimer = 0f;
		backgroundTexture = HvlMath.randomInt(2) == 0 ? Main.INDEX_NEBULA1 : Main.INDEX_NEBULA2;
	}
	
	public static void drawMainBackground(float delta){
		mainTimer += delta;
		
		float backgroundOffset = Main.getNewestInstance().getTimer().getTotalTime()*-10f;
		backgroundOffset = backgroundOffset%Display.getWidth();
		hvlDrawQuad(backgroundOffset, 0, Display.getWidth(), Display.getHeight(), Main.getTexture(backgroundTexture));
		hvlDrawQuad(Display.getWidth() + backgroundOffset, 0, Display.getWidth(), Display.getHeight(), Main.getTexture(backgroundTexture));
		float backgroundOffset2 = Main.getNewestInstance().getTimer().getTotalTime()*-20f;
		backgroundOffset2 = backgroundOffset2%Display.getWidth();
		hvlDrawQuad(backgroundOffset2, 0, Display.getWidth(), Display.getHeight(), Main.getTexture(Main.INDEX_STARS));
		hvlDrawQuad(Display.getWidth() + backgroundOffset2, 0, Display.getWidth(), Display.getHeight(), Main.getTexture(Main.INDEX_STARS));
		hvlDrawQuad(0, 0, Display.getWidth(), Display.getHeight(), new Color(0f, 0f, 0f, 0.2f));
		if(mainTimer < 1.5f){
			float location = HvlMath.map(mainTimer, 0f, 1f, -1000, 2000);
			HvlCoord2D loc = new HvlCoord2D(location*1.5f, 1000 - location);
			hvlRotate(loc, 60);
			hvlDrawQuadc(loc.x, loc.y, 1024, 1024, Main.getTexture(Main.INDEX_CARGO_SHIP_LARGE));
			hvlResetRotation();
		}
		if(mainTimer > 2f){
			float raiderOffset = HvlMath.limit(HvlMath.map(mainTimer%16f, 8f, 12f, -2000, 3000), -2000, 3000);
			float raiderOffset2 = HvlMath.limit(HvlMath.map(mainTimer%16f, 10f, 14f, -2000, 3000), -2000, 3000);
			float zoom = HvlMath.limit(HvlMath.map(mainTimer, 2f, 3f, 8f, 1f), 1f, 8f);
			HvlCoord2D offset = new HvlCoord2D((float)Math.sin(mainTimer) * 4f, (float)Math.sin(mainTimer) * 16f);
			hvlRotate(Display.getWidth()/2, Display.getHeight()/2, 60);
			hvlDrawQuadc((Display.getWidth()/2) + (offset.x*0.5f) - 300, (Display.getHeight()/2) + (offset.x*0.5f) - raiderOffset2 - 200, zoom * 96, zoom * 96, Main.getTexture(Main.INDEX_RAIDER));
			hvlDrawQuadc((Display.getWidth()/2) + (offset.x*0.5f) + (100f * zoom) - 100, (Display.getHeight()/2) + (offset.x*0.5f) - raiderOffset - 200, zoom * 96, zoom * 96, Main.getTexture(Main.INDEX_RAIDER));
			hvlDrawQuadc((Display.getWidth()/2) + (offset.x*0.5f) + (100f * zoom), (Display.getHeight()/2) + (offset.x*0.5f) - raiderOffset, zoom * 96, zoom * 96, Main.getTexture(Main.INDEX_RAIDER));
			hvlDrawQuadc((Display.getWidth()/2) + offset.x + (350f * zoom), (Display.getHeight()/2) + offset.y - (zoom * 200f), zoom * 96, zoom * 96, Main.getTexture(Main.INDEX_CARGO_SHIP_SMALL));
			hvlDrawQuadc((Display.getWidth()/2) + offset.x - (256f * zoom), (Display.getHeight()/2) + offset.y - (zoom * 150f), zoom * 192, zoom * 192, Main.getTexture(Main.INDEX_CARGO_SHIP_MEDIUM));
			hvlDrawQuadc((Display.getWidth()/2) + offset.x, (Display.getHeight()/2) + offset.y, zoom * 288, zoom * 288, Main.getTexture(Main.INDEX_CARGO_SHIP_LARGE));
			hvlResetRotation();
		}
		if(mainTimer > 1.5f){
			float flash = HvlMath.limit(HvlMath.map(mainTimer, 1.5f, 2f, 0f, 1f), 0f, 1f) - HvlMath.limit(HvlMath.map(mainTimer, 2f, 2.5f, 0f, 1f), 0f, 1f);
			hvlDrawQuad(0, 0, Display.getWidth(), Display.getHeight(), new Color(1f, 1f, 1f, flash));
		}
	}
	
	public static final float 
	GAME_LEVEL_FUEL_X = 16f,
	GAME_LEVEL_ENERGY_X = GAME_LEVEL_FUEL_X + 128f + 48f,
	GAME_LEVEL_AMMO_X = GAME_LEVEL_ENERGY_X + 128f + 48f;

	private static float 
	level_fuel_track = 0f,
	level_energy_track = 1f,
	level_ammo_track = 0f;

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
		hvlDrawQuad(GAME_LEVEL_ENERGY_X + 12, 8, level_energy_track * 128f, 8f, new Color(0f, 0f, 1f));
		hvlDrawLine(GAME_LEVEL_ENERGY_X + 12 + (Game.ENERGY_PULSE_AMOUNT * 128f), 8, 
				GAME_LEVEL_ENERGY_X + 12 + (Game.ENERGY_PULSE_AMOUNT * 128f), 16, Color.white);
		if(energySize > 16) {
			hvlRotate(GAME_LEVEL_ENERGY_X, 12, HvlMath.randomInt(360));
			hvlDrawQuadc(GAME_LEVEL_ENERGY_X, 12, energySize+20, energySize+20, Main.getTexture(HvlMath.randomIntBetween(Main.INDEX_LIGHT_1, Main.INDEX_LIGHT_3+1)));
			hvlResetRotation();
		}
		if(Game.selected_level > 1) {
			hvlDrawQuadc(GAME_LEVEL_AMMO_X, 12, 16, 16, Main.getTexture(Main.INDEX_CANISTER_AMMO));
			hvlDrawQuad(GAME_LEVEL_AMMO_X + 12 - 1, 8 - 1, 128f + 2, 8f + 2, Color.gray);
			hvlDrawQuad(GAME_LEVEL_AMMO_X + 12, 8, 128f, 8f, Color.black);
			hvlDrawQuad(GAME_LEVEL_AMMO_X + 12, 8, level_ammo_track * 128f, 8f, Color.green);
			hvlDrawLine(GAME_LEVEL_AMMO_X + 12 + (Game.SHOOT_AMMO_COST * 128f), 8, 
					GAME_LEVEL_AMMO_X + 12 + (Game.SHOOT_AMMO_COST * 128f), 16, Color.white);
		}

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
		if(Game.selected_level > 1) {
			for(float f = 0; f < PARTICLE_EXPLOSION_COUNT; f++){
				new TextParticle(plus ? "+" : "-", plus ? Color.green : Color.red, 
						HvlMath.randomFloatBetween(GAME_LEVEL_AMMO_X + 12, GAME_LEVEL_AMMO_X + 12 + 128), 16, 
						HvlMath.randomFloatBetween(-PARTICLE_EXPLOSION_SPEED, PARTICLE_EXPLOSION_SPEED), HvlMath.randomFloatBetween(-PARTICLE_EXPLOSION_SPEED, PARTICLE_EXPLOSION_SPEED));
			}
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
