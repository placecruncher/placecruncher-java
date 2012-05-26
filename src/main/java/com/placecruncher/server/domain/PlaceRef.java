package com.placecruncher.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.placecruncher.server.application.Constants;


@Entity
@Table(name=PlaceRef.DB_TABLE)
@SequenceGenerator(name = PlaceRef.DB_SEQ, sequenceName = PlaceRef.DB_SEQ)
public class PlaceRef extends AbstractEntity {

    public static final String DB_TABLE = "PLACE_REF";
    public static final String DB_SEQ = DB_TABLE + "_SEQ";

    private Integer id;
    private Member member;
    private PrivacyEnum privacy = PrivacyEnum.PRIVATE;
    private int rating;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = DB_SEQ)
    @Column(name="ID", nullable=false)
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }


    @Column(name="PRIVACY", nullable=false, length=Constants.ENUM_MAXLEN)
    @Enumerated(EnumType.STRING)
    public PrivacyEnum getPrivacy() {
        return privacy;
    }
    public void setPrivacy(PrivacyEnum privacy) {
        this.privacy = privacy;
    }

    @Transient
    public boolean isPublic() {
        return PrivacyEnum.PUBLIC.equals(privacy);
    }
    @Transient
    public boolean isPrivate() {
        return PrivacyEnum.PRIVATE.equals(privacy);
    }
    @Transient
    public void setPublic() {
        privacy = PrivacyEnum.PUBLIC;
    }
    @Transient
    public void setPrivate() {
        privacy = PrivacyEnum.PRIVATE;
    }

    @Column(name="RATING", nullable=false)
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    protected enum PrivacyEnum {
        PUBLIC,
        PRIVATE
    }

}
