package com.team.web.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a generic {@code Service Response}.
 *
 * @param <T> the data type to response.
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class ServiceResponse<T> {
    private String status;
    private T data;
}
