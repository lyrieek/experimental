package com.th.js;

import java.util.ArrayList;
import java.util.List;


public class Content {
	
	List<ContextBlack> black;
	
	long movePoint = 0;
	
	public Content() {
		black = new ArrayList<>();
	}

	public Content(ContextBlack cb) {
		this();
		add(cb);
	}
	
	public void add(ContextBlack cb) {
		movePoint += cb.length();
		if (!cb.isEmpty()) {
			black.add(cb);
		}
	}
	
	public boolean isEmpty() {
		return black.size() == 0;
	}
	
	public boolean isSingle() {
		return black.size() == 1;
	}
	
	public long getMovePoint() {
		return movePoint;
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "Content [black is empty, movePoint=" + movePoint + "]";
		}
		if (isSingle()) {
			return "Content [single black=" + black.get(0) + ", movePoint=" + movePoint + "]";
		}
		return "Content [black=" + black + ", movePoint=" + movePoint + "]";
	}
	
	
}
