package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawLine;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import org.newdawn.slick.Color;

import com.hyprgloo.ld42.ships.Raider;
import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class SpaceStationPartTurret extends SpaceStationPart{

	public float turretRotation;

	public static final float KILL_TIME = 6;
	public static final float KILL_RANGE = 400;
	public float killTimer = KILL_TIME;
	public static boolean laserSoundPlayed = false;

	public SpaceStationPartTurret(float xGridArg, float yGridArg, float rotationArg){
		super(xGridArg, yGridArg, rotationArg, Main.INDEX_STATION_TURRET_MOUNT);
		turretRotation = 0;
	}

	public void updateTurretRotation(float delta){
		Raider target = null;
		for(Ship s : Ship.ships){
			
			if(target == null && s instanceof Raider) {
				target = (Raider)s;
			}
			if(target != null && !target.isDead) {
			float distance = HvlMath.distance(x, y, target.x, target.y);
			float distanceTest = HvlMath.distance(x, y, s.x, s.y);
				if(distanceTest < distance && s instanceof Raider) {
					target = (Raider)s;
				}
			}
		}

		if(target != null && Game.state != Game.EndState.LOSS_ENERGY){
			float newRotation = (float)Math.toDegrees(HvlMath.fullRadians(new HvlCoord2D(x, y), new HvlCoord2D(target.x, target.y)));
			while(Math.abs(newRotation - turretRotation) > 180f){
				if(Math.abs(newRotation - 360f - turretRotation) < Math.abs(newRotation + 360f - turretRotation))
					newRotation -= 360f;
				else newRotation += 360f;
			}
			turretRotation = HvlMath.stepTowards(turretRotation, delta * 100f, newRotation);
			if(HvlMath.distance(x, y, target.x, target.y) < KILL_RANGE && !target.isDead && Game.level_ammo >= Game.SHOOT_AMMO_COST) {
				if(!laserSoundPlayed) {
					if(Main.settings.soundEnabled) Main.getSound(Main.INDEX_LASER2).playAsSoundEffect(1f, 0.55f, false);
					laserSoundPlayed = true;
				}
				killTimer = HvlMath.stepTowards(killTimer, delta, 0);
				if(killTimer <= 0) {
					Game.level_ammo -= Game.SHOOT_AMMO_COST;
					target.isDead = true;
					laserSoundPlayed = false;
					if(Main.settings.soundEnabled) Main.getSound(Main.INDEX_CRASH).playAsSoundEffect(1, 0.25f, false);
					killTimer = KILL_TIME;
				} 
				if(!target.isDead) {
					hvlDrawLine(target.x, target.y, x, y, Color.pink);
					hvlDrawLine(target.x+1, target.y, x+1, y, Color.magenta);
					hvlDrawLine(target.x-1, target.y, x-1, y, Color.magenta);
				}
			}
		}else killTimer = KILL_TIME;

		hvlRotate(x, y, turretRotation - 90);
		hvlDrawQuadc(x, y, GRID_SIZE, GRID_SIZE, Main.getTexture(Main.INDEX_STATION_TURRET), Game.endStateColor);
		hvlResetRotation();
	}

}
