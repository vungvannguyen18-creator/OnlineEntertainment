package com.fpoly.oe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {
	@Id
	@Column(name = "Id", length = 50)
	private String id;
	@Column(name = "Password", length = 50, nullable = false)
	private String password;
	@Column(name = "Email", length = 150, nullable = false, unique = true)
	private String email;
	@Column(name = "Fullname", length = 100, nullable = false, columnDefinition = "NVARCHAR(100)")
	private String fullname;
	@Column(name = "Admin")
	private boolean admin = false;
	@Column(name = "Active")
	private boolean active = true;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    
    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
