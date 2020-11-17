package qtx.consumo;

public class ConsumidorMensajesAlt extends ConsumidorMensajes {
	@Override
	protected void procesarMensaje(String contenido) {
		System.out.println("*** Enviando catálogo a nuevo cliente:[" + contenido + "] ***");
		
	}
}
