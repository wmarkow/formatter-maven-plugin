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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.eclipse.cdt.core.CCorePlugin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.revelc.code.formatter.AbstractFormatterTest;

public class CppFormatterTest extends AbstractFormatterTest {

    private CppFormatter cppFormatter;

    @Before
    public void init() {
        cppFormatter = new CppFormatter();
    }

    @After
    public void clean() {
        cppFormatter.cleanCDT();
    }

    @Test
    public void testDoFormatCppFileWithoutWarnings() throws Exception {
        doTestFormat(cppFormatter, "Test.cpp",
                "fbde8597dd5d63404df0a569d8a0357e6ea663caf220f779dd3d7340194791c0885fc631210f116d1d99a7fdc3900ade7a778f2017cde4aa7d41ac29e4f11752");
    }

    @Test
    public void testDoFormatCppFileWithWarnings() throws Exception {
        doTestFormat(new CppFormatter(), "TestWarnings.cpp",
                "bfd73e571b24e36b277cdab8dca314106630674f6fc23030605e2ebdf47ddc8a0e22871047ee0ac9003e34fa96389c380c1fbff8b72af2e49436fddb0e3e8609");
    }

    @Test
    public void testIsIntialized() throws Exception {
        assertFalse(cppFormatter.isInitialized());
        final File targetDir = new File("target/testoutput");
        targetDir.mkdirs();
        cppFormatter.init(new HashMap<String, String>(), new AbstractFormatterTest.TestConfigurationSource(targetDir));
        assertTrue(cppFormatter.isInitialized());
    }

    @Test
    public void testCCorePluginInitialized() {
        cppFormatter.init(new HashMap<String, String>(),
                new AbstractFormatterTest.TestConfigurationSource(new File("")));

        assertNotNull(CCorePlugin.getDefault());
    }
}
