package schema;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Cliente1 {
	
	/*
	 * 
	 * Direccion local de la maquina
	 */
	public static final String LOCAL_HOST = "192.168.199.52";
	/**
	 * Puerto por donde se establecera la conexion
	 */
	public static final int PORT = 8000;
	/**
	 * Socket que permitira la conexion con el servidor
	 */
	private static Socket socket;
	
	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		
		DataInputStream in;
		DataOutputStream out;

		try {
			
			BufferedReader br = new BufferedReader(new InputStreamReader( System.in));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
			
			System.out.println("::Cliente disponible para ser atendido:: \nIngrese "
					+ "el mensaje a enviar!!::");
			
						
			socket = new Socket(LOCAL_HOST, PORT);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
//			String mensaje = br.readLine();
			int clave=Integer.parseInt(in.readUTF());
			System.out.println("La clave del chat en hexadecimal es: " +clave);
			
//			String cesar= encriptarMensaje(mensaje, clave);			
//			out.writeUTF(cesar);
			
			Thread sendMessage = new Thread(new Runnable()  
	        { 
	            @Override
	            public void run() { 
	                while (true) { 
	  
	                    // read the message to deliver. 
	                   
	                      
	                    try { 
	                    	 String msg = br.readLine(); 
	                    	 String msgCesar=encriptarMensaje(msg, clave);
	                        // write on the output stream 
	                        out.writeUTF(msgCesar); 
	                    } catch (IOException e) { 
	                        e.printStackTrace(); 
	                    } 
	                } 
	            } 
	        }); 
	          
	        // readMessage thread 
	        Thread readMessage = new Thread(new Runnable()  
	        { 
	            @Override
	            public void run() { 
	  
	                while (true) { 
	                    try { 
	                        // read the message sent to this client 
	                        String msg = in.readUTF(); 
	                        System.out.println(msg); 
	                    } catch (IOException e) { 
	  
	                        e.printStackTrace(); 
	                    } 
	                } 
	            } 
	        }); 
	  
	        sendMessage.start(); 
	        readMessage.start(); 
	  
			
			
			
//			System.out.println("El mensaje enviado fue: " +in.readUTF());
//			bw.flush();
//			bw.close();
//			br.close();
//			socket.close();
//			in.close();
//			out.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	/**
	 * Metodo encargado de realizar la encriptacion de Cesar, sumando 2 posiciones
	 * al ASCII de cada caracter
	 * 
	 * @param mensajeObtenidoCliente != Null && != ""
	 * @return
	 */
	private static String encriptarMensaje(String mensajeObtenidoCliente, int clave) {

		String respuesta = "";
		char caracter;

		if (mensajeObtenidoCliente != "") {
			for (int i = 0; i < mensajeObtenidoCliente.length(); i++) {
				caracter = mensajeObtenidoCliente.charAt(i);
				caracter = (char) (caracter + clave);
				respuesta += Character.toString((caracter)) + "";
			}
		} else {
			respuesta = "::El cliente no envio texto para encriptar::";
		}
		return respuesta;

	}

	
	

}
