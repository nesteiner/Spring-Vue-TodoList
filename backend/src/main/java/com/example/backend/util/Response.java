package com.example.backend.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    Status code;
    String message;
    T data;

    public static <T> Response<T> Ok(String message, T data) {
        return new Response<>(Status.Ok, message, data);
    }

    public static <T> Response<T> Err(String message) {
        return new Response<>(Status.Err, message, null);
    }
}
