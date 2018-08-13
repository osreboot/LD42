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
		
		tutorials.put(new Tutorial(5, new HvlAction2<Float, Integer>(){
			@Override
			public void run(Float delta, Integer stage){
				if(stage == 0){
					emphasize(250, 375, 475, 300);
					Main.font.drawWordc("This station has flight paths.", Display.getWidth()/2, Display.getHeight()/4*3, Color.white, 0.25f);
				}
				if(stage == 1){
					emphasize(775, 350, 250, 400);
					Main.font.drawWordc("Flight paths are good for keeping", Display.getWidth()/2, Display.getHeight()/4*3, Color.white, 0.25f);
					Main.font.drawWordc("ships organized.", Display.getWidth()/2, Display.getHeight()/4*3 + 48f, Color.white, 0.25f);
				}
				if(stage == 2){
					emphasize(750, 250 - 48, FlightPath.BOX_WIDTH + 32f, FlightPath.BOX_HEIGHT + 32f);
					Main.font.drawWordc("Tell a ship to use a flight pattern by", Display.getWidth()/2, Display.getHeight()/4*3, Color.white, 0.25f);
					Main.font.drawWordc("directing it towards the pattern's label.", Display.getWidth()/2, Display.getHeight()/4*3 + 48f, Color.white, 0.25f);
					hvlDrawQuadc(Display.getWidth()/8*3, Display.getHeight()/8*2, 192, 192, Main.getTexture(Main.INDEX_TUTORIAL + 7));
				}
				if(stage == 3){
					emphasize(750, 250 - 48, FlightPath.BOX_WIDTH + 32f, FlightPath.BOX_HEIGHT + 32f);
					Main.font.drawWordc("Flight path movement can be cancelled at", Display.getWidth()/2, Display.getHeight()/4*3, Color.white, 0.25f);
					Main.font.drawWordc("any time.", Display.getWidth()/2, Display.getHeight()/4*3 + 48f, Color.white, 0.25f);
				}
				if(stage == 4){
					emphasize(750, 250 - 48, FlightPath.BOX_WIDTH - 32f, FlightPath.BOX_HEIGHT - 32f);
					Main.font.drawWordc("Flight paths are labeled by ship size, using", Display.getWidth()/2, Display.getHeight()/4*3, Color.white, 0.25f);
					Main.font.drawWordc("inappropriate sizes won't guarantee safety!", Display.getWidth()/2, Display.getHeight()/4*3 + 48f, Color.white, 0.25f);
				}
			}
		}, new HvlAction0r<Boolean>(){
			@Override
			public Boolean run(){
				return Main.settings.tutorialsEnabled && Game.selected_level >= 2;
			}
		}, new HvlAction0(){
			@Override
			public void run(){
				Main.settings.tutorial0Completed = true;
				Main.saveConfig();
			}
		}), Main.settings.tutorial0Completed);
		
		tutorials.put(new Tutorial(5, new HvlAction2<Float, Integer>(){
			@Override
			public void run(Float delta, Integer stage){
				if(stage == 0){
					emphasize(Display.getWidth()/2, Display.getHeight()/2, 256 + 16, 128);
					Main.font.drawWordc("There's a lot to do on board a space station,", Display.getWidth()/2, Display.getHeight()/4*3, Color.white, 0.25f);
					Main.font.drawWordc("so stay focused.", Display.getWidth()/2, Display.getHeight()/4*3 + 48f, Color.white, 0.25f);
				}
				if(stage == 1){
					emphasize(FancyOverlay.GAME_LEVEL_FUEL_X + 64, 16, 192, 24);
					Main.font.drawWordc("You need to jump your ship out of this sector.", Display.getWidth()/2, Display.getHeight()/4*3, Color.white, 0.25f);
					Main.font.drawWordc("Fill this bar up with hyper-fuel to win.", Display.getWidth()/2, Display.getHeight()/4*3 + 48f, Color.white, 0.25f);
					hvlDrawQuadc(FancyOverlay.GAME_LEVEL_FUEL_X + 64, 128, 96, 96, Main.getTexture(Main.INDEX_CANISTER_FUEL));
				}
				if(stage == 2){
					emphasize(FancyOverlay.GAME_LEVEL_ENERGY_X + 64, 16, 192, 24);
					Main.font.drawWordc("A station uses up a lot of power, keep us", Display.getWidth()/2, Display.getHeight()/4*3, Color.white, 0.25f);
					Main.font.drawWordc("charged to stay in the game.", Display.getWidth()/2, Display.getHeight()/4*3 + 48f, Color.white, 0.25f);
					hvlDrawQuadc(FancyOverlay.GAME_LEVEL_ENERGY_X + 64, 128, 96, 96, Main.getTexture(Main.INDEX_CANISTER_ENERGY));
				}
				if(stage == 3){
					emphasize(64, Display.getHeight() - 32, 192, 32);
					Main.font.drawWordc("You can't win if you're a serial killer.", Display.getWidth()/2, Display.getHeight()/4*3, Color.white, 0.25f);
					Main.font.drawWordc("Play by the rules and don't murder ships.", Display.getWidth()/2, Display.getHeight()/4*3 + 48f, Color.white, 0.25f);
					Main.font.drawWord("disasters: 0", 8f, Display.getHeight() - 24f, Color.white, 0.125f);
				}
				if(stage == 4){
					emphasize(Display.getWidth()/2, Display.getHeight()/2, 256 + 16, 128);
					Main.font.drawWordc("Good luck!", Display.getWidth()/2, Display.getHeight()/4*3, Color.white, 0.25f);
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
				Main.settings.tutorial1Completed = true;
				Main.saveConfig();
			}
		}), Main.settings.tutorial1Completed);
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
