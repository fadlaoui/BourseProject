package com.projet.security.service;



import com.projet.security.model.SpringSecurityUser;
import com.projet.dao.UserRepository;
import com.projet.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository cmptDAO;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    	User appUser = this.cmptDAO.findByEmail(email);
        //User appUser = new UserParticulier();
        //appUser.setEmail("fadlaoui");
        //appUser.setPassword("$2a$10$aS/lF2c/9JWPUjDHfJ/zTed1ihGBgfX/7xnGTOM5/lW59X4FHalSi");
        if (appUser == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", email));
        } else {
            return new SpringSecurityUser(
                    appUser.getId()+"",
                    appUser.getEmail(),
                    appUser.getPassword(),
                    null,
                    null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN, ROLE_EM PLOYEE, ROLE_MANAGER")
            );
        }
    }

}
