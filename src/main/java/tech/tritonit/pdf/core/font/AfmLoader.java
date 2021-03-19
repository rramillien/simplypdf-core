/*
 * Copyright 2021 tritonit.tech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tech.tritonit.pdf.core.font;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import tech.tritonit.pdf.core.document.PdfContext;
import tech.tritonit.pdf.core.geometry.BoundingBox;

/**
 * Loader for AFM fonts.
 * 
 * @author Régis Ramillien
 *
 */
public class AfmLoader {
	/**
	 * This should never be invoked for a utility class.
	 */
	private AfmLoader() {
		throw new IllegalAccessError("Utility class");
	}
	
	/**
	 * Load a font variant in AFM format from the input stream.
	 *
	 * @param context The context of the PDF document.
	 * @param stream THe stream to load font variant from.
	 * @return The loaded font variant.
	 * @throws IOException When font variant cannot be loaded.
	 */
	public static FontVariant load(PdfContext context, InputStream stream) throws IOException {
		Map<String, Byte> nameByteMapping = new HashMap<>();
		
		FontVariant fontVariant = new FontVariant(context);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		String line = reader.readLine();

		while (line != null) {
			if (line.startsWith("FontName ")) {
				String[] tokens = line.split(" ");
				fontVariant.setName(tokens[1]);
			} else if (line.startsWith("UnderlinePosition ")) {
				String[] tokens = line.split(" ");
				fontVariant.setUnderlinePosition(Integer.parseInt(tokens[1]));
			} else if (line.startsWith("UnderlineThickness ")) {
				String[] tokens = line.split(" ");
				fontVariant.setUnderlineThickness(Integer.parseInt(tokens[1]));
			} else if (line.startsWith("C ")) {
				String[] tokens = line.split(" ");
				byte c = (byte) Integer.parseInt(tokens[1]);
				//Do not parse bytes with unknown char code. Doesn't need them for now.
				if(c != -1) {
					//Add width
					fontVariant.addWidth(c, Integer.parseInt(tokens[4]));
					
					//Store name for further use.
					String name = tokens[7];
					nameByteMapping.put(name, c);
					
					//Add BoundingBox
					fontVariant.addBox(c, new BoundingBox(Integer.parseInt(tokens[10]),
							Integer.parseInt(tokens[11]),  
							Integer.parseInt(tokens[12]),  
							Integer.parseInt(tokens[13])));
				}
			} else if (line.startsWith("KPX ")) {
				String[] tokens = line.split(" ");
				Byte source = nameByteMapping.get(tokens[1]);
				Byte destination = nameByteMapping.get(tokens[2]);
				if(source != null && destination != null) {
					fontVariant.addKerning(source, destination, Integer.parseInt(tokens[3]));
				}
			}

			line = reader.readLine();
		}
		
		return fontVariant;
	}

}
