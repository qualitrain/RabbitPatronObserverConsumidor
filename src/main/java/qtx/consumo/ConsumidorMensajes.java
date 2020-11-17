package qtx.consumo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ConsumidorMensajes {
	private String nombreCola;
	private String hostRabbitMQ;
	private Connection conexion;
	private Channel canal;
	private String nombreIntermediario;
	private boolean conectado;
	private String idConsumidor;

	public ConsumidorMensajes() {
		super();
		this.nombreCola = "";
		this.hostRabbitMQ = "localhost";
		this.nombreIntermediario = "";
		this.conectado = false;
	}
	public void suscribirseA(String nomIntermediario) {
		this.nombreIntermediario = nomIntermediario;
		ConnectionFactory fabricaConexiones = new ConnectionFactory();
		fabricaConexiones.setHost(this.hostRabbitMQ);
		try {
			this.conexion = fabricaConexiones.newConnection();
			this.canal = conexion.createChannel();
			this.conectado = true;
			this.canal.exchangeDeclare(this.nombreIntermediario, "fanout");
	        this.nombreCola = canal.queueDeclare()
	        		                     .getQueue(); //Cola an�nima
	        // queueBind​(String queue, String exchange, String routingKey)
	        canal.queueBind(this.nombreCola, this.nombreIntermediario, "");		
	    } 
		catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		finally {
			
		}
		
	}
	public void consumirMensajes() {
		if(this.conectado == false) {
			System.out.println("Error: Conexión cerrada");
			return;
		}
		DeliverCallback procesadorMensajes = getProcesadorMensajes();
		CancelCallback procesadorCancelacion = getProcesadorCancelacion();
		try {
			// basicConsume​(String queue, boolean autoAck, Map<String,​Object> arguments, Consumer callback)
			this.idConsumidor = canal.basicConsume(this.nombreCola, true, procesadorMensajes, procesadorCancelacion );
			System.out.println("Consumer Tag:[" + this.idConsumidor + "]");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("Recuperación de mensajes en segundo plano, activa");
			
		}
		
	}
	private DeliverCallback getProcesadorMensajes() {
		DeliverCallback callback = (consumerTag, objMensaje) -> {
			String contenido = new String(objMensaje.getBody(),"UTF-8");
			System.out.println("Mensaje recibido: [" + contenido + "] por " + this.idConsumidor );
			try {
				procesarMensaje(contenido);
			}
			finally {
				System.out.println("Mensaje procesado");
			}
		};
		return callback;
	}
	protected void procesarMensaje(String contenido) {
		System.out.println("Procesando mensaje:[" + contenido + "]");
		try {
			Thread.sleep(1000 * ((contenido.length() % 5) + 1) );
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	private CancelCallback getProcesadorCancelacion() {
		return consumerTag-> {
		};
	} 
}
