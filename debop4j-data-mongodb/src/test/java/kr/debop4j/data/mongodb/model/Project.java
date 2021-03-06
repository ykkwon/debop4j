package kr.debop4j.data.mongodb.model;

import com.google.common.collect.Lists;
import kr.debop4j.data.model.AnnotatedEntityBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * org.hibernate.ogm.test.mongodb.model.Project
 *
 * @author 배성혁 ( sunghyouk.bae@gmail.com )
 *         13. 3. 23. 오후 2:21
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class Project extends AnnotatedEntityBase {

    private static final long serialVersionUID = -3585255464808037454L;

    public Project() {}

    public Project(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    private String id;

    @Column(name = "ProjectName", nullable = false, length = 128)
    private String name;

    @OneToMany(cascade = { CascadeType.PERSIST })
    @OrderColumn(name = "ordering")
    private List<Module> modules = Lists.newArrayList();
}
