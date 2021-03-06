package com.hyprgloo.ld42.ships;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.newdawn.slick.Color;

import com.hyprgloo.ld42.Cargo;
import com.hyprgloo.ld42.FancyOverlay;
import com.hyprgloo.ld42.Game;
import com.hyprgloo.ld42.LevelShipSequencer;
import com.hyprgloo.ld42.Main;
import com.hyprgloo.ld42.Ship;
import com.hyprgloo.ld42.SpaceStation;
import com.hyprgloo.ld42.SpaceStationPart;
import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class Raider extends Ship{

	public float tradeTime, maxTradeTime, dockRotationOffset, idleTime;
	public Cargo cargo;
	public boolean docking = false, docked = false;
	SpaceStationPart goalPart;

	public Raider(float xArg, float yArg, float xGoalArg, float yGoalArg, float rotationArg, float maxSpeedArg, float collisionSizeArg,
			float tradeTimeArg, float dockRotationOffsetArg, SpaceStationPart goalPartArg){
		super(xArg, yArg, xGoalArg, yGoalArg, rotationArg, maxSpeedArg, collisionSizeArg);
		cargo = Cargo.EMPTY;
		tradeTime = tradeTimeArg;
		maxTradeTime = tradeTimeArg;
		dockRotationOffset = dockRotationOffsetArg;
		goalPart = goalPartArg;
		idleTime = 1f;
	}
	public boolean isOccupied() {
		for(SpaceStationPart p : LevelShipSequencer.raiderCompParts) {
			for(Ship r : Ship.ships) {
				if(this.goalPart == p && r instanceof Raider && ((Raider)r).docked && r != this) {
					return true;
				}
			}

		}
		return false;
	}

	@Override
	public void update(float delta){
		super.update(delta);
		if(checkCollision() || isDead){
			isDead = true;
			docking = false;
			docked = false;
			isLeaving = true;
			doDeadMovement(delta);
		}else{
			float goalDistance = HvlMath.distance(x, y, xGoal, yGoal);
			if(!docking && goalDistance < AUTO_DOCK_DISTANCE){
				idleTime = HvlMath.stepTowards(idleTime, delta, 0f);
			}
			if(idleTime == 0 && !docking && cargo == Cargo.EMPTY && !isOccupied()){
				if(!shipInProximity(goalPart.x, goalPart.y, SpaceStation.GRID_SIZE)){
					docking = true;
					xGoal = goalPart.x;
					yGoal = goalPart.y;
				}
			}
			goalDistance = HvlMath.distance(x, y, xGoal, yGoal);
			float newRotation = 0f;
			if(docking && goalDistance < AUTO_DOCK_DISTANCE){
				x = xGoal;
				y = yGoal;
				docking = false;
				docked = true;
				if(Main.settings.soundEnabled) Main.getSound(Main.INDEX_ITEM_STOLEN).playAsSoundEffect(1f, 0.25f, false);
			}
			if(!docked){
				speed = HvlMath.stepTowards(speed, delta * maxSpeed, Math.min(maxSpeed, goalDistance));
				HvlCoord2D speedCoord = new HvlCoord2D(xGoal - x, yGoal - y);
				speedCoord.normalize();
				if(Float.isNaN(speedCoord.x)) speedCoord.x = 0;
				if(Float.isNaN(speedCoord.y)) speedCoord.y = 0;
				speedCoord.mult(speed);
				xs = HvlMath.stepTowards(xs, delta * maxSpeed, speedCoord.x);
				ys = HvlMath.stepTowards(ys, delta * maxSpeed, speedCoord.y);
				x += xs * delta;
				y += ys * delta;
				newRotation = (float)Math.toDegrees(HvlMath.fullRadians(new HvlCoord2D(x, y), new HvlCoord2D(xGoal, yGoal)));
			}else{
				for(SpaceStationPart p : SpaceStation.stationParts){
					if(p.x == x && p.y == y && p.getTextureIndex() <= -1) newRotation = p.rotation + dockRotationOffset;
				}
				tradeTime = HvlMath.stepTowards(tradeTime, delta, 0);
				if(tradeTime == 0){

					if(HvlMath.randomFloatBetween(0f, 1f) > 0.5f) cargo = Cargo.FUEL; else cargo = Cargo.ENERGY;
					if(this.y > 360) {
						this.setGoal(this.x-128, 680);
		
					}else {
						this.setGoal(this.x-128, 40);

					}
					docked = false;
					if(cargo == Cargo.FUEL){
						Game.level_fuel -= Game.RESUPPLY_FUEL_AMOUNT * 3f;
						FancyOverlay.spawnFuelTextExplosion(false);
					}else if(cargo == Cargo.ENERGY){
						Game.level_energy -= Game.RESUPPLY_ENERGY_AMOUNT * 2f;
						FancyOverlay.spawnEnergyTextExplosion(false);
					}
				}
			}
			while(Math.abs(newRotation - rotation) > 180f){
				if(Math.abs(newRotation - 360f - rotation) < Math.abs(newRotation + 360f - rotation))
					newRotation -= 360f;
				else newRotation += 360f;
			}
			rotation = HvlMath.stepTowards(rotation, delta * maxSpeed * 2f, newRotation);
		}
	}
	
	public static final float PROGRESS_BAR_WIDTH = 20f, PROGRESS_BAR_HEIGHT = 6f;

	protected void drawTradeProgressBar(float xArg, float yArg){
		if(docked){
			hvlDrawQuadc(xArg, yArg, PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT, Color.red);
			hvlDrawQuadc(xArg, yArg, PROGRESS_BAR_WIDTH - 2, PROGRESS_BAR_HEIGHT - 2, Color.black);
			hvlDrawQuad(xArg - ((PROGRESS_BAR_WIDTH - 2)/2f), yArg - ((PROGRESS_BAR_HEIGHT - 2)/2f), (PROGRESS_BAR_WIDTH - 2) * (1f - (tradeTime / maxTradeTime)), PROGRESS_BAR_HEIGHT - 2, Color.red);
		}
	}

}
