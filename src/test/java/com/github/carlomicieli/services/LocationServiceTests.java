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
package com.github.carlomicieli.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Carlo Micieli
 */
@RunWith(MockitoJUnitRunner.class)
public class LocationServiceTests {

    @Mock
    private RestTemplate mockRest;
    @InjectMocks
    private LocationService locationService;

    @Before
    public void setUp() {
        //This method has to be called to initialize annotated fields.
        MockitoAnnotations.initMocks(this);
    }

    private Map<?, ?> buildMockResponse(String address, Double lat, Double lng) {
        Map<String, Double> location = new LinkedHashMap<String, Double>();
        location.put("lng", lng);
        location.put("lat", lat);

        Map<String, Object> geometry = new LinkedHashMap<String, Object>();
        geometry.put("location", location);

        Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("formatted_address", address);
        result.put("geometry", geometry);

        List<Object> results = new ArrayList<Object>();
        results.add(result);

        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("results", results);
        response.put("status", "OK");

        return response;
    }

    private Map<?, ?> buildMockErrorResponse(String status) {
        List<Object> results = new ArrayList<Object>();
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("results", results);
        response.put("status", status);

        return response;
    }

    @Test
    public void shouldFindTheLocationFromTheAddress() throws RestClientException, UnsupportedEncodingException {
        String address = "1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043%2C+USA";

        when(mockRest.getForObject(eq(LocationService.GOOGLE_MAPS_API_ENDPOINT), eq(Map.class), eq(address)))
                .thenReturn(buildMockResponse(address, 37.42109430, -122.08525150));

        double[] loc = locationService.findLocation("1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA");

        verify(mockRest, times(1)).getForObject(eq(LocationService.GOOGLE_MAPS_API_ENDPOINT), eq(Map.class), eq(address));
        assertEquals(2, loc.length);
        assertEquals(37.42109430, loc[0], 0.1);
        assertEquals(-122.08525150, loc[1], 0.1);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionAfterErrors() throws RestClientException, UnsupportedEncodingException {
        when(mockRest.getForObject(eq(LocationService.GOOGLE_MAPS_API_ENDPOINT), eq(Map.class), isA(String.class)))
                .thenReturn(buildMockErrorResponse("ZERO_RESULTS"));

        locationService.findLocation("1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA");
    }
}
