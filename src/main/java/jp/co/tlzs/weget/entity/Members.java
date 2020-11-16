package jp.co.tlzs.weget.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
//@Getter
//@Setter
@Table(name = "mst_members")
public class Members extends  AbstractAuditable{

    @Id
    @Column(nullable=false)
    private String  memberId;
    @Column(nullable=false)
    private String  memberCode       ;
    @Column(nullable=false)
    private String  email             ;
    @Column(nullable=false)
    private String  password          ;
    @Column(nullable=false)
    private String  name            ;
    private String  nameKana   ;
    private Date birthday          ;
    private String  gender            ;
    private String  phoneNumber      ;
    private String  postCodeFront   ;
    private String  postCodeBack    ;
    private String  prefecture       ;
    private String  city             ;
    private String  address        ;
    private boolean  termWasRead    ;
    private String  paymentMemberId ;
    private boolean  leave    ;


}
