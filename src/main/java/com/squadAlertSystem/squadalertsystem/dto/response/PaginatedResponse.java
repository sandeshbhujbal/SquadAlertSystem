package com.squadAlertSystem.squadalertsystem.dto.response;

import lombok.*;

import java.util.List;

/**
 * @author Milan Rathod
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponse<T> {

    private List<T> contents;

    private int currentPage;

    private int totalPages;

    private long totalElements;
}
