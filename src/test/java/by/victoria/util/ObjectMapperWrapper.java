package by.victoria.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.test.web.servlet.MvcResult;

public class ObjectMapperWrapper {

    @SneakyThrows
    public static <T> T toObject(ObjectMapper objectMapper, MvcResult result, TypeReference<T> typeReference) {
        return objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
    }
}
