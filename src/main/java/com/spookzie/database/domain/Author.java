package com.spookzie.database.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author
{
    // Using Long & Integer here instead of long & int
    // This makes sure that the default value for a new variable would be null instead of 0
    private Long id;
    private String name;
    private Integer age;
}