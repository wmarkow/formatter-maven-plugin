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

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.core.internal.runtime.InternalPlatform;
import org.eclipse.core.internal.runtime.Log;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.TextEdit;
import org.osgi.framework.Bundle;

import net.revelc.code.formatter.ConfigurationSource;
import net.revelc.code.formatter.LineEnding;

public class CppFormatter extends DefaultCppFormatter {

    private NullBundle fakeBundle = new NullBundle();
    private CCorePlugin cCorePlugin = new CCorePlugin();
    private FormatterLogger formatterLogger = new FormatterLogger(fakeBundle);

    @Override
    public void init(Map<String, String> options, ConfigurationSource cfg) {
        super.init(options, cfg);

        initCDT();
    }

    @Override
    public String doFormat(String code, LineEnding ending) throws BadLocationException {
        this.formatterLogger.clear();

        TextEdit te = doFormat0(code, ending);
        if (te == null) {
            this.log.debug("Code cannot be formatted.");
            return null;
        }

        switch (formatterLogger.getLastIStatus().getSeverity()) {
        case IStatus.WARNING:
        case IStatus.INFO:
            // display more detailed info of what kind of warning CDT formatter rises
            log.warn(formatterLogger.getLastIStatus().toString());
        case IStatus.OK:
            // despite of the warnings, the code can be formatted
            return prepareFormattedCode(code, te);
        }

        throw new BadLocationException(formatterLogger.getLastIStatus().toString());
    }

    /***
     * Prepares the minimal CDT environment to be usable to format C/C++ files that contains formatting warnings. Uses
     * reflection to inject objects into private fields of some CDT classes.
     */
    public void initCDT() {
        try {
            Whitebox.setInternalState(cCorePlugin, "bundle", fakeBundle);

            Map<Bundle, Log> logs = (Map<Bundle, Log>) Whitebox.getInternalState(InternalPlatform.getDefault(), "logs");
            logs.put(fakeBundle, formatterLogger);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /***
     * Cleans the CDT environment to its default state. Uses reflection to set some private fields of CDT classes to its
     * default state.
     */
    public void cleanCDT() {
        try {
            Whitebox.setInternalState(cCorePlugin, "bundle", null);

            Map<Bundle, Log> logs = (Map<Bundle, Log>) Whitebox.getInternalState(InternalPlatform.getDefault(), "logs");
            logs.clear();
        } catch (Exception e) {
            log.error(e);
        }
    }
}
