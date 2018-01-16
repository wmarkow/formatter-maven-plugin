/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.revelc.code.formatter.cpp;

import java.util.Map;

import org.eclipse.cdt.core.ToolFactory;
import org.eclipse.cdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import net.revelc.code.formatter.AbstractCacheableFormatter;
import net.revelc.code.formatter.ConfigurationSource;
import net.revelc.code.formatter.Formatter;
import net.revelc.code.formatter.LineEnding;

class DefaultCppFormatter extends AbstractCacheableFormatter implements Formatter {

    private CodeFormatter formatter;

    @Override
    public void init(Map<String, String> options, ConfigurationSource cfg) {
        super.initCfg(cfg);

        // do not use ToolFactory.createCodeFormatter as it throws exception
        // (see
        // net.revelc.code.formatter.cpp.DefaultCppFormatterTest.testToolFactoryCreateCodeFormatter())
        this.formatter = ToolFactory.createDefaultCodeFormatter(options);
    }

    @Override
    public String doFormat(String code, LineEnding ending) throws BadLocationException {
        TextEdit te = doFormat0(code, ending);
        if (te == null) {
            this.log.debug("Code cannot be formatted.");

            return null;
        }

        return prepareFormattedCode(code, te);
    }

    @Override
    public boolean isInitialized() {
        return formatter != null;
    }

    protected TextEdit doFormat0(String code, LineEnding ending) throws BadLocationException {
        try {
            TextEdit te = this.formatter.format(CodeFormatter.K_UNKNOWN, code, 0, code.length(), 0, ending.getChars());

            return te;
        } catch (Exception e) {
            log.debug(e);
            // map any Exception into BadLocationException so the formatting
            // will fail
            throw new BadLocationException(e.getMessage());
        }
    }

    protected String prepareFormattedCode(String code, TextEdit te)
            throws MalformedTreeException, BadLocationException {
        IDocument doc = new Document(code);
        te.apply(doc);
        String formattedCode = doc.get();

        if (code.equals(formattedCode)) {
            return null;
        }

        return formattedCode;
    }
}
