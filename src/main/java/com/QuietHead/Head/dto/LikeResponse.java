package com.QuietHead.Head.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {
    private int likeCount;
    private boolean hasLiked;
    private Set<String> likedBy;
}