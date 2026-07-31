package com.liamreal.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

//Singeton pattern
public class Controller implements KeyListener {
	// will use these maps to dynamically access interactions in O(1) time
	private Map<String, Boolean> activeControls;
	private Map<KeyEvent, String> keyControls;
	// key mappings for all player controls, static so can see what controls (keys) player has that you can do
	public static HashSet<String> availableControls;
	static {
		availableControls = new HashSet<>();
		availableControls.add("up");
		availableControls.add("down");
		availableControls.add("left");
		availableControls.add("right");
		availableControls.add("use");
	}


	public Controller(Map<KeyEvent, String> keyControls) { 
		// KeyboardFocusManager.getCurrentKeyboardFocusManager()
		// .addKeyEventDispatcher(e -> {
		//     return false;
		// });

		// verify control keys passed in
		this.verifyKeyControls(keyControls);
		this.initialiseActiveControls();
		// since all controls valid, assign them
		this.keyControls = keyControls;
	}

	// set all keys to not be pressed on construction
	void initialiseActiveControls() {
		for (String c : availableControls) { activeControls.put(c, false); }
	}

	// ensure that key controls have all valid values for controls and no extra unnecessary controls
	void verifyKeyControls(Map<KeyEvent, String> keyControls) {
		// will need to make sure all possible controls have been assigned
		Collection<String> createdControls = keyControls.values();
		for (String c : createdControls) {
			// if there is an invalid control name (for example "yup" instead of "up"), throw error (not a valid control)
			if (!availableControls.contains(c)) {
				throw new RuntimeException(String.format("%s is not a valid control! Make sure assign ALL and only valid controls!", c));
			}
		}
		// if there wasnt an invalid control, could still have less controls than we need to specify
		if (createdControls.size() < availableControls.size()) {
			throw new RuntimeException("One or more controls have not been assigned! Ensure all controls have been assigned!");
		}
	}

	@Override
	// need to add this dummy to fulfil interface
	public void keyTyped(KeyEvent e) {}

	// used to set active/inactive control
	void setControl(KeyEvent e, boolean active) {
		if (keyControls.containsKey(e)) {
			activeControls.put(keyControls.get(e), active);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) { this.setControl(e, true); }
	
	@Override
	public void keyReleased(KeyEvent e) { this.setControl(e, false); }
}

/*
 * 
 * KEYBOARD :-) . can you add a mouse or a gamepad 

 *@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ @@@@@@@@@@@@@@@

  @@@     @@@@    @@@@    @@@@    @@@@     @@@     @@@     @@@     @@@     @@@  

  @@@     @@@     @@@     @@@@     @@@     @@@     @@@     @@@     @@@     @@@  

  @@@     @@@     @@@     @@@@    @@@@     @@@     @@@     @@@     @@@     @@@  

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

@     @@@     @@@     @@@      @@      @@@     @@@     @@@     @@@     @@@     @

@     @@@   W   @@@     @@@      @@      @@@     @@@     @@@     @@@     @@@     @

@@    @@@@     @@@@    @@@@    @@@@    @@@@     @@@     @@@     @@@     @@@     @

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@N@@@@@@@@@@@@@@@@@@@@@@@@@@@

@@@     @@@      @@      @@      @@      @@@     @@@     @@@     @@@     @@@    

@@@   A   @@@  S     @@  D     @@      @@@     @@@     @@@     @@@     @@@     @@@    

@@@@ @  @@@@@@@@@@@@ @@@@@@@    @@@@@@@@@@@@    @@@@@@@@@@@@     @@@@   @@@@@   

    @@@     @@@@    @@@@    @@@@    $@@@     @@@     @@@     @@@     @@@     @@@

    @@@ $   @@@      @@      @@ /Q   @@ ]M   @@@     @@@     @@@     @@@     @@@

    @@@     @@@      @@      @@      @@      @@@     @@@     @@@     @@@     @@@

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

@       @@@                                                @@@       @@@       @

@       @@@              SPACE KEY       @@@        @@ PQ     

@       @@@                                                @@@        @@        

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * 
 * 
 * 
 * 
 * 
 */
