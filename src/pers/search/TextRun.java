package pers.search;

public class TextRun {

	public static void main(String[] args) {
		// TextSearch ts = new TextSearch("bvhjsdfduisni","s");
		// ts.setIndex(3);
		// System.out.println(ts.search()+":"+ts.getBeforeText()+":"+ts.getAfterText());
		// System.out.println(ts.search()+":"+ts.getBeforeText()+":"+ts.getAfterText());
//		TextSearch ts = new TextSearch("(as)dds()f(cer)sdf(dfd)f)", "(");
		TextSearch ts = new TextSearch("fewf.sdfwe.fweef.sds.", ".");
		System.out.println(ts.next() + ":" + ts.getSourceText().substring(ts.getLastIndex(), ts.getIndex() - 1));
		System.out.println(ts.next() + ":" + ts.getSourceText().substring(ts.getLastIndex(), ts.getIndex() - 1));
		System.out.println(ts.next() + ":" + ts.getSourceText().substring(ts.getLastIndex(), ts.getIndex() - 1));
		System.out.println(ts.next() + ":" + ts.getSourceText().substring(ts.getLastIndex(), ts.getIndex() - 1));
		// System.out.println(ts.next());
		// System.out.println(ts.next()+":"+ts.getBeforeText().substring(0,ts.getLastIndex()));
		// System.out.println(ts.next());

	}

}
