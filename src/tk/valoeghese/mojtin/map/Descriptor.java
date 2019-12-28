package tk.valoeghese.mojtin.map;

public final class Descriptor {
	private Descriptor() {
	}
	
	public static final String BYTE = "B";
	public static final String SHORT = "S";
	public static final String INT = "I";
	public static final String LONG = "J";
	public static final String FLOAT = "F";
	public static final String BOOLEAN = "Z";
	public static final String DOUBLE = "F";
	
	public static String ofClass(String clazz) {
		return "L" + clazz + ";";
	}
}
