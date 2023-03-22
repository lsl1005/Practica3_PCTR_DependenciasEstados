package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

public class Parque implements IParque{

	static final long MIN = 0; // m�nimo valor permitido
	static final long MAX = 50; // m�ximo valor permitido 
	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
	}

	@Override
	public void entrarAlParque(String puerta){		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}		
		try {
			comprobarAntesDeEntrar();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		//Comprobamos e informamos
		checkInvariante();
		notifyAll();		
	}
	
	@Override
	public synchronized void salirDelParque(String puerta){
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}		
		try {
			comprobarAntesDeSalir();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		// Reducimos el contador total y el individual
		contadorPersonasTotales--;
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)-1);		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Salida");
		//Comprobamos e informamos
		checkInvariante();
		notifyAll();
	}
	
	
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); //+ " tiempo medio de estancia: "  + tmedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuerta.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}
	
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte.";
		assert contadorPersonasTotales <= MAX : "INV: El n�mero de personas no debe superar el l�mite superior.";
		assert contadorPersonasTotales > (MIN-1) : "INV: El n�mero de personas no debe ser inferior al l�mite.";
	}

	protected void comprobarAntesDeEntrar() throws InterruptedException {
		while (contadorPersonasTotales == MAX) {
			wait();
		}
	}

	protected void comprobarAntesDeSalir() throws InterruptedException {
		while (contadorPersonasTotales == MIN) {
			wait();
		}
	}


}
