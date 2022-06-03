package com.sdau.hotelsystem.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class UploadResult {
    private Integer code;
    private String msg;
    private String src;
    private Map<String,String> data;
}
