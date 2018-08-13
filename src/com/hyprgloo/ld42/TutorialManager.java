package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawLine;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.hyprgloo.ld42.ships.ShipMerchant;
import com.osreboot.ridhvl.action.HvlAction0;
import com.osreboot.ridhvl.action.HvlAction0r;
import com.osreboot.ridhvl.action.HvlAction2;

public class TutorialManager {

	public static void emphasize(float x, float y, float xl, float yl){
		hvlDrawQuad(0, 0, x - (xl/2), Display.getHeight(), new Color(0f, 0f, 0f, 0.5f));
		hvlDrawQuad(x - (xl/2), 0, xl, y - (yl/2), new Color(0f, 0f, 0f, 0.5f));
		hvlDrawQuad(x - (xl/2), y + (yl/2), xl, Display.getHeight() - (y + (yl/2)), new Color(0f, 0f, 0f, 0.5f));
		hvlDrawQuad(x + (xl/2), 0, Display.getWidth() - (x + (xl/2)), Display.getHeight(), new Color(0f, 0f, 0f, 0.5f));
		hvlDrawLine(x - (xl/2), y - (yl/2), x + (xl/2), y - (yl/2), Color.red, 2f);
		hvlDrawLine(x - (xl/2), y - (yl/2), x - (xl/2), y + (yl/2), Color.red, 2f);
		hvlDrawLine(x + (xl/2), y - (yl/2), x + (xl/2), y + (yl/2), Color.red, 2f);
		hvlDrawLine(x - (xl/2), y + (yl/2), x + (xl/2), y + (yl/2), Color.red, 2f);
	}

	public static HashMap<Tutorial, Boolean> tutorials = new HashMap<>();
	public static ArrayList<Tutorial> stageTutorials = new ArrayList<>();

	public static Tutorial current;

	public static void initialize(){
		TutorialStageInitializer.initialize();
		
		tutorials.put(new Tutorial(2, new HvlAction2<Float, Integer>(){
			@Override
			public void run(Float delta, Integer stage){
				if(stage == 1){
					TutorialManager.emphasize(Ship.ships.get(1).x, Ship.ships.get(1).y, 64, 64);
					Main.font.drawWordc("Now tell this ship to exit the radio space.", Display.getWidth()/2, Display.getHeight()/4*3, Color.white, 0.25f);
				}
			}
		}, new HvlAction0r<Boolean>(){
			@Override
			public Boolean run(){
				return Main.settings.tutorialsEnabled && Game.selected_level >= 1;
			}
		}, new HvlAction0(){
			@Override
			public void run(){
				Main.settings.tutorial0Completed = true;
				Main.saveConfig();
			}
		}), Main.settings.tutorial0Completed);
	}

	public static void update(float delta){
		if(current != null){
			current.display.run(delta, current.stage);
			Main.font.drawWordc("<space> to continue...", Display.getWidth()/2, Display.getHeight()/16*15, Color.white, 0.125f);
		}else{
			for(Tutorial t : tutorials.keySet()){
				if(!tutorials.get(t) && t.isTriggered.run()){
					tutorials.put(t, true);
					current = t;
					break;
				}
			}
			if(current == null){
				for(Tutorial t : stageTutorials){
					if(t.isTriggered.run()){
						current = t;
						break;
					}
				}
			}
		}
	}

	public static void advanceTutorial(){
		if(current != null){
			current.stage++;
			if(current.stage >= current.maxStage){
				current.conclude.run();
				current.stage = 0;
				current = null;
			}
		}
	}

}
