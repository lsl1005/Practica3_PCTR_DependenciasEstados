package src.p03.c01;

public class SistemaLanzador {
	public static void main(String[] args) {		
		IParque parque = new Parque(50);
		char letra_puerta = 'A';		
		System.out.println("¡Parque abierto!");
		final int NUMPUERTAS= 5;
		for (int i = 0; i < NUMPUERTAS; i++) {
			String puerta = ""+((char) (letra_puerta++));			
			// Creacion de hilos de entrada
			ActividadEntradaPuerta entradas = new ActividadEntradaPuerta(puerta, parque);
			new Thread (entradas).start();
			// Creacion de hilos de salida
			ActividadSalidaPuerta salidas = new ActividadSalidaPuerta(puerta, parque);
			new Thread (salidas).start();
		}
	}	
}