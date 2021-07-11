package com.team.web.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This is a generic {@code Service Response}.
 *
 * @param <T> the data type to response.
 */
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class ServiceResponse<T> {
    private String status;
    private T data;
}
