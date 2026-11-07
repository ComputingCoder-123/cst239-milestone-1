package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gcu.cst239.kabwe.elijah.lab1.models.ProductDto;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ProductDto> readProducts(String filePath) throws IOException {
        File file = new File(filePath);
        return objectMapper.readValue(file, new TypeReference<List<ProductDto>>() {});
    }
}