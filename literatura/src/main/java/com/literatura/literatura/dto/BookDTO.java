package com.literatura.literatura.dto;

import java.util.List;

public record BookDTO(
        String title,
        List<String> languages,
        Integer downloadCount,
        AuthorDTO author
) {

}


