package cn.sangedon.edu.kafka.data.service;

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

    private String formId;

    private Map<String, Object> fieldMap;

    private String policeId;

    private String mainDataId;
}
