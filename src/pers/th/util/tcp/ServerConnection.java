package pers.th.util.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection extends ServerSocket {

	public ServerConnection(int port) throws IOException  {
		super(port); 
	}
	
	public void start(SocketTask st) {
		try {
			Socket current;
			while ((current = accept()) != null) {
				ObjectStream os = new ObjectStream();
				st.accept(current);
				st.accept(current.getInputStream());
				st.accept(current.getOutputStream());
				st.accept(os.reverse(current.getInputStream()));
				if (st.isAutoClose()) {
					os.close();
					st.close(current);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
