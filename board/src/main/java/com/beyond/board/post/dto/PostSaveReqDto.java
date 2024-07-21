package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostSaveReqDto {

    private String title;
    private String contents;
    private String email;

    public Post toEntity(Author author) {
        return Post.builder()
                .title(this.title)
                .contents(this.contents)
                .author(author)
                .build();
    }
}
