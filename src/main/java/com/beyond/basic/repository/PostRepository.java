package com.beyond.basic.repository;

import com.beyond.basic.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

//    //  네이밍 룰이 아닌 로우 쿼리 생성
//
//    //  fetch 는 p의 모든 컬럼과 a의 모든 컬럼을 left Join 함.
//    @Query("select p from Post p left join fetch p.author")
//    List<Post> findAllFetch();
//
//    //  fetch 를 빼면 p 에서만 컬럼을 가져온다. a를 가지고 filtering 을 하는 기능.
//    //  Left Join 으로는 N + 1 이슈를 해결할 수 없다.
//    @Query("select p from Post p left join p.author")
//    List<Post> findAllLeftJoin();
}
