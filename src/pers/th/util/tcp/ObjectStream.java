package pers.th.util.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pers.th.util.tcp.entity.DataWrap;

public class ObjectStream {

	ObjectInputStream ois;

	ObjectOutputStream oos;

	public DataWrap reverse(InputStream is) {
		try {
			ois = new ObjectInputStream(is);
			Object obj = ois.readObject();
			if (obj instanceof DataWrap) {
				return (DataWrap) obj;
			} else {
				throw new RuntimeException("Object not is DataWrap");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ObjectOutputStream reverse(OutputStream os) {
		try {
			oos = new ObjectOutputStream(os);
			return oos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * auto flush
	 * 
	 * @param os
	 * @param dw
	 */
	public void reverse(OutputStream os, DataWrap dw) {
		reverse(os);
		output(dw);
	}

	public void output(DataWrap dw) {
		try {
			oos.writeObject(dw);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ObjectInputStream getInput() {
		return ois;
	}

	public ObjectOutputStream getOutput() {
		return oos;
	}

	public void close() {
		System.out.println("close");
		try {
			if (ois != null) {
				ois.close();
			}
			if (oos != null) {
				oos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
