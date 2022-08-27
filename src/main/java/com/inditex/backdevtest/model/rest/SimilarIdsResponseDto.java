package com.inditex.backdevtest.model.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
public class SimilarIdsResponseDto {
    private List<String> similarIds;
}
