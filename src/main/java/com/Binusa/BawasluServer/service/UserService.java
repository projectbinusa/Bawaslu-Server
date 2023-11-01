package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.UserLoginRequest;
import com.Binusa.BawasluServer.DTO.UserRegistrationRequest;
import com.Binusa.BawasluServer.model.UserModel;
import com.Binusa.BawasluServer.repository.UserRepository;
import com.Binusa.BawasluServer.response.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; // Anda perlu mengkonfigurasi PasswordEncoder

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
            response.setStatus("success");
            response.setCode(200);
            response.setMessage("Login berhasil.");
            // Hapus data null dari respons
            response.setData(null);
            return response;
        }

        response.setStatus("error");
        response.setCode(401); // Unauthorized
        response.setMessage("Gagal masuk. Periksa kredensial Anda.");
        return response;
    }


}


