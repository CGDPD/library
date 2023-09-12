package com.cgdpd.library.catalog.app.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class TestUtils {

    public static <T> T getObjectFromResultActions(ResultActions resultActions, Class<T> clazz,
                                                   ObjectMapper objectMapper) {
        try {
            var json = getJsonObjectFromResult(resultActions);
            return objectMapper.readValue(json.toString(), clazz);
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject getJsonObjectFromResult(ResultActions resultActions)
          throws UnsupportedEncodingException, JSONException {
        var mvcResult = resultActions.andReturn();
        var jsonResponse = mvcResult.getResponse().getContentAsString();
        return new JSONObject(jsonResponse);
    }
}
