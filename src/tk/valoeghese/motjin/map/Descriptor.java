package tk.valoeghese.motjin.map;

import tk.valoeghese.motjin.util.Tuple;

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

	public static String classFromDescriptor(String descriptor) {
		return descriptor.substring(1, descriptor.length() - 1);
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

	public static String from(String descriptor) {
		switch (descriptor) {
		case VOID:
			return "void";
		case BYTE:
			return "byte";
		case SHORT:
			return "short";
		case INT:
			return "int";
		case LONG:
			return "long";
		case FLOAT:
			return "float";
		case DOUBLE:
			return "double";
		case BOOLEAN:
			return "boolean";
		default:
			return classFromDescriptor(descriptor);
		}
	}

	public static Tuple<String, Boolean> fromWithFlag(String descriptor) {
		switch (descriptor) {
		case VOID:
			return Tuple.of("void", false);
		case BYTE:
			return Tuple.of("byte", false);
		case SHORT:
			return Tuple.of("short", false);
		case INT:
			return Tuple.of("int", false);
		case LONG:
			return Tuple.of("long", false);
		case FLOAT:
			return Tuple.of("float", false);
		case DOUBLE:
			return Tuple.of("double", false);
		case BOOLEAN:
			return Tuple.of("boolean", false);
		default:
			return Tuple.of(classFromDescriptor(descriptor), true);
		}
	}
}
