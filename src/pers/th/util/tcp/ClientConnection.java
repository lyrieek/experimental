package pers.th.util.tcp;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import pers.th.util.tcp.entity.DataWrap;

public class ClientConnection extends Socket {

	ObjectStream os;

	public ClientConnection(String ip, int port) throws UnknownHostException, IOException {
		super(ip, port);
	}

	public void send(DataWrap dw) {
		getObjectStream().output(dw);
		close();
	}

	public ObjectStream getObjectStream() {
		if (os == null) {
			os = new ObjectStream();
			try {
				os.reverse(getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return os;
	}
	
	@Override
	public synchronized void close() {
		try {
			super.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
