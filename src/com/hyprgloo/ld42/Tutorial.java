package com.hyprgloo.ld42;

import com.osreboot.ridhvl.action.HvlAction0;
import com.osreboot.ridhvl.action.HvlAction0r;
import com.osreboot.ridhvl.action.HvlAction2;

public class Tutorial {

	int stage, maxStage;
	public HvlAction2<Float, Integer> display;
	public HvlAction0r<Boolean> isTriggered;
	public HvlAction0 conclude;

	public Tutorial(int maxStageArg, HvlAction2<Float, Integer> displayArg, HvlAction0r<Boolean> isTriggeredArg, HvlAction0 concludeArg){
		stage = 0;
		maxStage = maxStageArg;
		display = displayArg;
		isTriggered = isTriggeredArg;
		conclude = concludeArg;
	}
}
