package in.glootech.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "BLOG_DETAILS")
public class BlogDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer blogId;
	private String blogTitle;
	private String blogShort;
	@Lob
	private String blogDesc;
	@Column(updatable = false)
	@CreationTimestamp
	private LocalDate createDate;
	@Column(insertable = false)
	@UpdateTimestamp
	private LocalDate updateDate;
	private Integer softDelete;
	@ManyToOne
	@JoinColumn(name = "userId")
	private UserDetails userId;
	@OneToMany(mappedBy = "blogId",cascade = CascadeType.REMOVE)
	private List<CommentDetails> comments;
	
	
}
