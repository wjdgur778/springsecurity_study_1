package com.example.demo.api.common;

import lombok.Builder;

@Builder
public class Result<T> {
    private String message;
    private T data;

}
