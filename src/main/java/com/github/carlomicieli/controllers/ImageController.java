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
package com.github.carlomicieli.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.carlomicieli.models.Movie;

@Controller
@RequestMapping("/images")
public class ImageController {
	private MongoTemplate mongoTemplate;
	
	private enum ImageType {
		POSTER, THUMB
	};
	
	@Autowired
	public ImageController(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@RequestMapping(value = "/posters/{movieId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> renderPoster(@PathVariable String movieId) throws IOException {
	    return render(movieId, ImageType.POSTER);
	}
	
	@RequestMapping(value = "/thumb/{movieId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> renderThumb(@PathVariable String movieId) throws IOException {
	    return render(movieId, ImageType.THUMB);
	}
	
	private ResponseEntity<byte[]> render(String movieId, ImageType t) throws IOException {
		ObjectId id = new ObjectId(movieId);
		
		Movie m = mongoTemplate.findById(id, Movie.class);
	    
		byte[] aob = null;
		if (t==ImageType.POSTER)
			aob = m.getPoster();
		else if (t==ImageType.THUMB)
			aob = m.getThumb();
		
		if (aob==null) return null;
		
		InputStream in = new ByteArrayInputStream(aob);

	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_PNG);
	    return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
	}
}
