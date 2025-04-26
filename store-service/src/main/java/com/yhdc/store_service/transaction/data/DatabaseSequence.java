package com.yhdc.store_service.transaction.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "database_sequence")
public class DatabaseSequence {

    @Id
    private String id;

    private Long seq;

}
