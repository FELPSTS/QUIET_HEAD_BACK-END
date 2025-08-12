package com.QuietHead.Head.dto;

public record LoginResponse(
    String token,
    Long userId,
    String email,
    String name
) {}
