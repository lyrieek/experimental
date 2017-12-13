package chars.u.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import chars.u.*;
import pers.th.util.FileReader;

public class DeleteRemark {

//	@SuppressWarnings("resource")
//	public static void main2(String[] args) {
//		System.out.println(new Scanner(System.in).nextLine().replaceAll("\\\\", "/"));
//	}

	public static List<Function> loadFunctions(BaseHandle instance) throws Exception {
		List<Function> ms = new ArrayList<>();
		Class<BaseHandle> classes = BaseHandle.class;
		Method[] methods = classes.getMethods();
		for (Method item : methods) {
			Annotation[] annotation = item.getAnnotations();
			if (annotation.length >= 1) {
				for (Annotation annotationItem : annotation) {
					if (annotationItem.annotationType().equals(Handle.class)) {
						ms.add(new Function(item, ((Handle) annotationItem).value(), instance));
					}
				}
			}
		}
		return ms;
	}

	public static void main(String[] args) throws Exception {
		BaseHandle instance = BaseHandle.class.newInstance();
		List<Function> ms = loadFunctions(instance);
		ContextComposite<ContextBlack> cc = new ContextComposite<>();
		StatusManager status = new StatusManager();
		PointReader pReader = new PointReader(
				FileReader.reader("G:/th/CmsWebApp/client/app/transaction/transaction.js"), "\\S+|\\s+");
		while (pReader.pushRegex()) {
			status.tempSave(pReader.item());
			instance.init(pReader.getTextPoint());
			for (Function fun : ms) {
				if (!fun.equalsName(status.code())) {
					continue;
				}
				if (fun.getNeedParam().length == 0) {
					status.change(fun.exec().toString());
					break;
				} else if (fun.getNeedParam().length == 1) {
					if (fun.hasNeedParam(TextPoint.class)) {
						status.change(fun.exec(pReader.getTextPoint()).toString());
					} else {
						status.change(fun.exec(pReader.item()).toString());
					}
					break;
				}
			}
			List<ContextBlack> cblist = instance.getContext();
			if (cblist != null) {
				cc.addAll(cblist);
			}
			int codeInedx = status.code().indexOf("|");
			if (codeInedx == -1) {
				status.save();
				continue;
			}
			String nextCode = status.code().substring(codeInedx + 1);
			status.change(status.code().substring(0, codeInedx));
			status.save();
			status.change(nextCode);
		}
		// status.printfHistroy();
		for (ContextBlack contextBlack : cc) {
			if (contextBlack.getStatus() != null && contextBlack.getStatus().is("remark")) {
				System.out.println(contextBlack.getStatus() + ":" + contextBlack);
			}
		}
		// for (StatusItem item : status.histroy()) {
		// if (item.is("read")) {
		// System.out.println(item);
		// }
		// }
	}

}
