package cz.osu.gamingblog.services;

import cz.osu.gamingblog.exceptions.EntityAlreadyExistsException;
import cz.osu.gamingblog.exceptions.EntityDoesNotExistException;
import cz.osu.gamingblog.models.Category;
import cz.osu.gamingblog.models.Post;
import cz.osu.gamingblog.repositories.ICategoryRepository;
import cz.osu.gamingblog.repositories.IPostRepository;
import cz.osu.gamingblog.repositories.IUserRepository;
import cz.osu.gamingblog.repositories.OffsetBasedPageRequest;
import cz.osu.gamingblog.repositories.specifications.PostSpecifications;
import cz.osu.gamingblog.requests.CreatePostRequest;
import cz.osu.gamingblog.responses.PostResponse;
import cz.osu.gamingblog.services.interfaces.IImageService;
import cz.osu.gamingblog.services.interfaces.IPostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static cz.osu.gamingblog.repositories.specifications.CategorySpecifications.hasName;
import static cz.osu.gamingblog.repositories.specifications.UserSpecifications.hasUsername;

@Service
public class PostService implements IPostService {
    private final IPostRepository _postRepository;
    private final IUserRepository _userRepository;
    private final ICategoryRepository _categoryRepository;
    private final ModelMapper _modelMapper;

    public PostService(IPostRepository _postRepository, ModelMapper _modelMapper, IUserRepository userRepository,
                       ICategoryRepository categoryRepository) {
        this._postRepository = _postRepository;
        this._modelMapper = _modelMapper;
        this._userRepository = userRepository;
        this._categoryRepository = categoryRepository;
    }

    @Override
    public List<PostResponse> retrieveLatestPosts(int postsNumber, String categoryName) {
        var category = _categoryRepository.findOne(hasName(categoryName))
                .orElseThrow(EntityDoesNotExistException::new);

        var latestPosts = _postRepository.retrieveLatestPosts(
                category, PageRequest.of(0, 4)
        );

        return latestPosts.stream()
                .map(x -> _modelMapper.map(x, PostResponse.class))
                .toList();
    }

    public List<PostResponse> retrieveLatestPosts(int postsNumber, int skipNumber, String categoryName) {
        var category = _categoryRepository.findOne(hasName(categoryName))
                .orElseThrow(EntityDoesNotExistException::new);

        var latestPosts = _postRepository.retrieveLatestPosts(
                category,
                new OffsetBasedPageRequest(skipNumber, postsNumber)
        );

        return latestPosts.stream()
                .map(x -> _modelMapper.map(x, PostResponse.class))
                .toList();
    }

    @Override
    public PostResponse retrieveById(long postId) {
        var post = _postRepository.findById(postId)
                .orElseThrow(EntityDoesNotExistException::new);

        return _modelMapper.map(post, PostResponse.class);
    }

    @Override
    public void createPost(CreatePostRequest createPostRequest) {
        var author = _userRepository.findOne(hasUsername(createPostRequest.getAuthorUsername()))
                .orElseThrow(EntityDoesNotExistException::new);

        var categories = _categoryRepository.retrieveCategoriesByNames(
                createPostRequest.getCategories()
        );

        var post = _modelMapper.map(createPostRequest, Post.class);
        post.setId(0L);
        post.setThumbnailUrl("/images/thumbnails/" + createPostRequest.getImageName());
        post.setAuthor(author);
        post.setCategories(categories);

        try {
            _postRepository.save(post);
        } catch (Exception ex) {
            throw new EntityAlreadyExistsException("Post");
        }
    }

    @Override
    public void editPost(CreatePostRequest createPostRequest, long id) {
        var author = _userRepository.findOne(hasUsername(createPostRequest.getAuthorUsername()))
                .orElseThrow(EntityDoesNotExistException::new);

        var categories = _categoryRepository.retrieveCategoriesByNames(
                createPostRequest.getCategories()
        );

        var post = _modelMapper.map(createPostRequest, Post.class);
        post.setId(id);
        post.setTitle(createPostRequest.getTitle());
        post.setDescription(createPostRequest.getDescription());
        post.setHtmlContent(createPostRequest.getHtmlContent());
        post.setThumbnailUrl("/images/thumbnails/" + createPostRequest.getImageName());
        post.setAuthor(author);
        post.setCategories(categories);

        try {
            _postRepository.save(post);
        } catch (Exception ex) {
            throw new EntityAlreadyExistsException("Post");
        }
    }

    @Override
    public void delete(long postId) {
        try {
            _postRepository.deleteById(postId);
        } catch (Exception ex) {
            throw new EntityDoesNotExistException();
        }
    }
}
