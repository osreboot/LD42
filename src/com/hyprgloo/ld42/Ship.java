package com.hyprgloo.ld42;

import java.util.ArrayList;

import com.osreboot.ridhvl.HvlMath;

public abstract class Ship {

	public static ArrayList<Ship> ships = new ArrayList<>();

	public static void updateShips(float delta){
		for(Ship s : ships) s.update(delta);
		for(Ship s : ships) s.draw(delta);
	}
	
	public float x, y, xs, ys, xGoal, yGoal, rotation, maxSpeed;
	
	public Ship(float xArg, float yArg, float xsArg, float ysArg, float xGoalArg, float yGoalArg, float rotationArg, float maxSpeedArg){
		x = xArg;
		y = yArg;
		xs = xsArg;
		ys = ysArg;
		xGoal = xGoalArg;
		yGoal = yGoalArg;
		rotation = rotationArg;
		maxSpeed = maxSpeedArg;
		ships.add(this);
	}
	
	public void update(float delta){
		if(x < xGoal) xs = HvlMath.stepTowards(xs, delta * maxSpeed, Math.min(maxSpeed, Math.abs(x - xGoal)));
		if(x > xGoal) xs = HvlMath.stepTowards(xs, delta * maxSpeed, -Math.min(maxSpeed, Math.abs(x - xGoal)));
		if(y < yGoal) ys = HvlMath.stepTowards(ys, delta * maxSpeed, Math.min(maxSpeed, Math.abs(y - yGoal)));
		if(y > yGoal) ys = HvlMath.stepTowards(ys, delta * maxSpeed, -Math.min(maxSpeed, Math.abs(y - yGoal)));
		
		x += xs * delta;
		y += ys * delta;
	}
	
	public abstract void draw(float delta);
	
	public void setGoal(float xArg, float yArg){
		xGoal = xArg;
		yGoal = yArg;
	}
	
}
