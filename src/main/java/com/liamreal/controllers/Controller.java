package com.liamreal.controllers;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Controller {
	// will use these maps to dynamically access interactions in O(1) time
	private Map<String, Boolean> activeControls;
	private Map<Integer, String> keyBindings;
	// key mappings for all player controls, static so can see what controls (keys) player has that you can do
	public static final Set<String> AVAILABLE_CONTROLS = Set.of(
		"up",
		"down",
		"left",
		"right",
		"use"
	);


	public Controller(Map<Integer, String> keyBindings) { 
		// verify control keys passed in
		this.verifyKeyBindings(keyBindings);
		// since all controls valid, assign them
		this.keyBindings = keyBindings;
		this.initialiseActiveControls();
	}

	// get currently active controls as a set -- written by AI for convenience
	public Set<String> getActiveControls() {
		return activeControls.entrySet().stream()
				.filter(Map.Entry::getValue)
				.map(Map.Entry::getKey)
				.collect(Collectors.toSet());
	}

	// set all keys to not be pressed on construction
	private void initialiseActiveControls() {
		this.activeControls = new HashMap<>();
		for (String c : AVAILABLE_CONTROLS) { activeControls.put(c, false); }
	}

	// ensure key controls have all valid values and no missing controls
	void verifyKeyBindings(Map<Integer, String> keyControls) {
		// have to pass in valid map of keys
		if (keyControls == null) { throw new RuntimeException("Key bindings cannot be null!"); }
		Set<String> createdControls = new HashSet<>(keyControls.values());

		// check that exactly all controls are assigned
		if (!createdControls.equals(AVAILABLE_CONTROLS)) {
			throw new RuntimeException("One or more controls have not been assigned! Ensure all controls are assigned exactly once!");
		}
	}

	// handles input
	public void handleInput(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) { this.setControl(e, true); }
		if (e.getID() == KeyEvent.KEY_RELEASED) { this.setControl(e, false); }
	}

	// used to set active/inactive control
	private void setControl(KeyEvent e, boolean active) {
    	int keyCode = e.getKeyCode(); // get key code from event as integer
		if (keyBindings.containsKey(keyCode)) {
			activeControls.put(keyBindings.get(keyCode), active);
		}
	}
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
