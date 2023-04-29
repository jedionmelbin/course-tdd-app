package com.xprotec.app.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collation = "Feedback")
public class Feedback {
    private String id;
    private String productId;
    private String userId;
    private String status;
    private String version;
    private String message;
}
