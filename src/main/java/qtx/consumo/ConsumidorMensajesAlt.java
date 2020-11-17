package qtx.consumo;

public class ConsumidorMensajesAlt extends ConsumidorMensajes {
	@Override
	protected void procesarMensaje(String contenido) {
		System.out.println("*** Procesando mensaje x consumidor Alterno:[" + contenido + "] ***");
		
	}
}
