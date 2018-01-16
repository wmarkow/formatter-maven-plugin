/**
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
/*
 * Test.cpp
 *
 *  Created on: 09.01.2018
 *      Author: wmarkowski
 */

#define PRIPSTR "%s"
void test1()
{
 uint8_t a = 5;
 printf_P(PSTR("Data Rate\t = "PRIPSTR"\r\n"), pgm_read_word(&rf24_datarate_e_str_P[getDataRate()]));
}

void test2()
{
 uint8_t a = 5;
}
