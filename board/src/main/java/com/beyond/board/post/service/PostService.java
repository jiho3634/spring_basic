package com.beyond.board.post.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorUpdateReqDto;
import com.beyond.board.author.service.AuthorService;
import com.beyond.board.post.domain.Post;
import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.dto.PostUpdateReqDto;
import com.beyond.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final AuthorService authorService;

    @Autowired
    public PostService(PostRepository postRepository, AuthorService authorService) {
        this.postRepository = postRepository;
        this.authorService = authorService;
    }

    public Post postCreate(PostSaveReqDto dto) {
        Author author = authorService.authorFindByEmail(dto.getEmail());
        return postRepository.save(dto.toEntity(author));
    }

    public List<PostListResDto> postList() {
        return postRepository
                .findAllLeftJoin()
                .stream()
                .map(post -> new PostListResDto()
                    .listFromEntity(post))
                    .collect(Collectors.toList());
    }

    public PostDetResDto postDetail(Long id) {
        return new PostDetResDto()
                .detFromEntity(
                    postRepository
                    .findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(
                        "id에 해당하는 글이 없습니다.")));
    }

    @Transactional
    public void postDelete(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public void postUpdate(Long id, PostUpdateReqDto postUpdateReqDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("post is not found"));
        post.updatePost(postUpdateReqDto);
        postRepository.save(post);
    }
}
