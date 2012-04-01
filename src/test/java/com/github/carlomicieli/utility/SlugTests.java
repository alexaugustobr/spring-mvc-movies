/*
 * Copyright 2012 the original author or authors.
 *
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
package com.github.carlomicieli.utility;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Carlo P. Micieli
 *
 */
public class SlugTests {
	@Test
	public void convertingEmptyStringToSlug() {
		String slug = Slug.makeSlug("");
		assertEquals("", slug);
	}
	
	@Test
	public void replacingWhitespaceToSlug() {
		String slug = Slug.makeSlug("Time is an illusion");
		assertEquals("time-is-an-illusion", slug);
	}
	
	@Test
	public void replacingPunctuationToSlug() {
		String slug = Slug.makeSlug("Time; is an: illusion.");
		assertEquals("time-is-an-illusion", slug);
	}
	
	@Test
	public void replacingNonLatinToSlug() {
		String slug = Slug.makeSlug("Timè is àn illusiòn.");
		assertEquals("time-is-an-illusion", slug);
	}
}
