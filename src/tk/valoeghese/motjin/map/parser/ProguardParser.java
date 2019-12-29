package tk.valoeghese.motjin.map.parser;

import tk.valoeghese.motjin.map.ClassEntry;
import tk.valoeghese.motjin.map.TinyDescriptor;

// Note that signatures and descriptors of methods and fields use the mapped names with this parser
// Instead of the standard for TINY mappings, which is that of using the obfuscated names for signatures and descriptors
public final class ProguardParser extends ObfuscationMap {
	ProguardParser() {
		super("proguardParser");
	}

	private ClassEntry recent;

	@Override
	protected void parseLine(String line) {
		char firstChar = line.charAt(0);

		if (firstChar != '#') {
			if (firstChar == ' ') {
				String lineTrim = line.trim();

				if (Character.isDigit(lineTrim.charAt(0))) {
					parseMethod(lineTrim.split("\\:")[2].split(" "));
				} else {
					if (lineTrim.contains("(")) {
						parseMethod(lineTrim.split(" "));
					} else {
						parseField(lineTrim.split(" "));
					}
				}
			} else {
				this.parseClass(line.trim().split(" "));
			}
		}
	}

	private void parseField(String[] in) {
		String descriptor = TinyDescriptor.of(p2tArrays(p2tPackage(in[0])));
		String mappedName = in[1].trim();
		String obfName = in[3].trim();

		this.recent.addField(obfName, mappedName, descriptor);
	}

	private void parseMethod(String[] in) {
		String returnType = TinyDescriptor.of(p2tArrays(p2tPackage(in[0])));
		String[] methodSplit = in[1].split("\\(");

		String mappedName = methodSplit[0].trim();
		String signature = parseMethodSignature(returnType, methodSplit[1]);
		String obfName = in[3].trim();

		this.recent.addMethod(obfName, mappedName, signature);
	}

	private String parseMethodSignature(String returnType, String parameters) {
		boolean flag = parameters.length() > 1;
		StringBuilder signature = new StringBuilder("(");

		if (flag) {
			String[] paramArr = parameters.substring(0, parameters.length() - 1).split(",");

			for (String proguardParam : paramArr) {
				signature.append(TinyDescriptor.of(p2tArrays(p2tPackage(proguardParam))));
			}
		}

		signature.append(")").append(returnType);
		//debugger.listen(signature.toString());
		return signature.toString();
	}

	private void parseClass(String[] in) {
		String obfName = p2tPackage(in[2]);
		obfName = obfName.substring(0, obfName.length() - 1); // remove ":" character
		String mappedName = p2tPackage(in[0]);

		ClassEntry classEntry = new ClassEntry.Builder()
				.obfName(obfName)
				.mappedName(mappedName) 
				.build();

		this.addClassEntry(obfName, mappedName, classEntry);
		this.recent = classEntry;
	}

	private static String p2tPackage(String in) {
		// convert proguard "." for package separation to "/"
		return in.trim().replace('.', '/');
	}

	private String p2tArraysDebug(String in) {
		if (in.charAt(in.length() - 1) == ']') {
			String result = p2tModifyArrays(in);
			debugger.listen(result);
			return result;
		} else {
			return in;
		}
	}

	private static String p2tArrays(String in) {
		if (in.charAt(in.length() - 1) == ']') {
			return p2tModifyArrays(in);
		} else {
			return in;
		}
	}

	private static String p2tModifyArrays(String in) {
		return '[' + in.substring(0, in.length() - 2);
	}
}
