package com.hyprgloo.ld42;

import org.lwjgl.input.Mouse;

import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.input.HvlInput;
import com.osreboot.ridhvl.painter.HvlCursor;

public class ShipSelector {
	public static Ship selectedShip;
	static HvlInput selectShip;
	static HvlInput chooseLocation;
	public static Ship getClosestShip() {
		Ship closestShip = null;
		if(Ship.ships.size() > 0) {
			for(Ship allShips : Ship.ships) {
				if(closestShip == null) {
					closestShip = allShips;
				}
				float distance = HvlMath.distance(HvlCursor.getCursorX(), HvlCursor.getCursorY(), closestShip.x, closestShip.y);
				float distanceTest = HvlMath.distance(HvlCursor.getCursorX(), HvlCursor.getCursorY(), allShips.x, allShips.y);

				if(distanceTest < distance) {
					closestShip = allShips;
				}
			}
		}
		return closestShip;
	}
	
	public static void initialize() {
		selectShip = new HvlInput(new HvlInput.InputFilter() {
			@Override
			public float getCurrentOutput() {
				if(Mouse.isButtonDown(0)) {
					return 1;
				}
				else {
					return 0;
				}
			}
		});
		chooseLocation = new HvlInput(new HvlInput.InputFilter() {
			@Override
			public float getCurrentOutput() {
				if(Mouse.isButtonDown(1)) {
					return 1;
				}
				else {
					return 0;
				}
			}
		});
		selectShip.setPressedAction(new HvlAction1<HvlInput>() {
			@Override
			public void run(HvlInput a) {
				selectedShip = getClosestShip();
			}
		});
		chooseLocation.setPressedAction(new HvlAction1<HvlInput>() {
			@Override
			public void run(HvlInput a) {
				selectedShip.setGoal(HvlCursor.getCursorX(), HvlCursor.getCursorY());
			}
		});
	}
	public static void update(float delta) {
		
	}
}
