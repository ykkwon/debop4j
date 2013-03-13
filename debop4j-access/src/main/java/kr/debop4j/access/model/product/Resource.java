package kr.debop4j.access.model.product;

import com.google.common.base.Objects;
import kr.debop4j.access.model.AccessEntityBase;
import kr.debop4j.access.model.ICodeBaseEntity;
import kr.debop4j.core.Guard;
import kr.debop4j.core.tools.HashTool;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * 프로그램에서 생산한 자원 정보. Actor가 접근 가능한 대상을 말합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 3. 10.
 */
@Entity
@Table(name = "Resource")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class Resource extends AccessEntityBase implements ICodeBaseEntity {

    private static final long serialVersionUID = 3675784179606293494L;

    protected Resource() {}

    public Resource(Product product, String code) {
        this(product, code, code);
    }

    public Resource(Product product, String code, String name) {
        Guard.shouldNotBeNull(product, "product");
        Guard.shouldNotBeEmpty(code, "code");
        Guard.shouldNotBeEmpty(name, "name");

        this.product = product;
        this.code = code;
        this.name = name;
    }

    @Id
    @GeneratedValue
    @Column(name = "ResourceId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ProductId", nullable = false)
    @NaturalId
    private Product product;

    @Column(name = "ResourceCode", nullable = false, length = 128)
    @NaturalId
    private String code;

    @Column(name = "ResourceName", nullable = false, length = 128)
    private String name;

    @Column(name = "ResourceActive")
    private Boolean active;

    @Column(name = "ResourceDesc", length = 4000)
    private String description;

    @Column(name = "ResourceExAttr", length = 4000)
    private String exAttr;

    @Override
    public int hashCode() {
        if (isPersisted())
            return HashTool.compute(id);
        return HashTool.compute(code);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("id", id)
                .add("code", code)
                .add("name", name)
                .add("active", active);
    }
}