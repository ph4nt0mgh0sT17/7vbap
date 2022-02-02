package cz.osu.gamingblog.requests;

import cz.osu.gamingblog.responses.AuthorResponse;
import cz.osu.gamingblog.responses.CategoryResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreatePostRequest {
    private String title;
    private String description;
    private String htmlContent;
    private String imageName;
    private String category;
    private String authorUsername;
}
