package com.th.js;

import java.util.ArrayList;
import java.util.List;

public class Content {

	private List<ContextBlack> black;
	
	private ContextBlack lastContext;

	private long movePoint = 0;

	public Content() {
		black = new ArrayList<>();
	}

	public Content(ContextBlack cb) {
		this();
		add(cb);
	}

	public void add(ContextBlack cb) {
		movePoint += cb.length();
		black.add(lastContext = cb);
	}
	
	public ContextBlack getLastContext() {
		return lastContext;
	}

	public boolean isEmpty() {
		return black.size() == 0;
	}

	public boolean isSingle() {
		return black.size() == 1;
	}

	public ContextBlack getSingleContextBlack() {
		return isSingle() ? black.get(0) : null;
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
