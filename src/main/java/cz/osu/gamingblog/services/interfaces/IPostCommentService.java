package cz.osu.gamingblog.services.interfaces;

import cz.osu.gamingblog.requests.PostCommentRequest;
import cz.osu.gamingblog.responses.PostCommentResponse;

import java.security.Principal;
import java.util.List;

public interface IPostCommentService {
    List<PostCommentResponse> retrieveAllPostCommentsByPostId(long postId);

    /**
     * Create a new comment to the post identified by postId.
     *
     * @param postCommentRequest The request object containing the text of the comment.
     * @param postId             The ID of the post that is being commented.
     */
    void create(PostCommentRequest postCommentRequest, long postId, Principal principal);

    void delete(long postCommentId, Principal principal);
}
