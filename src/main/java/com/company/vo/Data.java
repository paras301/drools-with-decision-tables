package com.company.vo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Data {

    private JsonNode data;

    public String get(String xpath) {
    	try {
    		JsonNode jsonNode = data.at(xpath);
            return jsonNode.asText();
    	} catch (Exception e) {
            log.error("Can not get xpath " + xpath, e);
        }
        return null;
    }

    public Integer getInt(String xpath) {
        return Integer.valueOf(get(xpath));
    }

    public Long getLong(String xpath) {
        return Long.valueOf(get(xpath));
    }

    public Double getDouble(String xpath) {
        return Double.valueOf(get(xpath));
    }

    public Boolean getBoolean(String xpath) {
        return Boolean.valueOf(get(xpath));
    }

    public void set(String xpath, String value) {
        try {
            log.debug("set called for xpath " + xpath + " with value " + value);
            ((ObjectNode) data).put(xpath, value);
        } catch (Exception e) {
            log.error("Can not set xpath " + xpath + " with value " + value + " as parent is null", e);
        }
    }

    public void remove(String xpath) {
        log.debug("remove called for xpath " + xpath);
        ((ObjectNode) data).remove(xpath);
    }
}
