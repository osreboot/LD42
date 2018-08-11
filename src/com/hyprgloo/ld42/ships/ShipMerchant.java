package com.hyprgloo.ld42.ships;

import com.hyprgloo.ld42.Cargo;
import com.hyprgloo.ld42.Ship;
import com.hyprgloo.ld42.SpaceStation;
import com.hyprgloo.ld42.SpaceStationPart;
import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class ShipMerchant extends Ship{

	public float tradeTime, dockRotationOffset;
	public Cargo cargo;
	public boolean docking = false, docked = false;
	
	public ShipMerchant(float xArg, float yArg, float xGoalArg, float yGoalArg, float rotationArg, float maxSpeedArg,
			Cargo cargoArg, float tradeTimeArg, float dockRotationOffsetArg){
		super(xArg, yArg, xGoalArg, yGoalArg, rotationArg, maxSpeedArg);
		cargo = cargoArg;
		tradeTime = tradeTimeArg;
		dockRotationOffset = dockRotationOffsetArg;
	}

	@Override
	public void update(float delta){
		float goalDistance = HvlMath.distance(x, y, xGoal, yGoal);
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
				if(p.x == x && p.y == y && p.textureIndex == -1) newRotation = p.rotation + dockRotationOffset;
			}
			tradeTime = HvlMath.stepTowards(tradeTime, delta, 0);
			if(tradeTime == 0){
				docked = false;
				
				//TODO add items from transaction here, successful transaction
				cargo = Cargo.EMPTY;
			}
		}
		while(Math.abs(newRotation - rotation) > 180f){
			if(Math.abs(newRotation - 360f - rotation) < Math.abs(newRotation + 360f - rotation))
				newRotation -= 360f;
			else newRotation += 360f;
		}
		if(goalDistance > 64f || docking || docked) rotation = HvlMath.stepTowards(rotation, delta * maxSpeed * 2f, newRotation);
	}
	
	public boolean canDock(){
		return !docked && cargo != Cargo.EMPTY;
	}
	
}
