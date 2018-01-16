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

import org.eclipse.core.internal.runtime.Log;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;

/***
 * A CDT logger implementation that will store the formatting warnings created during formatting process.
 * 
 * @author wmarkowski
 *
 */
class FormatterLogger extends Log {

    private IStatus lastIStatus = Status.OK_STATUS;

    public FormatterLogger(Bundle plugin) {
        super(plugin, null);
    }

    public void log(IStatus status) {
        this.lastIStatus = status;
    }

    public IStatus getLastIStatus() {
        return lastIStatus;
    }

    public void clear() {
        lastIStatus = Status.OK_STATUS;
    }
}
