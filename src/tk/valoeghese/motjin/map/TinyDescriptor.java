package tk.valoeghese.motjin.map;

import tk.valoeghese.motjin.util.Tuple;

public final class TinyDescriptor {
	private TinyDescriptor() {
	}

	public static final String BYTE = "B";
	public static final String SHORT = "S";
	public static final String INT = "I";
	public static final String LONG = "J";
	public static final String FLOAT = "F";
	public static final String DOUBLE = "D";
	public static final String BOOLEAN = "Z";
	public static final String VOID = "V";

	// For the initial proguard parsing
	public static final String ABYTE = "[B";
	public static final String ASHORT = "[S";
	public static final String AINT = "[I";
	public static final String ALONG = "[J";
	public static final String AFLOAT = "[F";
	public static final String ADOUBLE = "[D";
	public static final String ABOOLEAN = "[Z";

	public static String ofClass(String clazz, boolean tinyArray) {
		if (tinyArray) {
			return "[L" + clazz + ";";
		} else {
			return "L" + clazz + ";";
		}
	}

	public static String classFromDescriptor(String descriptor) {
		return descriptor.substring(1, descriptor.length() - 1);
	}

	public static String of(String in) {
		boolean tinyArray = in.charAt(0) == '['; // hack to handle arrays formatted in tiny in the initial proguard parsing
		String toSwitch = tinyArray ? in.substring(1) : in;

		switch (toSwitch) {
		case "void":
			return VOID;
		case "byte":
			return tinyArray ? ABYTE : BYTE;
		case "short":
			return tinyArray ? ASHORT : SHORT;
		case "int":
			return tinyArray ? AINT : INT;
		case "long":
			return tinyArray ? ALONG : LONG;
		case "float":
			return tinyArray ? AFLOAT : FLOAT;
		case "double":
			return tinyArray ? ADOUBLE : DOUBLE;
		case "boolean":
			return tinyArray ? ABOOLEAN : BOOLEAN;
		default:
			return ofClass(toSwitch, tinyArray);
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
