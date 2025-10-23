package com.ms_reclutador.service;

import com.ms_reclutador.dto.DipomexResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus; // Importación necesaria para el manejo de status
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class DipomexService {

    private final WebClient webClient;
    private final String apiKey;

    public DipomexService(@Value("${dipomex.api.url}") String baseUrl,
                          @Value("${dipomex.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                // 💡 CORRECCIÓN CRÍTICA: Descomentar la línea para enviar la APIKEY en el Header.
                .defaultHeader("APIKEY", apiKey)
                .build();
    }

    /**
     * Consulta el código postal en la API de DIPOMEX de forma segura.
     * @param cp Código Postal a buscar.
     * @return Objeto DTO con la información de ubicación.
     */
    public DipomexResponseDTO buscarCodigoPostal(String cp) {
        if (cp == null || cp.isEmpty()) {
            throw new IllegalArgumentException("El código postal no puede ser nulo o vacío");
        }

        // Configuración de la URL para el endpoint /codigo_postal
        Mono<DipomexResponseDTO> responseMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/codigo_postal")
                        .queryParam("cp", cp)
                        .build())
                .retrieve()
                // Usar lambda status -> status.is... para HttpStatusCode
                .onStatus(status -> status.is4xxClientError(), response ->
                        Mono.error(new RuntimeException("Error del cliente al consultar DIPOMEX: " + response.statusCode()))
                )
                // Usar lambda status -> status.is... para HttpStatusCode
                .onStatus(status -> status.is5xxServerError(), response ->
                        Mono.error(new RuntimeException("Error del servidor de DIPOMEX: " + response.statusCode()))
                )
                .bodyToMono(DipomexResponseDTO.class);

        // Bloqueo la respuesta de forma síncrona
        DipomexResponseDTO response = responseMono.block();

        // Manejo de errores específicos de la API (si devuelve error=true)
        if (response != null && response.isError()) {
            throw new RuntimeException("Error en la respuesta de DIPOMEX: " + response.getMessage());
        }

        return response;
    }
}
