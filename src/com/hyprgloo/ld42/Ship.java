package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.hyprgloo.ld42.ships.ShipMerchant;
import com.osreboot.ridhvl.HvlMath;

public class Ship {

	public static final float 
	AUTO_DOCK_DISTANCE = 5f,
	HOLDING_SPEED = 30f;

	public static ArrayList<Ship> ships = new ArrayList<>();

	public static void updateShips(float delta){
		ArrayList<Ship> cleanup = new ArrayList<>();
		for(Ship s : ships){
			s.update(delta);
			if(s.isDead && s.deadLife == 0) cleanup.add(s);
		}
		for(Ship s : ships) s.draw(delta);
		for(Ship s : cleanup) ships.remove(s);
	}

	public static int getMerchantCount(){
		int count = 0;
		for(Ship s : ships) if(s instanceof ShipMerchant) count++;
		return count;
	}

	public static boolean shipInProximity(float xArg, float yArg, float distance){
		for(Ship s : ships){
			if(HvlMath.distance(s.x, s.y, xArg, yArg) < distance) return true;
		}
		return false;
	}

	public static boolean shipInProximity(float xArg, float yArg, float distance, Ship exception){
		for(Ship s : ships){
			if(HvlMath.distance(s.x, s.y, xArg, yArg) < distance && s != exception) return true;
		}
		return false;
	}

	public float x, y, xs, ys, speed, xGoal, yGoal, rotation, deadRotationSpeed, deadLife, maxSpeed, realMaxSpeed, collisionSize;
	public boolean isLeaving = false, isDead = false, holding = true;

	public Ship(float xArg, float yArg, float xGoalArg, float yGoalArg, float rotationArg, float maxSpeedArg, float collisionSizeArg){
		x = xArg;
		y = yArg;
		xs = 0;
		ys = 0;
		speed = 0;
		xGoal = xGoalArg;
		yGoal = yGoalArg;
		rotation = rotationArg;
		maxSpeed = maxSpeedArg;
		realMaxSpeed = maxSpeedArg;
		collisionSize = collisionSizeArg;
		deadLife = 5f;
		ships.add(this);
	}

	public void update(float delta){
		float goalDistance = HvlMath.distance(x, y, xGoal, yGoal);
		if(goalDistance < AUTO_DOCK_DISTANCE && (y < LevelShipSequencer.SHIP_SPAWN_EDGE_SPACING || 
				y > Display.getHeight() - LevelShipSequencer.SHIP_SPAWN_EDGE_SPACING)){
			holding = true;
		}
		if(holding){
			maxSpeed = HOLDING_SPEED; 
			xGoal = Display.getWidth() + 128f;
			yGoal = y;
		}else maxSpeed = realMaxSpeed;
		if(x > Display.getWidth() - 96){
			isLeaving = true;
			xGoal = Display.getWidth() + 128;
			yGoal = y;
		}
		if(x > Display.getWidth() + 64 || y < -64 || y > Display.getHeight() + 64) isDead = true;
	}

	public void draw(float delta){
		if(!isDead && !isLeaving)
			hvlDrawQuadc(xGoal, yGoal, 10, 10, new Color(1f, 1f, 1f, 0.1f));
	}

	public void setGoal(float xArg, float yArg){
		xGoal = xArg;
		yGoal = yArg;
		holding = false;
	}

	public boolean checkCollision(){
		for(Ship s : ships){
			if(HvlMath.distance(s.x, s.y, x, y) < s.collisionSize + collisionSize && s != this) return true;
		}
		return false;
	}

	public void doDeadMovement(float delta){
		deadLife = HvlMath.stepTowards(deadLife, delta, 0f);
		if(deadRotationSpeed == 0) deadRotationSpeed = HvlMath.randomFloatBetween(-90, 90);
		xs = HvlMath.stepTowards(xs, delta * 20f, 0f);
		ys = HvlMath.stepTowards(ys, delta * 20f, 0f);
		x += xs * delta;
		y += ys * delta;
		rotation += deadRotationSpeed * delta;
	}

}
