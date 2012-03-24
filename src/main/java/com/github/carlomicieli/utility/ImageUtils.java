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
package com.github.carlomicieli.utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import static org.imgscalr.Scalr.*;

public class ImageUtils {
	public static byte[] createThumbnail(MultipartFile file, int targetSize) throws IOException {
		validateFile(file);
		
		final BufferedImage image = convertToImage(file);
		final BufferedImage thumb = pad(
				resize(image, Method.SPEED, targetSize, OP_ANTIALIAS, OP_BRIGHTER), 2);
		
		return convertToArray(thumb, file.getContentType());
	}
	
	public static byte[] convert(MultipartFile file) throws IOException {
		validateFile(file);
		return file.getBytes();
	}
	
	private static void validateFile(MultipartFile file) throws IOException {
		String contentType = file.getContentType();
		if (!contentType.equals(MediaType.IMAGE_JPEG.toString()) && !contentType.equals(MediaType.IMAGE_PNG.toString()))
			throw new IOException("Invalid media type");
	}
	
	private static BufferedImage convertToImage(MultipartFile file) throws IOException {		
		InputStream in = new ByteArrayInputStream(file.getBytes());
		return ImageIO.read(in);
	}
	
	private static byte[] convertToArray(BufferedImage image, String contentType) throws IOException {
		byte[] imageInByte;
		
		String typeName = "jpg";
		if (contentType.equals(MediaType.IMAGE_PNG))
			typeName = "png";
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, typeName, baos);
		baos.flush();
		imageInByte = baos.toByteArray();
		baos.close();
		
		return imageInByte;
	}
}
