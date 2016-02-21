package com.noxer.games.wii;

/* SERVIDOR   
 * Creado por Sebastian Cipolat
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	Socket skCliente1, skCliente2;
	ServerSocket skServidor1, skServidor2;
	String datareceived, substring1, substring2;
	final int PUERTO1 = 5555;// Puerto que utilizara el servidor utilizar este
							// mismo en el cliente
	final int PUERTO2 = 5556;
	String IP_client1, IP_client2;
	Mensaje_data mdata = null;
	ObjectOutputStream oos = null;
	String TimeStamp;
	public static float giro[];
	

	public Server() {
		giro = new float[2];
		giro[0] = giro[1] = 0;
		try {
			System.out.println("************ SERVER ****************");
			// creamos server socket
			skServidor1 = new ServerSocket(PUERTO1);
			skServidor2 = new ServerSocket(PUERTO2);
			System.out.println("Escuchando el puerto1 " + PUERTO1);
			System.out.println("Escuchando el puerto2 " + PUERTO2);
			System.out.println("En Espera....");

			TimeStamp = new java.util.Date().toString();

			new Thread(new Runnable(){

	            @Override
	            public void run() {
	            	try {
	    				// Creamos socket para manejar conexion con cliente
	    				skCliente1 = skServidor1.accept(); // esperamos al cliente
	    				// una vez q se conecto obtenemos la ip
	    				IP_client1 = skCliente1.getInetAddress().toString();
	    				System.out.println("[" + TimeStamp + "] Conectado al cliente "
	    						+ "IP:" + IP_client1);
	    				while(skCliente1.getInetAddress().toString() == "/127.0.0.1"){
	    					skCliente1.close();
	    					skCliente1 = skServidor1.accept();
	    				}
	    				//skCliente2 = skServidor2.accept();
	    				/*IP_client2 = skCliente2.getInetAddress().toString();
	    				System.out.println("[" + TimeStamp + "] Conectado al cliente "
	    						+ "IP:" + IP_client2);*/
	    				
	    				 
	    				String aux1 = "-180";
	    				while (true) {
	    					//skCliente1.setTcpNoDelay(true);
	    					// Manejamos flujo de Entrada
	    					//ObjectInputStream ois1 = new ObjectInputStream(skCliente1.getInputStream());
	    					//System.out.println(ois1.available());
	    					//String response = new BufferedReader(new InputStreamReader(skCliente1.getInputStream())).readLine();		
	    					//ObjectInputStream ois2 = new ObjectInputStream(skCliente2.getInputStream());
	    					
	    					//System.out.println("socket2 OPEN TOO");
	    					if(skCliente1.getInputStream().available()>0){
	    						aux1 = new BufferedReader(new InputStreamReader(skCliente1.getInputStream())).readLine();
	    							//System.out.println("HI");
	    							giro[0] = Float.parseFloat(aux1);
	    						
	    					}
	    					//else aux1 = "-180"; //SIGNIFICA QUE NO HAY CAMBIO
    						if (!aux1.equalsIgnoreCase("cerrar")) {
    							//System.out.println("HERE: "+aux1);
    							giro[0] = Float.parseFloat(aux1);
    							//System.out.println("PUTA: " + giro[0]);
    						} else {// cerramos socket
    							skCliente1.close();
    							//ois1.close();
    							System.out .println("[" + TimeStamp
										+ "] Last_msg detected Conexion cerrada, gracias vuelva pronto");
    							break;
    						}
	    				}
	            	} catch (Exception e) {
	    				e.printStackTrace();
	    				System.out.println("[" + TimeStamp + "] Error ");
	    			}
	            }
	                /*ServerSocketHints serverSocketHint = new ServerSocketHints();
	                // 0 means no timeout.  Probably not the greatest idea in production!
	                serverSocketHint.acceptTimeout = 0;
	                
	                ServerSocket serverSocket = Gdx.net.newServerSocket(Protocol.TCP, 5555, serverSocketHint);
	                Socket socket = serverSocket.accept(null);
	                System.out.println("IP Conectada: " + socket.getRemoteAddress());
	                BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));*/
	                
		      }).start();
			
					
					
					///////////////////////////////
					//                           //
					//   MANDO NUMERO 2          //
					//                           //
					///////////////////////////////
			new Thread(new Runnable(){

	            @Override
	            public void run() {
	            	try {
	    				// Creamos socket para manejar conexion con cliente
	    				skCliente2 = skServidor2.accept(); // esperamos al cliente
	    				// una vez q se conecto obtenemos la ip
	    				IP_client2 = skCliente2.getInetAddress().toString();
	    				System.out.println("[" + TimeStamp + "] Conectado al cliente "
	    						+ "IP:" + IP_client2);
	    				
	    				while(skCliente2.getInetAddress().toString() == "/127.0.0.1"){
	    					skCliente2.close();
	    					skCliente2 = skServidor2.accept();
	    				}
	    				//skCliente2 = skServidor2.accept();
	    				/*IP_client2 = skCliente2.getInetAddress().toString();
	    				System.out.println("[" + TimeStamp + "] Conectado al cliente "
	    						+ "IP:" + IP_client2);*/
	    				
	    				 
	    				String aux1 = "-180";
	    				while (true) {
	    					//skCliente1.setTcpNoDelay(true);
	    					// Manejamos flujo de Entrada
	    					//ObjectInputStream ois1 = new ObjectInputStream(skCliente1.getInputStream());
	    					//System.out.println(ois1.available());
	    					//String response = new BufferedReader(new InputStreamReader(skCliente1.getInputStream())).readLine();		
	    					//ObjectInputStream ois2 = new ObjectInputStream(skCliente2.getInputStream());
	    					
	    					//System.out.println("socket2 OPEN TOO");
	    					if(skCliente2.getInputStream().available()>0){
	    						aux1 = new BufferedReader(new InputStreamReader(skCliente2.getInputStream())).readLine();
	    							//System.out.println("HI");
	    							giro[1] = Float.parseFloat(aux1);
	    						
	    					}
	    					//else aux1 = "-180"; //SIGNIFICA QUE NO HAY CAMBIO
    						if (!aux1.equalsIgnoreCase("cerrar")) {
    							//System.out.println("HERE: "+aux1);
    							giro[1] = Float.parseFloat(aux1);
    							//System.out.println("PUTA: " + giro[0]);
    						} else {// cerramos socket
    							skCliente2.close();
    							//ois1.close();
    							System.out .println("[" + TimeStamp
										+ "] Last_msg detected Conexion cerrada, gracias vuelva pronto");
    							break;
    						}
	    				}
	            	} catch (Exception e) {
	    				e.printStackTrace();
	    				System.out.println("[" + TimeStamp + "] Error ");
	    			}
	            }
	                /*ServerSocketHints serverSocketHint = new ServerSocketHints();
	                // 0 means no timeout.  Probably not the greatest idea in production!
	                serverSocketHint.acceptTimeout = 0;
	                
	                ServerSocket serverSocket = Gdx.net.newServerSocket(Protocol.TCP, 5555, serverSocketHint);
	                Socket socket = serverSocket.accept(null);
	                System.out.println("IP Conectada: " + socket.getRemoteAddress());
	                BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));*/
	                
		      }).start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// en base al codigo de accion recibido realizaremos una accion
	/*public void Exec(int action_num) {
		String ACTNUM = null;
		String ACT1 = "vlc";// abrir VLC
		String ACT2 = "/opt/google/chrome/google-chrome %U";// Chrome
		String ACT3 = "gnome-terminal";// terminal
		String ACT4 = "";
			  //Realizamos la accion
		     //Process p = Runtime.getRuntime().exec (ACTNUM);
  
	  }*/
	
}	