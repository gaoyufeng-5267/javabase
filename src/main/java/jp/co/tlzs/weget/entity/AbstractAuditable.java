package jp.co.tlzs.weget.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class AbstractAuditable {

//    @Column(name = "is_deleted")
//    private boolean deleted;

//    @Version
//    @Column(name = "record_version")
@Column(nullable=false)
    protected Long version;

//    @CreatedBy
//    @AttributeOverride(name = "name", column = @Column(name = "creator_name"))
//    @AttributeOverride(name = "type", column = @Column(name = "creator_kind"))
@Column(nullable=false)
    private String createUser;

//    @CreatedDate
//    @Column(name = "create_datetime")
@Column(nullable=false)
    private LocalDateTime createTime;

//    @LastModifiedBy
//    @AttributeOverride(name = "name", column = @Column(name = "updater_name"))
//    @AttributeOverride(name = "type", column = @Column(name = "updater_kind"))
@Column(nullable=false)
    private String updateUser;

//    @LastModifiedDate
//    @Column(name = "update_datetime")
@Column(nullable=false)
    private LocalDateTime updateTime;
}
