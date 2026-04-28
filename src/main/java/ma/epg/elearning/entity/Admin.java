package ma.epg.elearning.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity @DiscriminatorValue("ADMIN")
@Data @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Admin extends User {}
