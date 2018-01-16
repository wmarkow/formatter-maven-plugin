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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.ToolFactory;
import org.junit.Test;

import net.revelc.code.formatter.AbstractFormatterTest;
import net.revelc.code.formatter.Result;

public class DefaultCppFormatterTest extends AbstractFormatterTest {

    @Test
    public void testIsIntialized() throws Exception {
        DefaultCppFormatter cppFormatter = new DefaultCppFormatter();
        assertFalse(cppFormatter.isInitialized());
        final File targetDir = new File("target/testoutput");
        targetDir.mkdirs();
        cppFormatter.init(new HashMap<String, String>(), new AbstractFormatterTest.TestConfigurationSource(targetDir));
        assertTrue(cppFormatter.isInitialized());
    }

    @Test
    public void testDoFormatCppFileWithoutWarnings() throws Exception {
        doTestFormat(new DefaultCppFormatter(), "Test.cpp",
                "fbde8597dd5d63404df0a569d8a0357e6ea663caf220f779dd3d7340194791c0885fc631210f116d1d99a7fdc3900ade7a778f2017cde4aa7d41ac29e4f11752");
    }

    @Test
    public void testDoFormatCppFileWithWarnings() throws Exception {
        Result result = doFormat(new DefaultCppFormatter(), "TestWarnings.cpp");

        assertEquals(Result.FAIL, result);
    }

    @Test(expected = NullPointerException.class)
    public void testToolFactoryCreateCodeFormatter() {
        Map<String, String> options = new HashMap<String, String>();

        ToolFactory.createCodeFormatter(options);
    }

    @Test
    public void testToolFactoryCreateDefaultCodeFormatter() {
        Map<String, String> options = new HashMap<String, String>();

        ToolFactory.createDefaultCodeFormatter(options);
    }
}
