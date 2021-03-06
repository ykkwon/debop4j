package kr.debop4j.search.hibernate.model;

import kr.debop4j.data.model.AnnotatedEntityBase;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * kr.debop4j.search.hibernate.model.SearchItem
 *
 * @author 배성혁 ( sunghyouk.bae@gmail.com )
 * @since 13. 2. 28.
 */
@Entity
@Table(name = "SEARCH_ITEM")
@Indexed
// @Analyzer(impl = org.apache.lucene.analysis.cjk.CJKAnalyzer.class)
@Analyzer(impl = org.apache.lucene.analysis.kr.KoreanAnalyzer.class)
@Getter
@Setter
public class SearchItem extends AnnotatedEntityBase {

    private static final long serialVersionUID = -4795821070108007345L;

    @Id
    @GeneratedValue
    @Column(name = "SEARCH_ITEM_ID")
    @DocumentId
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @NotNull
    @Column(name = "SEARCH_ITEM_TITLE", length = 255)
    @Field(termVector = TermVector.YES)
    private String title;

    @Column(name = "SEARCH_ITEM_DESC", length = 2000)
    @Field(termVector = TermVector.YES)
    private String description;

    @Column(name = "SEARCH_ITEM_EAN")
    @Field
    private String ean;

    @Column(name = "SEARCH_ITEM_AMOUNT")
    @Field
    @NumericField(precisionStep = 6)
    private double amount = 0.0;

    @Column(name = "SEARCH_ITEM_IMG")
    private String imageURL;                            // not indexing (default)
}
