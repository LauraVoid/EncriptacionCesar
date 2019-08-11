package schema;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable {

	Scanner sc = new Scanner(System.in);
	public String name;
	final DataInputStream in;
	final DataOutputStream out;
	Socket socket;
	boolean connected;

	public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
		this.in = dis;
		this.out = dos;
		this.name = name;
		this.socket = s;
		this.connected = true;
	}

	@Override
	public void run() {

		String received; 
		
        while (true)  
        { 
            try
            { 
                // receive the string 
                received = in.readUTF(); 
                  
                System.out.println(received); 
                  
                if(received.equals("logout")){ 
                    this.connected=false; 
                    this.socket.close(); 
                    break; 
                } 
                  
                // break the string into message and recipient part 
                StringTokenizer st = new StringTokenizer(received); 
                String MsgToSend = st.nextToken(); 
                String recipient = st.nextToken(); 
  
                // search for the recipient in the connected devices list. 
                // ar is the vector storing client of active users 
                for (ClientHandler mc : Server.clients)  
                { 
                    // if the recipient is found, write on its 
                    // output stream 
                    if (mc.name.equals(recipient) && mc.connected==true)  
                    { 
                        mc.out.writeUTF(this.name+" : "+MsgToSend); 
                        break; 
                    } 
                } 
            } catch (IOException e) { 
                  
                e.printStackTrace(); 
            } 
              
        } 
        try
        { 
            // closing resources 
            this.in.close(); 
            this.out.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
	}

}
