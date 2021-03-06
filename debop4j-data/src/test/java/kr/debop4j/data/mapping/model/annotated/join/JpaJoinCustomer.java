package kr.debop4j.data.mapping.model.annotated.join;

import com.google.common.base.Objects;
import kr.debop4j.core.tools.HashTool;
import kr.debop4j.data.model.AnnotatedEntityBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;

/**
 * org.annotated.mapping.domain.model.join.JpaJoinCustomer
 *
 * @author 배성혁 ( sunghyouk.bae@gmail.com )
 * @since 12. 12. 4.
 */
@Entity
@Table(name = "JPA_JOIN_CUSTOMER")
@SecondaryTable(name = "JPA_JOIN_CUSTOMER_ADDR", pkJoinColumns = @PrimaryKeyJoinColumn(name = "CUSTOMER_ID"))
@Getter
@Setter
public class JpaJoinCustomer extends AnnotatedEntityBase {

    private static final long serialVersionUID = 6609847114968580068L;

    @Id
    @GeneratedValue
    @Column(name = "CUSTOMER_ID")
    private Long id;

    @Column(name = "CUSTOMER_NAME")
    private String name;

    @Column(name = "CUSTOMER_EMAIL")
    private String email;


    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "street",
                                       column = @Column(name = "STREET", table = "JPA_JOIN_CUSTOMER_ADDR")),
                    @AttributeOverride(name = "zipcode",
                                       column = @Column(name = "ZIPCODE", table = "JPA_JOIN_CUSTOMER_ADDR")),
                    @AttributeOverride(name = "city",
                                       column = @Column(name = "CITY", table = "JPA_JOIN_CUSTOMER_ADDR")),
            }
    )
    private JpaJoinAddress joinAddress = new JpaJoinAddress();

    // Generated 는 DB에서 값을 관리하므로, Hibernate에서 특정 작업 후 그 값을 갱신하도록 refresh를 강제화한다는 뜻이다.
    // MySqlUpdatedTimestamp, MySqlUpdatedTimestamp 를 참고하여, DB 컬럼에 정의해줘야 한다.

    @Generated(GenerationTime.INSERT)
    @Column(name = "CREATED_TIME", insertable = false, updatable = false)
    @Temporal( TemporalType.TIMESTAMP )
    //@Setter(AccessLevel.PROTECTED)
    private Date created;

    @Generated(GenerationTime.ALWAYS)
    @Column(name = "UPDATED_TIME", insertable = false, updatable = false)
    @Temporal( TemporalType.TIMESTAMP )
    //@Setter(AccessLevel.PROTECTED)
    private Date lastUpdated;

    @Override
    public int hashCode() {
        if (isPersisted())
            return HashTool.compute(id);

        return HashTool.compute(name);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("id", id)
                .add("name", name)
                .add("email", email);
    }
}
