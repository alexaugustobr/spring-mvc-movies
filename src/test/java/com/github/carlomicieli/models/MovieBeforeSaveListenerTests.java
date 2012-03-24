/*
Copyright [2012] [Carlo P. Micieli]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.github.carlomicieli.models;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import static org.junit.Assert.*;

public class MovieBeforeSaveListenerTests {
	@Test
	public void beforeSaveEventFillsValue() {
		MovieBeforeSaveListener listener = new MovieBeforeSaveListener();
		Movie movie = new Movie();
		movie.setTitle("Animal House");
		DBObject dbo = new BasicDBObject();
		
		listener.onBeforeSave(movie, dbo);
		assertEquals("animal-house", dbo.get("slug"));
		assertNotNull(dbo.get("savedAt")); // don't check the actual ts
	}
}
