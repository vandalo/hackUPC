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
	static private boolean connected = false;
	static ObjectOutputStream oos;
	static ObjectInputStream ois;
	static Mensaje_data mdata;
	
	
	//Conectamos
		public static boolean Connect(String IP, int PORT) {
			//Obtengo datos ingresados en campos
			//String IP = ipinput.getText().toString();
			//int PORT = Integer.valueOf(portinput.getText().toString());
			//String IP = "192.168.1.129";
			/*InetAddress IP;
			try {
				IP = InetAddress.getLocalHost();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			//int PORT = 5555;
			
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
				//Si hubo algun error mostrmos error
				//txtstatus.setTextColor(Color.RED);
				//txtstatus.setText(" !!! ERROR  !!!");
				//Log.e("Error connect()", "" + e);
				return false;
			}
		}

		//Metodo de desconexion
		static public void Disconnect() {
			try {
				//Prepramos mensaje de desconexion
				Mensaje_data msgact = new Mensaje_data();
				msgact.texto = "";
				msgact.Action = -1;
				msgact.last_msg = true;
				//avisamos al server que cierre el canal
				boolean val_acc = Snd_Msg(msgact);

				if (!val_acc) {//hubo un error
					//Set_txtstatus(" Error  ", 0);
					//Change_leds(false);
					Gdx.app.log("Disconnect() -> ", "!ERROR!");

				} else {//ok nos desconectamos
					//Set_txtstatus("Desconectado", 0);
					//camibmos led a rojo
					//Change_leds(false);
					Gdx.app.log("Disconnect() -> ", "!ok!");
					//cerramos socket	
					miCliente.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (!miCliente.isConnected());
				//Change_leds(false);
		}

		//Enviamos mensaje de accion segun el boton q presionamos
		static public void Snd_Action(int bt) {
			Mensaje_data msgact = new Mensaje_data();
			//no hay texto
			msgact.texto = "";
			//seteo en el valor action el numero de accion
			msgact.Action = bt;
			//no es el ultimo msg
			msgact.last_msg = false;
			//mando msg
			boolean val_acc = Snd_Msg(msgact);
			//error al enviar
			if (!val_acc) {
				//Set_txtstatus(" Error  ", 0);
				//Change_leds(false);
				Gdx.app.log("Snd_Action() -> ", "!ERROR!");

			}

			if (!miCliente.isConnected());
				//Change_leds(false);
		}

		//Envio mensaje de texto 
		static public void Snd_txt_Msg(String txt) {

			Mensaje_data mensaje = new Mensaje_data();
			//seteo en texto el parametro  recibido por txt
			mensaje.texto = txt;
			//action -1 no es mensaje de accion
			mensaje.Action = -1;
			//no es el ultimo msg
			mensaje.last_msg = false;
			//mando msg
			boolean val_acc = Snd_Msg(mensaje);
			//error al enviar
			if (!val_acc) {
				//Set_txtstatus(" Error  ", 0);
				//Change_leds(false);
				Gdx.app.log("Snd_txt_Msg() -> ", "!ERROR!");
			}
			if (!miCliente.isConnected());
				//Change_leds(false);
		}
		
		/*Metodo para enviar mensaje por socket
		 *recibe como parmetro un objeto Mensaje_data
		 *retorna boolean segun si se pudo establecer o no la conexion
		 */
		static private boolean Snd_Msg(Mensaje_data msg) {

			try {
				//Accedo a flujo de salida
				oos = new ObjectOutputStream(miCliente.getOutputStream());
				//creo objeto mensaje
				Mensaje_data mensaje = new Mensaje_data();

				if (miCliente.isConnected())// si la conexion continua
				{
					//lo asocio al mensaje recibido
					mensaje = msg;
					//Envio mensaje por flujo
					oos.writeObject(mensaje);
					//envio ok
					return true;

				} else {//en caso de que no halla conexion al enviar el msg
					//Set_txtstatus("Error...", 0);//error
					return false;
				}

			} catch (IOException e) {// hubo algun error
				Gdx.app.log("Snd_Msg() ERROR -> ", "" + e);

				return false;
			}
		}
}
