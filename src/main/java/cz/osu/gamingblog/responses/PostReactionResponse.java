package cz.osu.gamingblog.responses;

import cz.osu.gamingblog.models.PostReactionItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostReactionResponse {
    private PostReactionItem reaction;
    private String authorUsername;
}
