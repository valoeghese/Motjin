package tk.valoeghese.mojtin.map;

public final class Descriptor {
	private Descriptor() {
	}
	
	public static final String BYTE = "B";
	public static final String SHORT = "S";
	public static final String INT = "I";
	public static final String LONG = "J";
	public static final String FLOAT = "F";
	public static final String DOUBLE = "D";
	public static final String BOOLEAN = "Z";
	public static final String VOID = "V";
	
	public static String ofClass(String clazz) {
		return "L" + clazz + ";";
	}
	
	public static String of(String in) {
		switch (in) {
		case "void":
			return VOID;
		case "byte":
			return BYTE;
		case "short":
			return SHORT;
		case "int":
			return INT;
		case "long":
			return LONG;
		case "float":
			return FLOAT;
		case "double":
			return DOUBLE;
		case "boolean":
			return BOOLEAN;
		default:
			return ofClass(in);
		}
	}
}
