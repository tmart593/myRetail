package com.target.myretail.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ResponseMessageUtility {

    public static String composeErrorMessage(String errMsg)  {
            return "{\"errorMessage\":\""+errMsg+"\"}";

    }
    public static String composeSuccessMessage(String status)  {
        return "{\"status\":\""+status+"\"}";

    }

    public static String composeFromErrorList(List<DataInputError> dataInputErrorList) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(dataInputErrorList);
        } catch (JsonProcessingException e) {
            return composeErrorMessage("problem creating field level errror data");
        }
    }

}
