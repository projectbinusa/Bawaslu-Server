package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.UserLoginRequest;
import com.Binusa.BawasluServer.DTO.UserRegistrationRequest;
import com.Binusa.BawasluServer.model.UserModel;
import com.Binusa.BawasluServer.repository.UserRepository;
import com.Binusa.BawasluServer.response.CustomResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; // Anda perlu mengkonfigurasi PasswordEncoder
    private final String secretKey = "Bawaslu82772"; // Ganti dengan kunci rahasia yang lebih kuat

    public boolean registerAdmin(UserRegistrationRequest registrationRequest) {
        // Periksa apakah username sudah ada
        if (userRepository.findByUsername(registrationRequest.getUsername()) != null) {
            return false; // Gagal mendaftar karena username sudah ada
        }

        UserModel user = new UserModel();
        user.setUsername(registrationRequest.getUsername());
        // Enkripsi kata sandi sebelum menyimpannya
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        userRepository.save(user);
        return true; // Berhasil mendaftar
    }

    public CustomResponse loginAdmin(UserLoginRequest loginRequest) {
        CustomResponse response = new CustomResponse();

        UserModel user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // Generate JWT token jika login berhasil
            String token = generateToken(user.getUsername());

            response.setStatus("success");
            response.setCode(200);
            response.setMessage("Login berhasil.");
            response.setData(token); // Mengirim token ke klien
            return response;
        }

        response.setStatus("error");
        response.setCode(401); // Unauthorized
        response.setMessage("Gagal masuk. Periksa kredensial Anda.");
        return response;
    }

    private String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 864000000); // Token berlaku selama 10 hari (misalnya)

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
