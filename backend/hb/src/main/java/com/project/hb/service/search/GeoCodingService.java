package com.project.hb.service.search;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.project.hb.exception.GeoCodingException;
import com.project.hb.model.search.Location;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class GeoCodingService {
    private GeoApiContext geoApiContext;

    public Location getLatLon(Long id, String address) throws GeoCodingException {
        try {
            GeocodingResult result = GeocodingApi.geocode(geoApiContext, address).await()[0];
            if (result.partialMatch) {
                throw new GeoCodingException("Failed to encode address.");
            }
            return new Location(id, new GeoPoint(result.geometry.location.lat, result.geometry.location.lng));
        } catch (IOException | InterruptedException | ApiException e) {
            e.printStackTrace();
            throw new GeoCodingException("Failed to encode address.");
        }
    }
}
