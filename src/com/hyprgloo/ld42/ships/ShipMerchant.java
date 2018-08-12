package com.hyprgloo.ld42.ships;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.hyprgloo.ld42.Cargo;
import com.hyprgloo.ld42.FancyOverlay;
import com.hyprgloo.ld42.FlightPath;
import com.hyprgloo.ld42.Game;
import com.hyprgloo.ld42.LevelShipSequencer;
import com.hyprgloo.ld42.Ship;
import com.hyprgloo.ld42.ShipSelector;
import com.hyprgloo.ld42.SpaceStation;
import com.hyprgloo.ld42.SpaceStationPart;
import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class ShipMerchant extends Ship{

	public float tradeTime, maxTradeTime, dockRotationOffset, cargoMultiplier;
	public Cargo cargo;
	public boolean docking = false, docked = false;
	public int dockingReq;

	public FlightPath flightPath;
	public int flightPathIndex;

	public ShipMerchant(float xArg, float yArg, float xGoalArg, float yGoalArg, float rotationArg, float maxSpeedArg, float collisionSizeArg,
			Cargo cargoArg, float tradeTimeArg, float dockRotationOffsetArg, int dockingReqArg, float cargoMultiplierArg){
		super(xArg, yArg, xGoalArg, yGoalArg, rotationArg, maxSpeedArg, collisionSizeArg);
		cargo = cargoArg;
		tradeTime = tradeTimeArg;
		maxTradeTime = tradeTimeArg;
		dockRotationOffset = dockRotationOffsetArg;
		dockingReq = dockingReqArg;
		cargoMultiplier = cargoMultiplierArg;
		flightPathIndex = 0;
	}

	@Override
	public void update(float delta){
		super.update(delta);
		if(checkCollision() || isDead){
			if(!isDead && x < Display.getWidth() && x > 0) Game.collisions++;
			isDead = true;
			docking = false;
			docked = false;
			isLeaving = true;
			doDeadMovement(delta);
		}else{
			float goalDistance = HvlMath.distance(x, y, xGoal, yGoal);
			float newRotation = 0f;
			if(flightPath != null){
				holding = false;
				xGoal = flightPath.path.get(flightPathIndex).c.x;
				yGoal = flightPath.path.get(flightPathIndex).c.y;
				goalDistance = HvlMath.distance(x, y, xGoal, yGoal);
				if(goalDistance < AUTO_DOCK_DISTANCE){
					do{
						if(flightPathIndex < flightPath.path.size() - 1 && !isShipAheadInFlightPath(1)) 
							flightPathIndex++; else break;
					}while(flightPath.path.get(flightPathIndex).buffer == true);
				}
			}else flightPathIndex = 0;
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
					if(p.x == x && p.y == y && p.textureIndex <= dockingReq) newRotation = p.rotation + dockRotationOffset;
				}
				tradeTime = HvlMath.stepTowards(tradeTime, delta, 0);
				if(tradeTime == 0){

					docked = false;
					if(cargo == Cargo.FUEL){
						Game.level_fuel += Game.RESUPPLY_FUEL_AMOUNT * cargoMultiplier;
						FancyOverlay.spawnFuelTextExplosion(true);
					}else if(cargo == Cargo.ENERGY){
						Game.level_energy += Game.RESUPPLY_ENERGY_AMOUNT * cargoMultiplier;
						FancyOverlay.spawnEnergyTextExplosion(true);
					}else if(cargo == Cargo.AMMO){
						Game.level_ammo += Game.RESUPPLY_AMMO_AMOUNT * cargoMultiplier;
						FancyOverlay.spawnAmmoTextExplosion(true);
					}
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
	}

	@Override
	public void setGoal(float xArg, float yArg){
		super.setGoal(xArg, yArg);
		flightPath = null;
		flightPathIndex = 0;
	}

	public boolean canDock(int dockingReqArg){
		return !docked && cargo != Cargo.EMPTY && dockingReq >= dockingReqArg;
	}

	public void setFlightPath(FlightPath flightPathArg){
		flightPath = flightPathArg;
		flightPathIndex = 0;
		holding = false;
	}

	public boolean isShipAheadInFlightPath(int steps){
		for(Ship s : ships){
			if(s instanceof ShipMerchant && s != this){
				ShipMerchant ship = (ShipMerchant)s;
				if(ship.flightPath == this.flightPath && ship.flightPathIndex == this.flightPathIndex + steps) return true;
			}
		}
		return false;
	}

	public static final float PROGRESS_BAR_WIDTH = 20f, PROGRESS_BAR_HEIGHT = 6f;

	protected void drawTradeProgressBar(float xArg, float yArg){
		if(docked){
			hvlDrawQuadc(xArg, yArg, PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT, Color.green);
			hvlDrawQuadc(xArg, yArg, PROGRESS_BAR_WIDTH - 2, PROGRESS_BAR_HEIGHT - 2, Color.black);
			hvlDrawQuad(xArg - ((PROGRESS_BAR_WIDTH - 2)/2f), yArg - ((PROGRESS_BAR_HEIGHT - 2)/2f), (PROGRESS_BAR_WIDTH - 2) * (1f - (tradeTime / maxTradeTime)), PROGRESS_BAR_HEIGHT - 2, Color.green);
		}
	}

}
