package schema;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
	
	public static Vector <ClientHandler> clients= new Vector<>();

	/**
	 * Puerto por donde el servidor atendera a los clientes
	 */
	public static final int PORT = 8000;
	/**
	 * El servidor dispone de un serversocket, para permitir la conexion a los
	 * clientes
	 */
	private static ServerSocket serverSocket;
	/**
	 * El servidor dispone de un socket para atender a cada cliente por individual
	 */
	private static Socket socket;

	public static void main(String[] args) {

		DataInputStream in;
		DataOutputStream out;

		int i=0;
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("::Servidor escuchando a los posibles clientes::");

			while (true) {

				socket = serverSocket.accept();
				System.out.println("El cliente se ha conectado!");
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				ClientHandler client= new ClientHandler(socket,"Cliente" +i,in,out);
				Thread t =new Thread(client);
				 	
				
				clients.add(client);
				t.start();
				i++;
				
//				String key=asignarClave();
//				System.out.println("La clave es: " +key);
//				out.writeUTF(key);				
//				String mensajeObtenidoCliente = in.readUTF();
//				System.out.println("El mensaje enviado por el cliente fue 88 : " + mensajeObtenidoCliente);
//				
//				
//				int clave= Integer.parseInt(key);
//				String respuestaServer = metodoServicioServer(mensajeObtenidoCliente,clave);
//				out.writeUTF(respuestaServer);
//				socket.close();
//				System.out.println("::El cliente fue desconectado del server::");
			

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private static String asignarClave() {
		
		int key=(int)(Math.random()*20);
		
		return key+"";
		
		
	}

	/**
	 * Metodo encargado de realizar la encriptacion de Cesar, sumando 2 posiciones
	 * al ASCII de cada caracter
	 * 
	 * @param mensajeObtenidoCliente != Null && != ""
	 * @return
	 */
	private static String metodoServicioServer(String mensajeObtenidoCliente, int key) {

		String respuesta = "";
		char caracter;

		if (mensajeObtenidoCliente != "") {
			for (int i = 0; i < mensajeObtenidoCliente.length(); i++) {
				caracter = mensajeObtenidoCliente.charAt(i);
				caracter = (char) (caracter - key);
				respuesta += Character.toString((caracter)) + "";
			}
		} else {
			respuesta = "::El cliente no envio texto para encriptar::";
		}
		return respuesta;

	}
	
	
}
