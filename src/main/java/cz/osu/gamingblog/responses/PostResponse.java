package cz.osu.gamingblog.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private long id;
    private String title;
    private String description;
    private String htmlContent;
    private String thumbnailUrl;
    private List<CategoryResponse> categories;
    private AuthorResponse author;
}
