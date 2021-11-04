package com.haizhi.kafkaimportdata.thirddata.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Map;
import lombok.Data;

/**
 * @author huqq
 */
@Data
public class ThirdCommitFormData implements Serializable {
    private static final long serialVersionUID = -684979447075231310L;

    private String identity;

    @JsonProperty("form_id")
    private String formId;

    @JsonProperty("field_map")
    private Map<String, Object> fieldMap;

    @JsonProperty("police_id")
    private String policeId;

    @JsonProperty("main_data_id")
    private String mainDataId;
}
