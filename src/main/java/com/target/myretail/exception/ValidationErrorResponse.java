package com.target.myretail.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    private List<DataInputError> dataInputErrors = new ArrayList<>();

    public List<DataInputError> getDataInputErrors() {
        return dataInputErrors;
    }

}
