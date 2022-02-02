package cz.osu.gamingblog.repositories.specifications;

import cz.osu.gamingblog.models.Category_;
import cz.osu.gamingblog.models.Post;
import cz.osu.gamingblog.models.Post_;
import cz.osu.gamingblog.models.User_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostSpecifications {
    public static Specification<Post> findByAuthor(String authorName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            root.fetch(Post_.author, JoinType.INNER);
            root.fetch(Post_.category, JoinType.INNER);

            return criteriaBuilder.equal(root.get(Post_.author).get(User_.username), authorName);
        };
    }

    public static Specification<Post> findReview() {
        return (root, criteriaQuery, criteriaBuilder) -> {
            root.fetch(Post_.author, JoinType.INNER);
            root.fetch(Post_.category, JoinType.INNER);

            return criteriaBuilder.equal(root.get(Post_.category).get(Category_.name), "Recenze");
        };
    }
}
