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
package com.github.carlomicieli.nerdmovies.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import static java.net.URLEncoder.encode;

/**
 * A location service that using Google geocoding API return the location coordinates.
 *
 * @author Carlo Micieli
 */
@Service("locationService")
public class LocationService {

    public static final String STATUS_OK = "OK";
    public static final String STATUS_ZERO_RESULTS = "ZERO_RESULTS";
    public static final String STATUS_OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";
    public static final String STATUS_REQUEST_DENIED = "REQUEST_DENIED";
    public static final String STATUS_INVALID_REQUEST = "INVALID_REQUEST";

    // google maps api endpoint
    public static final String GOOGLE_MAPS_API_ENDPOINT = "http://maps.googleapis.com/maps/api/geocode/json?address={address}&sensor=false";

    private RestTemplate restTemplate;

    @Autowired
    public LocationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String buildMessage(String status) {
        if (status == STATUS_ZERO_RESULTS)
            return "No result is found";
        else if (status == STATUS_OVER_QUERY_LIMIT)
            return "You are over your quota";
        else if (status == STATUS_REQUEST_DENIED)
            return "Your request was denied";
        else if (status == STATUS_INVALID_REQUEST)
            return "The query is missing";

        return "";
    }

    /**
     * Perform a geocode request to find the coordinates for a valid address.
     *
     * @param address the address that you want to geocode.
     * @return the coordinates.
     * @throws RestClientException
     * @throws UnsupportedEncodingException
     */
    public double[] findLocation(String address) throws RestClientException, UnsupportedEncodingException {
        Map<?, ?> obj = restTemplate.getForObject(GOOGLE_MAPS_API_ENDPOINT, Map.class, encode(address, "UTF-8"));

        // check the response status
        String status = (String) obj.get("status");
        if (!status.equals(STATUS_OK)) {
            throw new RuntimeException(buildMessage(status));
        }

        List<?> results = (List<?>) obj.get("results");
        Map<?, ?> result = (Map<?, ?>) results.get(0);
        Map<?, ?> geometry = (Map<?, ?>) result.get("geometry");
        Map<?, ?> location = (Map<?, ?>) geometry.get("location");

        return new double[]{
                (Double) location.get("lat"),
                (Double) location.get("lng")
        };
    }
}
