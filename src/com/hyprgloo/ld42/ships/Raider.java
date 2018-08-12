package com.hyprgloo.ld42.ships;

import com.hyprgloo.ld42.Cargo;
import com.hyprgloo.ld42.FancyOverlay;
import com.hyprgloo.ld42.Game;
import com.hyprgloo.ld42.Ship;
import com.hyprgloo.ld42.SpaceStation;
import com.hyprgloo.ld42.SpaceStationPart;
import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class Raider extends Ship{

	public float tradeTime, dockRotationOffset, idleTime;
	public Cargo cargo;
	public boolean docking = false, docked = false;
	SpaceStationPart goalPart;

	public Raider(float xArg, float yArg, float xGoalArg, float yGoalArg, float rotationArg, float maxSpeedArg, float collisionSizeArg,
			float tradeTimeArg, float dockRotationOffsetArg, SpaceStationPart goalPartArg){
		super(xArg, yArg, xGoalArg, yGoalArg, rotationArg, maxSpeedArg, collisionSizeArg);
		cargo = Cargo.EMPTY;
		tradeTime = tradeTimeArg;
		dockRotationOffset = dockRotationOffsetArg;
		goalPart = goalPartArg;
		idleTime = 3f;
	}

	@Override
	public void update(float delta){
		super.update(delta);
		if(checkCollision() || isDead){
			if(!isDead) Game.collisions++;
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
			if(idleTime == 0 && !docking && cargo == Cargo.EMPTY){
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
						this.setGoal(this.x, 680);
		
					}else {
						this.setGoal(this.x, 40);

					}
					docked = false;
					if(cargo == Cargo.FUEL){
						Game.level_fuel -= Game.RESUPPLY_FUEL_AMOUNT;
						FancyOverlay.spawnFuelTextExplosion(false);
					}else if(cargo == Cargo.ENERGY){
						Game.level_energy -= Game.RESUPPLY_ENERGY_AMOUNT;
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

}
