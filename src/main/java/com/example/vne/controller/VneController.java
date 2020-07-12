package com.example.vne.controller;

import com.example.vne.Exception.ResourceNotFoundException;
import com.example.vne.Service.IP360Service;
import com.example.vne.model.Vne;
import com.example.vne.repository.VneRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/Vne")
@Transactional
public class VneController {
    @Autowired
    VneRepository vneRepository;




    @Autowired
    private IP360Service ip360Service;



    @GetMapping("/")
    public Iterable<Vne> getAllVne() {
        return vneRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Vne> findOne(@PathVariable long id) throws ResourceNotFoundException {
        Vne vne = vneRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Vne not found for this id :: " + id));
        return ResponseEntity.ok().body(vne);
    }

    /*
     * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
     */
    private static HttpHeaders getHeaders(){
        String plainCredentials="ip360@tripwire.com:ip360@tripwire.com";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }


    @PostMapping("/")
    public Vne save(@RequestBody Vne vne) throws ResourceNotFoundException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        if(!ip360Service.TestVneConnection(vne)){
            throw new ResourceNotFoundException("Vne Not Exist");
        }

        return vneRepository.save(vne);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vne> updateVne(@PathVariable(value = "id") Long vneId,
                                               @RequestBody Vne vneDetails) throws ResourceNotFoundException {
        Vne vne = vneRepository.findById(vneId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + vneId));
        vne.setHost(vneDetails.getHost());
        vne.setPassword(vneDetails.getPassword());
        vne.setUser(vneDetails.getUser());


        final Vne updatedVne = vneRepository.save(vne);
        return ResponseEntity.ok(updatedVne);
    }


    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteById(@PathVariable long vneId) throws ResourceNotFoundException {

        Vne vne = vneRepository.findById(vneId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + vneId));

        vneRepository.deleteById(vneId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}
