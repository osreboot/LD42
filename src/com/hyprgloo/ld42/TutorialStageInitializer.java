package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.action.HvlAction0;
import com.osreboot.ridhvl.action.HvlAction0r;
import com.osreboot.ridhvl.action.HvlAction2;

public class TutorialStageInitializer {

	public static void initialize(){
		TutorialManager.tutorials.put(new Tutorial(3, new HvlAction2<Float, Integer>(){
			@Override
			public void run(Float delta, Integer stage){
				TutorialManager.emphasize(Ship.ships.get(0).x, Ship.ships.get(0).y, 64, 64);
				if(stage == 0) Main.font.drawWordc("A Ship has just entered your radio space!", 512, Display.getHeight()/4, Color.white, 0.25f);
				if(stage == 1) Main.font.drawWordc("Left-click the ship to command it.", 512, Display.getHeight()/4, Color.white, 0.25f);
				if(stage == 2){
					Main.font.drawWordc("When you see a thin green line", 512, Display.getHeight()/4, Color.white, 0.25f);
					Main.font.drawWordc("you are able to command that ship.", 512, Display.getHeight()/4 + 48f, Color.white, 0.25f);
					hvlDrawQuadc(512, Display.getHeight()/4*3, 192, 192, Main.getTexture(Main.INDEX_TUTORIAL));
				}
			}
		}, new HvlAction0r<Boolean>(){
			@Override
			public Boolean run(){
				return Game.selected_level == 0 && Game.tutorialStageIndex == 0 && Ship.ships.get(0).x > 32f;
			}
		}, new HvlAction0(){
			@Override
			public void run(){
				Game.tutorialStageIndex++;
			}
		}), false);
		TutorialManager.tutorials.put(new Tutorial(3, new HvlAction2<Float, Integer>(){
			@Override
			public void run(Float delta, Integer stage){
				if(stage == 0){
					TutorialManager.emphasize(Ship.ships.get(0).x, Ship.ships.get(0).y, 256, 256);
					Main.font.drawWordc("After selecting a ship with left-click", Display.getWidth()/2, Display.getHeight()/4*3, Color.white, 0.25f);
					Main.font.drawWordc("you can order it to move by right-clicking a location.", Display.getWidth()/2, Display.getHeight()/4*3 + 48f, Color.white, 0.25f);
				}
				if(stage == 1){
					TutorialManager.emphasize(Ship.ships.get(0).x - 8, Ship.ships.get(0).y, 28, 28);
					Main.font.drawWordc("This ship is carrying valuable hyper-fuel.", Display.getWidth()/2, Display.getHeight()/4*3, Color.white, 0.25f);
					Main.font.drawWordc("Our station needs that to jump out of this sector.", Display.getWidth()/2, Display.getHeight()/4*3 + 48f, Color.white, 0.25f);
				}
				if(stage == 2){
					TutorialManager.emphasize(512 + 32f, Display.getHeight()/2, 32, 32);
					Main.font.drawWordc("Right click the docking icon to order the", Display.getWidth()/2, Display.getHeight()/4*3, Color.white, 0.25f);
					Main.font.drawWordc("ship to dock with our station.", Display.getWidth()/2, Display.getHeight()/4*3 + 48f, Color.white, 0.25f);
					hvlDrawQuadc(Display.getWidth()/4*3, Display.getHeight()/4*2, 192, 192, Main.getTexture(Main.INDEX_TUTORIAL + 1));
				}
			}
		}, new HvlAction0r<Boolean>(){
			@Override
			public Boolean run(){
				return Game.selected_level == 0 && Game.tutorialStageIndex == 1 && ShipSelector.selectedShip != null;
			}
		}, new HvlAction0(){
			@Override
			public void run(){
				Game.tutorialStageIndex++;
			}
		}), false);
		
		TutorialManager.tutorials.put(new Tutorial(2, new HvlAction2<Float, Integer>(){
			@Override
			public void run(Float delta, Integer stage){
				
				if(stage == 0){
					TutorialManager.emphasize(Ship.ships.get(1).x, Ship.ships.get(1).y, 64, 64);
					Main.font.drawWordc("This is a ship, idiot.", 400, 200, Color.white, 0.25f);
				}
				if(stage == 1){
					TutorialManager.emphasize(512 - 32f, Display.getHeight()/2, 32, 32);
					Main.font.drawWordc("It flies. Like ships do.", 400, 200, Color.white, 0.25f);
				}
			}
		}, new HvlAction0r<Boolean>(){
			@Override
			public Boolean run(){
				return Game.selected_level == 0 && Game.tutorialStageIndex == 2 && Ship.ships.get(1).x > 32f;
			}
		}, new HvlAction0(){
			@Override
			public void run(){
				Game.tutorialStageIndex++;
			}
		}), false);
	}
	
}
