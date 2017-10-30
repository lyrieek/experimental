package chars.u;

import java.util.ArrayList;
import java.util.List;

public class ContextComposite<T> extends ArrayList<ContextBlack>{
	private static final long serialVersionUID = 1L;
	
	
	public void add(ContextBlack... cbs) {
		for (ContextBlack contextBlack : cbs) {
			add(contextBlack);
		} 
	}
	
	public boolean addAll(List<? extends ContextBlack> c) {
		return super.addAll(c);
	}

	
	
	
}
