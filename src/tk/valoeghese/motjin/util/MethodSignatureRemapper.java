package tk.valoeghese.motjin.util;

import java.util.function.Function;

import tk.valoeghese.motjin.map.TinyDescriptor;

public final class MethodSignatureRemapper {
	private MethodSignatureRemapper() {
	}

	static String buffer;

	public static String remapMethodSignature(String tinySignature, Function<String, String> remappingFunction) {
		String[] paramVreturn = tinySignature.split("\\)");
		StringBuilder sb = new StringBuilder("(");
		StringBuilder parameterBuilder = new StringBuilder();
		char[] parameters = paramVreturn[0].substring(1).toCharArray();

		for (char c : parameters) {
			parameterBuilder.append(c);

			if (PARAMETER_END_TYPES.contains(c)) {
				boolean clazz = c == ';';
				String parameter = parameterBuilder.toString();

				if (!clazz && parameter.length() != 1) {
					continue;
				}

				if (clazz) {
					parameter = remapTypeAccordingTo(parameter, remappingFunction);
				}
				sb.append(parameter);
				parameterBuilder = new StringBuilder();
				buffer = parameter;
			}
		}

		String returnType = paramVreturn[1];
		char c = returnType.charAt(returnType.length() - 1);

		if (c == ';') {
			returnType = remapTypeAccordingTo(returnType, remappingFunction);
		}
		return sb.append(")").append(returnType).toString();	
	}

	private static String remapTypeAccordingTo(String parameter, Function<String, String> remappingFunction) {
		if (parameter.charAt(0) == '[') {
			return "[L" + remappingFunction.apply(parameter.substring(2, parameter.length() - 1)) + ";";
		} else {
			return "L" + remappingFunction.apply(TinyDescriptor.classFromDescriptor(parameter)) + ";";
		}
	}

	private static final ImmutableList<Character> PARAMETER_END_TYPES = ImmutableList.of('B', 'S', 'I', 'J', 'F', 'D', 'Z', 'V', ';');
}
