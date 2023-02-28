package com.project.hb.model.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Document(indexName = "loc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @Field(type = FieldType.Long)
    private Long id;
    @GeoPointField
    private GeoPoint geoPoint;
}
