package com.microshophub.userservice;

import com.microshophub.userservice.model.User;
import com.microshophub.userservice.repository.UserRepository;
import com.microshophub.userservice.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Méthode pour enregistrer un nouvel utilisateur.
     * @param username nom d'utilisateur
     * @param rawPassword mot de passe en clair
     * @return un jeton JWT pour l'utilisateur nouvellement créé
     */
    @Transactional
    public String registerUser(String username, String rawPassword) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Cet utilisateur existe déjà");
        }

        // Encoder le mot de passe
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // Créer et sauvegarder l'utilisateur
        User user = new User(username, encodedPassword);
        userRepository.save(user);

        // Générer le jeton JWT
        String jwtToken = jwtTokenProvider.generateToken(user);

        return jwtToken;
    }

    /**
     * Méthode pour authentifier un utilisateur.
     * @param username nom d'utilisateur
     * @param rawPassword mot de passe en clair
     * @return un jeton JWT pour l'utilisateur authentifié
     */
    public String authenticateUser(String username, String rawPassword) {
        // Récupérer l'utilisateur
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé ou mot de passe incorrect"));

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Utilisateur non trouvé ou mot de passe incorrect");
        }

        // Générer le jeton JWT
        String jwtToken = jwtTokenProvider.generateToken(user);

        return jwtToken;
    }

    /**
     * Méthode pour retrouver un utilisateur par son nom d'utilisateur.
     * @param username nom d'utilisateur
     */
    public User findById(String id) {
        return userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
    }
}