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
package tech.tritonit.pdf.core.document;

import java.io.OutputStream;

import tech.tritonit.pdf.core.exceptions.PdfGenerationException;

public interface PdfObject {
	/**
	 * Render an object to output stream.
	 * 
	 * @param stream The output stream where object will be rendered.
	 * @return The number of bytes rendered.
	 * @throws PdfGenerationException When object cannot be rendered to output stream.
	 */
	int write(OutputStream stream) throws PdfGenerationException;
	
	/**
	 * Get the identifier of this object.
	 * 
	 * @return The identifier of this object in document.
	 */
	int getId();
}
