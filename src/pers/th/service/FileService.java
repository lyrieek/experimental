package pers.th.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import pers.th.service.model.FileModel;

@RestController
public class FileService {

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/files.do")
    public @ResponseBody String list(@RequestParam(required = false) String url) {
	return JSON.toJSONString(getFiles(url));
    }

    @RequestMapping("/readFile.do")
    public @ResponseBody String readFile(@RequestParam String path) {
	return reader(path);
    }

    @RequestMapping("/updateFile.do")
    public @ResponseBody String updateFile(@RequestParam String path, @RequestParam String context) {
	return writer(path, context);
    }

    @RequestMapping("/deleteFile.do")
    public @ResponseBody String deleteFile(@RequestParam String path) {
	File file = new File(path);
	if (!file.exists()) {
	    return "NOT Exists";
	}
	return file.delete() ? "SUCCESS" : "Delete Error";
    }

    public String writer(String path, String context) {
	try {
	    FileWriter fw = new FileWriter(path, false);
	    fw.write(context.toCharArray());
	    fw.flush();
	    fw.close();
	} catch (Exception e) {
	    return "ERROR:"+e.getMessage();
	}
	return "SUCCESS";
    }

    public String reader(String path) {
	File file = new File(path);
	if (!file.exists() || !file.isFile() || !file.canRead()) {
	    return "Can't Reader!";
	}
	StringBuffer data = new StringBuffer();
	InputStream in = null;
	try {
	    in = new FileInputStream(file);
	    int length = 0;
	    byte[] buffer = new byte[8192 * 4];
	    while ((length = in.read(buffer)) != -1) {
		data.append(new String(buffer, 0, length));
	    }
	    in.close();
	} catch (Exception e) {
	    System.out.println("Reader error");
	    e.printStackTrace();
	}
	return data.toString();
    }

    public List<FileModel> getFiles(String url) {
	if (url == null || url.trim().isEmpty()) {
	    return reverseFiles(File.listRoots());
	}
	return reverseFiles(new File(url).listFiles());
    }

    public List<FileModel> reverseFiles(File[] files) {
	if (files == null || files.length == 0) {
	    return null;
	}
	List<FileModel> fileModels = new ArrayList<>();
	for (File file : files) {
	    fileModels.add(reverseFile(file));
	}
	fileModels.sort(new Comparator<FileModel>() {
	    @Override
	    public int compare(FileModel f1, FileModel f2) {
		if (f1.equalsType("dir") && f2.equalsType("file")) {
		    return -1;
		} else if (f1.equalsType("file") && f2.equalsType("dir")) {
		    return 0;
		}
		return 0;
	    }
	});
	return fileModels;
    }

    public FileModel reverseFile(File fs) {
	FileModel fm = new FileModel();
	fm.setName(fs.getName());
	fm.setPath(fs.getAbsolutePath());
	fm.setType(fs.isDirectory() ? "dir" : "file");
	fm.setCanExecute(fs.canExecute());
	fm.setCanRead(fs.canRead());
	fm.setCanWrite(fs.canWrite());
	fm.setLastDate(dateFormat.format(new Date(fs.lastModified())));
	fm.setRead(false);
	String[] list = fs.list();
	if (fs.isFile()) {
	    fm.setSize(formetFileSize(fs.length()));
	    if (fs.canRead() && fs.length() < 2000000) {
		fm.setRead(true);
	    }
	} else if (list != null && list.length != 0) {
	    fm.setSize(list.length + " item.");
	}
	return fm;
    }

    public String formetFileSize(long fileS) {
	DecimalFormat df = new DecimalFormat("#.00");
	String fileSizeString = "";
	if (fileS < 1024) {
	    fileSizeString = df.format((double) fileS) + "byte";
	} else if (fileS < 1048576) {
	    fileSizeString = df.format((double) fileS / 1024) + "KB";
	} else if (fileS < 1073741824) {
	    fileSizeString = df.format((double) fileS / 1048576) + "MB";
	} else {
	    fileSizeString = df.format((double) fileS / 1073741824) + "GB";
	}
	return fileSizeString;
    }
}
