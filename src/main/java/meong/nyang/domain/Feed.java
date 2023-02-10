package meong.nyang.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedId")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Long type;

    @NotNull
    @Column(columnDefinition = "TEXT", length = 65535)
    private String material;

    @NotNull
    @Column(columnDefinition = "TEXT", length = 65535)
    private String ingredient;

    @Column(columnDefinition = "TEXT", length = 65535)
    private String img;
}