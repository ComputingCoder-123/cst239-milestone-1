package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Map<String, Object>> readProducts(String filePath) throws IOException {
        File file = new File(filePath);
        return objectMapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});
    }
}