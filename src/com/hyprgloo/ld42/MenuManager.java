package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.HashMap;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.action.HvlAction2;
import com.osreboot.ridhvl.menu.HvlButtonMenuLink;
import com.osreboot.ridhvl.menu.HvlComponent;
import com.osreboot.ridhvl.menu.HvlComponentDefault;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox;
import com.osreboot.ridhvl.menu.component.HvlButton;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox.ArrangementStyle;
import com.osreboot.ridhvl.menu.component.HvlComponentDrawable;
import com.osreboot.ridhvl.menu.component.HvlSpacer;
import com.osreboot.ridhvl.menu.component.collection.HvlLabeledButton;

public class MenuManager {
	
	public static final float BUTTON_SPACING = 16f;
	
	private static HashMap<HvlLabeledButton, ButtonWrapper> buttonWrappers = new HashMap<>();

	public static HvlMenu main, levels, game;
	
	public static void initialize(){
		
		HvlArrangerBox defaultArrangerBox = new HvlArrangerBox(Display.getWidth(), Display.getHeight(), ArrangementStyle.VERTICAL);
		defaultArrangerBox.setxAlign(0.05f);
		HvlComponentDefault.setDefault(defaultArrangerBox);
		
		HvlLabeledButton defaultLabeledButton = new HvlLabeledButton(256, 64, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
				hvlDrawQuad(xArg, yArg, widthArg, heightArg, Main.COLOR_BLUE3);
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
				hvlDrawQuad(xArg, yArg, widthArg, heightArg, Main.COLOR_BLUE4);
			}
		}, Main.font, "", Color.white);
		defaultLabeledButton.setUpdateOverride(new HvlAction2<HvlComponent, Float>(){
			@Override
			public void run(HvlComponent aArg, Float delta){
				HvlLabeledButton b = (HvlLabeledButton)aArg;
				if(!buttonWrappers.containsKey(b)) buttonWrappers.put(b, new ButtonWrapper());
				b.update(delta);
			}
		});
		defaultLabeledButton.setTextScale(0.25f);
		defaultLabeledButton.setyAlign(0.7f);
		defaultLabeledButton.setxAlign(0.5f);
		HvlComponentDefault.setDefault(defaultLabeledButton);
		
		main = new HvlMenu();
		levels = new HvlMenu();
		game = new HvlMenu();
		
		main.add(new HvlArrangerBox.Builder().build());
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("start").setClickedCommand(new HvlButtonMenuLink(levels)).build());
		main.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("credits").build());
		main.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("options").build());
		main.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("exit").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				System.exit(0);
			}
		}).build());
		
		levels.add(new HvlArrangerBox.Builder().build());
		levels.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("tutorial").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				Game.selected_level = 0;
				HvlMenu.setCurrent(game);
				Game.restart();
			}
		}).build());
		levels.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		levels.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("station 1").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				Game.selected_level = 1;
				HvlMenu.setCurrent(game);
				Game.restart();
			}
		}).build());
		levels.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		levels.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("station 2").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				Game.selected_level = 2;
				HvlMenu.setCurrent(game);
				Game.restart();
			}
		}).build());
		levels.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		levels.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("station 3").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				Game.selected_level = 3;
				HvlMenu.setCurrent(game);
				Game.restart();
			}
		}).build());
		levels.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		levels.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("back").setClickedCommand(new HvlButtonMenuLink(main)).build());
		
		HvlMenu.setCurrent(main);
	}
	
	public static void update(float delta){
		HvlMenu.updateMenus(delta);
		
		if(HvlMenu.getCurrent() == main || HvlMenu.getCurrent() == levels){
			for(HvlLabeledButton b : buttonWrappers.keySet()){
				buttonWrappers.get(b).update(delta, b.isHovering(), HvlMenu.getCurrent().getFirstArrangerBox().getChildren().contains(b));
				hvlDrawQuad(b.getX() - (buttonWrappers.get(b).fade * BUTTON_TARGET_SIZE/2f), b.getY() - (buttonWrappers.get(b).fade * BUTTON_TARGET_SIZE/2f), 
						b.getWidth() + (buttonWrappers.get(b).fade * BUTTON_TARGET_SIZE), b.getHeight() + (buttonWrappers.get(b).fade * BUTTON_TARGET_SIZE), 
						Main.getTexture(Main.INDEX_BUTTON_TARGET), new Color(1f, 1f, 1f, buttonWrappers.get(b).fade));
			}
		}else if(HvlMenu.getCurrent() == game){
			Game.update(delta);
		}
	}
	
	public static final float BUTTON_TARGET_SIZE = 16f;
	
	private static class ButtonWrapper{
		
		public float fade = 0f;
		
		private ButtonWrapper(){}
		
		private void update(float delta, boolean hovering, boolean menu){
			fade = menu ? HvlMath.stepTowards(fade, delta * 8f, hovering ? 1f : 0f) : 0f;
		}
		
	}
	
}
