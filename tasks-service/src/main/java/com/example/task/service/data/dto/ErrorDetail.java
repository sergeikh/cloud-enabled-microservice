package com.example.task.service.data.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Error details for error response.
 */
@Data
public class ErrorDetail {
    private	String	title;
    private	int	status;
    private	String	detail;
    private	long	timeStamp;
    private	String	developerMessage;
    private Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
}
