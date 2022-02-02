package cz.osu.gamingblog.repositories;

import cz.osu.gamingblog.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    @Query("SELECT distinct p FROM Post p " +
            "INNER JOIN FETCH p.author a " +
            "INNER JOIN FETCH p.category c " +
            "WHERE c.name = ?1 " +
            "ORDER BY p.id DESC")
    List<Post> retrieveLatestPosts(String categoryName, Pageable pageable);
}
