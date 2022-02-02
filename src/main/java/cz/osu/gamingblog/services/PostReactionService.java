package cz.osu.gamingblog.services;

import cz.osu.gamingblog.exceptions.EntityDoesNotExistException;
import cz.osu.gamingblog.models.PostReaction;
import cz.osu.gamingblog.models.embeddable.PostReactionId;
import cz.osu.gamingblog.repositories.IPostReactionRepository;
import cz.osu.gamingblog.repositories.IPostRepository;
import cz.osu.gamingblog.repositories.IUserRepository;
import cz.osu.gamingblog.requests.PostReactionRequest;
import cz.osu.gamingblog.responses.PostReactionResponse;
import cz.osu.gamingblog.services.interfaces.IPostReactionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

import static cz.osu.gamingblog.repositories.specifications.PostReactionSpecifications.findByAuthorUserame;
import static cz.osu.gamingblog.repositories.specifications.PostReactionSpecifications.findByPostId;
import static cz.osu.gamingblog.repositories.specifications.UserSpecifications.hasUsername;

@Service
public class PostReactionService implements IPostReactionService {
    private final IPostReactionRepository _postReactionRepository;
    private final IPostRepository _postRepository;
    private final IUserRepository _userRepository;
    private final ModelMapper _modelMapper;

    public PostReactionService(IPostReactionRepository postReactionRepository, IPostRepository postRepository,
                               IUserRepository userRepository, ModelMapper modelMapper) {
        _postReactionRepository = postReactionRepository;
        _postRepository = postRepository;
        _userRepository = userRepository;
        _modelMapper = modelMapper;
    }

    @Override
    public List<PostReactionResponse> retrieveAllPostReactionsByPostId(long postId) {
        var postReactions = _postReactionRepository.findAll(findByPostId(postId));

        return postReactions.stream()
                .map(x -> _modelMapper.map(x, PostReactionResponse.class))
                .toList();
    }

    @Override
    public void savePostReaction(PostReactionRequest postReactionRequest, long postId, Principal principal) {
        var user = _userRepository.findOne(hasUsername(principal.getName()))
                .orElseThrow(EntityDoesNotExistException::new);

        var post = _postRepository.findById(postId)
                .orElseThrow(EntityDoesNotExistException::new);

        var postReaction = new PostReaction();
        postReaction.setId(new PostReactionId(user.getId(), postId));
        postReaction.setAuthor(user);
        postReaction.setPost(post);
        postReaction.setReaction(postReactionRequest.getPostReactionItem());

        _postReactionRepository.save(postReaction);
    }

    @Override
    public void deletePostReaction(long postId, Principal principal) {
        var postReaction = _postReactionRepository.findOne(
                findByPostId(postId).and(findByAuthorUserame(principal.getName()))
        ).orElseThrow(EntityDoesNotExistException::new);

        try {
            _postReactionRepository.delete(postReaction);
        } catch (Exception ex) {
            throw new EntityDoesNotExistException("PostReaction");
        }
    }
}
