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
	

	public Server() {

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
	    					
	    					System.out.println("socket2 OPEN TOO");
	    					if(skCliente1.getInputStream().available()>0){
	    						aux1 = new BufferedReader(new InputStreamReader(skCliente1.getInputStream())).readLine();
	    					}
	    					//else aux1 = "-180"; //SIGNIFICA QUE NO HAY CAMBIO
    						if (!aux1.equalsIgnoreCase("cerrar")) {
    							System.out.println(aux1);
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

						/*// Cremos un Objeto con lo recibido del cliente
						Object aux2 = ois2.readObject();// leemos objeto
						if (aux2 instanceof Mensaje_data) {
							// casteamos el objeto
							mdata = (Mensaje_data) aux2;
	
							// Analizamos el mensaje recibido
							// si no es el mensaje FINAL
							if (!mdata.last_msg) {
	
								// Es un mensaje de Accion
								if (mdata.Action != -1) {
									// exec accion
									Exec(mdata.Action);
									System.out.println("[" + TimeStamp + "] "
											+ "Ejecutar Accion " + mdata.Action
											+ " [" + IP_client2 + "]");
								} else {
									// No es un mensaje de accion entonces es de
									// texto
									System.out.println("[" + TimeStamp + "] "
											+ "Mensaje de [" + IP_client2 + "]--> "
											+ mdata.texto);
								}
							} else {// cerramos socket
								skCliente2.close();
								ois2.close();
								System.out
										.println("["
												+ TimeStamp
												+ "] Last_msg detected Conexion cerrada, gracias vuelva pronto");
								break;
							}
						}
						}*/						
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