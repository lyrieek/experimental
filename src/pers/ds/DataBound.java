package pers.ds;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataBound {

	private DataSource ds;
	private File file;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private boolean sync = false;
	private DataSet dataSet;

	public static void main(String[] args) throws IOException {
//		System.out.println(DataBound.getData().append("ver:aaa"));
//		System.out.println(DataBound.getData().readOnly("ver"));
//		System.out.println(DataBound.getData().getAll());
		DataBound db = DataBound.getData();
		db.getAll().printf();
		db.close();
	}
	
	public boolean remove() {
		return file.delete();
	}

	public DataSet getAll() {
		syncToMemory();
		return dataSet;
	}
	
	public static DataBound getData() {
		return new DataBound();
	}
	
	public DataWarp readOnly(String key) {
		syncToMemory();
		DataWarp dWarp = get("ss");
		close();
		return dWarp;
	}

	public void init() {
		try {
			dataSet = new DataSet();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (output != null) {
				output.close();
			}
			if (input != null) {
				input.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void set(String item) {
		try {
			dataSet.add(DataWarp.toSet(item));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void syncToMemory() {
		try {
			input = new ObjectInputStream(new FileInputStream(file));
			Object obj = input.readObject();
			if (!(obj instanceof DataSet)) {
				System.err.println("data error");
				return;
			}
			dataSet = ((DataSet) obj);
			sync = true;
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void commit() {
		syncToFile();
		close();
	}

	public void syncToFile() {
		try {
			sync = true;
			output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(dataSet);
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DataWarp get(String name) {
		return dataSet.get(name);
	}

	public DataBound() {
		try {
			sync = false;
			ds = new DataSource("G:\\th\\CmsApplicationTest\\out");
			file = new File(ds.getUrl() + "\\data.dat");
			if (!file.exists()) {
				file.createNewFile();
				dataSet = new DataSet();
				dataSet.add(new DataWarp("null Data"));
				syncToFile();
			}
			syncToMemory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public boolean isSync() {
		return sync;
	}

}
