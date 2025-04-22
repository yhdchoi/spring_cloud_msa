package com.yhdc.image_server.transaction.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteImageDto {
    private String dirId;
    private List<String> imageFileNameList;
}
