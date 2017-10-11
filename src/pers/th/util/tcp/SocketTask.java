package pers.th.util.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import pers.th.util.tcp.entity.DataWrap;

public abstract class SocketTask {
	
	public SocketTask() {
	}
	
	private boolean autoClose = true;
	
	public void accept(Socket current) {
	}
	
	public void accept(InputStream is,OutputStream os) {
	}
	
	public DataWrap accept(DataWrap input) throws Exception {
		return null;
	}
	
	public void accept(InputStream is) {
	}
	
	public void accept(OutputStream os) {
	}
	
	public void close(Socket current) {
		if (current !=null && !current.isClosed()) {
			try {
				current.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isAutoClose() {
		return autoClose;
	}

	public void setAutoClose(boolean autoClose) {
		this.autoClose = autoClose;
	}
	
}
