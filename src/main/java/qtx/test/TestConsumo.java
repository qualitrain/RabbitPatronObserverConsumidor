package qtx.test;

import qtx.consumo.ConsumidorMensajes;
import qtx.consumo.ConsumidorMensajesAlt;

public class TestConsumo {

	public static void main(String[] args) {
		
		ConsumidorMensajes consumidor = new ConsumidorMensajes();
		consumidor.suscribirseA("exClientesNuevos", "colaCosumidor01");
		
		ConsumidorMensajes consumidor2 = new ConsumidorMensajesAlt();
		consumidor2.suscribirseA("exClientesNuevos", "colaCosumidor02");
		
		consumidor.consumirMensajes();
		consumidor2.consumirMensajes();						
	}

}
