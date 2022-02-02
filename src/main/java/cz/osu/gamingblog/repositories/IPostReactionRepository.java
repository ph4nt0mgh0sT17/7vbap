package cz.osu.gamingblog.repositories;

import cz.osu.gamingblog.models.PostReaction;
import cz.osu.gamingblog.models.embeddable.PostReactionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostReactionRepository extends JpaRepository<PostReaction, PostReactionId>, JpaSpecificationExecutor<PostReaction> {
}
