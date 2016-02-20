package com.noxer.games.wii.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.badlogic.gdx.Gdx;

public class SocketSender {
	static Socket miCliente;
	static boolean conected = false;
	static ObjectOutputStream oos;
	
	
	//Conectamos
		public static boolean Connect(String IP, int PORT) {
			try {//creamos sockets con los valores anteriores
				miCliente = new Socket(IP, PORT);
				//si nos conectamos
				if (miCliente.isConnected() == true) {
					Gdx.app.log("aa", "Conectado");
					return true;
				} else {
					Gdx.app.log("aa", "NO Conectado");
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}

		//Metodo de desconexion
		static public void Disconnect() {
			try {
				Snd_Msg("cerrar");
				miCliente.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		//Envio mensaje de texto 
		static public void Snd_txt_Msg(String txt) {
			//if (!miCliente.isConnected());
			if(miCliente.isConnected()) Snd_Msg(txt);
		}
		
		/*Metodo para enviar mensaje por socket
		 *recibe como parmetro un objeto Mensaje_data
		 *retorna boolean segun si se pudo establecer o no la conexion
		 */
		static private boolean Snd_Msg(String msg) {
			try {
				//Accedo a flujo de salida
				//oos = new ObjectOutputStream(miCliente.getOutputStream());
				if (miCliente.isConnected()){
					//oos.writeBytes(msg);
					//oos.flush();
					miCliente.getOutputStream().write((msg+"\n").getBytes());
					//miCliente.setTcpNoDelay(true);
					return true;
				} else {//en caso de que no halla conexion al enviar el msg
					//Set_txtstatus("Error...", 0);//error
					return false;
				}

			} catch (IOException e) {// hubo algun error
				return false;
			}
		}
}
