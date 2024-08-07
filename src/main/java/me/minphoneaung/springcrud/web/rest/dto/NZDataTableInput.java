package me.minphoneaung.springcrud.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author by Mya Thinzar on 30 Oct, 2023
 */
@Data
public class NZDataTableInput {

    private Integer pageIndex = 1;
    private Integer pageSize = 10;
    @JsonProperty("queryCriteria")
    private JsonNode queryCriteria;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sortField;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sortOrder;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String searchValue;

    public Pageable getPageable() {
        int startIndex = pageIndex - 1;
        Sort.Direction dir = Sort.Direction.valueOf(sortOrder.toUpperCase());
        return PageRequest.of(startIndex, pageSize, Sort.by(dir, sortField));
    }

    public Pageable getAllPageable() {
        int startIndex = pageIndex - 1;
        Sort.Direction dir = Sort.Direction.valueOf("asc".toUpperCase());
        return PageRequest.of(startIndex, Integer.MAX_VALUE, Sort.by(dir, "folderCreationDate"));
    }
}
