package cz.osu.gamingblog.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private CategoryResponse category;
    private AuthorResponse author;
}
