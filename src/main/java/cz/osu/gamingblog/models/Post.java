package cz.osu.gamingblog.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "post")
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_post_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "title_name")
    private String title;

    @Column(name = "description", length = 100)
    String description;

    @Column(name = "html_content")
    private String htmlContent;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "post_category",
            joinColumns = @JoinColumn(name = "pk_post_id", referencedColumnName = "pk_post_id"),
            inverseJoinColumns = @JoinColumn(name = "pk_category_id", referencedColumnName = "pk_category_id")
    )
    private Collection<Category> categories;
}
