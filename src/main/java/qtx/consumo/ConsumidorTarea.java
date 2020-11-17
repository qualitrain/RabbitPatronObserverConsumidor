package qtx.consumo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ConsumidorTarea {
	private String nombreColaTareas;
	private String hostRabbitMQ;
	private Connection conexion;
	private Channel canal;
	private String idConsumidor;
	private static final int MAX_CANT_MENSAJES = 1;
	
	public ConsumidorTarea(String nombreColaTareas, String hostRabbitMQ) {
		super();
		this.nombreColaTareas = nombreColaTareas;
		this.hostRabbitMQ = hostRabbitMQ;
	}

	public ConsumidorTarea() {
		super();
		this.nombreColaTareas = "colaTareas01";
		this.hostRabbitMQ = "localhost";
	}

	public void consumirTareas() {
		ConnectionFactory fabricaConexiones = new ConnectionFactory();
		fabricaConexiones.setHost(this.hostRabbitMQ);
		try {
			this.conexion = fabricaConexiones.newConnection();
			this.canal = conexion.createChannel();
			
			canal.queueDeclare(this.nombreColaTareas, true, false, false, null);
			canal.basicQos(MAX_CANT_MENSAJES);
			DeliverCallback procesadorMensajes = getProcesadorMensajes();
			CancelCallback procesadorCancelacion = getProcesadorCancelacion();
			this.idConsumidor = canal.basicConsume(this.nombreColaTareas, false, procesadorMensajes, procesadorCancelacion );
		}
		catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		finally {
			
		}
	}
	private CancelCallback getProcesadorCancelacion() {
		return consumerTag-> {
		};
	} 

	private DeliverCallback getProcesadorMensajes() {
		DeliverCallback callback = (consumerTag, objMensaje) -> {
			String contenido = new String(objMensaje.getBody(),"UTF-8");
			System.out.println("Mensaje recibido: [" + contenido + "]");
			try {
				procesarMensaje(contenido);
			}
			finally {
				System.out.println("Mensaje procesado");
//**			basicAck(deliveryTag, boolean multiple)
				this.canal.basicAck(objMensaje.getEnvelope().getDeliveryTag(), false);
			}
		};
		return callback;
	}

	private void procesarMensaje(String contenido) {
		System.out.println(this.idConsumidor + "-> Procesando mensaje:[" + contenido + "]");
		try {
			Thread.sleep(1000 * ((contenido.length() % 5) + 1) );
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
}
