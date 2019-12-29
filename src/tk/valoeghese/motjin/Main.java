package tk.valoeghese.motjin;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import tk.valoeghese.motjin.map.ClassEntry;
import tk.valoeghese.motjin.map.Descriptor;
import tk.valoeghese.motjin.map.FieldEntry;
import tk.valoeghese.motjin.map.parser.ObfuscationMap;

public class Main {
	public static MainArgs options;

	public static void main(String[] args) {
		options = MainArgs.of(args);

		ObfuscationMap intermediary = ObfuscationMap.parseTiny(options.intermediaryMap);
		ObfuscationMap mojmap = ObfuscationMap.parseProguard(options.mojMap);

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
					String mojmapDescriptor = flag ? Descriptor.of(getClassEntryForDescriptor(mojmap, intermediaryFieldEntry.descriptor).getMappedName()) : intermediaryFieldEntry.descriptor;
					System.out.println(mojmapDescriptor);
					String mojmapFieldKey = intermediaryFieldEntry.obfName + ":" + mojmapDescriptor;

					// Get field key and set final column mapping
					FieldEntry mojmapFieldEntry = mojmapEntry.fieldMap.getOrDefault(mojmapFieldKey, intermediaryFieldEntry);
					intermediaryFieldEntry.setFinalColumnMapping(mojmapFieldEntry.getMappedName());

					// Add to output
					output.append("\n").append(intermediaryFieldEntry.toString());
				});

				writer.println(output.toString());
			});
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private static ClassEntry getClassEntryForDescriptor(ObfuscationMap remappingMap, String descriptor) {
		ClassEntry result = remappingMap.getClassEntryForObf(Descriptor.classFromDescriptor(descriptor));
		if (result == null) {
			System.out.println("OhNo Null");
		}
		return result;
	}
}
