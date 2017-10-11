package pers.th.util.tcp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import pers.th.util.tcp.entity.DataWrap;
import pers.th.util.tcp.entity.DataWrapType;

public class TcpServer {

	public TcpServer() {
		ServerConnection sc = openConnection();
		sc.start(new SocketTask() {

			@Override
			public DataWrap accept(DataWrap input) throws Exception {
				this.setAutoClose(false);
				if (input.isType(DataWrapType.TEXT)) {
					System.out.println(new String(input.getData()));
				} else if (input.isType(DataWrapType.FILE)) {
					File file = new File(input.getRemark().toString());
					file.createNewFile();
					FileOutputStream fos = new FileOutputStream(file);
//					fos.write(new String(input.getData(), charset).getBytes(charset));
					fos.write(input.getData());
					fos.flush();
					fos.close();
				} else if (input.isType(DataWrapType.DEFAULT)) {
					if (new String(input.getData()).contains("close")
							|| input.getRemark().toString().contains("close")) {
						sc.close();
						return null;
					}
				}
				return input;
			}
		});
		sc.close();
	}

	public static void main(String[] args) {
		new TcpServer();
	}

	public ServerConnection openConnection() {
		try {
			return new ServerConnection(1212);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
