package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jypj.zgcsx.common.dto.DtoMenu;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户实体类（不关联数据库，为session存储对象）
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public class UserInfo implements UserDetails {

    private static final long serialVersionUID = 7550272607750820905L;
    private String id;
    private String loginName;
    private String nickName;
    private String phone;
    private String idCard;
    private String userType;
    private String userName;
    private String logoUrl;
    private String roleId;
    private String roleSize;
    private String roleName;
    private List<DtoMenu> menuList;
    /**
     * 数据范围：为null表示具有全部数据范围
     * 为emptyList表示没有数据范围
     * 其他代表班级数组
     */
    private String[] clazzes;

    @JsonIgnore
    @TableField(exist = false)
    private String password;

    @JsonIgnore
    @TableField(exist = false)
    private boolean accountNonExpired = true;

    @JsonIgnore
    @TableField(exist = false)
    private boolean accountNonLocked = true;

    @JsonIgnore
    @TableField(exist = false)
    private boolean credentialsNonExpired = true;

    @JsonIgnore
    @TableField(exist = false)
    private boolean enabled = true;

    private Set<GrantedAuthority> authorities = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> auth = new HashSet<>(authorities);
        if (menuList == null || menuList.size() == 0) {
            return auth;
        }
        for (DtoMenu menu : menuList) {
            if (menu.getUrl() != null) {
                auth.add(new SimpleGrantedAuthority("ROLE_" + menu.getUrl()));
            }
        }
        return auth;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleSize() {
        return roleSize;
    }

    public void setRoleSize(String roleSize) {
        this.roleSize = roleSize;
    }

    public List<DtoMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<DtoMenu> menuList) {
        this.menuList = menuList;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String[] getClazzes() {
        return clazzes;
    }

    public void setClazzes(String[] clazzes) {
        this.clazzes = clazzes;
    }
}
