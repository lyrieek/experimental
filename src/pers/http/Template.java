package pers.http;

public class Template {
	
	private String template;
	
	public Template(String template) {
		this.template = template;
	}
	
	public String template() {
		return template;
	}

	public String replace(String source, String newString) {
		return template = template.replace(source, newString);
	}
	
	@Override
	public String toString() {
		return template;
	}
	
}
