package in.glootech.entity;

import java.time.LocalDate;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "COMMENT_DETAILS")
public class CommentDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer commentId;
	private String name;
	private String email;
	private Integer softDelete;
	@Lob
	private String commentText;
	@CreationTimestamp
	private LocalDate createDate;
	@ManyToOne
	@JoinColumn(name = "blogId")
	private BlogDetails blogId;
}
