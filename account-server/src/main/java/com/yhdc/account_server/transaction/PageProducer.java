package com.yhdc.account_server.transaction;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageProducer {

    public Pageable getPageable(String pageNo, String pageSize, String sortBy, String sortOrder) {

        final Sort sort = switch (sortBy) {
            case "ASC" -> Sort.by(Sort.Direction.ASC, sortOrder);
            default -> Sort.by(Sort.Direction.DESC, sortOrder);
        };

        return PageRequest.of(Integer.parseInt(pageNo), Integer.parseInt(pageSize), sort);
    }

}
