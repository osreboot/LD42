package com.hyprgloo.ld42;

import java.io.Serializable;

public class Settings implements Serializable{
	private static final long serialVersionUID = -2611869798103365523L;
	
	public boolean soundEnabled = true;
	public boolean musicEnabled = true;
	public boolean tutorialsEnabled = true;
	public boolean customCursor = true;
	
	public boolean tutorial0Completed = false;
	public boolean tutorial1Completed = false;
	
}
