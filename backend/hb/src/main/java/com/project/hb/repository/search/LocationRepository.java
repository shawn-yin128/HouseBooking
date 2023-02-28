package com.project.hb.repository.search;

import com.project.hb.model.search.Location;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends ElasticsearchRepository<Location, Long> {
    @Query("{\"bool\": {\"filter\": {\"geo_distance\": {\"distance\": \"?2km\", \"geoPoint\": {\"lat\": ?0, \"lon\": ?1}}}}}")
    List<Location> findByDistance(double lat, double lon, String distance);
}
