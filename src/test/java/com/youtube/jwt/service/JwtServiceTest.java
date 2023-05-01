package com.youtube.jwt.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.youtube.jwt.dao.RoleDao;
import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.JwtRequest;
import com.youtube.jwt.entity.JwtResponse;
import com.youtube.jwt.entity.Role;
import com.youtube.jwt.entity.User;
import com.youtube.jwt.util.JwtUtil;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class JwtServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDao userDao;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private JwtService jwtService;

    private User testUser;
    @Autowired
    private RoleDao roleDao;
    @Before
    public void setup() {
        Role testRole = new Role();
        testRole.setRoleName("testRoleUser");        Set<Role> userRoles = new HashSet<>();
        userRoles.add(testRole);
        User testUser = new User();
        testUser.setUserName("12345678");
        testUser.setUserFirstName("houcem");
        testUser.setRole(userRoles);
        testUser.setUserPassword("testPassword");
        userDao.save(testUser);
    }

    @Test
    public void testAuthenticate() throws Exception {
        String userName = "testUser";
        String userPassword = "testPassword";

        // Test valid authentication
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        jwtService.authenticate(userName, userPassword);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Test disabled user
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new DisabledException("USER_DISABLED"));
        try {
            jwtService.authenticate(userName, userPassword);
        } catch (Exception e) {
            assert (e.getMessage().contains("USER_DISABLED"));
        }
        verify(authenticationManager, times(2)).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Test bad credentials
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new BadCredentialsException("INVALID_CREDENTIALS"));
        try {
            jwtService.authenticate(userName, userPassword);
        } catch (Exception e) {
            assert (e.getMessage().contains("INVALID_CREDENTIALS"));
        }
        verify(authenticationManager, times(3)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }


 /*   @Test
    public void testCreateJwtToken() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUserName("testuser");
        jwtRequest.setUserPassword("testpassword");
        when(userDao.findById(anyString())).thenReturn(Optional.of(testUser));
        when(jwtUtil.generateToken(mock(UserDetails.class))).thenReturn("testtoken");

        JwtResponse jwtResponse = jwtService.createJwtToken(jwtRequest);

        assertEquals(testUser.getUserName(), jwtResponse.getUser().getUserName());
        assertEquals("testtoken", jwtResponse.getJwtToken());
    }*/
/*
    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsernameUserNotFound() {
        when(userDao.findById(anyString())).thenReturn(Optional.empty());

        jwtService.loadUserByUsername("nonexistentuser");
    }

    @Test
    public void testLoadUserByUsername() {
        when(userDao.findById(anyString())).thenReturn(Optional.of(testUser));

        UserDetails userDetails = jwtService.loadUserByUsername("testuser");

        assertEquals(testUser.getUserName(), userDetails.getUsername());
        assertEquals(testUser.getUserPassword(), userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }*/

}
