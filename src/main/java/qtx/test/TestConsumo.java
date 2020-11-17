package qtx.test;

import qtx.consumo.ConsumidorMensajes;
import qtx.consumo.ConsumidorMensajesAlt;

public class TestConsumo {

	public static void main(String[] args) {
		if(args.length > 0)
			System.out.println("Consumidor " + args[0]);
		
		ConsumidorMensajes consumidor = new ConsumidorMensajes();
		consumidor.suscribirseA("nuevoCliente");
		
		ConsumidorMensajes consumidor2 = new ConsumidorMensajesAlt();
		consumidor2.suscribirseA("nuevoCliente");
		
		consumidor.consumirMensajes();
		consumidor2.consumirMensajes();
		
		
		
	}

}
