package ar.com.api.ibera.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.com.api.ibera.models.UserRole;
import ar.com.api.ibera.repositories.UserRepository;

@Service("userService")
public class UserService implements UserDetailsService{
	
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ar.com.api.ibera.models.User user = userRepository.findByUsername(username);
		return bulidUser(user,buildAuthorities(user.getUserRole()));
	}
	
	private User bulidUser(ar.com.api.ibera.models.User user, List<GrantedAuthority> authorities){
		return new User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
	}
	
	private List<GrantedAuthority> buildAuthorities(Set<UserRole> userRoles){
		Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
		for (UserRole ur : userRoles) {
			auths.add(new SimpleGrantedAuthority(ur.getRole()));
		}
		return new ArrayList<GrantedAuthority>(auths);
	}

}
