package com.literatura.literatura.service;

import com.literatura.literatura.dto.AuthorDTO;
import com.literatura.literatura.dto.BookDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class ApiService {

        private final String API_URL = "https://gutendex.com/books?search=";

    public BookDTO buscarLibro(String titulo) {
        try {
            String url = API_URL + titulo.replace(" ", "%20");
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> respuesta = restTemplate.getForObject(url, Map.class);

            List<Map<String, Object>> results = (List<Map<String, Object>>) respuesta.get("results");
            if (results == null || results.isEmpty()) {
                return null;
            }

            Map<String, Object> primerLibro = results.get(0);

            String tituloLibro = (String) primerLibro.get("title");
            List<String> idiomas = (List<String>) primerLibro.get("languages");
            Integer downloads = (Integer) primerLibro.get("download_count");

            List<Map<String, Object>> autoresList = (List<Map<String, Object>>) primerLibro.get("authors");
            Map<String, Object> autorMap = autoresList.isEmpty() ? null : autoresList.get(0);

            String nombreAutor = autorMap != null ? (String) autorMap.get("name") : "Desconocido";
            Integer nacimiento = autorMap != null ? (Integer) autorMap.get("birth_year") : null;
            Integer muerte = autorMap != null ? (Integer) autorMap.get("death_year") : null;

            AuthorDTO authorDTO = new AuthorDTO(nombreAutor, nacimiento, muerte);

            return new BookDTO(tituloLibro, idiomas, downloads, authorDTO);

        } catch (Exception e) {
            System.out.println("Error al buscar: " + e.getMessage());
            return null;
        }
    }


    private String extraerValor(String json, String clave) {
            int inicio = json.indexOf(clave);
            if (inicio == -1) return null;
            inicio += clave.length();
            int fin = json.indexOf("\"", inicio);
            return json.substring(inicio, fin);
        }
}

