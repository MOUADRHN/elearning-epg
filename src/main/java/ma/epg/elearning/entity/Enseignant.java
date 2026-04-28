package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity @DiscriminatorValue("ENSEIGNANT")
@Data @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Enseignant extends User {
    private String specialite;
    private String grade;
    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL) private List<Course> courses;
    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL) private List<Assignment> assignments;
    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL) private List<ShopItem> shopItems;
    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL)
    private List<Announcement> announcements;}
