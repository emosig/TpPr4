package es.ucm.sim.obj;

import java.util.ArrayList;
import java.util.Map;

import es.ucm.fdi.ini.IniSection;

public class Vehicle extends SimObj{
	private int localizacion;
	private ArrayList<Road> itinerario;
	private int posItinerario; //número de carretera
	private Boolean haLlegado;
	protected int velMaxima;
	protected int velActual;
	protected int tAveria;
	protected int kilometrage() {
		int k = 0;
		for(int i = 0; i < posItinerario; ++i) k+= itinerario.get(i).getLong();
		k += localizacion;
		return k;
	}
	
	public Vehicle(String id) {
		super(id);
		velMaxima = 0;
		localizacion = 0;
		itinerario = new ArrayList<>();
		posItinerario = 0;
		tAveria = 0;
		haLlegado = false;
	}
	public Vehicle(int vMax, ArrayList<Road> it, String id) {
		super(id);
		velMaxima = vMax;
		localizacion = 0;
		itinerario = it;
		posItinerario = 0;
		tAveria = 0;
		haLlegado = false;
	}
	public int getLoc() {
		return localizacion;
	}
	public String getRoad() {
		return itinerario.get(posItinerario).getId();
	}
	public boolean getLlegado() {
		return haLlegado;
	}
	public int getTAveria() {
		return tAveria;
	}
	public void setTiempoAveria(int t){
		if(tAveria < 1) velActual = 0; //Si no estaba parado lo para
		tAveria += t;
	}
	public void setVelocidadActual(int v) {
		if(v < velMaxima) velActual = v;
		else velActual = velMaxima;
	}
	public void avanza() {
		if(tAveria > 0) //está averiado
			--tAveria;
		else {
			localizacion += velActual;
			if(itinerario.get(posItinerario).getLong() < localizacion) {
				localizacion = itinerario.get(posItinerario).getLong(); //localizacion al final de la carretera
				itinerario.get(posItinerario).saleVehiculo(this); //sale de la carretera
				itinerario.get(posItinerario).getFinalJ().entraVehiculo(this); //entra en cruce
				velActual = 0;
			}
		}
	}
	public void moverASiguienteCarretera() {
		//contemplar caso inicial (no está en ninguna carretera??)
		/*if(localizacion != itinerario.get(posItinerario).getLong())
			throw algo*/
		++posItinerario;
		localizacion = 0;
		if(posItinerario == itinerario.size()) haLlegado = true;
	}
	/*public String generaInforme() {
		IniSection ini = new IniSection("vehicle_report");
		ini.setValue("id", getId( ));
		//ini.setValue("time", value); ??
		ini.setValue("speed", velActual);
		ini.setValue("kilometrage", kilometrage());
		ini.setValue("faulty", tAveria);
		ini.setValue("location", "(" + itinerario.get(posItinerario).getId() + "," + localizacion + ")"); //meter 2 valores
		return ini.toString();
	}*/

	@Override
	protected void fillReportDetails(Map<String, String> out) {
		out.put("speed", String.valueOf(velActual));
		out.put("kilometrage", String.valueOf(kilometrage()));
		out.put("faulty", String.valueOf(tAveria));
		out.put("location", "(" + itinerario.get(posItinerario).getId() + "," + localizacion + ")");
	}

	@Override
	protected String getReportHeader() {
		return "vehicle_report";
	}
	
}
