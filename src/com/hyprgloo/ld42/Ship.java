package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class Ship {

	public static final float AUTO_DOCK_DISTANCE = 5f;

	public static ArrayList<Ship> ships = new ArrayList<>();

	public static void updateShips(float delta){
		for(Ship s : ships) s.update(delta);
		for(Ship s : ships) s.draw(delta);
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

	public float x, y, xs, ys, speed, xGoal, yGoal, rotation, maxSpeed;

	public Ship(float xArg, float yArg, float xGoalArg, float yGoalArg, float rotationArg, float maxSpeedArg){
		x = xArg;
		y = yArg;
		xs = 0;
		ys = 0;
		speed = 0;
		xGoal = xGoalArg;
		yGoal = yGoalArg;
		rotation = rotationArg;
		maxSpeed = maxSpeedArg;
		ships.add(this);
	}

	public void update(float delta){
		float goalDistance = HvlMath.distance(x, y, xGoal, yGoal);
		float newRotation = 0f;
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
		while(Math.abs(newRotation - rotation) > 180f){
			if(Math.abs(newRotation - 360f - rotation) < Math.abs(newRotation + 360f - rotation))
				newRotation -= 360f;
			else newRotation += 360f;
		}
		if(goalDistance > 64f) rotation = HvlMath.stepTowards(rotation, delta * maxSpeed * 2f, newRotation);
	}

	public void draw(float delta){
		hvlDrawQuadc(xGoal, yGoal, 10, 10, new Color(1f, 1f, 1f, 0.1f));
	}

	public void setGoal(float xArg, float yArg){
		xGoal = xArg;
		yGoal = yArg;
	}

}
