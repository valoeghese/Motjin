package tk.valoeghese.motjin;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import tk.valoeghese.motjin.map.ClassEntry;
import tk.valoeghese.motjin.map.FieldEntry;
import tk.valoeghese.motjin.map.MethodEntry;
import tk.valoeghese.motjin.map.TinyDescriptor;
import tk.valoeghese.motjin.map.parser.ObfuscationMap;
import tk.valoeghese.motjin.util.Debugger;
import tk.valoeghese.motjin.util.MethodSignatureRemapper;

public class Main {
	public static MainArgs options;
	private static Debugger debugger = Debugger.of(5, "main");

	public static void main(String[] args) {
		options = MainArgs.of(args);

		ObfuscationMap mojmap = ObfuscationMap.parseProguard(options.mojMap);
		ObfuscationMap intermediary = ObfuscationMap.parseTiny(options.intermediaryMap);

		writeTiny(options.outputFile, intermediary, mojmap);
	}

	private static void writeTiny(String file, ObfuscationMap intermediary, ObfuscationMap mojmap) {
		try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
			writer.println("v1\tofficial\tintermediary\tnamed");

			intermediary.forEachObf((obf, intermediaryEntry) -> {
				StringBuilder output = new StringBuilder();

				// Add mojang name to intermediary class entry
				ClassEntry mojmapEntry = mojmap.getClassEntryForObf(obf);
				intermediaryEntry.setFinalColumnMapping(mojmapEntry.getMappedName());

				// Add to output tiny
				output.append(intermediaryEntry.toString());

				// Process Fields
				intermediaryEntry.fields.forEach(intermediaryFieldEntry -> {
					// Get mojmap field key
					boolean flag = intermediaryFieldEntry.descriptor.charAt(0) == 'L';
					String mojmapDescriptor = flag ? getDescriptorMappedForObf(mojmap, intermediaryFieldEntry.descriptor) : intermediaryFieldEntry.descriptor;
					String mojmapFieldKey = intermediaryFieldEntry.obfName + ":" + mojmapDescriptor;

					// Get field entry and set final column mapping
					FieldEntry mojmapFieldEntry = mojmapEntry.fieldMap.getOrDefault(mojmapFieldKey, intermediaryFieldEntry);
					intermediaryFieldEntry.setFinalColumnMapping(mojmapFieldEntry.getMappedName());

					// Add to output
					output.append("\n").append(intermediaryFieldEntry.toString());
				});

				// Process Methods
				intermediaryEntry.methods.forEach(intermediaryMethodEntry -> {
					// Get mojmap method key
					String mojmapSignature = MethodSignatureRemapper.remapMethodSignature(intermediaryMethodEntry.signature, descriptor -> getMappedForObf(mojmap, descriptor));
					String mojmapMethodKey = intermediaryMethodEntry.obfName + ":" + mojmapSignature;

					// Get method entry and set final column mapping
					MethodEntry mojmapMethodEntry = mojmapEntry.methodMap.getOrDefault(mojmapMethodKey, intermediaryMethodEntry);
					intermediaryMethodEntry.setFinalColumnMapping(mojmapMethodEntry.getMappedName());

					// Add to output
					output.append("\n").append(intermediaryMethodEntry.toString());
				});

				writer.println(output.toString());
			});
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private static ClassEntry getClassEntryForDescriptor(ObfuscationMap remappingMap, String descriptor) {
		String clazz = TinyDescriptor.classFromDescriptor(descriptor);
		ClassEntry result = remappingMap.getClassEntryForObf(clazz);
		return result;
	}

	private static String getDescriptorMappedForObf(ObfuscationMap remappingMap, String obfDescriptor) {
		ClassEntry entry = getClassEntryForDescriptor(remappingMap, obfDescriptor);
		if (entry == null) {
			// Is not an item with a mapping. Likely a library class.
			return obfDescriptor;
		} else {
			return TinyDescriptor.of(entry.getMappedName());
		}
	}

	private static String getMappedForObf(ObfuscationMap remappingMap, String descriptor) {
		ClassEntry entry = remappingMap.getClassEntryForObf(descriptor);
		if (entry == null) {
			// Is not an item with a mapping. Likely a library class.
			return descriptor;
		} else {
			return entry.getMappedName();
		}
	}
}
