package com.fpoly.oe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Videos")
public class Video {
@Id
@Column(name = "Id", length = 50)
private String id ;
@Column(name = "Title ", nullable = false, columnDefinition = "NVARCHAR(255)")
private String title;
@Column(name = "Poster", length = 255)
private String poster;
@Column(name = "Views")
private int views = 0;
@Column(name = "Description", columnDefinition = "NVARCHAR(MAX)")
private String description;
	@Column(name = "Active")
	private boolean active = true;
	
	@jakarta.persistence.Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	@Column(name = "UploadDate")
	private java.util.Date uploadDate = new java.util.Date();

	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "CategoryId")
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getViews() {

	return views;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getPoster() {
	return poster;
}
public void setPoster(String poster) {
	this.poster = poster;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public boolean isActive() {
	return active;
}
public void setActive(boolean active) {
	this.active = active;
}
public void setViews(int views) {
	this.views = views;
}


	
}
