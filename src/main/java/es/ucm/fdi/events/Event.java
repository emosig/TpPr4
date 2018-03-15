package es.ucm.fdi.events;

import java.util.ArrayList;

import es.ucm.fdi.exceptions.IdException;
import es.ucm.fdi.exceptions.MissingObjectExc;
import es.ucm.sim.Simulator;
import es.ucm.sim.obj.Junction;
import es.ucm.sim.obj.Road;
import es.ucm.sim.obj.Vehicle;

public abstract class Event {
	protected int time;
	protected final String name;
	protected boolean done; //se pone a true al ejecutar y garantiza la no repetición de eventos
	
	public Event(int time, String name) {
		this.time = time;
		this.name = name;
		done = false;
	}
	
	public boolean getDone() {
		return done;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTime() {
		return time;
	}
	
	public abstract void ejecuta(Simulator s, ArrayList<Junction> js, ArrayList<Road> rs, ArrayList<Vehicle> vs) throws MissingObjectExc, IdException;
}
